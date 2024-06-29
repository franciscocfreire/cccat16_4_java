package br.com.freire.uber.domain;

public abstract class RideStatus {

    String value;
    Ride ride;

    RideStatus(Ride ride, String value) {
        this.ride = ride;
        this.value = value;
    }

    abstract void request();

    abstract void accept();

    abstract void start();
}
