package nestexpress.nest.services;

import org.hibernate.Session;
import org.springframework.stereotype.Service;

import nestexpress.nest.entity.Orders;
import nestexpress.nest.dao.OrdersDAO;

import java.util.List;

@Service
public class OrdersService {

    private static OrdersDAO ordersDao;

    public OrdersService() {
        ordersDao = new OrdersDAO();
    }

    // Inserts a new order list.
    public void insertOrder(Orders order) {
        Session currentSession = ordersDao.openCurrentSessionwithTransaction();
        ordersDao.insertOrder(order, currentSession);
        ordersDao.closeCurrentSessionwithTransaction(currentSession);
    }

    // Gets all uncompleted orders.
    public Orders getUncompletedOrder(int userId) {
        Session currentSession = ordersDao.openCurrentSession();
        Orders userOrder       = ordersDao.getUncompletedOrder(userId, currentSession);
        ordersDao.closeCurrentSession(currentSession);
        return userOrder;
    }

    // Get all completed orders.
    public List<Orders> getCompletedOrders(int userId) {
        Session currentSession  = ordersDao.openCurrentSession();
        List<Orders> userOrders = ordersDao.getCompletedOrders(userId, currentSession);
        ordersDao.closeCurrentSession(currentSession);
        return userOrders;
    }

    // Updates user orders.
    public void updateOrders(Orders order,  double userBalance, int userId) {
        Session currentSession = ordersDao.openCurrentSessionwithTransaction();
        ordersDao.updateOrders(order, userBalance, userId, currentSession);
        ordersDao.closeCurrentSessionwithTransaction(currentSession);
    }
}