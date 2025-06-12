import java.util.*;
import java.io.*;
class TruckService {
    private Truck truck;
    private Logistics logistics;
    private ServiceStatus status;
    private Date completionDate;

    public TruckService(Truck truck, Logistics logistics) {
        this.truck = truck;
        this.logistics = logistics;
        this.status = ServiceStatus.PENDING;
        this.completionDate = null;
    }

  public TruckService(Truck truck, Logistics logistics, ServiceStatus status, Date completionDate) {
      this.truck = truck;
      this.logistics = logistics;
      this.status = status;
      this.completionDate = completionDate;
  }

  public Truck getTruck() {
      return truck;
  }

  public Logistics getLogistics() {
      return logistics;
  }

  public ServiceStatus getStatus() {
      return status;
  }

  public Date getCompletionDate() {
      return completionDate;
  }
}
