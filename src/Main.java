import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.*;
import java.io.*;
import java.text.*;
class InsufficientFundsException extends Exception {
    public InsufficientFundsException(String message) {
        super(message);
    }
}
public class Main {
 

    private static final String USER_DATA_FILE = "user_data.txt";
    private static final String TRUCK_DATA_FILE = "truck_data.txt";
    private static final String SERVICE_DATA_FILE = "service_data.txt";
    private static final String BOOKING_DATA_FILE = "booking_data.txt";

    private static List<Logistics> logisticsUsers = new ArrayList<>();
    private static List<TruckOwner> truckOwnerUsers = new ArrayList<>();
    private static List<Agency> agencyUsers = new ArrayList<>();

    public static void main(String[] args) {
     

        List<User> users = FileHandler.readUsers();
        List<Truck> trucks = FileHandler.readTrucks();
        List<TruckService> services = FileHandler.readServices();
        List<BookingDetails> bookings = FileHandler.readBookings();
    
        Scanner scanner = new Scanner(System.in);
    
        // Ask the user for sign-up or login
        System.out.println("Welcome to the Truck Reservation System!");
        System.out.println("1. Sign Up\n2. Login");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                // Sign-up
                User newUser = signUp(scanner);
                if (newUser != null) {
                    users.add(newUser);
                    addUserToTypeList(newUser);
                    System.out.println("Sign-up successful!");
                } else {
                    System.out.println("Sign-up failed. User with the same username already exists.");
                }
                break;
            case 2:
                // Login
                User authenticatedUser = login(scanner, users);
                if (authenticatedUser != null) {
                    System.out.println("Login successful for " + authenticatedUser.getUsername());
                    UserType userType = getUserTypeFromRole();
                    authenticatedUser.setUserType(userType);
                    switch (userType) {
                        case LOGISTICS:
                            performLogisticsOperations(authenticatedUser, scanner, trucks, services, bookings,users);
                            break;
                        case TRUCK_OWNER:
                            performTruckOwnerOperations(authenticatedUser, scanner, trucks, services,bookings);
                            break;
                        case AGENCY:
                            performAgencyOperations(authenticatedUser, scanner, trucks, services,bookings);
                            break;
                    }
                } else {
                    System.out.println("Login failed. Invalid username or password.");
                }
                break;
            default:
                System.out.println("Invalid choice.");
                break;
        }



