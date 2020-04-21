package nestexpress.nest.dao;

import org.hibernate.Session;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpServerErrorException;


import nestexpress.nest.entity.User;

@Component
public class UserDAO {

    static final int EMPTY = 0;

    public UserDAO() {
        super();
    }

    public Session openCurrentSession() {
        return HibernateUtil.getSessionFactory().openSession();
    }

    public Session openCurrentSessionPrice() {
        return HibernateUtil.getSessionFactory().openSession();
    }

    public Session openCurrentSessionwithTransaction() {
        Session currentSession = HibernateUtil.getSessionFactory().openSession();
        currentSession.beginTransaction();
        return currentSession;
    }

    public void closeCurrentSession(Session currentSession) {
        currentSession.close();
    }

    public void closeCurrentSessionwithTransaction(Session currentSession) {
        currentSession.getTransaction().commit();
        currentSession.close();
    }

    // Creates a new user.
    public void insertUser(User user, Session currentSession) {
        try {
            currentSession.save(user);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // Updates the current user.
    public void updateUser(User user, Session currentSession) {
        try {
            currentSession.update(user);
        } catch (Exception e) {
            throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Searches for a user by ID.
    public User findById(int userId, Session currentSession) {
        try {
            return (User) currentSession.get(User.class, userId);
        } catch (Exception e) {
            throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Search for a username.
    // Returns a user if the user exists, else it returns an empty user.
    public User findByUsername(String username, Session currentSession) {

        try{
            User user = (User) currentSession
                            .createQuery("from User where username=:username", User.class)
                            .setParameter("username", username)
                            .uniqueResult();

            if (user == null)
                return null;
            else
                return user;
        } catch (Exception e) {
            return null;
        }
    }

    // Checks to make sure the user has enough funds in their account.
    public double getTotalPrice(int userId, int orderId, double totalCost, Session currentSession) {

        try {
            return (double) currentSession.createNativeQuery("SELECT round(user_account.balance - " +
                                ":totalCost, 2) from [user] as user_account " +
                                "join [orders] on user_account.u_id=orders.u_id " +
                                "join [order_items] on order_items.o_id=:orderId " +
                                "join [product] on order_items.p_id=product.p_id " +
                                "where user_account.u_id=:userId and orders.o_id=:orderId " +
                                "GROUP BY user_account.balance")
                                    .setParameter("totalCost", totalCost)
                                    .setParameter("userId", userId)
                                    .setParameter("orderId", orderId)
                                    .getSingleResult();
        } catch (Exception e) {
            throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Gets the total cart price.
    public double getProductTotal(int userId, int orderId, Session currentSession) {

        try {
            return (double) currentSession.createNativeQuery("SELECT sum(product.price * order_items.quantity) "+
                            "from [user] as user_account " +
                            "join [orders] on user_account.u_id=orders.u_id " +
                            "join [order_items] on order_items.o_id=:orderId " +
                            "join [product] on order_items.p_id=product.p_id " +
                            "where user_account.u_id=:userId and orders.o_id=:orderId GROUP BY user_account.balance")
                               .setParameter("userId", userId)
                               .setParameter("orderId", orderId)
                               .getSingleResult();
        } catch (Exception e) {
            return 0.00;
        }
    }

    // Gets the user balance.
    public double getBalance(int userId, Session currentSession) {

        try {
            return (double) currentSession.createNativeQuery("SELECT user_account.balance from [user] " +
                            "as user_account where user_account.u_id=:userId")
                                .setParameter("userId", userId)
                                .getSingleResult();
        } catch (Exception e) {
            throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}