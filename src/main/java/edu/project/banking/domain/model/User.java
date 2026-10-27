package edu.project.banking.domain.model;

import java.time.Instant;

public class User {
    private final Long id;

    private final String email;

    private final String passwordHash;

    private final Instant createdAt;

    public User(Long id, String email, String passwordHash, Instant createdAt) {
        this.id = id;
        this.email = email;
        this.passwordHash = passwordHash;
        this.createdAt = createdAt;
    }
}
