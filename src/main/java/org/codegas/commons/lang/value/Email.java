package org.codegas.commons.lang.value;

import org.apache.commons.validator.routines.EmailValidator;

public final class Email extends AbstractId {

    private static final EmailValidator EMAIL_VALIDATOR = EmailValidator.getInstance(true, true);

    private String value;

    public static Email fromString(String string) {
        return string == null ? null : new Email(string);
    }

    public Email(String value) {
        this.value = validate(value);
    }

    protected Email() {

    }

    public static String validate(String value) {
        if (EMAIL_VALIDATOR.isValid(value)) {
            return value;
        } else {
            throw new IllegalArgumentException("This is not a valid E-Mail Address: " + value);
        }
    }

    @Override
    public Object idHash() {
        return value;
    }

    public String getValue() {
        return value;
    }
}
