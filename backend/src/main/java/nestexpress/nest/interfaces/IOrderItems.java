package nestexpress.nest.interfaces;

public interface IOrderItems {
    public int getOrderItemId(); // Gets the ID for the item in the order.
    public int getOrderId();     // Gets the ID of the order.
    public int getProductId();   // Gets the the product ID.
    public int getQuantity();    // Gets the quantity of an item in the order.

    public void setOrderId(int orderId);     // Sets the order ID.
    public void setProductId(int productId); // Sets the product ID.
    public void setQuantity(int quantity);   // Sets the quantity of an item in the order.
}