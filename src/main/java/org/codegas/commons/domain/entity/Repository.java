package org.codegas.commons.domain.entity;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Stream;

import org.codegas.commons.lang.value.Id;

public interface Repository<T extends DomainEntity> {

    default T get(Id id) {
        return find(id).orElseThrow(NoSuchElementException::new);
    }

    default Optional<T> remove(Id id) {
        return find(id).map(this::remove);
    }

    T add(T t);

    T remove(T t);

    Optional<T> find(Id id);

    Stream<T> get();
}
