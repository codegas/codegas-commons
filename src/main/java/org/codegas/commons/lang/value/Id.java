package org.codegas.commons.lang.value;

import java.io.Serializable;

import javax.persistence.Embeddable;

public interface Id extends Serializable {

    Object idHash();

    default Object toJpaQueryObject() {
        return getClass().isAnnotationPresent(Embeddable.class) ? this : toString();
    }
}
