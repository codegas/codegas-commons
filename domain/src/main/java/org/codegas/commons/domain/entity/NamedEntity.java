package org.codegas.commons.domain.entity;

import org.codegas.commons.lang.value.Id;

import javax.persistence.Basic;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class NamedEntity<T extends Id> extends DomainEntity<T> {

    @Basic(optional = false)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || (name = name.trim()).isEmpty()) {
            throw new IllegalArgumentException("Entity name must not be null/empty: " + name);
        }
        this.name = name;
    }
}
