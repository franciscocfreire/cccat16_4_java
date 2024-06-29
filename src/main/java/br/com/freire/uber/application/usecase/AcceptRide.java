package br.com.freire.uber.application.usecase;

import br.com.freire.uber.domain.Ride;
import br.com.freire.uber.domain.exceptions.ValidationError;
import br.com.freire.uber.infrastructure.repository.AccountRepository;
import br.com.freire.uber.infrastructure.repository.RideRepository;

import java.util.UUID;

public class AcceptRide {

    AccountRepository accountRepository;
    RideRepository rideRepository;

    public AcceptRide(AccountRepository accountRepository, RideRepository rideRepository) {
        this.accountRepository = accountRepository;
        this.rideRepository = rideRepository;
    }

    public void execute(InputAcceptRide input) {
        var optionalAccount = accountRepository.getAccountById(UUID.fromString(input.driverId));
        if (optionalAccount.isEmpty()) {
            throw new ValidationError("Account not found", -5);
        }

        var account = optionalAccount.get();
        if (!account.isDriver()) {
            throw new ValidationError("Account is not from driver", -6);
        }
        Ride ride = rideRepository.getRideById(input.rideId).orElseThrow(() -> new ValidationError("Ride not found", -12));
        ride.accept(input.driverId);
        rideRepository.updateRide(ride);

    }

    public record InputAcceptRide(String rideId, String driverId) {
    }
}
