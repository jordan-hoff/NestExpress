package nestexpress.nest.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.lang.Integer;
import java.lang.Double;
import java.util.Map;

import nestexpress.nest.interfaces.IUser;;

@Entity
@Table(name="[user]")
public class User implements IUser {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="u_id")
    private int userId;

    @Column(name="username")
    private String username;

    @Column(name="password")
    private String password;

    @Column(name="room_number", nullable=true)
    private int roomNumber;

    @Column(name="dorm", nullable=false)
    private int dorm;

    @Column(name="balance", nullable=true)
    private double balance;

    @Column(name="f_name")
    private String firstName;

    @Column(name="l_name")
    private String lastName;

    public User() {
        super();
    }

    @JsonCreator
    public User(@JsonProperty("username") String username,
                @JsonProperty("password") String password,
                @JsonProperty("roomNumber") int roomNumber,
                @JsonProperty("dorm") int dorm,
                @JsonProperty("balance") double balance,
                @JsonProperty("first_name") String firstName,
                @JsonProperty("last_name") String lastName) {
        this.username   = username;
        this.password   = password;
        this.roomNumber = roomNumber;
        this.dorm       = dorm;
        this.balance    = balance;
        this.firstName  = firstName;
        this.lastName   = lastName;
    }

    @JsonCreator
    public User(Map<String,String> user) {
        this.username = user.get("username");
        this.password = user.get("password");

        try {
            if(user.get("roomNumber") != null)
                this.roomNumber = Integer.valueOf(user.get("roomNumber"));
            else
                this.roomNumber = 0;
        }
        catch(NumberFormatException e) {
            this.roomNumber = 0;
        }

        try {
            if(user.get("dorm") != null)
                this.dorm = Integer.valueOf(user.get("dorm"));
            else
                this.dorm = 0;
        }
        catch(NumberFormatException e) {
            this.dorm = 0;
        }

        try {
            if(user.get("balance") != null)
                this.balance = Double.valueOf(user.get("balance"));
            else
                this.balance = 0.0;
        }
        catch(NumberFormatException e) {
            this.balance = 0.0;
        }
        catch(NullPointerException e) {
            this.balance = 0.0;
        }

            this.firstName  = user.get("first_name");
            this.lastName   = user.get("last_name");
    }

    // Gets the user ID.
    @Override
    public int getUserId() {
        return userId;
    }

    // Gets the username of an account.
    @Override
    public String getUsername() {
        return username;
    }

    // Gets the password of an account.
    @Override
    public String getPassword() {
        return password;
    }

    // Gets the user's room number.
    @Override
    public int getRoomNumber() {
        return roomNumber;
    }

    // Gets the user's dorm.
    @Override
    public int getDorm() {
        return dorm;
    }

    // Gets the user's balance.
    @Override
    public double getBalance() {
        return balance;
    }

    // Gets the user's first name.
    @Override
    public String getFirstName() {
        return firstName;
    }

    // Gets the user's last name.
    @Override
    public String getLastName() {
        return lastName;
    }

    // Sets the username of an account.
    @Override
    public void setUsername(String username) {
        this.username = username;
    }

    // Sets the password of an account.
    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    // Sets the room number of an account.
    @Override
    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    // Sets the dorm of an account.
    @Override
    public void setDorm(int dorm) {
        this.dorm = dorm;
    }

    // Sets the balance of an account.
    @Override
    public void setBalance(double balance) {
        this.balance = balance;
    }

    // Sets the first name of a user.
    @Override
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    // Sets the last name of a user.
    @Override
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}