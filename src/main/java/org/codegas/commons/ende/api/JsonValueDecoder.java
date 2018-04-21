package org.codegas.commons.ende.api;

import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.util.stream.Stream;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonValue;

@FunctionalInterface
public interface JsonValueDecoder<T> extends Decoder<JsonValue, T> {

    static JsonValueDecoder<JsonValue> extractValue(String key) {
        return jsonValue -> asObject().andThen(jsonObject -> jsonObject.get(key)).apply(jsonValue);
    }

    static JsonValueDecoder<Stream<JsonValue>> toValueStream() {
        return jsonValue -> asArray().apply(jsonValue).stream();
    }

    static JsonValueDecoder<JsonObject> asObject() {
        return JsonObject.class::cast;
    }

    static JsonValueDecoder<JsonArray> asArray() {
        return JsonArray.class::cast;
    }

    default T decode(String json) {
        return json == null ? null : decode(new StringReader(json));
    }

    default T decode(Reader reader) {
        return decode(Json.createReader(reader).read());
    }

    default T decode(InputStream inputStream) {
        return decode(Json.createReader(inputStream).read());
    }
}
