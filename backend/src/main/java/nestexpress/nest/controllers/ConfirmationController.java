package nestexpress.nest.controllers;

import nestexpress.nest.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/confirmation")
public class ConfirmationController {

    private final UserService userService;

    @Autowired
    public ConfirmationController(UserService userService) {
        this.userService = userService;
    }

    // Get completed order details
    @GetMapping("/details")
    public Map<String, String> getConfirmationDetails(HttpServletRequest request, HttpServletResponse response)
                                                        throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        Map<String, String> confirmationDetails = new HashMap<String, String>();

        confirmationDetails.put("name",               session.getAttribute("name").toString());
        confirmationDetails.put("recipient",          session.getAttribute("recipient").toString());
        confirmationDetails.put("orderNumber", "NE" + session.getAttribute("previousOrderId").toString());
        confirmationDetails.put("orderTotal",         session.getAttribute("total").toString());
        confirmationDetails.put("orderDate",          session.getAttribute("orderDate").toString());
        confirmationDetails.put("deliveryTime",       session.getAttribute("deliveryTime").toString());
        confirmationDetails.put("deliveryAddress",    session.getAttribute("deliveryAddress").toString());

        return confirmationDetails;
    }

    // Invalidates the session for a guest user after checkout.
    // Removes capability to route back to page through url search.
    @PostMapping("/session/invalidate")
    public void invalidateGuestSession(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        if (session != null && session.getAttribute("firstname").equals("guest"))
            session.invalidate();

        try {
            session.removeAttribute("authConfirm");
        } catch (Exception e) {
            // The Session value has already been removed.
        }
    }

    // Gets authentication to route to confirmation page.
    @GetMapping("/auCm")
    public String getAuthentication(HttpServletRequest request, HttpServletResponse response)
                        throws ServletException, IOException {

        try {
            if (request.getSession(false).getAttribute("authConfirm") != null)
                return "approve";
            else
                return "unapproved";
        } catch (NullPointerException e) {
            return "unapproved";
        }
    }
}
