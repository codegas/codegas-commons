package org.codegas.commons.lang.value;

import java.util.Objects;
import java.util.UUID;

public abstract class AbstractId implements Id {

    @Override
    public boolean equals(Object object) {
        return object != null &&
            getClass().equals(object.getClass()) &&
            Objects.equals(idHash(), Id.class.cast(object).idHash());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(idHash());
    }

    @Override
    public String toString() {
        return Objects.toString(idHash());
    }

    protected static String generateValue() {
        return UUID.randomUUID().toString();
    }
}
