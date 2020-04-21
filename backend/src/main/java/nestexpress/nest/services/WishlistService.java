package nestexpress.nest.services;

import java.util.List;

import org.hibernate.Session;
import org.springframework.stereotype.Service;

import nestexpress.nest.dao.WishlistDAO;
import nestexpress.nest.entity.Wishlist;

@Service
public class WishlistService {

    private static WishlistDAO wishlistDao;

    public WishlistService() {
        wishlistDao = new WishlistDAO();
    }

    public void insertToWishlist(Wishlist wishlist) {
        Session currentSession = wishlistDao.openCurrentSessionWithTransaction();
        wishlistDao.insertToWishlist(wishlist, currentSession);
        wishlistDao.closeCurrentSessionWithTransaction(currentSession);
    }
    public List<Wishlist> getWishlistItems(int userId) {
        Session currentSession = wishlistDao.openCurrentSession();
        List<Wishlist> wishlistItems = wishlistDao.getWishlistItems(userId, currentSession);
        wishlistDao.closeCurrentSession(currentSession);

        return wishlistItems;
    }

    public void deleteWishlistItem(int userId, int productId) {
        Session currentSession = wishlistDao.openCurrentSessionWithTransaction();
        wishlistDao.deleteWishlistItem(userId, productId, currentSession);
        wishlistDao.closeCurrentSessionWithTransaction(currentSession);
    }

    public void deleteAllWishlistItems(int userId) {
        Session currentSession = wishlistDao.openCurrentSessionWithTransaction();
        wishlistDao.deleteAllWishlistItems(userId, currentSession);
        wishlistDao.closeCurrentSessionWithTransaction(currentSession);
    }
}