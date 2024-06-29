package br.com.freire.uber.domain;

import lombok.Getter;

import java.util.UUID;

public class Account {
    @Getter
    private final UUID accountId;
    private final Name name;
    private final Email email;
    private final Cpf cpf;
    private final CarPlate carPlate;
    @Getter
    private final boolean isPassenger;
    @Getter
    private final boolean isDriver;

    private Account(UUID accountId, Name name, Email email, Cpf cpf, CarPlate carPlate, boolean isPassenger, boolean isDriver) {
        this.accountId = accountId;
        this.name = name;
        this.email = email;
        this.cpf = cpf;
        this.carPlate = carPlate;
        this.isPassenger = isPassenger;
        this.isDriver = isDriver;
    }

    public static Account create(String name, String email, String cpf, String carPlate, boolean isPassenger, boolean isDriver) {
        return new Account(UUID.randomUUID(), new Name(name), new Email(email), new Cpf(cpf), new CarPlate(carPlate), isPassenger, isDriver);
    }

    public static Account restore(UUID accountId, String name, String email, String cpf, String carPlate, boolean isPassenger, boolean isDriver) {
        return new Account(accountId, new Name(name), new Email(email), new Cpf(cpf), new CarPlate(carPlate), isPassenger, isDriver);
    }

    public String getName() {
        return name.getValue();
    }

    public String getEmail(){
        return email.getValue();
    }

    public String getCpf() {
        return cpf.getValue();
    }


    public String getCarPlate() {
        return carPlate.getValue();
    }
}