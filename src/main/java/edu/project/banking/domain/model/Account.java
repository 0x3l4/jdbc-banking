package edu.project.banking.domain.model;

public class Account {
    private final Long id;

    private final Long ownerId;

    private final String accountNumber;

    private final Money balance;

    private final AccountStatus status;

    public Account(Long id, Long ownerId, String accountNumber, Money balance, AccountStatus status) {
        this.id = id;
        this.ownerId = ownerId;
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.status = status;
    }
}
