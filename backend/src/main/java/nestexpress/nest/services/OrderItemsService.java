package nestexpress.nest.services;

import java.util.List;

import org.hibernate.Session;
import org.springframework.stereotype.Service;

import nestexpress.nest.entity.OrderItems;
import nestexpress.nest.dao.OrderItemsDAO;

@Service
public class OrderItemsService {

    private static OrderItemsDAO orderItemsDao;

    public OrderItemsService() {
        orderItemsDao = new OrderItemsDAO();
    }

    // Gets a specific item to be used for quantity.
    public OrderItems getQuantityOfItem(OrderItems orderItems) {
        Session currentSession = orderItemsDao.openCurrentSession();
        OrderItems orderItem = orderItemsDao.getQuantityOfItem(orderItems, currentSession);
        orderItemsDao.closeCurrentSession(currentSession);
        return orderItem;
    }

    // Inserts a new item.
    public void insertItem(OrderItems orderItems) {
        Session currentSession = orderItemsDao.openCurrentSessionwithTransaction();
        orderItemsDao.insertItem(orderItems, currentSession);
        orderItemsDao.closeCurrentSessionwithTransaction(currentSession);
    }

    // Updates the quantity of an item.
    public void updateQuantity(OrderItems orderItems) {
        Session currentSession = orderItemsDao.openCurrentSessionwithTransaction();
        orderItemsDao.updateQuantity(orderItems, currentSession);
        orderItemsDao.closeCurrentSessionwithTransaction(currentSession);
    }

    // Gets all the items in an order.
    public List<OrderItems> getAllItems(int orderId) {
        Session currentSession = orderItemsDao.openCurrentSession();
        List<OrderItems> itemList = orderItemsDao.getAllItems(orderId, currentSession);
        orderItemsDao.closeCurrentSession(currentSession);
        return itemList;
    }

    // Gets the item quantity.
    public int getQuantity(int orderId) {
        Session currentSession = orderItemsDao.openCurrentSession();
        int itemQuantity = orderItemsDao.getQuantity(orderId, currentSession);
        orderItemsDao.closeCurrentSession(currentSession);
        return itemQuantity;
    }

    // Deletes an individual item.
    public void deleteItem(OrderItems orderItem) {
        Session currentSession = orderItemsDao.openCurrentSessionwithTransaction();
        orderItemsDao.deleteItem(orderItem, currentSession);
        orderItemsDao.closeCurrentSessionwithTransaction(currentSession);
    }

    // Deletes all items from the order.
    public void deleteAllItems(int orderId) {
        Session currentSession = orderItemsDao.openCurrentSessionwithTransaction();
        orderItemsDao.deleteAllItems(orderId, currentSession);
        orderItemsDao.closeCurrentSessionwithTransaction(currentSession);
    }

    // Inserts items into the database.
    public void insertListItems(List<OrderItems> orderItems, int orderId) {
        Session currentSession = orderItemsDao.openCurrentSession();
        List<OrderItems> itemList = orderItemsDao.getAllItems(orderId, currentSession);
        orderItemsDao.closeCurrentSession(currentSession);

        Session currentSessionwithTransaction = orderItemsDao.openCurrentSessionwithTransaction();
        orderItemsDao.insertListItems(orderItems, itemList, currentSessionwithTransaction);
        orderItemsDao.closeCurrentSessionwithTransaction(currentSessionwithTransaction);
    }
}