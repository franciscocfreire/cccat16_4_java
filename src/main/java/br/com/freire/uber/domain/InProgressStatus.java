package br.com.freire.uber.domain;

import br.com.freire.uber.domain.exceptions.ValidationError;

public class InProgressStatus extends RideStatus {

    InProgressStatus(Ride ride) {
        super(ride,"in_progress");
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
        throw new ValidationError("Invalid status", -11);
    }
}
