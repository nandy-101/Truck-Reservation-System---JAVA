import java.util.*;
class TruckOwner extends User {
    private List<Truck> ownedTrucks;
    public void displayMyTrucks(){
        for(Truck truck: ownedTrucks){
            System.out.println(truck);
        }
    }
    public TruckOwner(String username, String password) {
        super(username, password);
        this.ownedTrucks = new ArrayList<>();
    }
    
    public TruckOwner(String username, String password, String mailId) {
        super(username, password, mailId);
        this.ownedTrucks = new ArrayList<>();
    }

    

    public void addTruck(Truck truck) {
        // Logic
        ownedTrucks.add(truck);
        System.out.println("Truck added: " + truck.getTruckID());
    }

    public void removeTruck(Truck truck) {
        // Logic 
        ownedTrucks.remove(truck);
        System.out.println("Truck removed: " + truck.getTruckID());
    }

    public List<TruckService> viewServices(Truck truck) {
        // Logic to view services for a truck
        return Collections.emptyList();
    }

  public String toString()
  {
    return super.getUsername();
  }
}
