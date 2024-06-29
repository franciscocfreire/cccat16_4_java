package br.com.freire.uber.infrastructure.repository;

import br.com.freire.uber.domain.Account;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class AccountRepositoryMemory implements AccountRepository {

    Map<String, Account> mapAccountByAccountId;
    Map<String, Account> mapAccountByAccountEmail;

    public AccountRepositoryMemory() {
        this.mapAccountByAccountId = new HashMap<>();
        this.mapAccountByAccountEmail = new HashMap<>();
    }

    @Override
    public Optional<Account> getAccountByEmail(String email) {
        return Optional.ofNullable(this.mapAccountByAccountEmail.get(email));
    }

    @Override
    public Optional<Account> getAccountById(UUID accountId) {
        return Optional.ofNullable(this.mapAccountByAccountId.get(accountId.toString()));
    }

    @Override
    public UUID saveAccount(Account account) {
        this.mapAccountByAccountId.put(account.getAccountId(), account);
        this.mapAccountByAccountEmail.put(account.getEmail(), account);
        return UUID.fromString(account.getAccountId());
    }
}
