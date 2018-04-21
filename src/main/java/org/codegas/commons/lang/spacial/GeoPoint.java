package org.codegas.commons.lang.spacial;

import java.util.Objects;

import javax.json.Json;
import javax.json.JsonValue;

import org.codegas.commons.ende.api.JsonValueDecoder;

public final class GeoPoint {

    private static final JsonDecoder JSON_DECODER = new JsonDecoder();

    private double lat;

    private double lng;

    public GeoPoint(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    protected GeoPoint() {

    }

    public static GeoPoint fromString(String json) {
        return jsonDecoder().decode(json);
    }

    public static JsonValueDecoder<GeoPoint> jsonDecoder() {
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

        GeoPoint geoPoint = (GeoPoint) o;
        return this.lat == geoPoint.lat &&
            this.lng == geoPoint.lng;
    }

    @Override
    public int hashCode() {
        return Objects.hash(lat, lng);
    }

    @Override
    public String toString() {
        return createValue().toString();
    }

    public JsonValue createValue() {
        return JSON_DECODER.toValue(this);
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

    private static final class JsonDecoder implements JsonValueDecoder<GeoPoint> {

        @Override
        public GeoPoint decode(JsonValue jsonValue) {
            return JsonValueDecoder.asObject()
                .andThen(geoPointObject -> new GeoPoint(geoPointObject.getJsonNumber("lat").doubleValue(),
                    geoPointObject.getJsonNumber("lng").doubleValue()))
                .applyNullSafe(jsonValue);
        }

        protected JsonValue toValue(GeoPoint geoPoint) {
            return Json.createObjectBuilder()
                .add("lat", geoPoint.getLat())
                .add("lng", geoPoint.getLng())
                .build();
        }
    }
}
