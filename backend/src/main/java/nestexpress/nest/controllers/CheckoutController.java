package nestexpress.nest.controllers;

import nestexpress.nest.entity.Dorm;
import nestexpress.nest.entity.OrderItems;
import nestexpress.nest.entity.User;
import nestexpress.nest.exceptions.UserNotFoundException;
import nestexpress.nest.services.DormService;
import nestexpress.nest.services.ProductService;
import nestexpress.nest.services.OrderItemsService;
import nestexpress.nest.services.UserService;
import nestexpress.nest.session.SessionTrack;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Responsible for all information on the checkout page.
@RestController
@RequestMapping("/checkout")
public class CheckoutController {

    private static final int CLOSE_TIME = 22;
    private static final int NOON       = 12;
    private static final int OPEN_TIME  = 7;
    private static final int WAIT_TIME  = 2;

    private static final int NO_DORM_SELECTED = 0;           // No dorm
    private static final int NO_ROOM_SELECTED = 0;           // No Room
    private static final int BALLARD_DORM_ID  = 1;           // Dorm ID
    private static final int COBERLY_DORM_ID  = 2;           // Dorm ID
    private static final int RICE_DORM_ID     = 7;           // Dorm ID
    private static final int COBERLY_BALLARD_MIN_FLOOR = 1;  // Dorm minimum floor
    private static final int RICE_HIGH_FLOOR_MIN_ROOM  = 50; // Dorm upper floor minimum room
    private static final int RICE_LOW_FLOOR_MAX_ROOM   = 22; // Dorm lower floor maximum room
    private static final int RICE_MIN_FLOOR   = 1;           // Dorm minimum floor
    private static final int RICE_MAX_FLOOR   = 9;           // Dorm maximum floor
    private static final int ROOM_NUM_LENGTH  = 4;           // Room length

    private final DormService dormService;
    private final OrderItemsService orderItemsService;
    private final ProductService productService;
    private final UserService userService;

    @Autowired
    public CheckoutController(DormService dormService, OrderItemsService orderItemsService,
                              ProductService productService, UserService userService) {
        this.dormService = dormService;
        this.orderItemsService = orderItemsService;
        this.productService = productService;
        this.userService = userService;
    }

    @Autowired
    SessionTrack sessionTrack;

    // Fills the delivery map and sets the session details with default user information.
    @GetMapping("/info/delivery")
    public Map<String, String> getDeliveryInfo(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Map<String,String> deliveryInfo = new HashMap<>();
        HttpSession session = request.getSession(false);
        User        user    = userService.findById(Integer.parseInt(session.getAttribute("userId").toString()));

        deliveryInfo.put("name", String.format("%s %s", user.getFirstName(), user.getLastName()));
        if (user.getRoomNumber() == 0)
            deliveryInfo.put("deliveryAddress", "Campus Store");
        else
            deliveryInfo.put("deliveryAddress", String.format("%s %s",
                    String.valueOf(dormService.findDormById(user.getDorm()).getDormName()),
                    String.valueOf(user.getRoomNumber())));

        request.setAttribute("name",            deliveryInfo.get("name"));
        request.setAttribute("recipient",       "");
        request.setAttribute("deliveryAddress", deliveryInfo.get("deliveryAddress"));

        request.setAttribute("action", "updateDelivery");
        sessionTrack.doGet(request, response);

        return deliveryInfo;
    }

    // Fills the pickup map and sets the session details with order pickup information.
    @GetMapping("/info/pickup")
    public Map<String, String> getPickupInfo(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User user = userService.findById(Integer.parseInt(session.getAttribute("userId").toString()));

        Map<String, String> pickupInfo = new HashMap<>();

        pickupInfo.put("name", user.getFirstName() + " " + user.getLastName());
        pickupInfo.put("deliveryLocation", "Campus Store Front Desk");

        request.setAttribute("name",            pickupInfo.get("name"));
        request.setAttribute("recipient",       "");
        request.setAttribute("deliveryAddress", pickupInfo.get("deliveryLocation"));

        request.setAttribute("action", "updateDelivery");
        sessionTrack.doGet(request, response);

        return pickupInfo;
    }

