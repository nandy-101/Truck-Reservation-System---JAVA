import java.util.*;

class Logistics extends User {
    private List<Truck> bookedTrucks;

    public Logistics(String username, String password) {
        super(username, password);
        this.bookedTrucks = new ArrayList<>();
    }
    
    public Logistics(String username, String password, String mailId) {
        super(username, password, mailId);
        this.bookedTrucks = new ArrayList<>();
    }
    

    public BookingResponse bookTruck(BookingDetails details, List<Truck> trucks, List<BookingDetails> bookings) {
        String truckType = details.getTruckType();

        // Check if there is an available truck of the specified type
        Optional<Truck> availableTruck = trucks.stream()
                .filter(truck -> truck.getTruckType().equalsIgnoreCase(truckType) && truck.isAvailable())
                .findFirst();

        if (availableTruck.isPresent()) {
            Truck truck = availableTruck.get();

            // Update the truck availability status
            truck.setAvailable(false);

            // Move the assigned truck to the end of the list in the specific truck type file
            trucks.remove(truck);
            trucks.add(truck);

            // Update the booking status
            details.setAssignedTruckID(truck.getTruckID());
            details.setInProgress(true);

            // Save updated truck and service data
            FileHandler.writeTrucks(trucks);
            //FileHandler.writeServices(services);
            FileHandler.writeBookings(bookings);

            return new BookingResponse(truck, true);
        } else {
            // No available trucks of the specified type
            System.out.println("No available trucks of type " + truckType + ". Please wait for agency confirmation.");
            return new BookingResponse(null, false);
        }
    }

    public boolean completeJob(BookingDetails booking, List<Truck> trucks, List<BookingDetails> bookings) {
        // Logic to complete a job
        Scanner scanner = new Scanner(System.in);
        // Check if the logistics user indicates that the job is completed
        System.out.println("Is the job with Truck ID " + booking.getAssignedTruckID() + " completed? (true/false)");
        boolean jobCompleted = scanner.nextBoolean();

        if (jobCompleted) {
            // Find the assigned truck in the list
            Optional<Truck> assignedTruck = trucks.stream()
                    .filter(truck -> truck.getTruckID().equals(booking.getAssignedTruckID()))
                    .findFirst();

            assignedTruck.ifPresent(truck -> {
                // Update the truck availability status
                truck.setAvailable(true);

                // Move the completed truck to the end of the list in the specific truck type file
                trucks.remove(truck);
                trucks.add(truck);

                // Update the booking status
                booking.setInProgress(false);

                // Save the updated truck data and booking details
                FileHandler.writeTrucks(trucks);
                FileHandler.writeBookings(bookings);

                System.out.println("Job completed for Truck ID: " + truck.getTruckID());
            });

            return true;
        } else {
            System.out.println("Job not yet completed. Continue monitoring.");
            return false;
        }
    }
}
