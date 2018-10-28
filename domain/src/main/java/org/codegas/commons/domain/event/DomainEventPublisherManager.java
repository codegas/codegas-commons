package org.codegas.commons.domain.event;

public class DomainEventPublisherManager {

    private static final ThreadLocal<Integer> LOCAL_CONTEXT_COUNT = ThreadLocal.withInitial(() -> 0);

    public void enterPublishingContext() {
        int count = LOCAL_CONTEXT_COUNT.get();
        if (count == 0) {
            DomainEventPublisher.instance().reset();
        }
        LOCAL_CONTEXT_COUNT.set(count + 1);
    }

    public void exitPublishingContext() {
        int count = LOCAL_CONTEXT_COUNT.get() - 1;
        if (count == 0) {
            DomainEventPublisher.instance().reset();
        }
        LOCAL_CONTEXT_COUNT.set(count);
    }
}