    // Calculates and provides order tax, grand total, and discount information.
    @GetMapping("/info/summary")
    public Map<String, String> getOrderSummary(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        Map<String, String> orderSummary = new HashMap<String, String>();

        orderSummary.put("subtotal",  String.valueOf(session.getAttribute("subtotal")));
        orderSummary.put("taxTotal",  String.valueOf(session.getAttribute("tax")));
        orderSummary.put("totalCost", String.valueOf(session.getAttribute("total")));
        orderSummary.put("couponEntered", String.valueOf(session.getAttribute("couponEntered")));

        return orderSummary;
    }

    // Sets the current order to be the previous order and sets order information in the current session.
    @PostMapping("/set/order")
    public void setPreviousOrder(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        LocalDateTime today = LocalDateTime.now();

        // Sets the order id in the session.
        if (session.getAttribute("firstname").equals("guest"))
            session.setAttribute("previousOrderId", String.valueOf((int)(Math.random() * 99 + 1)) + "G");
        else
            session.setAttribute("previousOrderId",session.getAttribute("orderId"));

        // Sets the delivery time in the session.
        if(today.getHour() >= CLOSE_TIME - WAIT_TIME)
            session.setAttribute("deliveryTime", "Tomorrow at 8:00 AM");
        else if (today.getHour() < OPEN_TIME)
            session.setAttribute("deliveryTime","Today at 8:00 AM");
        else if (today.getHour() + WAIT_TIME > NOON)
            session.setAttribute("deliveryTime", String.format("Today at %d:%02d PM",
                                                    today.getHour() + WAIT_TIME - NOON, today.getMinute()));
        else
            session.setAttribute("deliveryTime", String.format("Today at %d:%02d AM",
                                                    today.getHour() + WAIT_TIME, today.getMinute()));

        // Sets the order date in the session.
        session.setAttribute("orderDate", String.format("%s %s %d, %d", toProperCase(String.valueOf(today.getDayOfWeek())),
                toProperCase(String.valueOf(today.getMonth())), today.getDayOfMonth(), today.getYear()));

        // Sets the delivery location in the session.
        session.setAttribute("deliveryAddress", String.format("%s %s", session.getAttribute("dorm"),
                                                                          session.getAttribute("room")));

        // Updates session attributes.
        request.setAttribute("action","updateSession");
        sessionTrack.doGet(request, response);
    }

