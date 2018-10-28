package org.codegas.commons.jpa.domain.persistence;

import java.lang.reflect.ParameterizedType;
import java.util.Optional;
import java.util.stream.Stream;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.codegas.commons.domain.entity.DomainEntity;
import org.codegas.commons.domain.persistence.Repository;
import org.codegas.commons.lang.value.Id;

public abstract class RepositoryImpl<T extends DomainEntity> implements Repository<T> {

    @PersistenceContext
    protected EntityManager entityManager;

    protected final Class<T> entityClass;

    public RepositoryImpl() {
        entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    @Override
    public T add(T t) {
        entityManager.persist(t);
        return t;
    }

    @Override
    public T remove(T t) {
        entityManager.remove(t);
        return t;
    }

    @Override
    public Optional<T> find(Id id) {
        return Optional.ofNullable(entityManager.find(entityClass, id.toJpaQueryObject()));
    }

    @Override
    public Stream<T> get() {
        return entityManager.createQuery(" SELECT entity" +
                " FROM " + entityClass.getSimpleName() + " entity",
            entityClass)
            .getResultStream();
    }
}
