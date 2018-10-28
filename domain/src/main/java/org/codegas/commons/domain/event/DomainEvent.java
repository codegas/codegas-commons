package org.codegas.commons.domain.event;

import java.time.LocalDateTime;

public interface DomainEvent {

    LocalDateTime occurredOn();
}
