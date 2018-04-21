package org.codegas.commons.lang.spacial;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonValue;

import org.codegas.commons.lang.collection.CollectionUtil;
import org.codegas.commons.ende.api.JsonValueDecoder;

public final class Polygon {

    private static final int RANDOM_COORD_GENERATION_RANGE = 16;

    private static final JsonDecoder JSON_DECODER = new JsonDecoder();

    private List<Point> vertices;

    public Polygon(List<Point> vertices) {
        this.vertices = validateVertices(vertices);
    }

    protected Polygon() {

    }

    public static Polygon fromString(String json) {
        return jsonDecoder().decode(json);
    }

    public static JsonValueDecoder<Polygon> jsonDecoder() {
        return JSON_DECODER;
    }

    public boolean areVerticesAdjacent(Point vertex1, Point vertex2) {
        int vertex1Index = vertices.indexOf(vertex1);
        int previousVertexIndex = vertex1Index > 0 ? vertex1Index - 1 : vertices.size() - 1;
        int nextVertexIndex = vertex1Index < (vertices.size() - 1) ? vertex1Index + 1 : 0;
        return vertex1Index >= 0 && (vertices.get(previousVertexIndex).equals(vertex2) || vertices.get(nextVertexIndex).equals(vertex2));
    }

    // "Does this Line Segment affect the shape of this polygon from both the inside and out?"
    public boolean isIntersectedBy(LineSegment lineSegment) {
        return (!isVertex(lineSegment.getPoint1()) && !isVertex(lineSegment.getPoint2()) && contains(lineSegment.getPoint1()) != contains(lineSegment.getPoint2())) ||
            getSegments().stream().anyMatch(lineSegment::intersectsExclusive);
    }

    // "Can this Point only be reached by going 'through' this Polygon?"
    public boolean contains(Point point) {
        LineSegment outerSegment = generateOuterNonVertexContainingSegmentWithPoint(point);
        List<LineSegment> segments = getSegments();
        return segments.stream().noneMatch(segment -> segment.containsInclusive(point)) &&
            segments.stream().filter(outerSegment::intersectsExclusive).count() % 2 == 1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || !getClass().equals(o.getClass())) {
            return false;
        }

        Polygon polygon = (Polygon) o;
        return vertices.size() == polygon.vertices.size() &&
            Collections.indexOfSubList(doubleVertices(), polygon.vertices) >= 0;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        for (Point point : vertices) {
            hash ^= point.hashCode();
        }
        return hash;
    }

    @Override
    public String toString() {
        return createValue().toString();
    }

    public JsonValue createValue() {
        return JSON_DECODER.toValue(this);
    }

    public List<LineSegment> getSegments() {
        return IntStream.range(0, vertices.size())
            .mapToObj(i -> new LineSegment(vertices.get(i), vertices.get((i + 1) % vertices.size())))
            .collect(Collectors.toList());
    }

    public List<Point> getVertices() {
        return vertices;
    }

    private List<Point> validateVertices(List<Point> vertices) {
        if (vertices == null) {
            throw new IllegalArgumentException("Vertices for Polygon must not be null.");
        } else if (vertices.size() < 3) {
            throw new IllegalArgumentException("Number of vertices for Polygon must be at least 3.");
        } else {
            return vertices;
        }
    }

    private boolean isVertex(Point point) {
        return vertices.stream().anyMatch(point::equals);
    }

    private LineSegment generateOuterNonVertexContainingSegmentWithPoint(Point point) {
        Point nonInnerPoint = vertices.stream().reduce(vertices.get(0), maxPoint());
        LineSegment lineSegment;
        do {
            double xCoordOffset = randomCoordOffset();
            double yCoordOffset = randomCoordOffset(xCoordOffset <= 0d);
            lineSegment = new LineSegment(point, new Point(nonInnerPoint.getX() + xCoordOffset, nonInnerPoint.getY() + yCoordOffset));
        } while (vertices.stream().anyMatch(lineSegment::containsExclusive));
        return lineSegment;
    }

    private BinaryOperator<Point> maxPoint() {
        return (point1, point2) -> new Point(Math.max(point1.getX(), point2.getX()), Math.max(point1.getY(), point2.getY()));
    }

    private double randomCoordOffset(boolean forcePositive) {
        return forcePositive ? ((Math.random() * RANDOM_COORD_GENERATION_RANGE) / 2) + 1 : randomCoordOffset();
    }

    private double randomCoordOffset() {
        double random = (Math.random() - 0.5d) * RANDOM_COORD_GENERATION_RANGE;
        return random >= 0d ? random + 1 : random;
    }

    private List<Point> doubleVertices() {
        return CollectionUtil.concat(vertices, vertices);
    }

    private static final class JsonDecoder implements JsonValueDecoder<Polygon> {

        @Override
        public Polygon decode(JsonValue jsonValue) {
            return JsonValueDecoder.extractValue("vertices")
                .andThen(JsonValueDecoder.toValueStream())
                .andThen(stream -> stream.map(pointValue -> Point.jsonDecoder().decode(pointValue)).collect(Collectors.toList()))
                .andThen(Polygon::new)
                .apply(jsonValue);
        }

        protected JsonValue toValue(Polygon polygon) {
            return Json.createObjectBuilder()
                .add("vertices", createGeoPointArray(polygon.getVertices()))
                .build();
        }

        private static JsonArray createGeoPointArray(Collection<Point> points) {
            JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
            points.forEach(point -> arrayBuilder.add(point.createValue()));
            return arrayBuilder.build();
        }
    }
}
