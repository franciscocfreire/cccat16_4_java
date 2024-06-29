package br.com.freire.uber.domain;

import br.com.freire.uber.domain.exceptions.ValidationError;

public class RequestedStatus extends RideStatus{

    RequestedStatus(Ride ride) {
        super(ride, "requested");
    }

    @Override
    void request() {
        throw new ValidationError("Invalid status", -11);
    }

    @Override
    void accept() {
        this.ride.setStatus(new AcceptedStatus(this.ride));
    }

    @Override
    void start() {
        throw new ValidationError("Invalid status", -11);

    }
}
