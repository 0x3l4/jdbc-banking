package edu.project.banking.domain.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public class LedgerEntry {
    private final long id;

    private final UUID transferId;

    private final long accountId;

    private final BigDecimal amount;

    private final Instant createdAt;

    public LedgerEntry(long id, UUID transferId, long accountId, BigDecimal amount, Instant createdAt) {
        this.id = id;
        this.transferId = transferId;
        this.accountId = accountId;
        this.amount = amount;
        this.createdAt = createdAt;
    }
}
