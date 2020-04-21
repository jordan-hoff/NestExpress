package nestexpress.nest.interfaces;

public interface IOrders {
    public int     getOrderId();   // Gets the order ID.
    public int     getUserId();    // Gets the user ID.
    public boolean getIsOrdered(); // Gets the status of an order.
    public String  getOrderDate(); // Gets the date an order was placed.
    public String  getLocation();  // Gets the location of delivery.
    public double  getTotal();     // Gets the order total.

    public void setUserId(int user);             // Sets the user ID.
    public void setIsOrdered(boolean isOrdered); // Sets the status of an order.
    public void setOrderId(int orderId);         // Sets the order ID.
    public void setOrderDate(String orderDate);  // Sets the date an order was placed.
    public void setLocation(String location);    // Sets the location of delivery.
    public void setTotal(double total);          // Sets the order total.
}