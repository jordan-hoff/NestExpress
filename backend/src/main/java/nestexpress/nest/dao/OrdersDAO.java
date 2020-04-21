package nestexpress.nest.dao;

import java.util.List;

import javax.persistence.Query;

import org.hibernate.Session;

import org.springframework.stereotype.Component;

import nestexpress.nest.entity.Orders;

@Component
public class OrdersDAO {

    public OrdersDAO() {
        super();
    }

    public Session openCurrentSession() {
        Session currentSession =  HibernateUtil.getSessionFactory().openSession();
        return currentSession;
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

    public void insertOrder(Orders order, Session currentSession) {
        currentSession.save(order);
    }

    public Orders getUncompletedOrder(int userId, Session currentSession) {
        Query query = currentSession.createQuery("from Orders where u_id=:userId and is_ordered=false")
                                        .setParameter("userId", userId);

        return (Orders) query.getSingleResult();
    }

    @SuppressWarnings("unchecked")
    public List<Orders> getCompletedOrders(int userId, Session currentSession) {
        Query query = currentSession.createQuery("from Orders where u_id=:userId and is_ordered=true")
                                        .setParameter("userId", userId);

        return query.getResultList();
    }

    // Updates an order to when completed.
    public void updateOrders(Orders order, double userBalance, int userId, Session currentSession) {
        Query query = currentSession.createNativeQuery("update [orders] set [orders].is_ordered=:isOrdered, " +
                            "[orders].order_total=:total, [orders].delivery_location=:location, [orders].order_date=:orderDate " +
                            "where [orders].o_id=:orderId " +
                            "update [user] set [user].balance=:balance where [user].u_id=:userId")
                                .setParameter("orderDate", order.getOrderDate())
                                .setParameter("location", order.getLocation())
                                .setParameter("total", order.getTotal())
                                .setParameter("isOrdered", order.getIsOrdered())
                                .setParameter("orderId", order.getOrderId())
                                .setParameter("balance", userBalance)
                                .setParameter("userId", userId);

        query.executeUpdate();
    }
}