    // Sets alternate delivery information in the session.
    @PostMapping("/set/altInfo/{deliveryLocation}/{recipient}")
    public Map<String, String>  setAltInfo(@PathVariable("deliveryLocation") String newLocation,
                                           @PathVariable("recipient") String recipient, HttpServletRequest request,
                                            HttpServletResponse response) throws ServletException, IOException {

        HttpSession session =  request.getSession(false);

        User user = userService.findById(Integer.parseInt(session.getAttribute("userId").toString()));
        Dorm selectedDorm = new Dorm();

        Map<String, String> error = new HashMap<String, String>();
        Map<String, String> deliveryDetails = new HashMap<String, String>();
        List<Dorm> dormList = dormService.getAllDorms();

        boolean validDorm = false;
        String[] delivery = newLocation.split(" ");
        String dormName   = delivery[0];
        String roomNumberString = delivery[1];
        int roomNumber = Integer.valueOf(delivery[1]);

        // Checks for a guest user and validates the delivery location.
        if (session != null  && !session.getAttribute("firstname").equals("guest")) {

            for (int dorm = 0; dorm < dormList.size(); dorm++) {
                if (dormName.equals(dormList.get(dorm).getDormName())) {
                    validDorm = true;
                    selectedDorm = dormList.get(dorm);
                }
            }

            if (validDorm == false) {
                error.put("error", "Dorm entered is not a valid dorm.");
                return error;
            }

            // Dorm selected must be valid.
            if (selectedDorm.getDormId() < NO_DORM_SELECTED) {
                error.put("error", "Please select a dorm or select no dorm if applicable.");

                return error;
            }

            if (selectedDorm.getDormId() > NO_DORM_SELECTED && roomNumber == NO_ROOM_SELECTED) {
                error.put("error", "Please enter a room number in the dorm selected.");

                return error;
            }

            if (selectedDorm.getDormId() == NO_DORM_SELECTED)
                roomNumber = NO_ROOM_SELECTED;

            // Rice Floor 1 Max:0-22 Floor 2-9 Max:0-22   Floor 10-11:50-72
            // Room Number must 4 digits and the first digit must match the dorm selected.
            if (roomNumber != NO_ROOM_SELECTED) {
                if (String.valueOf(roomNumber).length() != ROOM_NUM_LENGTH) {
                    error.put("error", "Your room number must be four digits.");

                    return error;
                } else if (Integer.parseInt(Integer.toString(roomNumber).substring(0, 1)) != selectedDorm.getDormId()) {
                    error.put("error", "The room number entered is not in the dorm selected (" +
                            selectedDorm.getDormName() + ").");

                    return error;
                } else if (Integer.parseInt(Integer.toString(roomNumber).substring(1, 2)) > selectedDorm.getNumFloors()) {
                    error.put("error", "The floor entered is not a valid floor.");

                    return error;
                } else if (Integer.parseInt(Integer.toString(roomNumber).substring(2, 4)) > selectedDorm.getMaxRoom()) {
                    error.put("error", "The room entered is a higher number than the highest room number " +
                            "in the dorm.");

                    return error;
                }

                // Ballard and Coberly start at floor 1.
                else if ((Integer.parseInt(Integer.toString(roomNumber).substring(0, 1)) == BALLARD_DORM_ID ||
                          Integer.parseInt(Integer.toString(roomNumber).substring(0, 1)) == COBERLY_DORM_ID) &&
                          Integer.parseInt(Integer.toString(roomNumber).substring(1, 2)) < COBERLY_BALLARD_MIN_FLOOR) {
                    error.put("error", "The floor entered is not a valid floor.");

                    return error;
                // Special cases for Rice.
                } else if (Integer.parseInt(Integer.toString(roomNumber).substring(0, 1)) == RICE_DORM_ID) {
                    // If the floor entered is 2-9.
                    if ((Integer.parseInt(Integer.toString(roomNumber).substring(1, 2)) > RICE_MIN_FLOOR &&
                         Integer.parseInt(Integer.toString(roomNumber).substring(1, 2)) <= RICE_MAX_FLOOR) &&
                         Integer.parseInt(Integer.toString(roomNumber).substring(2, 4)) > RICE_LOW_FLOOR_MAX_ROOM) {
                        error.put("error", "The room entered is invalid.");

                        return error;
                    }

                    // Rooms 7123-7149 should give an error.
                    if (Integer.parseInt(Integer.toString(roomNumber).substring(1, 2)) == RICE_MIN_FLOOR &&
                        (Integer.parseInt(Integer.toString(roomNumber).substring(2, 4)) > RICE_LOW_FLOOR_MAX_ROOM &&
                        Integer.parseInt(Integer.toString(roomNumber).substring(2, 4)) < RICE_HIGH_FLOOR_MIN_ROOM)) {
                        error.put("error", "The room entered is invalid.");

                        return error;
                    }

                    // If the floor entered is 0 and the room is less than 50.
                    if (Integer.parseInt(Integer.toString(roomNumber).substring(1, 2)) == RICE_MIN_FLOOR-1 &&
                        Integer.parseInt(Integer.toString(roomNumber).substring(2, 4)) < RICE_HIGH_FLOOR_MIN_ROOM) {
                        error.put("error", "The room entered is invalid.");

                        return error;
                    }
                }
            } else {
                if (selectedDorm.getDormId() != NO_DORM_SELECTED) {
                    error.put("error", "Please enter a room number in the dorm selected.");

                    return error;
                }
            }
        } else
            throw new UserNotFoundException("User not found");

        deliveryDetails.put("name", String.format("%s %s", user.getFirstName(), user.getLastName()));
        deliveryDetails.put("recipient", recipient);
        deliveryDetails.put("deliveryAddress", newLocation);
        deliveryDetails.put("action", "updateDelivery");

        request.setAttribute("name", String.format("%s %s", user.getFirstName(), user.getLastName()));
        request.setAttribute("recipient", recipient);
        request.setAttribute("deliveryAddress", newLocation);
        request.setAttribute("action", "updateDelivery");
        sessionTrack.doGet(request, response);

        return deliveryDetails;
    }

    @GetMapping("/auCo")
    public String getAuthentication(HttpServletRequest request, HttpServletResponse response)
                        throws ServletException, IOException {

        try {
            if (request.getSession(false).getAttribute("authCheckout") != null)
                return "approve";
            else
                return "unapproved";
        } catch (NullPointerException e) {
            return "unapproved";
        }
    }

