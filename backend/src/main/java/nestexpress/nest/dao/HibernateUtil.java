package nestexpress.nest.dao;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import nestexpress.nest.entity.Dorm;
import nestexpress.nest.entity.Image;
import nestexpress.nest.entity.OrderItems;
import nestexpress.nest.entity.Orders;
import nestexpress.nest.entity.Product;
import nestexpress.nest.entity.User;
import nestexpress.nest.entity.Wishlist;

public class HibernateUtil {

    private static StandardServiceRegistry standardServiceRegistry;
    private static SessionFactory sessionFactory;

    static {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration().configure().addAnnotatedClass(Image.class)
                    .addAnnotatedClass(Dorm.class).addAnnotatedClass(OrderItems.class).addAnnotatedClass(Orders.class)
                    .addAnnotatedClass(Product.class).addAnnotatedClass(User.class).addAnnotatedClass(Wishlist.class);

                // Creates StandardServiceRegistry.
                standardServiceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties())
                    .configure()
                    .build();
                // Creates SessionFactory.
                sessionFactory = configuration.buildSessionFactory(standardServiceRegistry);
            } catch (Exception e) {
                    e.printStackTrace();

                    if (standardServiceRegistry != null) {
                        StandardServiceRegistryBuilder.destroy(standardServiceRegistry);
                    }
                }
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}