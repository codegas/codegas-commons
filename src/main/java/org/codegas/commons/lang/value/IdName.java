package org.codegas.commons.lang.value;

import java.util.Objects;

import javax.json.Json;
import javax.json.JsonValue;

import org.codegas.commons.ende.api.JsonValueDecoder;

public final class IdName {

    private static final JsonDecoder JSON_DECODER = new JsonDecoder();

    private String id;

    private String name;

    public IdName(String id, String name) {
        this.id = id;
        this.name = name;
    }

    protected IdName() {

    }

    public static IdName fromString(String json) {
        return jsonDecoder().decode(json);
    }

    public static JsonValueDecoder<IdName> jsonDecoder() {
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

        IdName idName = (IdName) o;
        return Objects.equals(this.id, idName.id) &&
            Objects.equals(this.name, idName.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return createValue().toString();
    }

    public JsonValue createValue() {
        return JSON_DECODER.toValue(this);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    private static final class JsonDecoder implements JsonValueDecoder<IdName> {

        @Override
        public IdName decode(JsonValue jsonValue) {
            return JsonValueDecoder.asObject()
                .andThen(idNameObject -> new IdName(idNameObject.getString("id"), idNameObject.getString("name")))
                .apply(jsonValue);
        }

        protected JsonValue toValue(IdName idName) {
            return Json.createObjectBuilder()
                .add("id", idName.getId())
                .add("name", idName.getName())
                .build();
        }
    }
}
