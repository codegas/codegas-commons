package org.codegas.commons.lang.value;

import java.util.Objects;

public final class PrincipalName extends AbstractId {

    private String value;

    public static PrincipalName fromString(String string) {
        return string == null ? null : new PrincipalName(string);
    }

    public PrincipalName(String value) {
        this.value = Objects.requireNonNull(value);
    }

    protected PrincipalName() {

    }

    @Override
    public Object idHash() {
        return value;
    }
}
