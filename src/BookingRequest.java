// Class BookingRequest
class BookingRequest {
    private String truckType;

    public BookingRequest(String truckType) {
        this.truckType = truckType;
    }
  public String getTruckType() {
      return truckType;
  }

  public void setTruckType(String truckType) {
      this.truckType = truckType;
  }
}