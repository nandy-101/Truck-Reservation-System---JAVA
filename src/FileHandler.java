import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class FileHandler {
//Truck details written to truck_data.txt
    private static final String USER_DATA_FILE = "user_data.txt";
    private static final String TRUCK_DATA_FILE = "truck_data.txt";
    private static final String SERVICE_DATA_FILE = "service_data.txt";
     private static final String BOOKING_DATA_FILE = "booking_data.txt";
    private static Date parseDate(String dateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

  public static void writeUsers(List<User> users) {
      try (PrintWriter writer = new PrintWriter(new FileWriter(USER_DATA_FILE))) {
          for (User user : users) {
              String userData = user.userDataToString();
              writer.println(userData);
          }
      } catch (IOException e) {
          e.printStackTrace();
      }
  }

    // Methods to write truck data
  public static void writeTrucks(List<Truck> trucks) {
      try (PrintWriter writer = new PrintWriter(new FileWriter(TRUCK_DATA_FILE))) {
          for (Truck truck : trucks) {
              // Format: TruckID,Type,OwnerName,Availability
              writer.println(truck.getTruckID() + "," + truck.getTruckType() + "," + truck.getOwnerName() + "," + truck.isAvailable());
          }
         // System.out.println("Truck details written to " + TRUCK_DATA_FILE);
      } catch (IOException e) {
          System.out.println("Error writing truck details to file: " + e.getMessage());
      }
  }

  
    
    // Methods to write service data
    public static void writeServices(List<TruckService> services) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(SERVICE_DATA_FILE))) {
            for (TruckService service : services) {
                writer.println(service.getTruck().getTruckID() + "," + service.getLogistics().getUsername() + "," + service.getStatus() + "," + (service.getCompletionDate() != null ? service.getCompletionDate() : "Not completed"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
  
  public static void writeBookings(List<BookingDetails> bookings) {
      try (PrintWriter writer = new PrintWriter(new FileWriter(BOOKING_DATA_FILE))) {
          for (BookingDetails booking : bookings) {
              // Write each booking detail to the file
              writer.println(booking.getUsername());
             // writer.println("User Name: "+ booking.getUsername());
              writer.println("Source: " + booking.getFrom());
              writer.println("Destination: " + booking.getTo());
              writer.println("Truck Type: " + booking.getTruckType());
              writer.println("Load Description: " + booking.getLoadDescription());
             writer.println("Pickup Date: " + new SimpleDateFormat("yyyy-MM-dd").format(booking.getDate())); 
              writer.println("Weight: " + booking.getWeight());
              writer.println("Distance: " + booking.getDistance());
              writer.println("Price: " + booking.getPrice());
              writer.println(); // Add a newline to separate bookings
          }
         // System.out.println("Booking details written to " + BOOKING_DATA_FILE);
      } catch (IOException e) {
          System.out.println("Error writing booking details to file: " + e.getMessage());
      }
  }


    // Methods to read user data
  public static List<User> readUsers() {
      List<User> users = new ArrayList<>();
      try (BufferedReader reader = new BufferedReader(new FileReader(USER_DATA_FILE))) {
          String line;
          while ((line = reader.readLine()) != null) {
              User user = new User();
              user.initializeFromUserData(line);
              users.add(user);
          }
      } catch (IOException e) {
          e.printStackTrace();
      }
      return users;
  }

  public static List<Truck> readTrucks() {
      List<Truck> trucks = new ArrayList<>();
      try (BufferedReader reader = new BufferedReader(new FileReader(TRUCK_DATA_FILE))) {
          String line;
          while ((line = reader.readLine()) != null) {
              String[] truckData = line.split(",");
              if (truckData.length == 4) {  // Check if there are enough elements
                  String truckID = truckData[0];
                  String type = truckData[1];
                  String ownerName = truckData[2];
                  boolean availability = Boolean.parseBoolean(truckData[3]);
                  trucks.add(new Truck(truckID, type, ownerName, availability));
              } else {
                  System.out.println("Invalid truck data format: " + line);
              }
          }
         // System.out.println("Truck details read from " + TRUCK_DATA_FILE);
      } catch (IOException e) {
          System.out.println("Error reading truck details from file: " + e.getMessage());
      }
      return trucks;
  }


    // Methods to read service data
    public static List<TruckService> readServices() {
        List<TruckService> services = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(SERVICE_DATA_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] serviceData = line.split(",");
                String truckID = serviceData[0];
                String logisticsUsername = serviceData[1];
                ServiceStatus status = ServiceStatus.valueOf(serviceData[2]);
                Date completionDate = serviceData[3].equals("Not completed") ? null : parseDate(serviceData[3]);
                Truck truck = new Truck(truckID, "","",false);  // You may need to adjust this based on your Truck constructor
                Logistics logistics = new Logistics(logisticsUsername, "");  
                services.add(new TruckService(truck, logistics, status, completionDate));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return services;
    }
  
  public static List<BookingDetails> readBookings() {
      List<BookingDetails> bookings = new ArrayList<>();

      try (Scanner scanner = new Scanner(new File(BOOKING_DATA_FILE))) {
          while (scanner.hasNextLine()) {
              String userName = readValue(scanner, "User Name: ");
              String source = readValue(scanner, "Source");
              String destination = readValue(scanner, "Destination");
              String truckType = readValue(scanner, "Truck Type");
              String loadDescription = readValue(scanner, "Load Description");
              String pickupDateStr = readValue(scanner, "Pickup Date"); // Read pickup date string

              String weight = readValue(scanner, "Weight");
              String distance = readValue(scanner, "Distance");
              String price = readValue(scanner, "Price");

              // Parse the pickup date string
              Date pickupDate = null;
              try {
                  pickupDate = new SimpleDateFormat("yyyy-MM-dd").parse(pickupDateStr);
              } catch (ParseException e) {
                  e.printStackTrace(); // Handle the exception according to your needs
              }

              // Parse other fields as needed
              double weightValue = Double.parseDouble(weight);
              double distanceValue = Double.parseDouble(distance);
              double priceValue = Double.parseDouble(price);

              // Create a BookingDetails object and add it to the list
              BookingDetails booking = new BookingDetails(userName,source, destination, truckType, loadDescription, pickupDate, weightValue, distanceValue, priceValue);
              // Set more fields as needed
              // ...

              bookings.add(booking);

              // Read the newline separating bookings
              if (scanner.hasNextLine()) {
                  scanner.nextLine();
              }
          }
          // System.out.println("Booking details read from " + BOOKING_DATA_FILE);
      } catch (IOException e) {
          System.out.println("Error reading booking details from file: " + e.getMessage());
      }

      return bookings;
  }


  

  private static String readValue(Scanner scanner, String fieldName) {
      // Reads a line and extracts the value for the given field name
      String line;

      // Check if there is another line available
      while (scanner.hasNextLine()) {
          line = scanner.nextLine().trim();

          if (!line.isEmpty()) {
              // Assuming each line has the format: "Field: Value"
              return line.replace(fieldName + ":", "");
          }
      }

      // If no line is found, return an empty string or handle the case accordingly
      return "";
  }


  
}
      