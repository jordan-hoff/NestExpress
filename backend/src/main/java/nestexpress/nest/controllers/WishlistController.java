package nestexpress.nest.controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import nestexpress.nest.entity.Wishlist;
import nestexpress.nest.exceptions.ProductNotFoundException;
import nestexpress.nest.exceptions.SessionExceptions;
import nestexpress.nest.services.WishlistService;
import nestexpress.nest.session.SessionTrack;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/wishlist")
public class WishlistController {

    private final WishlistService wishlistService;

    @Autowired
    public WishlistController(WishlistService wishlistService) {
        this.wishlistService   = wishlistService;
    }

    @Autowired
    SessionTrack sessionTrack;

    // Gets all items in a user's wishlist.
    @GetMapping("/getWishlist")
    public Map<String, Map<String, String>> getWishlist(HttpServletRequest request, HttpServletResponse response)
                                                                                throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        if (session != null) {
            List<Wishlist> wishlistProducts = wishlistService.getWishlistItems((int)session.getAttribute("userId"));

            if (wishlistProducts == null)
                throw new ProductNotFoundException("Your wishlist is empty.");
            else
                return wishlistMapping(wishlistProducts);
        } else
            throw new SessionExceptions("Your session has timed out.");
    }

    // Removes a single item from a user's wishlist.
    @PostMapping("/removeWishlistItem/{productId}")
    public void removeWishlistItem(@PathVariable("productId") int productId, HttpServletRequest request,
                                       HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        if (session != null)
            wishlistService.deleteWishlistItem(Integer.valueOf(session.getAttribute("userId").toString()), productId);
        else
            throw new SessionExceptions("Your session has timed out.");
    }

    // Removes all of a user's wishlist items.
    @PostMapping("/removeAllWishlistItems")
    public void removeAllWishlistItems(HttpServletRequest request, HttpServletResponse response)
                                                            throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        if (session != null)
            wishlistService.deleteAllWishlistItems(Integer.valueOf(session.getAttribute("userId").toString()));
        else
            throw new SessionExceptions("Your session has timed out.");
    }

    // Takes a list of products and outputs a list that contains the attributes needed for items.
    private Map<String, Map<String, String>> wishlistMapping(List<Wishlist> productList) {

        Map<String, Map<String, String>> newMap = new HashMap<String, Map<String, String>>();

        // Maps individual products to attributes, then maps that map to an item number.
        for (int currentProduct = 0; currentProduct < productList.size(); currentProduct++) {
            Map<String, String> wishlistMap = new HashMap<String, String>();
            wishlistMap.put("productId", String.valueOf(productList.get(currentProduct).getProductId()));
            wishlistMap.put("price", String.format("$%.2f", productList.get(currentProduct).getProduct().getPrice()));
            wishlistMap.put("productName", productList.get(currentProduct).getProduct().getProductName());
            wishlistMap.put("image", productList.get(currentProduct).getProduct().getImage().iterator().next().getImageUrl());
            wishlistMap.put("quantity", Integer.toString(newMap.size()));
			newMap.put("item" + currentProduct, wishlistMap);
        }
        return newMap;
    }

}