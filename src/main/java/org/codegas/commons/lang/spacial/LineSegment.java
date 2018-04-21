package org.codegas.commons.lang.spacial;

import java.util.Objects;

import javax.json.Json;
import javax.json.JsonValue;

import org.codegas.commons.ende.api.JsonValueDecoder;

public final class LineSegment {

    private enum Orientation {CLOCKWISE, COUNTERCLOCKWISE, COLINEAR}

    private static final double DOUBLE_EQUALITY_THRESHOLD = .000000000001d;

    private static final Decoder DECODER = new Decoder();

    private Point point1;

    private Point point2;

    public LineSegment(Point point1, Point point2) {
        this.point1 = point1;
        this.point2 = point2;
    }

    protected LineSegment() {

    }

    // Line Segment "bisects" this
    public boolean intersectsExclusive(LineSegment lineSegment) {
        Orientation o1 = calculateOrientation(point1, point2, lineSegment.point1);
        Orientation o2 = calculateOrientation(point1, point2, lineSegment.point2);
        Orientation o3 = calculateOrientation(lineSegment.point1, lineSegment.point2, point1);
        Orientation o4 = calculateOrientation(lineSegment.point1, lineSegment.point2, point2);
        return !areAnyColinear(o1, o2, o3, o4) && o1 != o2 && o3 != o4;
    }

    // Line Segment "touches" this
    public boolean intersectsInclusive(LineSegment lineSegment) {
        Orientation o1 = calculateOrientation(point1, point2, lineSegment.point1);
        Orientation o2 = calculateOrientation(point1, point2, lineSegment.point2);
        Orientation o3 = calculateOrientation(lineSegment.point1, lineSegment.point2, point1);
        Orientation o4 = calculateOrientation(lineSegment.point1, lineSegment.point2, point2);
        return isOverlap(lineSegment, o1, o2) || (o1 != o2 && o3 != o4);
    }

    public boolean containsExclusive(Point point) {
        return !point1.equals(point) && !point2.equals(point) && containsInclusive(point);
    }

    public boolean containsInclusive(Point point) {
        return point1.distanceTo(point) + point.distanceTo(point2) - point1.distanceTo(point2) <= DOUBLE_EQUALITY_THRESHOLD;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || !getClass().equals(o.getClass())) {
            return false;
        }

        LineSegment lineSegment = (LineSegment) o;
        return (Objects.equals(point1, lineSegment.point1) && Objects.equals(point2, lineSegment.point2)) ||
            (Objects.equals(point1, lineSegment.point2) && Objects.equals(point2, lineSegment.point1));
    }

    @Override
    public int hashCode() {
        return point1.hashCode() ^ point2.hashCode();
    }

    @Override
    public String toString() {
        return createValue().toString();
    }

    public JsonValue createValue() {
        return DECODER.toValue(this);
    }

    public Point getPoint1() {
        return point1;
    }

    public Point getPoint2() {
        return point2;
    }

    private static Orientation calculateOrientation(Point p, Point q, Point r) {
        double determinant = Point.determinant(p, q, r);
        if (Math.abs(determinant) <= DOUBLE_EQUALITY_THRESHOLD) {
            return Orientation.COLINEAR;
        } else {
            return determinant > 0 ? Orientation.COUNTERCLOCKWISE : Orientation.CLOCKWISE;
        }
    }

    private boolean isOverlap(LineSegment lineSegment, Orientation o1, Orientation o2) {
        return o1 == Orientation.COLINEAR && o2 == Orientation.COLINEAR &&
            (lineSegment.containsInclusive(point1) || lineSegment.containsInclusive(point2) || containsInclusive(lineSegment.point1) || containsInclusive(lineSegment.point2));
    }

    private boolean areAnyColinear(Orientation o1, Orientation o2, Orientation o3, Orientation o4) {
        return o1 == Orientation.COLINEAR || o2 == Orientation.COLINEAR || o3 == Orientation.COLINEAR || o4 == Orientation.COLINEAR;
    }

    private static final class Decoder implements JsonValueDecoder<LineSegment> {

        @Override
        public LineSegment decode(JsonValue jsonValue) {
            return JsonValueDecoder.asObject()
                .andThen(jsonObject -> new LineSegment(Point.jsonDecoder().decode(jsonObject.get("point1")),
                    Point.jsonDecoder().decode(jsonObject.get("point2"))))
                .apply(jsonValue);
        }

        protected JsonValue toValue(LineSegment lineSegment) {
            return Json.createObjectBuilder()
                .add("point1", lineSegment.point1.createValue())
                .add("point2", lineSegment.point2.createValue())
                .build();
        }
    }
}