        FileHandler.writeUsers(users);
        FileHandler.writeTrucks(trucks);
        FileHandler.writeServices(services);
    }

    private static User signUp(Scanner scanner) {
        System.out.println("Enter a username:");
        String username = scanner.next();
        System.out.println("Enter a password:");
        String password = scanner.next();
        
        // Check if the username already exists
        for (User user : FileHandler.readUsers()) {
            if (user.getUsername().equals(username)) {
                return null; // User with the same username already exists
            }
        }
        System.out.println("Enter Your MailID :");
        String mailId= scanner.next();
        
       
        System.out.println("Select your role: 1. Logistics, 2. Truck Owner, 3. Agency");
        int roleChoice = scanner.nextInt();

        UserType userType = null;
        switch (roleChoice) {
            case 1:
                userType = UserType.LOGISTICS;
                break;
            case 2:
                userType = UserType.TRUCK_OWNER;
                break;
            case 3:
                userType = UserType.AGENCY;
                break;
            default:
                return null; // Invalid role choice
        }

        // Create a new user with the specified type
        switch (userType) {
            case LOGISTICS:
                Logistics logisticsUser = new Logistics(username, password,mailId);
                logisticsUser.setUserType(userType); // Set the UserType for the created user
                return logisticsUser;
            case TRUCK_OWNER:
                TruckOwner truckOwnerUser = new TruckOwner(username, password,mailId);
                truckOwnerUser.setUserType(userType); 
                return truckOwnerUser;
            case AGENCY:
                Agency agencyUser = new Agency(username, password,mailId);
                agencyUser.setUserType(userType); 
                return agencyUser;
            default:
                return null;
        }
    }

    private static UserType getUserTypeFromRole() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your role (1/2/3): \n\t1. Logistics, 2. Truck Owner, 3. Agency");
        int roleChoice = scanner.nextInt();

        switch (roleChoice) {
        case 1:
                return UserType.LOGISTICS;
            case 2:
                return UserType.TRUCK_OWNER;
            case 3:
                return UserType.AGENCY;
            default:
                System.out.println("Invalid role. Setting to default role: LOGISTICS");
                return UserType.LOGISTICS;
        }
    }

    private static User login(Scanner scanner, List<User> users) {
        System.out.println("Enter your username:");
        String username = scanner.next();
        System.out.println("Enter your password:");
        String password = scanner.next();

        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                
                switch (user.getUserType()) {
                    case LOGISTICS:
                        return user;
                    case TRUCK_OWNER:
                        return user;
                    case AGENCY:
                        return user;
                }
            }
        }
        return null; // Authentication failed
    }
  private static void performLogisticsOperations(User user, Scanner scanner, List<Truck> trucks, List<TruckService> services, List<BookingDetails> bookings, List<User> users) {
      if (user.getUserType() == UserType.LOGISTICS) {
          Logistics logisticsUser = findOrCreateUser(user, users);
          boolean continueOperations = true;

          while (continueOperations) {
             
              System.out.println("Logistics Operations:");
              System.out.println("1. Book a Truck");
              
              System.out.println("2. View My Bookings");
              System.out.println("3. MyWallet");
              System.out.println("4. Cancel Booking");
              System.out.println("5. Logout");

              System.out.print("Enter your choice: ");
              int choice = scanner.nextInt();

              switch (choice) {
                  case 1:
                     
                      performBooking(logisticsUser, scanner, trucks, services, bookings,users);
                      break;
                  case 2:
                      viewLogisticsBookings(logisticsUser, bookings);
                      break;
                  case 3:
                      // MyWallet
                      manageWallet(logisticsUser, scanner);
                      break;
                case 4:
                      // Cancel Booking
                      cancelBooking(logisticsUser, scanner, bookings, trucks, users);
                      break;
                  //  private static void cancelBooking(User user, Scanner scanner, List<BookingDetails> bookings, List<Truck> trucks, List<User> users) {

                  case 5:
                      // Check for completed jobs and update the truck type file
                      checkCompletedJobsAndUpdateTruckTypeFile(trucks, bookings, scanner,logisticsUser.getUsername());
                      // Logout
                      continueOperations = false;
                      break;
                  default:
                      System.out.println("Invalid choice. Please try again.");
              }
          }
      } else {
          System.out.println("Invalid user type for logistics operations.");
      }
  }

  private static void cancelBooking(User user, Scanner scanner, List<BookingDetails> bookings, List<Truck> trucks, List<User> users) {
      if (user.getUserType() == UserType.LOGISTICS) {
          Logistics logisticsUser = findOrCreateUser(user, users);

          System.out.println("Enter the Truck Number to cancel the booking:");
          String truckNumber = scanner.next();

          // Find the booking associated with the specified truck number
          BookingDetails canceledBooking = null;
          for (BookingDetails booking : bookings) {
              if (booking.getUsername().equals(logisticsUser.getUsername()) && booking.getAssignedTruckID().equalsIgnoreCase(truckNumber) && booking.isInProgress()) {
                  canceledBooking = booking;
               
                  break;
              }
          }
          double bookedPrice;
          if (canceledBooking != null) {
              bookedPrice = canceledBooking.getPrice();
              // Calculate the refund amount (75% of the original price)
              double refundAmount = 0.75 * canceledBooking.getPrice();

              // Update the truck's availability and move it to the top of the list
              for (Truck truck : trucks) {
                  if (truck.getTruckID().equalsIgnoreCase(truckNumber)) {
                      truck.setAvailable(true);
                      trucks.remove(truck);
                      trucks.add(0, truck);
                      break;
                  }
              }

              // Remove the canceled booking from the list
              bookings.remove(canceledBooking);

              // Update the user's wallet with the refund amount
              logisticsUser.addMoneyToWallet(refundAmount);

              // Update the agency's wallet with the remaining amount (25%)
              Agency agencyUser = findUserByUsername(users, "Mmb"); // Replace with the actual agency username
              if (agencyUser != null) {
                  double remainingAmount = 0.25 * canceledBooking.getPrice();
                  agencyUser.withdrawMoneyFromWallet(bookedPrice);
                  System.out.println("Given " + refundAmount + " to the logistics user remaining amt " + remainingAmount + "added to the Agency.");;
                  agencyUser.addMoneyToWallet(remainingAmount);
                  
              } else {
                  System.out.println("Agency user not found or invalid user type.");
              }

              // Save the updated data
              FileHandler.writeBookings(bookings);
              FileHandler.writeTrucks(trucks);
              FileHandler.writeUsers(users);

              System.out.println("Booking canceled successfully. Refund amount: $" + refundAmount);
          } else {
              System.out.println("No active booking found for the specified truck number.");
          }
      } else {
          System.out.println("Invalid user type for canceling bookings.");
      }
  }

  
  private static Logistics findOrCreateUser(User user, List<User> users) {
      // Check if the user already exists
      for (User existingUser : users) {
          if (existingUser.getUsername().equals(user.getUsername()) && existingUser.getPassword().equals(user.getPassword()) &&  existingUser.getUserType() == UserType.LOGISTICS) {
             
              Logistics existingLogisticsUser = new Logistics(existingUser.getUsername(), existingUser.getPassword(), existingUser.getEmailId());
              existingLogisticsUser.setUserType(UserType.LOGISTICS);
              existingLogisticsUser.setWalletBalance(existingUser.getWalletBalance());

              
              users.remove(existingUser);

             
              users.add(existingLogisticsUser);

              return existingLogisticsUser;
          }
      }

      // If the user doesn't exist, create a new user and add it to the list
      Logistics newUser = new Logistics(user.getUsername(), user.getPassword(), user.getEmailId());
      newUser.setUserType(UserType.LOGISTICS);
      newUser.setWalletBalance(user.getWalletBalance());
      users.add(newUser);

      return newUser;
  }




  private static void manageWallet(Logistics logisticsUser, Scanner scanner) {
      boolean continueWalletOperations = true;

      while (continueWalletOperations) {
     
          System.out.println("MyWallet Operations:");
          System.out.println("1. View Balance");
          System.out.println("2. Add Money");
          System.out.println("3. Withdraw Money");
          System.out.println("4. Send Money");
          System.out.println("5. Back");

          System.out.print("Enter your choice: ");
          int walletChoice = scanner.nextInt();

          switch (walletChoice) {
              case 1:
                  // View Balance
                  System.out.println("Current Wallet Balance: $" + logisticsUser.getWalletBalance());
                  break;
              case 2:
                  // Add Money
                  System.out.print("Enter the amount to add: $");
                  double addAmount = scanner.nextDouble();
                  logisticsUser.addMoneyToWallet(addAmount);
                  break;
              case 3:
                  // Withdraw Money
                  System.out.print("Enter the amount to withdraw: $");
                  double withdrawAmount = scanner.nextDouble();
                  logisticsUser.withdrawMoneyFromWallet(withdrawAmount);
                  break;
              case 4:
                  // Send Money
                  System.out.print("Enter the username of the recipient: ");
                  String recipientUsername = scanner.next();
                  sendMoney(logisticsUser, recipientUsername, scanner);
                  break;
              case 5:
                  // Back
                  continueWalletOperations = false;
                  break;
              default:
                  System.out.println("Invalid choice. Please try again.");
          }
      }
  }

  private static void sendMoney(Logistics logisticsUser, String recipientUsername, Scanner scanner) {
    
      System.out.print("Enter the amount to send: $");
      double sendAmount = scanner.nextDouble();

     
      if (logisticsUser.getWalletBalance() >= sendAmount) {
          // Update the logistics user's balance
          logisticsUser.withdrawMoneyFromWallet(sendAmount);


          System.out.println("Money sent successfully.");
      } else {
          System.out.println("Insufficient funds in the wallet to send money.");
      }
  }
  private static void sendMoney(Agency agencyUser, String recipientUsername, Scanner scanner) {
      
      System.out.print("Enter the amount to send: $");
      double sendAmount = scanner.nextDouble();

      // Check if the logistics user has sufficient funds
      if (agencyUser.getWalletBalance() >= sendAmount) {
          // Update the logistics user's balance
          agencyUser.withdrawMoneyFromWallet(sendAmount);


          System.out.println("Money sent successfully.");
      } else {
          System.out.println("Insufficient funds in the wallet to send money.");
      }
  }


  
  private static void checkCompletedJobsAndUpdateTruckTypeFile(List<Truck> trucks, List<BookingDetails> bookings, Scanner scanner, String username) {
     
      for (BookingDetails booking : bookings) {
          if (booking.isInProgress() && booking.getUsername().equals(username)) {
              // Check if the logistics user indicates that the job is completed
              System.out.println("Is the job with Truck ID " + booking.getAssignedTruckID() + " completed? (true/false)");
              boolean jobCompleted = scanner.nextBoolean();

              if (jobCompleted) {
                  // Find the assigned truck in the list
                  Truck assignedTruck = findTruckById(trucks, booking.getAssignedTruckID());

                  if (assignedTruck != null) {
                      // Update the truck availability status
                      assignedTruck.setAvailable(true);

                      // Move the assigned truck to the end of the list in the specific truck type file
                      trucks.remove(assignedTruck);
                      trucks.add(assignedTruck);

                      // Update the booking status
                      booking.setInProgress(false);

                      // Save the updated truck type file and booking details
                      //FileHandler.writeTrucks(trucks);
                      //FileHandler.writeBookings(bookings);

                      System.out.println("Job completed for Truck ID: " + assignedTruck.getTruckID());
                  }
              }
          }
      }
    FileHandler.writeTrucks(trucks);
    FileHandler.writeBookings(bookings);
  }

  private static Truck findTruckById(List<Truck> trucks, String truckId) {
      for (Truck truck : trucks) {
          if (truck.getTruckID().equals(truckId)) {
              return truck;
          }
      }
      return null; // Return null if no matching truck is found
  }

  private static double calculatePrice(String truckType, double distance, double weight, Date pickupDate) {
    
      double BASE_PRICE = 1000.0;  // Base price for all trucks
      double DISTANCE_FACTOR = 5.0;  // Price per kilometer
      double WEIGHT_FACTOR = 2.0;  // Price per kilogram
      double ADVANCE_BOOKING_FACTOR = 0.1;  // Discount factor for advance booking (10% discount)

      double truckTypeFactor = 1.0;
      switch (truckType.toLowerCase()) {
          case "mini truck":
              truckTypeFactor = 1.0;
              break;
          case "box truck":
              truckTypeFactor = 1.2;
              break;
          case "refrigerated truck":
              truckTypeFactor = 1.5;
              break;
          case "trailer truck":
              truckTypeFactor = 1.8;
              break;
          case "tanker truck":
              truckTypeFactor = 2.0;
              break;
          default:
             
              System.out.println("The Given Truck is not available, Possibily it comes around,.");
              break;
      }

      // Calculate date difference in days for advance booking factor
      long daysDifference = daysBetween(new Date(), pickupDate);
      if(daysDifference > 0) {
      System.out.println("You are Booking " + daysDifference+" days advance");
      System.out.println("You will get 10% discount");
      }else{
        System.out.println("You are Booking " + daysDifference+" days advance");
      }
      double advanceBookingFactor = Math.max(1.0 - (daysDifference * ADVANCE_BOOKING_FACTOR), 0.9);  // Minimum 10% discount

      // Calculate the final price
      double price = BASE_PRICE * truckTypeFactor + distance * DISTANCE_FACTOR + weight * WEIGHT_FACTOR;
      return price * advanceBookingFactor;
      
  }

  private static long daysBetween(Date startDate, Date endDate) {
      long differenceInMillis = endDate.getTime() - startDate.getTime();
      return differenceInMillis / (24 * 60 * 60 * 1000);
  }


  

  private static void performBooking(Logistics logisticsUser, Scanner scanner, List<Truck> trucks, List<TruckService> services, List<BookingDetails> bookings, List<User> users) {
      // Gather details from the user
      System.out.println("Enter Source:");
      String source = scanner.next();

      System.out.println("Enter Destination:");
      String destination = scanner.next();
      scanner.nextLine();
      System.out.println("Enter Truck Type(Mini Truck/Box Truck/Refrigerated Truck/Trailer Truck/Tanker Truck):");
      String truckType = scanner.nextLine().trim();

      System.out.println("Enter Cargo Type:");
      String cargoType = scanner.next();
      scanner.nextLine();

      System.out.println("Enter Pickup Date (yyyy-MM-dd):");
      String pickupDateStr = scanner.next();

      Date pickupDate = null;
      try {
          pickupDate = new SimpleDateFormat("yyyy-MM-dd").parse(pickupDateStr);
      } catch (ParseException e) {
          e.printStackTrace();
      }

      System.out.println("Enter Cargo Weight in Metric Tons:");
      double cargoWeight = scanner.nextDouble();

      System.out.println("Enter Distance:");
      double distance = scanner.nextDouble();

      // Calculate the price
      double price = calculatePrice(truckType, distance, cargoWeight, pickupDate);

      // Display the calculated price to the user
      System.out.println("The estimated price for your booking is: $" + price);

      // Ask the user to confirm the booking with the displayed price
      System.out.println("Do you want to proceed with the booking? (true/false)");
      boolean proceedWithBooking = scanner.nextBoolean();

      if (proceedWithBooking) {
          try {
              // Deduct the booking amount from the user's wallet

              // Check if the logistics user has enough money in the wallet
              if (logisticsUser.getWalletBalance() < price) {
                  throw new InsufficientFundsException("Insufficient funds in the wallet. Please add Rs." + (price - logisticsUser.getWalletBalance()) + " before proceeding with the booking.");
              }

              BookingDetails bookingDetails = new BookingDetails(logisticsUser.getUsername(),source, destination, truckType, cargoType, pickupDate, cargoWeight, distance, price,logisticsUser.getEmailId());
              bookings.add(bookingDetails); // Add the booking with inProgress set to true

              FileHandler.writeTrucks(trucks);
              FileHandler.writeServices(services);
              FileHandler.writeBookings(bookings);

              
              System.out.println("Please wait for agency confirmation.");
              System.out.println("Booking in progress");

              
              Thread printerThread = new Thread(new LetterByLetterPrinter("....................................", 450)); 
              printerThread.start();
              printerThread.join();

              boolean truckAvailable = checkTruckAvailabilityInTypeFile(truckType, trucks);

              if (truckAvailable) {
                
            	  BookingDetails bkDetail=assignTruckToBooking(truckType, trucks, bookings, logisticsUser.getEmailId());
            	  System.out.println("Customer MAILID :"+logisticsUser.getEmailId());
                  // Withdraw money from the logistics user's wallet
                  logisticsUser.withdrawMoneyFromWallet(price);

                  // Find the agency user in the list
                  Agency agencyUser = findUserByUsername(users, "Mmb");
                  // Credit the amount to the agency user's wallet
                  if (agencyUser != null) {
                      agencyUser.addMoneyToWallet(price);
                      System.out.println("Amount credited to the agency user's wallet successfully.");
                      sendEmail(bkDetail.getBookedEmailId(), "Truck Booking Confirmation", "Dear Customer,\n\nWe are pleased to inform you that your truck booking has been confirmed successfully.\n\nBooking Details:\n- Truck Number: " + bkDetail.getAssignedTruckID() + "\n- From: " + bkDetail.getSource() + "\n- To: " + bkDetail.getTo() + "\n- Truck Type: " + bkDetail.getTruckType() + "\n- Load Description: " + bkDetail.getLoadDescription() + "\n- Date: " + new SimpleDateFormat("yyyy-MM-dd").format(bkDetail.getDate()) + "\n- Weight(Metric Ton): " + bkDetail.getWeight() + "\n- Distance(in KMs): " + bkDetail.getDistance() + "\n- Price:$ " + bkDetail.getPrice() + "\n\nThank you for choosing us!\n\nBest regards,\n[Truck Reservation System...]");

                  } else {
                      System.out.println("Agency user not found or invalid user type.");
                  }
               
                Thread printerThread1 = new Thread(new LetterByLetterPrinter(".............Thanks for choosing us!.............", 450)); 
                printerThread1.start();
                printerThread1.join();
              } else {
                  System.out.println("Requested truck type is not available at the moment. Please wait for agency confirmation.");
              }
          } catch (InsufficientFundsException | InterruptedException e) {
              System.out.println(e.getMessage());
          }
      } else {
          System.out.println("Booking canceled. Thank you.");
      }
      FileHandler.writeUsers(users);
  }

  private static void sendEmail(String to, String subject, String body) {
      final String username = "truckreservationsystem@gmail.com"; 
      final String password = "bhtwnrxrdnremgrf"; 

      Properties props = new Properties();
      props.put("mail.smtp.auth", "true");
      props.put("mail.smtp.starttls.enable", "true");
      props.put("mail.smtp.host", "smtp.gmail.com");
      props.put("mail.smtp.port", "587");

      Session session = Session.getInstance(props, new Authenticator() {
          protected PasswordAuthentication getPasswordAuthentication() {
              return new PasswordAuthentication(username, password);
          }
      });

      try {
          Message message = new MimeMessage(session);
          message.setFrom(new InternetAddress(username));
          message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
          message.setSubject(subject);
          message.setText(body);

          Transport.send(message);

          System.out.println("Email sent successfully to: " + to);
      } catch (MessagingException e) {
          throw new RuntimeException(e);
      }
  }
  private static Agency findUserByUsername(List<User> users, String username) {
      for (User user : users) {
          if (user.getUsername().equals(username) && user.getUserType() == UserType.AGENCY) {
              Agency foundAgency = new Agency(user.getUsername(), user.getPassword());
              foundAgency.setUserType(UserType.AGENCY);
              foundAgency.setWalletBalance(user.getWalletBalance());

         
              users.remove(user);

          
              users.add(foundAgency);

         
              return foundAgency;
          }
      }
      return null; 
  }



  private static boolean checkTruckAvailabilityInTypeFile(String truckType, List<Truck> trucks) {
    
    for (Truck truck : trucks) {
       // boolean isAvailable = truck.getTruckType().equalsIgnoreCase(truckType);
        //System.out.println("Checking availability for truck type: " + truck.getTruckType()+"Is available: " + isAvailable+"Is Free: "+truck.isAvailable());
        
        if (truck.getTruckType().equalsIgnoreCase(truckType) && truck.isAvailable()) {
          
            return true;
        }
    }

     
      return false;
  }

  private static BookingDetails assignTruckToBooking(String truckType, List<Truck> trucks, List<BookingDetails> bookings, String userEmail) {

      for (Truck truck : trucks) {
         
          if (truck.getTruckType().equalsIgnoreCase(truckType) && truck.isAvailable()) {
             
              truck.setAvailable(false);

            
              trucks.remove(truck);
              trucks.add(truck);

             
              for (BookingDetails booking : bookings) {
                    if (booking.getTruckType().equalsIgnoreCase(truckType) && booking.isInProgress()) {
                     
                      booking.setAssignedTruckID(truck.getTruckID());
                      System.out.println("Truck assigned successfully: " + truck.getTruckID());
                      booking.setBookedEmailId(userEmail);
                      return booking;
                   
                  }
              }

              // Save the updated truck type file and booking details
              FileHandler.writeTrucks(trucks);
              FileHandler.writeBookings(bookings); //count -2
              break;
          }
      }
      return null;
  }


  private static void viewLogisticsBookings(Logistics logisticsUser, List<BookingDetails> bookings) {
      
      for (BookingDetails booking : bookings) {
    	  
    	   
          if (booking.getUsername().equals(logisticsUser.getUsername()))
              System.out.println(booking);
          }
      
  }

      private static void performTruckOwnerOperations(User user, Scanner scanner, List<Truck> trucks, List<TruckService> services, List<BookingDetails> bookings) {
        if (user.getUserType() == UserType.TRUCK_OWNER) {
            TruckOwner truckOwnerUser = new TruckOwner(user.getUsername(), user.getPassword());
            truckOwnerUser.setUserType(UserType.TRUCK_OWNER);
          truckOwnerUser.setWalletBalance(user.getWalletBalance());

            boolean continueOperations = true;

            while (continueOperations) {
                
                System.out.println("Truck Owner Operations:");
                System.out.println("1. Add a Truck");
                System.out.println("2. Remove a Truck");
                System.out.println("3. View My Trucks");
                System.out.println("4. View Loads Assigned to My Trucks");
                System.out.println("5. Logout");

                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        // Add a truck
                        performAddTruck(truckOwnerUser, scanner, trucks);
                        break;
                    case 2:
                        // Remove a truck
                        performRemoveTruck(truckOwnerUser, scanner, trucks);
                        break;
                    case 3:
                        // View My Trucks
                        performViewTrucks(truckOwnerUser, trucks);
                        break;
                    case 4:
                        // View Loads Assigned to My Trucks
                        performViewLoads(truckOwnerUser, bookings);
                        break;
                    case 5:
                        // Logout
                        continueOperations = false;
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        } else {
            System.out.println("Invalid user type for truck owner operations.");
        }
      }

  private static void performAddTruck(TruckOwner truckOwnerUser, Scanner scanner, List<Truck> trucks) {
      System.out.println("Enter Truck ID:");
      String truckID = scanner.next();

      System.out.println("Enter Truck Type (1. Box Truck, 2. Trailer Truck, 3. Refrigerated Truck, 4. Tanker Truck, 5. Mini Truck):");
      int truckTypeChoice = scanner.nextInt();
      String truckType = getTruckTypeFromChoice(truckTypeChoice);

      //System.out.println("Enter Owner Name:");
      String ownerName = truckOwnerUser.getUsername();

      System.out.println("Enter Availability (true/false):");
      boolean isAvailable = scanner.nextBoolean();

      // Create a new truck
      Truck newTruck = new Truck(truckID, truckType, ownerName, isAvailable);
      trucks.add(newTruck);
      System.out.println("Truck added: " + newTruck.getTruckID());

      // Save the truck details to the respective file based on truck type
      saveTruckToFile(newTruck);
  }

  private static String getTruckTypeFromChoice(int choice) {
      switch (choice) {
          case 1:
              return "Box Truck";
          case 2:
              return "Trailer Truck";
          case 3:
              return "Refrigerated Truck";
          case 4:
              return "Tanker Truck";
          case 5:
              return "Mini Truck";
          default:
              return "Unknown";
      }
  }

  private static void saveTruckToFile(Truck truck) {
      String truckType = truck.getTruckType();
      try (PrintWriter writer = new PrintWriter(new FileWriter(truckType + "_trucks.txt", true))) {
         
          writer.println("Truck ID: " + truck.getTruckID());
          writer.println("Owner Name: " + truck.getOwnerName());
          writer.println("Availability: " + truck.isAvailable());
          writer.println();
          System.out.println("Truck details written to " + truckType + "_trucks.txt");
      } catch (IOException e) {
          System.out.println("Error writing truck details to file: " + e.getMessage());
      }
  }

  private static void performRemoveTruck(TruckOwner truckOwnerUser, Scanner scanner, List<Truck> trucks) {
      System.out.println("Enter Truck ID to remove:");
      String truckIDToRemove = scanner.next();
      System.out.println("Enter Type of Truck to remove:");
      String truckTypeToRemove = scanner.next();
      // Remove the truck with the specified ID from the list
      trucks.removeIf(truck -> truck.getTruckID().equals(truckIDToRemove));
      System.out.println("Truck removed from the list, if it existed.");

      // Remove the truck with the specified ID from the respective truck type file
      removeTruckFromFile(truckIDToRemove, truckTypeToRemove);
  }

  private static void removeTruckFromFile(String truckIDToRemove, String truckType) {
      String truckTypeFile = getTruckTypeFile(truckType);
      List<String> lines = new ArrayList<>();

      try (BufferedReader reader = new BufferedReader(new FileReader(truckTypeFile))) {
          String line;
          while ((line = reader.readLine()) != null) {
              String[] truckData = line.split(",");
              if (truckData.length == 4 && !truckData[0].equals(truckIDToRemove)) {
                  lines.add(line);
              }
          }
      } catch (IOException e) {
          System.out.println("Error reading truck details from file: " + e.getMessage());
      }

      // Write the updated lines back to the truck type file
      try (PrintWriter writer = new PrintWriter(new FileWriter(truckTypeFile))) {
          for (String line : lines) {
              writer.println(line);
          }
          System.out.println("Truck removed from the " + truckType + " file, if it existed.");
      } catch (IOException e) {
          System.out.println("Error writing truck details to file: " + e.getMessage());
      }
  }

  private static String getTruckTypeFile(String truckType) {
      switch (truckType) {
          case "box":
              return "box_trucks.txt";
          case "trailer":
              return "trailer_trucks.txt";
          case "refrigerated":
              return "refrigerated_trucks.txt";
          case "tanker":
              return "tanker_trucks.txt";
          case "mini":
              return "mini_trucks.txt";
          default:
              throw new IllegalArgumentException("Invalid truck type: " + truckType);
      }
  }


      private static void performViewTrucks(TruckOwner truckOwnerUser, List<Truck> trucks) {
        System.out.println("Trucks owned by " + truckOwnerUser.getUsername() + ":");
        for (Truck truck : trucks) {
        	if(truck.getOwnerName().equalsIgnoreCase(truckOwnerUser.getUsername()))
        		System.out.println(truck+" " + truck.getTruckType());
        }
      }

      private static void performViewLoads(TruckOwner truckOwnerUser, List<BookingDetails> bookings) {
        System.out.println("Loads assigned to trucks owned by " + truckOwnerUser.getUsername() + ":");
        
      }

      


  private static void performAgencyOperations(User user, Scanner scanner, List<Truck> trucks, List<TruckService> services, List<BookingDetails> bookings) {
    if (user.getUserType() == UserType.AGENCY) {
        Agency agencyUser = new Agency(user.getUsername(), user.getPassword());
        agencyUser.setUserType(UserType.AGENCY);
        agencyUser.setWalletBalance(user.getWalletBalance()); 
        boolean continueOperations = true;

        while (continueOperations) {
            // Display menu options for agency operations
            System.out.println("Agency Operations:");
            System.out.println("1. View Booking Requests");
            System.out.println("2. Respond to Booking Request");
            System.out.println("3. Manage Trucks of Truck Owners");
            System.out.println("4. Manage Wallet");
            System.out.println("5. Logout");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    // View Booking Requests
                    performViewBookingRequests(agencyUser, bookings);
                    break;
                case 2:
                    // Respond to Booking Request
                    performRespondToBookingRequest(agencyUser, scanner, bookings, trucks);
                    break;
                case 3:
                    // Manage Trucks of Truck Owners
                    performManageTrucksOfTruckOwners(agencyUser, trucks);
                    break;
                case 4:
                    // Manage Wallet
                    manageWallet(agencyUser, scanner);
                    break;
                case 5:
                    // Logout
                    continueOperations = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    } else {
        System.out.println("Invalid user type for agency operations.");
    }
  }

  private static void manageWallet(Agency agencyUser, Scanner scanner) {
    boolean continueWalletOperations = true;

    while (continueWalletOperations) {
      
        System.out.println("MyWallet Operations:");
        System.out.println("1. View Balance");
        System.out.println("2. Add Money");
        System.out.println("3. Withdraw Money");
        System.out.println("4. Send Money");
        System.out.println("5. Back");

        System.out.print("Enter your choice: ");
        int walletChoice = scanner.nextInt();

        switch (walletChoice) {
            case 1:
                // View Balance
                System.out.println("Current Wallet Balance: $" + agencyUser.getWalletBalance());
                break;
            case 2:
                // Add Money
                System.out.print("Enter the amount to add: $");
                double addAmount = scanner.nextDouble();
                agencyUser.addMoneyToWallet(addAmount);
                break;
            case 3:
                // Withdraw Money
                System.out.print("Enter the amount to withdraw: $");
                double withdrawAmount = scanner.nextDouble();
                agencyUser.withdrawMoneyFromWallet(withdrawAmount);
                break;
            case 4:
                // Send Money
                System.out.print("Enter the username of the recipient: ");
                String recipientUsername = scanner.next();
                sendMoney(agencyUser, recipientUsername, scanner);
                break;
            case 5:
                // Back
                continueWalletOperations = false;
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }
  }


  private static void performViewBookingRequests(Agency agencyUser, List<BookingDetails> bookings) {
    System.out.println("Booking requests from logistics:");
   
  }

  private static void performRespondToBookingRequest(Agency agencyUser, Scanner scanner, List<BookingDetails> bookings, List<Truck> trucks) {
    System.out.println("Enter Booking Request ID to respond:");
    String bookingRequestID = scanner.next();

    System.out.println("Enter Response (true/false for truck availability):");
    boolean isTruckAvailable = scanner.nextBoolean();

  
    BookingRequest bookingRequest = new BookingRequest("16-Wheeler");
    BookingResponse response = agencyUser.respondToBooking(bookingRequest);
    if (response != null && response.getResponseStatus()) {
        System.out.println("Booking request accepted. Truck ID: " + response.getTruck().getTruckID());
    } else {
        System.out.println("Failed to respond to the booking request.");
    }
  }

  private static void performManageTrucksOfTruckOwners(Agency agencyUser, List<Truck> trucks) {
    System.out.println("Manage trucks of truck owners:");
   
  }

  private static void performManageWallet(Agency agencyUser) {
    System.out.println("Manage wallet:");
   
  }




    private static void addUserToTypeList(User user) {
        switch (user.getUserType()) {
            case LOGISTICS:
                logisticsUsers.add((Logistics) user);
                break;
            case TRUCK_OWNER:
                truckOwnerUsers.add((TruckOwner) user);
                break;
            case AGENCY:
                agencyUsers.add((Agency) user);
                break;
        }
    }
}
