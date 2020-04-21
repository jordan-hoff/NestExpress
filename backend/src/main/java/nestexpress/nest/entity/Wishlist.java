package nestexpress.nest.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import nestexpress.nest.interfaces.IWishlist;

@Entity
@Table(name="[wishlist]")
public class Wishlist implements IWishlist {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="w_id")
    private int wishlistId;

    @Column(name="p_id")
    private int productId;

    @Column(name="u_id")
    private int userId;

    @OneToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="p_id", insertable=false, updatable=false)
    private Product product;

    public Wishlist() {
        super();
    }

    public Wishlist(int wishlistId, int productId, int userId) {
        this.wishlistId = wishlistId;
        this.productId  = productId;
        this.userId     = userId;
    }

    // Gets the wishlist ID.
    @Override
    public int getWishlistId() {
        return wishlistId;
    }

    // Gets the product ID.
    @Override
    public int getProductId() {
        return productId;
    }

    // Gets the user ID.
    @Override
    public int getUserId() {
        return userId;
    }

    // Sets the product ID.
    @Override
    public void setProductId(int productId) {
        this.productId = productId;

    }

    // Sets the user ID.
    @Override
    public void setUserId(int userId) {
        this.userId = userId;
    }

    // Gets the product.
    public Product getProduct() {
        return product;
    }
}