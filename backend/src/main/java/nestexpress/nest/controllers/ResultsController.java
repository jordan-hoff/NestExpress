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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/results")
public class ResultsController {

    final static Logger logger = Logger.getLogger(ResultsController.class);

    private final ProductService productService;

    @Autowired
    public ResultsController(ProductService productService) {
        this.productService = productService;
    }

    // Gets all available products.
    @GetMapping("/getProducts/all")
    public Map<String, Map<String, String>> searchAll() {

        List<Product> results = productService.getAll();

        if(results.size() == 0) {
            logger.error("Product Search Error.");
            throw new ProductNotFoundException("Product Search Error.");
        } else
            return multiProductMapping(results);
    }

    // Gets all the products related to an entered keyword.
    @GetMapping("/getProducts/keyword/{keyword}")
    public Map<String, Map<String, String>> keywordSearch(@PathVariable("keyword") String keyword) {

        List<Product> results = productService.findByKeyword(keyword);

        if(results.size() == 0) {
            logger.error("Error Finding keyword: "+ keyword);
            throw new ProductNotFoundException("Product Search Error.");
        } else {
            return multiProductMapping(results);
        }
    }

    // Gets all the products within a category.
    @GetMapping("/getProducts/category/{category}")
    public Map<String, Map<String, String>> categorySearch(@PathVariable("category") int category) {

        List<Product> results = productService.findByCategory(category);

        if(results.size() == 0) {
            logger.error("Error Finding Category: "+ category);
            throw new ProductNotFoundException("Product Search Error.");
        } else
            return multiProductMapping(results);
    }

    // Gets a product based on the product ID.
    @GetMapping("/getProducts/id/{id}")
    public Map<String, String> idSearch(@PathVariable("id") int id) {

        Product product = productService.findById(id);

        if(product == null) {
            logger.error("Error Finding id: "+ id);
            throw new ProductNotFoundException("");
        } else
            return singleProductMapping(product);
    }

    // Gets the range of items in a specific category.
    @GetMapping("/gtCatRg/{first}/{last}")
    public Map<String, Map<String, String>> getCategoryRange(@PathVariable("first") int firstCategory,
                                                             @PathVariable("last") int lastCategory) {
        List<Product> results = productService.getCategoryRange(firstCategory, lastCategory);

        if (results.size() == 0) {
            logger.error("Error finding range: " + firstCategory + " " + lastCategory);
            throw new ProductNotFoundException("No Products Were Found.");
        } else
            return multiProductMapping(results);
    }

    // Gets the name of a product.
    @GetMapping("/typeAhead")
    public ArrayList<String> typeAhead() {
        return productService.getProductName();
    }

    // Sets the attributes for a single product.
    private Map<String, String> singleProductMapping(Product product) {

        Map<String, String> productMap = new HashMap<String, String>();

        productMap.put("productName", product.getProductName());
        productMap.put("productId", Integer.toString(product.getProductId()));
        productMap.put("image", product.getImage().iterator().next().getImageUrl());
        productMap.put("price", String.format("%.2f", product.getPrice()));
        return productMap;
    }

    // Sets the attributes for mutliple products.
    private Map<String, Map<String, String>> multiProductMapping(List<Product> productList) {

        Map<String, Map<String, String>> newMap = new HashMap<String, Map<String, String>>();
        Map<String, String> listLengthMap       = new HashMap<String, String>();

        for(int current = 0; current < productList.size(); current++) {
            Map<String, String> productMap = new HashMap<String, String>();
            productMap.put("productName", productList.get(current).getProductName());
            productMap.put("productId", Integer.toString(productList.get(current).getProductId()));
            productMap.put("image", productList.get(current).getImage().iterator().next().getImageUrl());
            productMap.put("price", String.format("%.2f", productList.get(current).getPrice()));
            newMap.put("item" + current, productMap);
        }

        listLengthMap.put("listLength", String.format("%d", newMap.size()));

        newMap.put("listLength", listLengthMap);
        return newMap;
    }
}
