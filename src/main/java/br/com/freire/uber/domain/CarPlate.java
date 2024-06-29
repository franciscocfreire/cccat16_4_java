package br.com.freire.uber.domain;

import br.com.freire.uber.domain.exceptions.ValidationError;
import lombok.Getter;

import java.util.regex.Pattern;

@Getter
public class CarPlate {
    private final String value;
    public CarPlate(String carPlate) {
        if(carPlate != null && !carPlate.isEmpty() && !Pattern.matches("[A-Z]{3}[0-9]{4}", carPlate))
            throw new ValidationError("Invalid car plate", -5);
        this.value = carPlate;
    }
}
