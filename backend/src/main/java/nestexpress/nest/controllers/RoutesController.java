package nestexpress.nest.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class RoutesController {

    // Routes all pages to index.html
    @RequestMapping(value={"/signin", "/signup", "/results/{product}", "/results/{first}/{last}",
                            "/product/{id}", "/cart", "/checkout", "/confirmation", "/wishlist",
                            "/error", "/about", "/previousOrders"}, method=RequestMethod.GET)
    public String routes() {
        return "forward:/index.html";
    }
}