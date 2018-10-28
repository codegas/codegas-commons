package org.codegas.commons.lang.value;

import java.security.Principal;
import java.util.Objects;

public final class PrincipalName extends AbstractId {

    private String value;

    public static PrincipalName fromString(String string) {
        return string == null ? null : new PrincipalName(string);
    }

    public static PrincipalName fromPrincipal(Principal principal) {
        return principal == null ? null : new PrincipalName(principal.getName());
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
