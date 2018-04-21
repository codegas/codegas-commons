package org.codegas.commons.lang.spacial;

import java.util.Objects;

import javax.json.Json;
import javax.json.JsonValue;

import org.codegas.commons.lang.compare.Comparison;
import org.codegas.commons.ende.api.JsonValueDecoder;

public final class Point {

    private static final int HASH_PRECISION = 1000000;

    private static final double DOUBLE_EQUALITY_THRESHOLD = .000000000001d;

    private static final JsonDecoder JSON_DECODER = new JsonDecoder();

    private double x;

    private double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    protected Point() {

    }

    public static Point fromString(String json) {
        return jsonDecoder().decode(json);
    }

    public static JsonValueDecoder<Point> jsonDecoder() {
        return JSON_DECODER;
    }

    public static double determinant(Point p, Point q, Point r) {
        return (q.getX() - p.getX()) * (r.getY() - q.getY()) - (q.getY() - p.getY()) * (r.getX() - q.getX());
    }

    public double distanceTo(Point point) {
        // NOTE: I am purposefully not using Math.hypot(...). It is surprisingly slower than this manual computation.
        return Math.sqrt(Math.pow(point.x - x, 2d) + Math.pow(point.y - y, 2d));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || !getClass().equals(o.getClass())) {
            return false;
        }

        Point point = (Point) o;
        return Comparison.areValuesClose(this.x, point.x, DOUBLE_EQUALITY_THRESHOLD) &&
            Comparison.areValuesClose(this.y, point.y, DOUBLE_EQUALITY_THRESHOLD);
    }

    @Override
    public int hashCode() {
        return Objects.hash((int) (x * HASH_PRECISION), (int) (y * HASH_PRECISION));
    }

    @Override
    public String toString() {
        return createValue().toString();
    }

    public JsonValue createValue() {
        return JSON_DECODER.toValue(this);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    private static final class JsonDecoder implements JsonValueDecoder<Point> {

        @Override
        public Point decode(JsonValue jsonValue) {
            return JsonValueDecoder.asObject()
                .andThen(pointObject -> new Point(pointObject.getJsonNumber("x").doubleValue(),
                    pointObject.getJsonNumber("y").doubleValue()))
                .apply(jsonValue);
        }

        protected JsonValue toValue(Point point) {
            return Json.createObjectBuilder()
                .add("x", point.getX())
                .add("y", point.getY())
                .build();
        }
    }
}
