package nestexpress.nest.dao;

import java.util.List;

import javax.persistence.Query;

import org.hibernate.Session;

import org.springframework.stereotype.Component;

import nestexpress.nest.entity.OrderItems;

@Component
public class OrderItemsDAO {

    private final int EMPTY = 0;

    public OrderItemsDAO() {
        super();
    }

    public Session openCurrentSession() {
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

    // Gets the quantity of items from the database.
    public OrderItems getQuantityOfItem(OrderItems orderItems, Session currentSession) {
        Query query = currentSession.createQuery("from OrderItems where p_id=:productId and o_id=:orderId")
                                        .setParameter("productId", orderItems.getProductId())
                                        .setParameter("orderId", orderItems.getOrderId());

        return (OrderItems) query.getSingleResult();
    }

    // Inserts a new item into the user order unless the item already exists.
    // If the item exists, the update quantity method is called.
    public void insertItem(OrderItems orderItems, Session currentSession) {
        OrderItems items = (OrderItems) currentSession.createQuery("from OrderItems where p_id=:productId and o_id=:orderId")
                                                        .setParameter("productId", orderItems.getProductId())
                                                        .setParameter("orderId", orderItems.getOrderId())
                                                        .uniqueResult();

        if(items == null)
            currentSession.save(orderItems);
        else {
            items.setQuantity(items.getQuantity() + orderItems.getQuantity());
            updateQuantity(items, currentSession);
        }
    }

    // Gets all items for a specific order then either updates the quantity or
    // adds new items.
    public void insertListItems(List<OrderItems> itemList, List<OrderItems> currentItems, Session currentSession) {
        for (OrderItems item : itemList) {

            // Checks for already existing items and adds them to the account.
            for (int counter = 0; counter < currentItems.size(); counter++) {
                if (currentItems.get(counter).getProductId() == item.getProductId()) {
                    int tempQuantity = item.getQuantity();
                    item = currentItems.get(counter);
                    item.setQuantity(item.getQuantity() + tempQuantity);
                }
            }
            currentSession.saveOrUpdate(item);
        }
    }

    // Updates the quantity of items.
    public void updateQuantity(OrderItems orderItems, Session currentSession) {
        Query query = currentSession.createQuery("update OrderItems set quantity=:quantity where o_id=:oid and p_id=:pid")
                                        .setParameter("quantity", orderItems.getQuantity())
                                        .setParameter("oid", orderItems.getOrderId())
                                        .setParameter("pid", orderItems.getProductId());

        query.executeUpdate();
    }

    // Gets all items for a specific order.
    @SuppressWarnings("unchecked")
    public List<OrderItems> getAllItems(int orderId, Session currentSession) {
        Query query = currentSession.createQuery("from OrderItems where o_id=:orderId")
                                        .setParameter("orderId", orderId);

        return query.getResultList();
    }

    // Gets the quantity of all items.
    public int getQuantity(int orderId, Session currentSession) {
        Query query = currentSession.createQuery("Select sum(quantity) from OrderItems where o_id=:orderId")
                                        .setParameter("orderId", orderId);
        Long itemQuantity = (Long) query.getSingleResult();

        if (itemQuantity == null)
            return EMPTY;
        else
            return itemQuantity.intValue();
    }

    // Deletes items from a specific order.
    public void deleteItem(OrderItems orderItem, Session currentSession) {
        Query query = currentSession.createQuery("delete FROM OrderItems where o_id=:orderId and p_id=:productId")
                                        .setParameter("orderId", orderItem.getOrderId())
                                        .setParameter("productId", orderItem.getProductId());

        query.executeUpdate();
    }

    public void deleteAllItems(int orderId, Session currentSession) {
        Query query = currentSession.createQuery("delete from OrderItems where o_id=:orderId")
                                        .setParameter("orderId", orderId);

        query.executeUpdate();
    }
}