    // Removes authentication to checkout page.
    @PostMapping("/rmAuCo")
    public void removeAuthentication(HttpServletRequest request, HttpServletResponse response)
                                        throws ServletException, IOException {
            try {
                request.getSession(false).removeAttribute("authCheckout");
            } catch (NullPointerException nullPointerException) {}
              catch (Exception exception) {}
        }

    private String toProperCase(String string) {
        return string.substring(0,1).toUpperCase() + string.substring(1).toLowerCase();
    }

    // Applies coupon discount.
    @PostMapping("/couponTotal/{coupon}")
    public Map<String, String> checkCouponCode(HttpServletRequest request, HttpServletResponse response,
                                 @PathVariable("coupon") String coupon)
                                        throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        Map<String, String> costData = new HashMap<String, String>();
        Map<String, String> error    = new HashMap<String, String>();
        int    numberArizonas    = 0;
        double arizonaCost       = 0.99;
        double gradDiscountTotal = 0.00;
        double gradDiscountRate  = 0.20;
        double subtotal          = Double.parseDouble(String.valueOf(session.getAttribute("subtotal")));
        double taxPercentage     = 0.07;
        double taxTotal          = 0.00;
        double totalCost         = 0.00;

        // Applies the coupon if the user has not entered a coupon yet.
        if (session.getAttribute("couponEntered").equals("false")) {
            if (session != null && !session.getAttribute("firstname").equals("guest")) {
                List<OrderItems> cartItems = orderItemsService.getAllItems((int)session.getAttribute("orderId"));
                if (coupon.equals("2499")) {
                    for (int item = 0; item < cartItems.size(); item++) {
                        // Look through the session items for Arizonas (items 142-146).
                        if (cartItems.get(item).getProductId() >= 142 &&
                            cartItems.get(item).getProductId() <= 146)
                            numberArizonas += cartItems.get(item).getQuantity();
                    }
                    if (numberArizonas >= 2)
                        subtotal -= arizonaCost;
                    else {
                        error.put("error", "Not enough AriZona drinks in your cart.");
                        return error;
                    }
                } else if (coupon.toLowerCase().equals("grad2020")) {
                    gradDiscountTotal = subtotal * gradDiscountRate;
                    subtotal -= gradDiscountTotal;
                } else {
                    error.put("error", "Incorrect coupon entered.");
                    return error;
                }
            }

            if (session != null && session.getAttribute("firstname").equals("guest")) {
                Map<String, Map<String, Integer>> sessionItems = (Map<String, Map<String, Integer>>)
                    session.getAttribute("cartItems");

                if (coupon.equals("2499")) {
                    for (int itemCounter = 0; itemCounter < (int) session.getAttribute("itemCount"); itemCounter++) {
                        // Look through the session items for Arizonas (items 142-146)
                        if ((int)sessionItems.get("guestItem" + itemCounter).get("productId") >= 142 &&
                            (int)sessionItems.get("guestItem" + itemCounter).get("productId") <= 146)
                            numberArizonas += (int) sessionItems.get("guestItem" + itemCounter).get("quantity");
                    }
                    if (numberArizonas >= 2)
                        subtotal -= arizonaCost;
                    else {
                        error.put("error", "Not enough AriZona drinks in your cart.");
                        return error;
                    }
                } else if (coupon.toLowerCase().equals("grad2020")) {
                    gradDiscountTotal = subtotal * gradDiscountRate;
                    subtotal -= gradDiscountTotal;
                } else {
                    error.put("error", "Incorrect coupon entered.");
                    return error;
                }
            }

            // Calculates the tax and total.
            taxTotal  = subtotal * taxPercentage;
            totalCost = subtotal + taxTotal;

            costData.put("subtotal", String.format("%.2f", subtotal));
            costData.put("taxTotal", String.format("%.2f", taxTotal));
            costData.put("totalCost", String.format("%.2f", totalCost));
            costData.put("couponEntered", "true");

            // Sets user cost data in the session.
            request.setAttribute("subtotal", costData.get("subtotal"));
            request.setAttribute("tax", costData.get("taxTotal"));
            request.setAttribute("total", costData.get("totalCost"));
            request.setAttribute("couponEntered", "true");
            request.setAttribute("action", "enterCoupon");

            sessionTrack.doGet(request, response);

            return costData;
        } else {
            error.put("error", "Coupon has already been entered.");
            return error;
        }
    }
}