package edu.project.banking.application.repository;

import edu.project.banking.domain.model.Account;

import java.util.List;
import java.util.Optional;

public interface AccountRepository {
    Optional<Account> findByIdForUpdate(long accountId);

    List<Account> findByOwnerId(long ownerId);


}
