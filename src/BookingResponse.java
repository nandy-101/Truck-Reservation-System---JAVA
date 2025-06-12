import java.util.*;
class BookingResponse {
    private Truck truck;
    private boolean responseStatus;

    public BookingResponse(Truck truck, boolean responseStatus) {
        this.truck = truck;
        this.responseStatus = responseStatus;
    }

    // Getter for responseStatus
    public boolean getResponseStatus() {
        return responseStatus;
    }

    // Getter for truck
    public Truck getTruck() {
        return truck;
    }
}
