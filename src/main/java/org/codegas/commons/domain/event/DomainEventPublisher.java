package org.codegas.commons.domain.event;

import java.util.ArrayList;
import java.util.List;

public class DomainEventPublisher {

    private static final ThreadLocal<DomainEventPublisher> LOCAL_PUBLISHER = ThreadLocal.withInitial(DomainEventPublisher::new);

    private final List<DomainEventSubscriber> subscribers = new ArrayList<>();

    private boolean publishing;

    private DomainEventPublisher() {

    }

    public static DomainEventPublisher instance() {
        return LOCAL_PUBLISHER.get();
    }

    public void reset() {
        if (!publishing) {
            subscribers.clear();
        }
    }

    public <T extends DomainEvent> void subscribe(DomainEventSubscriber<T> subscriber) {
        if (!publishing) {
            subscribers.add(subscriber);
        }
    }

    public <T extends DomainEvent> void publish(T domainEvent) {
        if (!publishing && !subscribers.isEmpty()) {
            try {
                publishing = true;
                for (DomainEventSubscriber subscriber : subscribers) {
                    Class<?> subscribedEventType = subscriber.getSubscribedEventType();
                    if (subscribedEventType.isAssignableFrom(domainEvent.getClass())) {
                        subscriber.handleEvent(domainEvent);
                    }
                }
            } finally {
                publishing = false;
            }
        }
    }
}
