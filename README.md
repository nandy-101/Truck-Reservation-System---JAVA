
# Truck Reservation System

Truck Reservation System is a console based JAVA application designed to facilitate the process of reserving and managing trucks for transportation purposes. This type of system is commonly used in logistics and freight industries to streamline the booking and assignment of trucks for transporting goods from one location to another. Key features and functionalities  in truck reservation system:


## Features

- Booking a Truck
- Real-time Price Calculation
- Wallet Management
- Cancellation with Refund
- Email Notification

## Logistics User

- Can Book a Desired Truck
- Cancel His Booking
- View His Bookings
- Manage His Wallet
   -Add Money, Withdraw it, View Balance
   
## Agency User
- Can Manage Trucks
- Can Manage His Wallet - View Balance, Deposit, Withdraw
- Receives payment from logistic's user when he made a successful booking

## Truck Owner User
- Can add new Trucks
- Can remove his Trucks
- View His Trucks
- View His Services

**PROCESS INVOLVED IN THIS PROJECT**
1. **User Authentication:**
   - The system allows users to log in with appropriate credentials. Different user roles exist, such as logistics users, truck owners, and agency.

2. **Truck Booking:**
   - Logistics users can request the reservation of trucks by providing details such as source, destination, truck type, cargo type, pickup date, cargo weight, and distance.

3. **Price Calculation:**
   - The system calculates the estimated price for the booking based on factors like truck type, distance, cargo weight, and date of reservation.

4. **Wallet Management:**
   - Users often have virtual wallets where they can deposit funds. The system manages the financial transactions, deducting the booking amount from the user's wallet when a booking is done.

5. **Confirmation and Notifications:**
   - Once a booking is confirmed, the system sends notifications via **Email** to the user providing details such as the assigned truck ID and confirmation status.

6. **Truck Availability Check:**
   - The system checks the availability of trucks based on their types and current reservations. If a suitable truck is available, it gets assigned to the booking.

7. **Agency Integration:**
   - The system involve agencies responsible for managing the fleet of trucks. Funds from logistic's users wallet is transferred to the agency's account for a successful booking.

8. **Cancellation and Refund:**
   - Logistic Users can cancel their bookings, and the system processes refunds based on a predefined cancellation policy. A portion of the booking amount is retained and the remaining is transfered to the logistic user wallet.

9. **Truck Assignment:**
   - The system assigns trucks to bookings, updating their status as "in progress" or "completed." It also marks trucks as available or unavailable.



11. **Security Measures:**
    - To ensure data security and user privacy, the system incorporates authentication, authorization, and secure communication protocols.


12. **Email Notifications:**
    - Automated email notifications are sent to users at the stages of the booking process, keeping them informed about the status of their reservations, we used **google's SMTP protocols for sending the email** to the logistics user's email server.

## Concept Stack

**Core Java:** Inheritance, Polymorphism, Data Encapsulation, Exception Handling, Method Overloading and overridding etc...

**Advance Java:** MultiThreading, Java MailAPI, Java Activation Framework


## Dependencies to Run 

To run this project, you will need to add the following jar files to your class path

- java mail API
[https://www.oracle.com/java/technologies/javamail-api.html]
- java activation framework
[https://www.oracle.com/java/technologies/downloads.html]




## Steps to Run


To run the Truck Reservation System project, follow these steps:

1. **Download the Project:**
   - Download the project files to your local machine. Ensure that you have access to the `Main.java` file and associated resources.


3. **Open Command Line or Terminal:**
   - Open a command line (Windows Command Prompt, PowerShell, or Linux/Mac Terminal) in the directory where you have saved the project files.

4. **Compile the Code:**
   - Use the `javac` command to compile the `Main.java` file. Execute the following command and press Enter:
     ```bash
     javac Main.java
     ```

5. **Run the Application:**
   - After successful compilation, run the application with the `java` command. Enter the following command and press Enter:
     ```bash
     java Main
     ```

6. **Follow On-Screen Instructions:**
   - The application will start, and you will be prompted to Sign-Up or Log in. First Sign Up and then Login by providing the necessary credentials based on the user type (Logistics or Agency) and follow the on-screen instructions.

7. **Explore Features:**
   - Explore the various features of the Truck Reservation System.

8. **Exit the Application:**
   - When you are finished, follow the on-screen instructions to log out or exit the application.

Note: Ensure accurate data input during the booking process. Enjoy using the Truck Reservation System!
## Feedback

If you have any feedback, please reach out to us at truckreservationsystem@gmail.com


## Authors

- Madumitha E
- Micheal Berdinanth M
- Nandhalal S

