package nestexpress.nest.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;

import nestexpress.nest.interfaces.IProduct;

@Entity
@Table(name="[product]")
public class Product implements IProduct {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="p_id")
    private int productId;

    @Column(name="category")
    private int category;

    @Column(name="price")
    private double price;

    @Column(name="product_name")
    private String productName;

    @Column(name="descript")
    private String description;

    @OneToMany(fetch=FetchType.EAGER, mappedBy="product", cascade=CascadeType.MERGE)
    private Set<Image> image;

    public Product() {
        super();
    }

    public Product(String productName, double price, int category, String description,
                    Set<Image> image, OrderItems orderItems) {
        this.category    = category;
        this.price       = price;
        this.productName = productName;
        this.description = description;
        this.image       = image;
    }

    public Product(String productName, double price, int category, String description) {
        this.category    = category;
        this.price       = price;
        this.productName = productName;
        this.description = description;
    }

    // Gets the prodcut ID.
    @Override
    public int getProductId() {
        return productId;
    }

    // Gets the product name.
    @Override
    public String getProductName() {
        return productName;
    }

    // Gets the product price.
    @Override
    public double getPrice() {
        return price;
    }

    // Gets the category of a product.
    @Override
    public int getCategory() {
        return category;
    }

    // Gets the product description.
    @Override
    public String getDescript() {
        return description;
    }

    // Gets the images of a product.
    @Override
    public Set<Image> getImage() {
        return image;
    }

}