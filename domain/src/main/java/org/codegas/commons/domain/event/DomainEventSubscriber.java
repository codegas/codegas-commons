package org.codegas.commons.domain.event;

public interface DomainEventSubscriber<T extends DomainEvent> {

    Class<T> getSubscribedEventType();

    void handleEvent(T domainEvent);
}
