package nestexpress.nest.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.Transaction;

import org.springframework.stereotype.Component;

import nestexpress.nest.entity.OrderItems;
import nestexpress.nest.entity.Product;

@Component
public class ProductDAO {

    public ProductDAO() {
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

    public void closeCurrentSessionWithTransaction(Session currentSession, Transaction currentTransaction) {
        currentTransaction.commit();
        currentSession.close();
    }

    // Searches for a product by ID.
    public Product findById(int productId, Session currentSession) {
        Product searchedProduct = (Product) currentSession.get(Product.class, productId);
        return searchedProduct;
    }

    // Searches for products by keyword.
    @SuppressWarnings("unchecked")
    public List<Product> findByKeyword(String keyword, Session currentSession) {
        Query query = currentSession.createQuery("from Product where keywords like '%' + :keyword " +
                "or keywords like :keyword + '%' or keywords like '%' + :keyword + '%' " +
                "or product_name like '%' + :keyword or product_name like :keyword + '%' " +
                "or product_name like '%' + :keyword + '%'", Product.class)
                    .setParameter("keyword", keyword);

        return query.getResultList();
    }

    // Searches for products by category.
    @SuppressWarnings("unchecked")
    public List<Product> findByCategory(int category, Session currentSession) {
        Query query = currentSession.createQuery("from Product where category=:category")
                                        .setParameter("category", category);

        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<Product> getCategoryRange(int first, int last, int itemQty, Session currentSession) {
        Query query = currentSession.createQuery("from Product where category between :first and :last", Product.class)
                                        .setMaxResults(itemQty)
                                        .setParameter("first", first)
                                        .setParameter("last", last);

        return query.getResultList();
    }

    public List<Product> getAll(Session currentSession) {
        List<Product> resultList = currentSession.createQuery("from Product", Product.class).getResultList();

        return resultList;
    }

    @SuppressWarnings("unchecked")
    public List<Product> getProductRange(int firstId, int lastId, Session currentSession) {
        Query query = currentSession.createQuery("from Product where p_id between :first and :last ", Product.class)
                                        .setParameter("first", firstId)
                                        .setParameter("last", lastId);

        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<Product> getCategoryRange(int firstCategory, int lastCategory, Session currentSession) {
        Query query = currentSession.createQuery("from Product where category between :firstCategory and :lastCategory")
                                        .setParameter("firstCategory", firstCategory)
                                        .setParameter("lastCategory", lastCategory);

        return query.getResultList();
    }

    // Gets the guest users cart items from the database.
    @SuppressWarnings("unchecked")
    public List<Product> getGuestCartItems(Map<String, Map<String, Integer>> guestItems,
                                            HttpServletRequest request, Session currentSession) {
        String queryPrep     = "from Product where p_id=:productId";
        int itemCounter      = 0;
        boolean productAdded = false;

        if (guestItems.size() > 1)
            for (; itemCounter < Integer.valueOf(
                request.getSession(false).getAttribute("itemCount").toString()); itemCounter++) {
                try {
                    if (guestItems.get("guestItem" + itemCounter).get("productId") != null)
                        queryPrep = queryPrep + " or p_id=:productId" + itemCounter;
                } catch (NullPointerException nullPointer) {
                } catch (Exception e) {}
            }

        // Reset item counter for use again.
        itemCounter = 0;

        Query query = currentSession.createQuery(queryPrep, Product.class);

        // Fills to query with the items from the session.
        try {
            if (guestItems.get("guestItem" + itemCounter).get("productId") != null)
                query.setParameter("productId", guestItems.get("guestItem" + itemCounter).get("productId"));
        } catch (NullPointerException e) {
            for (;itemCounter <Integer.valueOf(
                request.getSession(false).getAttribute("itemCount").toString()) && productAdded == false; itemCounter++)
            {
                try {
                    if (guestItems.get("guestItem" + itemCounter).get("productId") != null) {
                        query.setParameter("productId",
                            guestItems.get("guestItem" + itemCounter).get("productId"));
                        productAdded = true;
                    }
                } catch (Exception exception) {
                    System.out.println("error");
                }
            }
        }

        if (guestItems.size() > 1)
            for (itemCounter = 0; itemCounter < Integer.valueOf(
                request.getSession(false).getAttribute("itemCount").toString()); itemCounter++) {
                try {
                    if (guestItems.get("guestItem" + itemCounter).get("productId") != null) {
                        query.setParameter("productId" + itemCounter,
                            guestItems.get("guestItem" + itemCounter).get("productId"));
                    }
                } catch (Exception e) {
                    System.out.println("error");
                }
            }

        return (List<Product>) query.getResultList();
    }

    // Gets the users similar items.
    @SuppressWarnings("unchecked")
    public List<Product> getGuestSimilarItems(List<Product> guestItems, Session currentSession) {
        String queryPrep = "from Product where category=:category";

        if (guestItems.size() > 1)
            for (int itemCounter = 0; itemCounter < guestItems.size(); itemCounter++) {
                queryPrep += " or category=:category" + itemCounter;
            }

        Query query = currentSession.createQuery(queryPrep, Product.class);

        query.setParameter("category", guestItems.get(0).getCategory());

        if (guestItems.size() > 1)
            for (int itemCounter = 0; itemCounter < guestItems.size(); itemCounter++) {
                query.setParameter("category" + itemCounter, guestItems.get(itemCounter).getCategory());
            }

        return (List<Product>) query.getResultList();
    }

    // Gets the users similar items.
    @SuppressWarnings("unchecked")
    public List<Product> getSimilarItems(List<OrderItems> userItems, Session currentSession) {
        String queryPrep = "from Product where category=:category";

        if (userItems.size() > 1)
            for (int itemCounter = 0; itemCounter < userItems.size(); itemCounter++) {
                queryPrep += " or category=:category" + itemCounter;
            }

        Query query = currentSession.createQuery(queryPrep, Product.class);

        query.setParameter("category", userItems.get(0).getProduct().getCategory());

        if (userItems.size() > 1)
            for (int itemCounter = 0; itemCounter < userItems.size(); itemCounter++) {
                query.setParameter("category" + itemCounter, userItems.get(itemCounter).getProduct().getCategory());
            }

        return (List<Product>) query.getResultList();
    }

    // Gets the product names.
    @SuppressWarnings("unchecked")
    public ArrayList<String> getProductName(Session currentSession) {
        return (ArrayList<String>) currentSession.createQuery("select productName from Product").getResultList();
    }
}
