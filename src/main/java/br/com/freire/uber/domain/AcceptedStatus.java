package br.com.freire.uber.domain;

import br.com.freire.uber.domain.exceptions.ValidationError;

public class AcceptedStatus extends RideStatus{

    AcceptedStatus(Ride ride) {
        super(ride, "accepted");
    }

    @Override
    void request() {
        throw new ValidationError("Invalid status", -11);
    }

    @Override
    void accept() {
        throw new ValidationError("Invalid status", -11);
    }

    @Override
    void start() {
        this.ride.setStatus(new InProgressStatus(this.ride));

    }
}
