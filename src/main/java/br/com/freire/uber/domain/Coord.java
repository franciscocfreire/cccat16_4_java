package br.com.freire.uber.domain;

import br.com.freire.uber.domain.exceptions.ValidationError;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class Coord {

    private final BigDecimal latitude;
    private final BigDecimal longitude;

    public Coord(BigDecimal latitude, BigDecimal longitude){
        if(latitude.compareTo(BigDecimal.valueOf(-90)) < 0 || latitude.compareTo(BigDecimal.valueOf(90)) > 0 ) throw new ValidationError("Invalid latitude",-10);
        if(longitude.compareTo(BigDecimal.valueOf(-180)) < 0 || longitude.compareTo(BigDecimal.valueOf(180)) > 0 ) throw new ValidationError("Invalid longitude",-11);
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
