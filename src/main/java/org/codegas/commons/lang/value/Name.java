package org.codegas.commons.lang.value;

import java.util.Objects;

public final class Name extends AbstractId {

    private String value;

    public static Name fromString(String string) {
        return string == null ? null : new Name(string);
    }

    public Name(String value) {
        this.value = Objects.requireNonNull(value);
    }

    protected Name() {

    }

    @Override
    public Object idHash() {
        return value;
    }
}
