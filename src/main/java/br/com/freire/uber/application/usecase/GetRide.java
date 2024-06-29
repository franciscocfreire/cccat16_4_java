package br.com.freire.uber.application.usecase;

import br.com.freire.uber.domain.Account;
import br.com.freire.uber.domain.exceptions.ValidationError;
import br.com.freire.uber.infrastructure.repository.AccountRepository;
import br.com.freire.uber.infrastructure.repository.RideRepository;

import java.math.BigDecimal;
import java.util.Optional;

public class GetRide {

    AccountRepository accountRepository;
    RideRepository rideRepository;

    public GetRide(AccountRepository accountRepository, RideRepository rideRepository) {
        this.accountRepository = accountRepository;
        this.rideRepository = rideRepository;
    }

    public OutputGetRide execute(InputGetRide input) {
        var rideOptional = rideRepository.getRideById(input.rideId);
        var ride = rideOptional.orElseThrow(() -> new ValidationError("Ride not found", -8));
        var passengerOptional = accountRepository.getAccountById(ride.getPassengerId());
        var passenger = passengerOptional.orElseThrow(() -> new ValidationError("Ride without passenger", -9));
        Optional<Account> optionalDriver = Optional.empty();
        if (ride.getDriverId() != null) {
            optionalDriver = accountRepository.getAccountById((ride.getDriverId()));
        }
        return new OutputGetRide(
                ride.getRideId().toString(),
                ride.getPassengerId().toString(),
                ride.getFromLatitude(),
                ride.getFromLongitude(),
                ride.getToLatitude(),
                ride.getToLongitude(),
                ride.getStatus(),
                passenger.getName(),
                passenger.getEmail(),
                optionalDriver.map(Account::getName).orElse(null),
                optionalDriver.map(Account::getEmail).orElse(null)
        );

    }

    public record InputGetRide(String rideId) {
    }

    public record OutputGetRide(String rideId, String passengerId, BigDecimal fromLat, BigDecimal fromLong,
                                BigDecimal toLat, BigDecimal toLong, String status, String passengerName,
                                String passengerEmail, String driverName, String driverEmail) {
    }
}
