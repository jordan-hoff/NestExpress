package nestexpress.nest.controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import nestexpress.nest.entity.OrderItems;
import nestexpress.nest.entity.Product;
import nestexpress.nest.entity.Wishlist;
import nestexpress.nest.exceptions.ProductNotFoundException;
import nestexpress.nest.services.OrderItemsService;
import nestexpress.nest.services.ProductService;
import nestexpress.nest.services.UserService;
import nestexpress.nest.services.WishlistService;
import nestexpress.nest.session.SessionTrack;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/cart")
public class CartController {

    final static Logger logger = Logger.getLogger(CartController.class);

    private final OrderItemsService orderItemsService;
    private final ProductService    productService;
    private final UserService       userService;
    private final WishlistService   wishlistService;

    private static final float EMPTY      = 0.00f;
    private static final int   EMPTY_CART = 0;

    @Autowired
    public CartController(OrderItemsService orderItemsService, ProductService productService,
                                        UserService userService, WishlistService wishlistService) {
        this.orderItemsService = orderItemsService;
        this.productService    = productService;
        this.userService       = userService;
        this.wishlistService   = wishlistService;
    }

    @Autowired
    SessionTrack sessionTrack;

    // Gets a list of items in a customer's cart.
    @SuppressWarnings("unchecked")
    @GetMapping("/getCart")
    public Map<String, Map<String, String>> getCartItems(HttpServletRequest request, HttpServletResponse response)
                                                             throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session != null && !session.getAttribute("firstname").equals("guest")) {
            List<OrderItems> cartList = orderItemsService.getAllItems((int)session.getAttribute("orderId"));

            if (cartList.contains(null)) {
                Map<String, String> error                    = new HashMap<String, String>();
                Map<String, Map<String, String>> errorResult = new HashMap<String, Map<String, String>>();

                error.put("error", "No items were found.");
                errorResult.put("error", error);

                return errorResult;
            } else
                return cartProductMapping(cartList);

        } else if (session != null && session.getAttribute("firstname").equals("guest")) {
            Map<String, Map<String, Integer>> sessionItems = (Map<String, Map<String, Integer>>)
                    session.getAttribute("cartItems");

            if (!session.getAttribute("cartQuantity").equals(EMPTY_CART))
                return guestCartProductMapping(productService.getGuestCartItems(sessionItems, request),
                                           (int) session.getAttribute("itemCount"), sessionItems);
            else
                return null;
        } else {
            Map<String, String> error                    = new HashMap<String, String>();
            Map<String, Map<String, String>> errorResult = new HashMap<String, Map<String, String>>();

            error.put("error", "There are no items in the cart.");
            errorResult.put("error", error);

            return errorResult;
        }
    }

    // Gets similar items for all the items in the cart.
    @SuppressWarnings("unchecked")
    @GetMapping("/getSimilarList/{listQty}")
    public Map<String, Map<String, String>> getSimilarItems(@PathVariable("listQty") int listQty,
                                                                HttpServletRequest request, HttpServletResponse response)
                                                                                        throws ServletException, IOException {

        HttpSession session                 = request.getSession(false);
        List<Product>    similarItems       = new ArrayList<>();
        List<Product>    productCategory    = new ArrayList<>();

        if (session != null && !session.getAttribute("firstname").equals("guest")) {
            List<OrderItems> cartList           = orderItemsService.getAllItems((int)session.getAttribute("orderId"));

            productCategory = productService.getSimilarUserItems(cartList);
            for (int currentItem = 0; currentItem < productCategory.size(); currentItem++)
                similarItems.add(productCategory.get(currentItem));

            // Randomize the items in the similar items list.
            Collections.shuffle(similarItems);

            // Checks for a valid list of items then gives a map of products.
            if (similarItems.contains(null))
                throw new ProductNotFoundException("No items were found.");
            else if (similarItems.size() > listQty)
                return similarProductMapping(similarItems.subList(0, listQty));
            else
                return similarProductMapping(similarItems);

        } else if (session != null && session.getAttribute("firstname").equals("guest")) {
            // Gets guest similar items list.

            Map<String, Map<String, Integer>> sessionItems = (Map<String, Map<String, Integer>>)
                    session.getAttribute("cartItems");

            productCategory =
                productService.getGuestSimilarUserItems(productService.getGuestCartItems(sessionItems, request));

            for (int currentItem = 0; currentItem < productCategory.size(); currentItem++)
                similarItems.add(productCategory.get(currentItem));

            // Randomizes the items in the similar items list.
            Collections.shuffle(similarItems);

            // Checks for a valid list of items then gives a map of products.
            if (similarItems.contains(null))
                throw new ProductNotFoundException("No items were found.");
            else if (similarItems.size() > listQty)
                return similarProductMapping(similarItems.subList(0, listQty));
            else
                return similarProductMapping(similarItems);
        } else
            return null;
    }

    // Calculates the total cost of the order.
    @SuppressWarnings("unchecked")
    @GetMapping("/totalCost")
    public Map<String, String> calculateTotal(HttpServletRequest request, HttpServletResponse response)
                                                                      throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        Map<String, String> costData      = new HashMap<String, String>();
        double              taxPercentage = 0.07;
        double              taxTotal      = 0.00;
        double              totalCost     = 0.00;

        if (session != null && !session.getAttribute("firstname").equals("guest")) {
            double              subtotal      = userService.getProductTotal(
                                                    (int)session.getAttribute("userId"),
                                                    (int)session.getAttribute("orderId"));

            taxTotal  = subtotal * taxPercentage;
            totalCost = subtotal + taxTotal;

            costData.put("subtotal", String.format("%.2f", subtotal));
            costData.put("taxTotal", String.format("%.2f", taxTotal));
            costData.put("totalCost", String.format("%.2f", totalCost));

            // Sets user cost data in the session.
            request.setAttribute("subtotal", costData.get("subtotal"));
            request.setAttribute("taxTotal", costData.get("taxTotal"));
            request.setAttribute("totalCost", costData.get("totalCost"));
            request.setAttribute("couponEntered", "false");
            request.setAttribute("action", "setCostData");

            sessionTrack.doGet(request, response);

            return costData;

        } else if (session != null && session.getAttribute("firstname").equals("guest")) {
            double subtotal                                = 0.00;
            Map<String, Map<String, Integer>> sessionItems = (Map<String, Map<String, Integer>>)
               session.getAttribute("cartItems");

            if (!session.getAttribute("cartQuantity").equals(EMPTY_CART)) {
                List<Product> cartItems = productService.getGuestCartItems(sessionItems, request);

                for (int itemCounter = 0; itemCounter < (int) session.getAttribute("itemCount"); itemCounter++) {
                    try {
                        for (Product item : cartItems)
                        {
                            if (sessionItems.get("guestItem" + itemCounter).get("productId")
                                    .equals(item.getProductId()))
                                subtotal += (item.getPrice() * (int) sessionItems.get("guestItem" + itemCounter)
                                                                                .get("quantity"));
                        }
                    } catch (Exception e) {}
                }

                taxTotal  = subtotal * taxPercentage;
                totalCost = subtotal + taxTotal;

                // Sets guest cost data in the session.
                costData.put("subtotal", String.format("%.2f", subtotal));
                costData.put("taxTotal", String.format("%.2f", taxTotal));
                costData.put("totalCost", String.format("%.2f", totalCost));

                request.setAttribute("subtotal", costData.get("subtotal"));
                request.setAttribute("taxTotal", costData.get("taxTotal"));
                request.setAttribute("totalCost", costData.get("totalCost"));
                request.setAttribute("couponEntered", "false");

                request.setAttribute("action", "setCostData");
                sessionTrack.doGet(request, response);

            } else {
                costData.put("subtotal", String.format("%.2f", EMPTY));
                costData.put("taxTotal", String.format("%.2f", EMPTY));
                costData.put("totalCost", String.format("%.2f", EMPTY));
            }
            return costData;
        } else {
            costData.put("subtotal", String.format("%.2f", EMPTY));
            costData.put("taxTotal", String.format("%.2f", EMPTY));
            costData.put("totalCost", String.format("%.2f", EMPTY));
            return costData;
        }
    }

    // Add items to the wishlist.
    @PostMapping("/addWishlist/{productId}")
    public Map<String, String> addToWishlist(@PathVariable("productId") int productId,
                                  HttpServletRequest request, HttpServletResponse response)
                                                        throws ServletException, IOException {

        HttpSession session              = request.getSession(false);
        Map<String, String> wishlistInfo = new HashMap<String, String>();

        if (session != null && !session.getAttribute("firstname").equals("guest")) {
            List<Wishlist>  wishlist      = wishlistService.getWishlistItems((int)session.getAttribute("userId"));
            Wishlist       wishlistItem   = new Wishlist();
            boolean        testInWishlist = false;

            if (wishlist != null) {
                // Tests if item is already in user's wishlist.
                for (int currentProductInWishlist = 0; currentProductInWishlist < wishlist.size()
                                                        && testInWishlist == false; currentProductInWishlist++) {
                    if (productId == wishlist.get(currentProductInWishlist).getProductId()) {
                        testInWishlist = true;
                    }
                }
                if (testInWishlist == true) {
                    wishlistInfo.put("error", "This item is already in your wishlist.");
                    return wishlistInfo;
                }
            }

            if (testInWishlist == false) {
                wishlistItem.setUserId((int)session.getAttribute("userId"));
                wishlistItem.setProductId(productId);
            }
            wishlistService.insertToWishlist(wishlistItem);

            wishlistInfo.put("success", "success");
            return wishlistInfo;
        } else {
            wishlistInfo.put("error", "Please Sign-in");
            return wishlistInfo;
        }
    }

    // Creates a map to each item in the guest cart list.
    private Map<String, Map<String, String>> guestCartProductMapping(List<Product> cartList, int guestCartSize,
                                                    Map<String, Map<String, Integer>> guestCart) {

        Map<String, Map<String, String>> cartListMap = new HashMap<String, Map<String, String>>();

        for (int currentProduct = 0; currentProduct < cartList.size(); currentProduct++) {
            Map<String, String> cartItemMap = new HashMap<String, String>();

            cartItemMap.put("productId", String.valueOf(cartList.get(currentProduct).getProductId()));
            cartItemMap.put("productName", cartList.get(currentProduct).getProductName());
            cartItemMap.put("price",  String.format("%.2f", cartList.get(currentProduct).getPrice()));
            cartItemMap.put("image", cartList.get(currentProduct).getImage().iterator().next().getImageUrl());

            for (int itemCounter = 0; itemCounter < guestCartSize; itemCounter++) {
                try {
                    if (Integer.valueOf(guestCart.get("guestItem" + currentProduct).get("productId")).equals(cartList.get(itemCounter).getProductId())) {
                        cartItemMap.put("quantity", String.valueOf(guestCart.get("guestItem" + currentProduct).get("quantity")));
                    }

                } catch (Exception e) {}
            }

            cartListMap.put("item" + currentProduct, cartItemMap);
        }

        return cartListMap;
    }

    // Create a map to each item in the cart list.
    private Map<String, Map<String, String>> cartProductMapping(List<OrderItems> cartList) {
        Map<String, Map<String, String>> cartListMap = new HashMap<String, Map<String, String>>();

        for (int currentProduct = 0; currentProduct < cartList.size(); currentProduct++) {
            Map<String, String> cartItemMap = new HashMap<String, String>();

            cartItemMap.put("productId", String.valueOf(cartList.get(currentProduct).getProduct().getProductId()));
            cartItemMap.put("productName", cartList.get(currentProduct).getProduct().getProductName());
            cartItemMap.put("price",  String.format("%.2f", cartList.get(currentProduct).getProduct().getPrice()));
            cartItemMap.put("image", cartList.get(currentProduct).getProduct().getImage().iterator().next().getImageUrl());
            cartItemMap.put("quantity", String.valueOf(cartList.get(currentProduct).getQuantity()));
            cartListMap.put("item" + currentProduct, cartItemMap);
        }
        return cartListMap;
    }

    // Takes a list of products and gives a list that contains the attributes needed for items.
    private Map<String, Map<String, String>> similarProductMapping(List<Product> productList) {

        Map<String, Map<String, String>> newMap = new HashMap<String, Map<String, String>>();

        // Maps individual products to attributes, then maps that map to an item number.
        for (int currentProduct = 0; currentProduct < productList.size(); currentProduct++){
            Map<String, String> productMap = new HashMap<String, String>();
            productMap.put("productId", String.valueOf(productList.get(currentProduct).getProductId()));
            productMap.put("price", String.format("%.2f", productList.get(currentProduct).getPrice()));
            productMap.put("productName", productList.get(currentProduct).getProductName());
			productMap.put("image",productList.get(currentProduct).getImage().iterator().next().getImageUrl());
            newMap.put("item" + currentProduct, productMap);
        }
        return newMap;
    }
}