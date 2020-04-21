package nestexpress.nest.interfaces;

public interface IUser {
    public int    getUserId();     // Gets the user ID.
    public String getUsername();   // Gets the username of an account.
    public String getPassword();   // Gets the password of an account.
    public int    getRoomNumber(); // Gets the user's room number.
    public int    getDorm();       // Gets the user's dorm.
    public double getBalance();    // Gets the user's balance.
    public String getFirstName();  // Gets the user's first name.
    public String getLastName();   // Gets the user's last name.

    public void setUsername(String username);   // Sets the username of an account.
    public void setPassword(String password);   // Sets the password of an account.
    public void setRoomNumber(int roomNumber);  // Sets the room number of an account.
    public void setDorm(int dorm);              // Sets the dorm of an account.
    public void setBalance(double balance);     // Sets the balance of an account.
    public void setFirstName(String firstName); // Sets the first name of a user.
    public void setLastName(String lastName);   // Sets the last name of a user.
}