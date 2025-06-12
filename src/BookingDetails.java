import java.text.*;
import java.util.*;

public class BookingDetails {
    private String username; 
    private String from;
    private String to;
    private String truckType;
    private String loadDescription;
    private Date date;
    private double weight;
    private double distance;
    private boolean inProgress;
    private String assignedTruckID;
    private double acceptedPrice;
    private String bookedEmailId;
    // Constructor without username
    public BookingDetails(String from, String to, String truckType, String loadDescription, Date date, double weight, double distance,double price) {
        this.from = from;
        this.to = to;
        this.truckType = truckType;
        this.loadDescription = loadDescription;
        this.date = date;
        this.weight = weight;
        this.distance = distance;
        this.inProgress = true;
        this.acceptedPrice = price;
        
      
    }

    // Constructor with username
    public BookingDetails(String username, String from, String to, String truckType, String loadDescription, Date date, double weight, double distance,double price) {
        this.username = username;
        this.from = from;
        this.to = to;
        this.truckType = truckType;
        this.loadDescription = loadDescription;
        this.date = date;
        this.weight = weight;
        this.distance = distance;
        this.inProgress = true;
        this.acceptedPrice = price;
    }
    
    public BookingDetails(String username, String from, String to, String truckType, String loadDescription, Date date, double weight, double distance,double price, String bookedMailId) {
        this.username = username;
        this.from = from;
        this.to = to;
        this.truckType = truckType;
        this.loadDescription = loadDescription;
        this.date = date;
        this.weight = weight;
        this.distance = distance;
        this.inProgress = true;
        this.acceptedPrice = price;
        this.bookedEmailId=bookedMailId;
    }

    // Getter for username
    public String getUsername() {
        return this.username;
    }

    // Setter for username
    public void setUsername(String username) {
        this.username = username;
    }

  public String getFrom() {
      return this.from;
  }
  public String getSource() {
      return this.from;
  }
  
  public String getBookedEmailId() {
	  return this.bookedEmailId;
  }
  
  public void setBookedEmailId(String mailId) {
	  this.bookedEmailId=mailId;
  }
  
  public String getTo() {
      return this.to;
  }

  public String getTruckType() {
      return this.truckType;
  }

  public String getLoadDescription() {
      return this.loadDescription;
  }
  public boolean isInProgress() {
      return inProgress;
  }
  public String getAssignedTruckID() {
      return assignedTruckID;
  }

  public void setAssignedTruckID(String assignedTruckID) {
      this.assignedTruckID = assignedTruckID;
  }
  public void setInProgress(boolean inProgress) {
      this.inProgress = inProgress;
  }
  public Date getDate() {
      return this.date;
  }

  public double getWeight() {
      return this.weight;
  }

  public double getDistance() {
      return this.distance;
  }
  public double getPrice() {
      return acceptedPrice;
  }
  public void setPrice(double acceptedPrice) {
      this.acceptedPrice = acceptedPrice;
  }

   
    @Override
    public String toString() {
        return "BookingDetails{" +
                "username='" + username + '\'' +
                ", from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", truckType='" + truckType + '\'' +
                ", loadDescription='" + loadDescription + '\'' +
                ", date=" + new SimpleDateFormat("yyyy-MM-dd").format(date) +
                ", weight=" + weight +
                ", distance=" + distance +
                '}';
    }
}
