package nestexpress.nest.services;

import org.hibernate.Session;
import org.springframework.stereotype.Service;

import nestexpress.nest.dao.UserDAO;
import nestexpress.nest.entity.User;

@Service
public class UserService {

    private static UserDAO userDao;

    public UserService() {
        userDao = new UserDAO();
    }

    public void insertUser(User user) {
        Session currentSession = userDao.openCurrentSessionwithTransaction();
        userDao.insertUser(user, currentSession);
        userDao.closeCurrentSessionwithTransaction(currentSession);
    }

    public void updateUser(User user) {
        Session currentSession = userDao.openCurrentSessionwithTransaction();
        userDao.updateUser(user, currentSession);
        userDao.closeCurrentSessionwithTransaction(currentSession);
    }

    public User findById(int userId) {
        Session currentSession = userDao.openCurrentSession();
        User user = userDao.findById(userId, currentSession);
        userDao.closeCurrentSession(currentSession);
        return user;
    }

    // Returns false if the user does not exist, otherwise true if the user exists
    public User findByUsername(String username) {
        Session currentSession = userDao.openCurrentSession();
        User userExists = userDao.findByUsername(username,currentSession);
        userDao.closeCurrentSession(currentSession);
        return userExists;
    }

    // Gets the the users balance after calculating the cost for their purchase.
    public double getTotalPrice(int userId, int orderId, double totalCost) {
        Session currentSession = userDao.openCurrentSessionPrice();
        double price = userDao.getTotalPrice(userId, orderId, totalCost, currentSession);
        userDao.closeCurrentSession(currentSession);
        return price;
    }

    // Gets the total of all the products in an order.
    public double getProductTotal(int userId, int orderId) {
        Session currentSession = userDao.openCurrentSessionPrice();
        double price = userDao.getProductTotal(userId, orderId, currentSession);
        userDao.closeCurrentSession(currentSession);
        return price;
    }

    public double getBalance(int userId) {
        Session currentSession = userDao.openCurrentSession();
        double balance = userDao.getBalance(userId, currentSession);
        userDao.closeCurrentSession(currentSession);
        return balance;
    }

    public UserDAO userDao() {
        return userDao;
    }
}