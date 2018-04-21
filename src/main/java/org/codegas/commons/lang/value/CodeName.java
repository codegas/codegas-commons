package org.codegas.commons.lang.value;

import java.util.Objects;

import javax.json.Json;
import javax.json.JsonValue;

import org.codegas.commons.ende.api.JsonValueDecoder;

public final class CodeName {

    private static final JsonDecoder JSON_DECODER = new JsonDecoder();

    private String code;

    private String name;

    public CodeName(String code, String name) {
        this.code = code;
        this.name = name;
    }

    protected CodeName() {

    }

    public static CodeName fromString(String json) {
        return jsonDecoder().decode(json);
    }

    public static JsonValueDecoder<CodeName> jsonDecoder() {
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

        CodeName idName = (CodeName) o;
        return Objects.equals(this.code, idName.code) &&
            Objects.equals(this.name, idName.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, name);
    }

    @Override
    public String toString() {
        return createValue().toString();
    }

    public JsonValue createValue() {
        return JSON_DECODER.toValue(this);
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    private static final class JsonDecoder implements JsonValueDecoder<CodeName> {

        @Override
        public CodeName decode(JsonValue jsonValue) {
            return JsonValueDecoder.asObject()
                .andThen(codeNameObject -> new CodeName(codeNameObject.getString("code"),
                    codeNameObject.getString("name")))
                .apply(jsonValue);
        }

        protected JsonValue toValue(CodeName codeName) {
            return Json.createObjectBuilder()
                .add("code", codeName.getCode())
                .add("name", codeName.getName())
                .build();
        }
    }
}
