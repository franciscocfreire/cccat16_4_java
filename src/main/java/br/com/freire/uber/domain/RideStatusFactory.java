package br.com.freire.uber.domain;

public class RideStatusFactory {

    static RideStatus create(Ride ride, String status) {
        return switch (status) {
            case "requested" -> new RequestedStatus(ride);
            case "accepted" -> new AcceptedStatus(ride);
            case "in_progress" -> new InProgressStatus(ride);
            default -> null;
        };
    }

}
