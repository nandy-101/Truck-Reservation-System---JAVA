
class Agency extends User {
    private double walletBalance;

    public Agency(String username, String password) {
        super(username, password);
        this.walletBalance = 0.0;
    }
    
    public Agency(String username, String password,String mailId) {
        super(username, password, mailId);
        this.walletBalance = 0.0;
    }
    
    public Agency(String username, String password, double walletBalance) {

       super(username, password);
        this.walletBalance = walletBalance;
    }
    public BookingResponse respondToBooking(BookingRequest request) {
        // Logic to respond to a booking request
        Truck truck = new Truck("456", "16-Wheeler","", true);
        return new BookingResponse(truck, true);
    }
    
}
