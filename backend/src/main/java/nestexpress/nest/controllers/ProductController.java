package nestexpress.nest.controllers;

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
@RequestMapping("/product")
public class ProductController {

    final static Logger logger = Logger.getLogger(ProductController.class);

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // Gets the product data based on a product ID passed in.
    @GetMapping("/getProduct/{productId}")
    public Map<String, String> getProductDetails(@PathVariable("productId") int productId) {

        Product selectedProduct = productService.findById(productId);

        if (selectedProduct == null) {
            logger.error("Find by id returned null: "+productId);
            throw new ProductNotFoundException("No items were found.");
        } else
            return selectedProductMapping(selectedProduct);
    }

    // Gets items for the similar items list.
    @GetMapping("/getSimilar/{category}")
    public Map<String, Map<String, String>> getSimilarItems(@PathVariable("category") int category) {

        List<Product> similarItems  = productService.findByCategory(category);

        // Checks for a valid list size and valid list of items then returns a map of products.
        if (similarItems.contains(null)) {
            logger.error("Error Finding Category due to null return: " + category);
            throw new ProductNotFoundException("No items were found.");
        } else
            return similarProductMapping(similarItems);
    }

    // Sets the needed attributes from a product passed in.
    private Map<String, String> selectedProductMapping(Product selectedProduct) {

        Map<String, String> productMap = new HashMap<String, String>();

        productMap.put("productId", String.valueOf(selectedProduct.getProductId()));
        productMap.put("productName", selectedProduct.getProductName());
        productMap.put("descript", selectedProduct.getDescript());
        productMap.put("price", String.format("$%.2f", selectedProduct.getPrice()));
        productMap.put("category", String.valueOf(selectedProduct.getCategory()));
        productMap.put("image", selectedProduct.getImage().iterator().next().getImageUrl());
        return productMap;
    }

    // Takes a list of products and outputs a list that contains the attributes needed for items on the home page.
    private Map<String, Map<String, String>> similarProductMapping(List<Product> productList) {

        Map<String, Map<String, String>> newMap = new HashMap<String, Map<String, String>>();

        // Maps individual products to attributes, then maps that map to an item number.
        for (int current = 0; current < productList.size(); current++){
            Map<String, String> productMap = new HashMap<String, String>();
            productMap.put("productId", String.valueOf(productList.get(current).getProductId()));
            productMap.put("price", String.format("$%.2f", productList.get(current).getPrice()));
            productMap.put("productName", productList.get(current).getProductName());
            newMap.put("item" + current, productMap);
        }
        return newMap;
    }
}