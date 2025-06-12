import java.util.*;
class Truck {
    private String truckID;
    private String type;
    private boolean available;
    private String ownerName;
    public Truck(String truckID, String type,String OwnerName,boolean availability) {
        this.truckID = truckID;
        this.type = type;
        this.ownerName=OwnerName;
        this.available = availability;
    }

    public String getTruckID() {
        return truckID;
    }
  public void setAvailable(boolean available) {
      this.available = available;
  }

  public String getTruckType() {
      return type;
  }
  public String getOwnerName() {
      return ownerName;
  }
  public boolean isAvailable() {
      return available;
  }

  public String toString() {
      return truckID;
  }
}