package edu.project.banking.domain.model;

import java.time.Instant;
import java.util.UUID;

public class Transfer {
    private final UUID id;

    private final Long initiatedBy;

    private final Long fromAccountId;

    private final Long toAccountId;

    private final Money amount;

    private final UUID idempotencyKey;

    private final Instant createdAt;

    public Transfer(UUID id, Long initiatedBy, Long fromAccountId, Long toAccountId, Money amount, UUID idempotencyKey, Instant createdAt) {
        this.id = id;
        this.initiatedBy = initiatedBy;
        this.fromAccountId = fromAccountId;
        this.toAccountId = toAccountId;
        this.amount = amount;
        this.idempotencyKey = idempotencyKey;
        this.createdAt = createdAt;
    }
}
