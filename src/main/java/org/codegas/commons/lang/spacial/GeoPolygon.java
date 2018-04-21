package org.codegas.commons.lang.spacial;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonValue;

import org.codegas.commons.ende.api.JsonValueDecoder;

public final class GeoPolygon {

    private static final JsonDecoder JSON_DECODER = new JsonDecoder();

    private List<GeoPoint> vertices;

    public GeoPolygon(List<GeoPoint> vertices) {
        this.vertices = vertices;
    }

    protected GeoPolygon() {

    }

    public static GeoPolygon fromString(String json) {
        return jsonDecoder().decode(json);
    }

    public static JsonValueDecoder<GeoPolygon> jsonDecoder() {
        return JSON_DECODER;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || !getClass().equals(o.getClass())) {
            return false;
        }

        GeoPolygon geoPolygon = (GeoPolygon) o;
        return vertices.size() == geoPolygon.vertices.size() &&
            Collections.indexOfSubList(doubleVertices(), geoPolygon.vertices) >= 0;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        for (GeoPoint point : vertices) {
            hash ^= point.hashCode();
        }
        return hash;
    }

    public String toString() {
        return createValue().toString();
    }

    public JsonValue createValue() {
        return JSON_DECODER.toValue(this);
    }

    public List<GeoPoint> getVertices() {
        return vertices;
    }

    private List<GeoPoint> doubleVertices() {
        List<GeoPoint> doubledVertices = new ArrayList<>(vertices.size() * 2);
        doubledVertices.addAll(vertices);
        doubledVertices.addAll(vertices);
        return doubledVertices;
    }

    private static final class JsonDecoder implements JsonValueDecoder<GeoPolygon> {

        @Override
        public GeoPolygon decode(JsonValue jsonValue) {
            return JsonValueDecoder.extractValue("vertices")
                .andThen(JsonValueDecoder.toValueStream())
                .andThen(stream -> stream.map(geoPointValue -> GeoPoint.jsonDecoder().decode(geoPointValue)).collect(Collectors.toList()))
                .andThen(GeoPolygon::new)
                .apply(jsonValue);
        }

        protected JsonValue toValue(GeoPolygon geoPolygon) {
            return Json.createObjectBuilder()
                .add("vertices", createGeoPointArray(geoPolygon.getVertices()))
                .build();
        }

        private static JsonArray createGeoPointArray(Collection<GeoPoint> geoPoints) {
            JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
            geoPoints.forEach(geoPoint -> arrayBuilder.add(geoPoint.createValue()));
            return arrayBuilder.build();
        }
    }
}
