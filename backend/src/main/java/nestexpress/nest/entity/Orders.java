package nestexpress.nest.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import nestexpress.nest.interfaces.IOrders;

@Entity
@Table(name="[orders]")
public class Orders implements IOrders {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="o_id")
    private int orderId;

    @JoinColumn(name="fk_user_id", referencedColumnName="u_id", table="user")
    @Column(name="u_id")
    private int userId;

    @Column(name="is_ordered", nullable=false)
    @Type(type="boolean")
    private Boolean isOrdered;

    @Column(name="order_date")
    private String orderDate;

    @Column(name="delivery_location")
    private String location;

    @Column(name="order_total")
    private double total;

    public Orders() {
        super();
    }

    public Orders(int orderId, int userId, boolean isOrdered) {
        this.isOrdered = isOrdered;
        this.userId    = userId;
        this.isOrdered = isOrdered;
    }
    public Orders(int orderId, int userId, boolean isOrdered, String orderDate, String location, double total) {
        this.isOrdered = isOrdered;
        this.userId    = userId;
        this.isOrdered = isOrdered;
        this.orderDate = orderDate;
        this.location  = location;
        this.total     = total;
    }

    public Orders(int userId) {
        this.userId    = userId;
        this.isOrdered = false;
    }

    // Gets the order ID.
    @Override
    public int getOrderId() {
        return orderId;
    }

    // Gets the user ID.
    @Override
    public int getUserId() {
        return userId;
    }

    // Gets the status of an order.
    @Override
    public boolean getIsOrdered() {
        return isOrdered;
    }

    // Gets the date an order was placed.
    @Override
    public String getOrderDate() {
        return orderDate;
    }

    // Gets the location of delivery.
    @Override
    public String getLocation() {
        return location;
    }

    // Gets the order total.
    @Override
    public double getTotal() {
        return total;
    }

    // Sets the user ID.
    @Override
    public void setUserId(int userId) {
        this.userId = userId;
    }

    // Sets the status of an order.
    @Override
    public void setIsOrdered(boolean isOrdered) {
        this.isOrdered = isOrdered;
    }

    // Sets the order ID.
    @Override
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    // Sets the date an order was placed.
    @Override
    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    // Sets the location of delivery.
    @Override
    public void setLocation(String location) {
        this.location = location;
    }

    // Sets the order total.
    @Override
    public void setTotal(double total) {
        this.total = total;
    }
}