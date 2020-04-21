package nestexpress.nest.controllers;

import nestexpress.nest.entity.Image;
import nestexpress.nest.entity.Product;
import nestexpress.nest.exceptions.ProductNotFoundException;
import nestexpress.nest.services.ProductService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/home")
public class HomeController {

    final static Logger logger = Logger.getLogger(HomeController.class);

    private final ProductService productService;

    private final int lastItem             = 154;
    private final int eagleGearClothing    = 32;
    private final int eagleGearAccessories = 33;

    @Autowired
    public HomeController(ProductService productService) {
        this.productService = productService;
    }

    // Gets items for the featured items list.
    @GetMapping("/getFeatured/{listQty}")
    public Map<String, Map<String,String>> getFeaturedItems(@PathVariable("listQty") int listQty) {

        int firstProduct = ((int)(Math.random() * lastItem) + 1);
        List<Product> featuredItems;

        if(firstProduct + listQty <= lastItem)
            featuredItems = productService.getProductRange(firstProduct, firstProduct+listQty);
        else
            featuredItems = productService.getProductRange(lastItem-listQty, lastItem);

        // Checks for a valid list size and valid list of items then returns a map of products.
        if (featuredItems.contains(null))
            throw new ProductNotFoundException("Could not find the requested items");
        else
            return homeProductMapping(featuredItems);
    }

    //Gets items for the eagle gear items list.
    @GetMapping("/getEagleGear/{listQty}")
    public Map<String, Map<String, String>> getEagleGear(@PathVariable("listQty") int listQty) {

        List<Product> eagleGear = productService.getCategoryRange(eagleGearClothing, eagleGearAccessories, listQty);

        // Checks for a valid list size and valid list of items then returns a map of products.
        if (eagleGear.contains(null))
            throw new ProductNotFoundException("Could not find the requested items");
        else
            return homeProductMapping(eagleGear);
    }

    // Gets items for the special items list.
    @GetMapping("/getSpecials/{listQty}")
    public Map<String, Map<String, String>> getSpecials(@PathVariable("listQty") int listQty) {

        int firstProduct = ((int)(Math.random() * lastItem) + 1);
        List<Product> specialsItems;

        if(firstProduct + listQty <= lastItem)
            specialsItems = productService.getProductRange(firstProduct, firstProduct+listQty);
        else
            specialsItems = productService.getProductRange(lastItem-listQty, lastItem);

        // Checks for a valid list size and valid list of items then returns a map of products.
        if (specialsItems.contains(null))
            throw new ProductNotFoundException("Could not find the requested items");
        else
            return homeProductMapping(specialsItems);
    }

    // Takes a list of products and outputs a list that contains the attributes needed for items on the home page.
    private Map<String, Map<String, String>> homeProductMapping(List<Product> productList) {

        HashMap<String, Map<String, String>> newMap = new HashMap<String, Map<String, String>>();

        for (int current = 0; current < productList.size(); current++){
            Map<String, String> productMap = new HashMap<String, String>();
            productMap.put("productId", String.valueOf(productList.get(current).getProductId()));
            productMap.put("price", String.format("%.2f", productList.get(current).getPrice()));
            productMap.put("productName", productList.get(current).getProductName());
            productMap.put("image", productList.get(current).getImage().iterator().next().getImageUrl());
            newMap.put("item" + current, productMap);
        }
        return newMap;
    }
}
