package nestexpress.nest.interfaces;

public interface IDorm {
    public int    getDormId();    // Gets the dorm ID.
    public String getDormName();  // Gets the dorm name.
    public int    getNumFloors(); // Gets the number of floors in the dorm.
    public int    getMaxRoom();   // Gets the highest room number in the dorm.

    public void setDormId(int dormId);        // Sets the dorm ID.
    public void setDormName(String dormName); // Sets the dorm name.
    public void setNumFloors(int numFloors);  // Sets the number of floors in the dorm.
    public void setMaxRoom(int maxRoom);      // Sets the highest room number in the dorm.
}