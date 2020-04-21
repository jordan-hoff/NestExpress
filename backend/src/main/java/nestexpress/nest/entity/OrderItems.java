package nestexpress.nest.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import nestexpress.nest.interfaces.IOrderItems;

@Entity
@Table(name="[order_items]")
public class OrderItems implements IOrderItems {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="oi_id")
    private int orderItemId;

    @Column(name="p_id")
    private int productId;

    @Column(name="o_id")
    private int orderId;

    @Column(name="quantity")
    private int quantity;

    @OneToOne
    @JoinColumn(name="p_id", insertable=false, updatable=false)
    private Product product;

    public OrderItems() {
        super();
    }

    public OrderItems(Integer productId, Integer orderId,
                        int quantity, Product product) {
        this.productId = productId;
        this.orderId   = orderId;
        this.quantity  = quantity;
        this.product   = product;
    }

    public OrderItems(Integer productId, Integer orderId, int quantity) {
        this.productId = productId;
        this.orderId   = orderId;
        this.quantity  = quantity;

    }

    public OrderItems(int productId, int quantity) {
        this.productId = productId;
        this.quantity  = quantity;
    }

    // Gets the ID for the item in the order.
    @Override
    public int getOrderItemId() {
        return orderItemId;
    }

    // Gets the ID of the order.
    @Override
    public int getOrderId() {
        return orderId;
    }

    // Gets the the product ID.
    @Override
    public int getProductId() {
        return productId;
    }

    // Gets the quantity of an item in the order.
    @Override
    public int getQuantity() {
        return quantity;
    }

    // Sets the order ID.
    @Override
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    // Sets the product ID.
    @Override
    public void setProductId(int productId) {
        this.productId = productId;
    }

    // Sets the quantity of an item in the order.
    @Override
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    // Gets the product.
    public Product getProduct() {
        return product;
    }
}