package org.codegas.commons.domain.entity;

import org.codegas.commons.lang.value.Id;

import java.util.Objects;

public abstract class DomainEntity<T extends Id> {

    @Override
    public boolean equals(Object object) {
        return object != null &&
            getClass().equals(object.getClass()) &&
            Objects.equals(getId(), DomainEntity.class.cast(object).getId());
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }

    public abstract T getId();
}
