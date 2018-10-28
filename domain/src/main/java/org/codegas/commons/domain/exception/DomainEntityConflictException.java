package org.codegas.commons.domain.exception;

public class DomainEntityConflictException extends RuntimeException {

    public DomainEntityConflictException(String message) {
        super(message);
    }
}
