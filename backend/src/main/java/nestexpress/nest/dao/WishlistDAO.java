package nestexpress.nest.dao;

import java.util.List;

import javax.persistence.Query;

import org.hibernate.Session;

import org.springframework.stereotype.Component;

import nestexpress.nest.entity.Wishlist;

@Component
public class WishlistDAO {

    public WishlistDAO() {
        super();
    }

    public Session openCurrentSession() {
        return HibernateUtil.getSessionFactory().openSession();
    }

    public Session openCurrentSessionWithTransaction() {
        Session currentSession = HibernateUtil.getSessionFactory().openSession();
        currentSession.beginTransaction();
        return currentSession;
    }

    public void closeCurrentSession(Session currentSession) {
        currentSession.close();
    }

    public void closeCurrentSessionWithTransaction(Session currentSession) {
        currentSession.getTransaction().commit();
        currentSession.close();
    }

    public void insertToWishlist(Wishlist wishlist, Session currentSession) {
        currentSession.save(wishlist);
    }

    // Gets all wishlist items for a specific user;
    @SuppressWarnings("unchecked")
    public List<Wishlist> getWishlistItems(int userId, Session currentSession) {
        return (List<Wishlist>) currentSession.createQuery("from Wishlist where u_id=:userId")
            .setParameter("userId", userId)
            .getResultList();
    }

    // Deletes a single wishlist item.
    public void deleteWishlistItem(int userId, int productId, Session currentSession) {
        Query query = currentSession.createQuery("delete from Wishlist where u_id = :userId and p_id = :productId")
                                        .setParameter("userId", userId)
                                        .setParameter("productId", productId);

        query.executeUpdate();
    }

    // Deletes all items from the wishlist.
    public void deleteAllWishlistItems(int userId, Session currentSession) {
        Query query = currentSession.createQuery("delete from Wishlist where u_id = :userId")
                                        .setParameter("userId", userId);

        query.executeUpdate();
    }
}