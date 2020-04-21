package nestexpress.nest.interfaces;

public interface IWishlist {
    public int getWishlistId(); // Gets the wishlist ID.
    public int getProductId();  // Gets the product ID.
    public int getUserId();     // Gets the user ID.

    public void setProductId(int productId); // Sets the product ID.
    public void setUserId(int userId);       // Sets the user ID.
}