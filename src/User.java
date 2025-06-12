import java.util.*;

enum ServiceStatus {
  PENDING,
  ASSIGNED,
  COMPLETED,
  CANCELED
}

enum UserType {
    LOGISTICS,
    TRUCK_OWNER,
    AGENCY
}

class User {
  private String username;
  private String password;
  private double walletBalance;
  private UserType userType;
  private String emailId;


  public UserType getUserType() {
      // Assuming userType is a field in your User class.
      return this.userType != null ? this.userType : UserType.LOGISTICS; 
  }

  public void setUserType(UserType userType) {
      this.userType = userType;
  }
  public User(){}
  public User(String username, String password) {
    this.username = username;
    this.password = password;
    //this.walletBalance = 0.0;
    this.userType = UserType.LOGISTICS;
  }
  
  public User(String username, String password, String mailId) {
	    this.username = username;
	    this.password = password;
	    //this.walletBalance = 0.0;
	    this.emailId=mailId;
	    this.userType = UserType.LOGISTICS;
	  }

  
  
  public User(String username, String password, double walletBalance) {
      this.username = username;
      this.password = password;
      this.walletBalance = walletBalance;
    this.userType = UserType.LOGISTICS;
  }

  public void initializeFromUserData(String userData) {
      // Parse the string and initialize the user object
      String[] data = userData.split(",");
      if (data.length >= 3) {
          setUsername(data[0]);
          setPassword(data[1]);
          setUserType(safeValueOf(data[2]));

         // System.out.println("User initialized with data: " + userData);
      }
      if (data.length >= 4) {
          setWalletBalance(Double.parseDouble(data[3]));
          setEmailId(data[4]);
      }
  }


  private static UserType safeValueOf(String userType) {
    for (UserType type : UserType.values()) {
        if (type.name().equalsIgnoreCase(userType.trim())) {
            return type;
        }
    }
    // If no match is found, return a default value
    return UserType.LOGISTICS;
  }
  public String getUsername() {
    return username;
  }
  public void setUsername(String username) {
    this.username = username;
  }
  
  public String getEmailId() {
	  return this.emailId;
  }
  public void setEmailId(String mailId) {
	  this.emailId=mailId;
  }
  
  public double getWalletBalance() {
    return walletBalance;
  }
  public void setWalletBalance(double walletBalance) {
    this.walletBalance = walletBalance;
  }
  public String getPassword() {
    return password;
  }
  public void setPassword(String password) {
    this.password = password;
  }
  public String userDataToString() {
      // Convert user data to a string representation
      // Format: "username,password,userType,walletBalance"
      return getUsername() + "," + getPassword() + "," + getUserType() + "," + getWalletBalance()+ "," + getEmailId();
  }

  

  public void addMoneyToWallet(double amount) {
    walletBalance += amount;
    System.out.println("Added $" + amount + " to the wallet.");
    System.out.println("New balance: $" + walletBalance);
  }

  public void withdrawMoneyFromWallet(double amount) {
    if (walletBalance >= amount) {
      walletBalance -= amount;
      System.out.println("Withdrawn $" + amount + " from the wallet.");
      System.out.println("New balance: $" + walletBalance);
    } else {
      System.out.println("Insufficient funds in the wallet.");
    }
  }

  
}
