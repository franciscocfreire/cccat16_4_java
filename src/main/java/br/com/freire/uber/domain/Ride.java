package br.com.freire.uber.domain;

import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class Ride {
    private final UUID rideId;
    private final UUID passengerId;
    private final Segment segment;
    private final String status;
    private final LocalDateTime date;

    private Ride(UUID rideId, UUID passengerId, Segment segment, String status, LocalDateTime date){
        this.rideId = rideId;
        this.passengerId = passengerId;
        this.segment = segment;
        this.status = status;
        this.date = date;
    }

    public static Ride create(UUID passengerId, BigDecimal fromLat, BigDecimal fromLong, BigDecimal toLat, BigDecimal toLong){
        UUID rideId =UUID.randomUUID();
        String status = "requested";
        LocalDateTime date = LocalDateTime.now();
        return new Ride(rideId, passengerId, new Segment(new Coord(fromLat, fromLong), new Coord(toLat, toLong)), status, date );
    }

    public static Ride restore(UUID rideId, UUID passengerId, BigDecimal fromLat, BigDecimal fromLong, BigDecimal toLat, BigDecimal toLong, String status, LocalDateTime date){
        return new Ride(rideId, passengerId, new Segment(new Coord(fromLat, fromLong), new Coord(toLat, toLong)), status, date );
    }

    public BigDecimal getFromLatitude(){
        return getSegment().getFrom().getLatitude();
    }
    public BigDecimal getFromLongitude(){
        return getSegment().getFrom().getLongitude();
    }
    public BigDecimal getToLatitude(){
        return getSegment().getTo().getLatitude();
    }
    public BigDecimal getToLongitude(){
        return getSegment().getTo().getLongitude();
    }

}
