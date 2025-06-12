import java.util.*;
class TruckReservation {
    private String from;
    private String to;
    private String truckType;
    private String loadDescription;
    private Date date;
    private double weight;
    private double distance;
    private double estimatedPrice;

    public TruckReservation(String from, String to, String truckType, String loadDescription, Date date, double weight, double distance) {
        this.from = from;
        this.to = to;
        this.truckType = truckType;
        this.loadDescription = loadDescription;
        this.date = date;
        this.weight = weight;
        this.distance = distance;
        this.estimatedPrice = calculateEstimatedPrice();
    }

    private double calculateEstimatedPrice() {
        // Logic to calculate estimated price based on distance, weight, etc.
        return 100.0;
    }
}
