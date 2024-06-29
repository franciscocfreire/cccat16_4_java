package br.com.freire.uber.application.usecase;

import br.com.freire.uber.domain.Account;
import br.com.freire.uber.infrastructure.repository.AccountRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class GetAccount {
    private final AccountRepository accountRepository;

    public GetAccount(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public OutputGetAccount getAccount(UUID accountId) {
        Account account = accountRepository.getAccountById(accountId).map(result -> Account.restore(
                        UUID.fromString(result.getAccountId()),
                        result.getName(),
                        result.getEmail(),
                        result.getCpf(),
                        result.getCarPlate(),
                        result.isPassenger(),
                        result.isDriver()))
                .orElse(null);
        if (account != null) {
            return new OutputGetAccount(account.getAccountId(), account.getName(), account.getEmail(), account.getCpf(), account.getCarPlate(), account.isPassenger(), account.isDriver());
        }
        return null;
    }

    public record OutputGetAccount(
            String accountId,
            String name,
            String email,
            String cpf,
            String carPlate,
            boolean isPassenger,
            boolean isDriver) {
    }
}
