package nestexpress.nest.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import nestexpress.nest.interfaces.IDorm;

@Entity
@Table(name="[dorm]")
public class Dorm implements IDorm {

    @Id
    @Column(name="dorm_id")
    private int dormId;

    @Column(name="dorm_name")
    private String dormName;

    @Column(name="num_floors")
    private int numFloors;

    @Column(name="max_room")
    private int maxRoom;

    public Dorm() {
        super();
    }

    public Dorm(int dormId, String dormName,
                int numFloors, int maxRoom) {
        this.dormId    = dormId;
        this.dormName  = dormName;
        this.numFloors = numFloors;
        this.maxRoom   = maxRoom;
    }

    // Gets the dorm ID.
    @Override
    public int getDormId() {
        return dormId;
    }

    // Gets the dorm name.
    @Override
    public String getDormName() {
        return dormName;
    }

    // Gets the number of floors in the dorm.
    @Override
    public int getNumFloors() {
        return numFloors;
    }

    // Gets the highest room number in the dorm.
    @Override
    public int getMaxRoom() {
        return maxRoom;
    }

    // Sets the dorm ID.
    @Override
    public void setDormId(int dormId) {
        this.dormId = dormId;
    }

    // Sets the dorm name.
    @Override
    public void setDormName(String dormName) {
        this.dormName = dormName;
    }

    // Sets the number of floors in the dorm.
    @Override
    public void setNumFloors(int numFloors) {
        this.numFloors = numFloors;
    }

    // Sets the highest room number in the dorm.
    @Override
    public void setMaxRoom(int maxRoom) {
        this.maxRoom = maxRoom;
    }
}