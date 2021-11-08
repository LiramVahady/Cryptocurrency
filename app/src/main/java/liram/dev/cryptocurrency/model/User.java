package liram.dev.cryptocurrency.model;

public class User {

    private String userName;
    private String password;
    private String userID;
    private double userBalance;

    public User(){}

    public User(String userName, String password, String userID, double userBalance) {
        this.userName = userName;
        this.password = password;
        this.userID = userID;
        this.userBalance = userBalance;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) { this.userName = userName; }
    public String getPassword() { return password; }
    public String getUserID() { return userID; }
    public double getUserBalance() { return userBalance; }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", userID='" + userID + '\'' +
                ", UserBalance='" + userBalance + '\'' +
                '}';
    }
}
