package nestexpress.nest.controllers;

import nestexpress.nest.entity.OrderItems;
import nestexpress.nest.entity.Orders;
import nestexpress.nest.entity.Product;
import nestexpress.nest.services.OrderItemsService;
import nestexpress.nest.services.OrdersService;
import nestexpress.nest.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/orders")
public class RecentOrdersController {

    private final ProductService    productService;
    private final OrdersService     ordersService;
    private final OrderItemsService orderItemsService;

    @Autowired
    public RecentOrdersController(ProductService productService, OrdersService ordersService,
                                  OrderItemsService orderItemsService) {
        this.productService    = productService;
        this.ordersService     = ordersService;
        this.orderItemsService = orderItemsService;
    }

    // Gets a list of a user's completed orders.
    @GetMapping("/completed")
    public Map<String, Map<String, String>> getCompletedOrders(HttpServletRequest request, HttpServletResponse response)
                                                                                    throws ServletException, IOException {
        HttpSession session     = request.getSession(false);
        List<Orders> userOrders = ordersService.getCompletedOrders(Integer.parseInt(session.getAttribute("userId").toString()));
        Map<String, Map<String, String>> recentOrders = new HashMap<String, Map<String, String>>();

        for (int currentOrder = 0; currentOrder < userOrders.size(); currentOrder++) {
            Map<String, String> orderDetails = new HashMap<String, String>();

            orderDetails.put("OrderNumber", String.valueOf(userOrders.get(currentOrder).getOrderId()));
            orderDetails.put("OrderDate", userOrders.get(currentOrder).getOrderDate());
            orderDetails.put("DeliveryLocation", userOrders.get(currentOrder).getLocation());
            orderDetails.put("OrderTotal", String.format("$%.2f",userOrders.get(currentOrder).getTotal()));
            recentOrders.put(String.valueOf(currentOrder), orderDetails);
        }
        return recentOrders;
    }

    // Gets the details for products in an order.
    @GetMapping("/details/{orderId}")
    public Map<String, Map<String, String>> getOrderDetails(@PathVariable("orderId")String orderId,
                        HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        List<OrderItems> orderItems = orderItemsService.getAllItems(Integer.parseInt(orderId));
        Map<Integer, Map<String,Integer>> productMap = new HashMap<Integer, Map<String,Integer>>();
        Map<String, Map<String, String>> order       = new HashMap<String, Map<String, String>>();

        // Creates a map for products in the selected order.
        for (int currentProduct = 0; currentProduct < orderItems.size(); currentProduct++) {
            Map<String, Integer> item = new HashMap<String, Integer>();
            item.put("productId", orderItems.get(currentProduct).getProductId());
            item.put("quantity", orderItems.get(currentProduct).getQuantity());
            productMap.put(currentProduct, item);
        }

        for (int currentProduct = 0; currentProduct < productMap.size(); currentProduct++) {
            Map<String, String> productDetails = new HashMap<String, String>();
            Product product = productService.findById(productMap.get(currentProduct).get("productId"));
            productDetails.put("ProductName", product.getProductName());
            productDetails.put("Price", String.format("$%.2f", product.getPrice()));
            productDetails.put("Quantity", String.valueOf(productMap.get(currentProduct).get("quantity")));
			productDetails.put("image", product.getImage().iterator().next().getImageUrl());
            order.put(String.valueOf(currentProduct), productDetails);
        }
        return order;
    }
}