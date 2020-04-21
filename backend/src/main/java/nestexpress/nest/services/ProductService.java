package nestexpress.nest.services;

import org.springframework.stereotype.Service;
import org.hibernate.Session;

import nestexpress.nest.entity.OrderItems;
import nestexpress.nest.entity.Product;
import nestexpress.nest.dao.ProductDAO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

@Service
public class ProductService {

    private static ProductDAO productDao;

    public ProductService() {
        productDao = new ProductDAO();
    }

    // Finds a product with a matching product ID
    public Product findById(int productId) {
        Session session = productDao.openCurrentSession();
        Product searchedProduct = productDao.findById(productId, session);
        productDao.closeCurrentSession(session);
        return searchedProduct;
    }

    // Finds a list of products with a matching keyword
    public List<Product> findByKeyword(String keyword) {
        Session session = productDao.openCurrentSession();
        List<Product> productList = productDao.findByKeyword(keyword, session);
        productDao.closeCurrentSession(session);
        return productList;
    }

    // Finds a list of products within a category
    public List<Product> findByCategory(int category) {
        Session session = productDao.openCurrentSession();
        List<Product> productList = productDao.findByCategory(category, session);
        productDao.closeCurrentSession(session);
        return productList;
    }

    // Gets all products within a specific category.
    public List<Product> getCategoryRange(int first, int last, int itemQty) {
        Session currentSession    = productDao.openCurrentSession();
        List<Product> productList = productDao.getCategoryRange(first, last, itemQty, currentSession);
        productDao.closeCurrentSession(currentSession);
        return productList;
    }

    // Gets all items.
    public List<Product> getAll() {
        Session session = productDao.openCurrentSession();
        List<Product> productList = productDao.getAll(session);
        productDao.closeCurrentSession(session);
        return productList;
    }

    // Gets a list of products within a product id range
    public List<Product> getProductRange(int firstId, int lastId) {
        Session currentSession = productDao.openCurrentSession();
        List<Product> productList = productDao.getProductRange(firstId, lastId, currentSession);
        productDao.closeCurrentSession(currentSession);
        return productList;
    }

    // Gets a list of products within a category range
    public List<Product> getCategoryRange(int firstCategory, int lastCategory) {
        Session currentSession = productDao.openCurrentSession();
        List<Product> productList = productDao.getCategoryRange(firstCategory, lastCategory, currentSession);
        productDao.closeCurrentSession(currentSession);
        return productList;
    }

    // Gets the guest cart items.
    public List<Product> getGuestCartItems(Map<String, Map<String, Integer>> guestItems,
                                            HttpServletRequest request) {
        Session session = productDao.openCurrentSession();
        List<Product> guestList = productDao.getGuestCartItems(guestItems, request, session);
        productDao.closeCurrentSession(session);
        return guestList;
    }

    // Gets the user similar items.
    public List<Product> getSimilarUserItems(List<OrderItems> userItems) {
        Session session = productDao.openCurrentSession();
        List<Product> guestList = productDao.getSimilarItems(userItems, session);
        productDao.closeCurrentSession(session);
        return guestList;
    }

    // Gets the guest similar items.
    public List<Product> getGuestSimilarUserItems(List<Product> userItems) {
        Session session = productDao.openCurrentSession();
        List<Product> guestList = productDao.getGuestSimilarItems(userItems, session);
        productDao.closeCurrentSession(session);
        return guestList;
    }

    // Gets the guest similar items.
    public ArrayList<String> getProductName() {
        Session session = productDao.openCurrentSession();
        ArrayList<String> products = productDao.getProductName(session);
        productDao.closeCurrentSession(session);
        return products;
    }

}
