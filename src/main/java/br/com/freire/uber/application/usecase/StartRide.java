package br.com.freire.uber.application.usecase;

import br.com.freire.uber.domain.Ride;
import br.com.freire.uber.domain.exceptions.ValidationError;
import br.com.freire.uber.infrastructure.repository.RideRepository;

public class StartRide {

    RideRepository rideRepository;

    public StartRide(RideRepository rideRepository) {
        this.rideRepository = rideRepository;
    }

    public void execute(InputStartRide input) {
        Ride ride = rideRepository.getRideById(input.rideId).orElseThrow(() -> new ValidationError("Ride not found", -12));
        ride.start();
        rideRepository.updateRide(ride);
    }

    public record InputStartRide(String rideId) {
    }
}
