package br.com.freire.uber.domain;

import lombok.Getter;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Getter
public class Segment {
    private static final BigDecimal EARTH_RADIUS = new BigDecimal("6371"); // Raio da Terra em quilômetros
    private static final BigDecimal PI = new BigDecimal(Math.PI);
    private static final BigDecimal DEGREES_TO_RADIANS = PI.divide(new BigDecimal("180"), 20, RoundingMode.HALF_UP);
    private final Coord to;
    private final Coord from;

    public Segment(Coord to, Coord from){
        this.to = to;
        this.from = from;
    }

    public BigDecimal getDistance(){
        BigDecimal deltaLat = degreesToRadians(to.getLatitude().subtract(from.getLatitude()));
        BigDecimal deltaLon = degreesToRadians(to.getLongitude().subtract(from.getLongitude()));

        BigDecimal sinDeltaLatDiv2 = BigDecimal.valueOf(Math.sin(deltaLat.divide(BigDecimal.valueOf(2), RoundingMode.HALF_UP).doubleValue()));
        BigDecimal sinDeltaLonDiv2 = BigDecimal.valueOf(Math.sin(deltaLon.divide(BigDecimal.valueOf(2), RoundingMode.HALF_UP).doubleValue()));

        BigDecimal a = sinDeltaLatDiv2.multiply(sinDeltaLatDiv2)
                .add(BigDecimal.valueOf(Math.cos(degreesToRadians(from.getLatitude()).doubleValue()))
                        .multiply(BigDecimal.valueOf(Math.cos(degreesToRadians(to.getLatitude()).doubleValue())))
                        .multiply(sinDeltaLonDiv2).multiply(sinDeltaLonDiv2));

        BigDecimal c = BigDecimal.valueOf(2).multiply(BigDecimal.valueOf(Math.atan2(Math.sqrt(a.doubleValue()), Math.sqrt(BigDecimal.ONE.subtract(a).doubleValue()))));

        BigDecimal distance = EARTH_RADIUS.multiply(c);

        return distance.setScale(0, RoundingMode.HALF_UP);
    }

    private BigDecimal degreesToRadians(BigDecimal degrees) {
        return degrees.multiply(DEGREES_TO_RADIANS);
    }

}
