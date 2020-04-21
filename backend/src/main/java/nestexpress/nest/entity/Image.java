package nestexpress.nest.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import nestexpress.nest.interfaces.IImage;

@Entity
@Table(name="[image_url]")
public class Image implements IImage {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="i_id")
    private int imageId;

    @Column(name="url")
    private String imageUrl;

    @Column(name="p_id")
    private int productId;

    @ManyToOne
    @JoinColumn(name="p_id", insertable=false, updatable=false)
    private Product product;

    public Image() {
        super();
    }

    public Image(int imageId, String imageUrl,
                    int productId, Product product) {
        this.imageId   = imageId;
        this.imageUrl  = imageUrl;
        this.productId = productId;
        this.product   = product;
    }

    // Gets the image ID.
    @Override
    public int getImageId() {
        return imageId;
    }

    // Gets the URL for the image.
    @Override
    public String getImageUrl() {
        return imageUrl;
    }

    // Gets the product ID related to the image.
    @Override
    public int getProductId() {
        return productId;
    }

    // Gets the product.
    public Product getProduct() {
        return product;
    }
}