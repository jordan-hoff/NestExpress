package nestexpress.nest.interfaces;

import java.util.Set;

import nestexpress.nest.entity.Image;

public interface IProduct {
    public int        getProductId();   // Gets the prodcut ID.
    public String     getProductName(); // Gets the product name.
    public double     getPrice();       // Gets the product price.
    public int        getCategory();    // Gets the category of a product.
    public String     getDescript();    // Gets the product description.
    public Set<Image> getImage();       // Gets the images of a product.
}