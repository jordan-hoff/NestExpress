package nestexpress.nest.controllers;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.IllegalFormatConversionException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.rowset.serial.SerialException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import nestexpress.nest.entity.Dorm;
import nestexpress.nest.entity.OrderItems;
import nestexpress.nest.entity.Orders;
import nestexpress.nest.entity.User;
import nestexpress.nest.exceptions.InvalidUserInformationException;
import nestexpress.nest.exceptions.SessionExceptions;
import nestexpress.nest.services.DormService;
import nestexpress.nest.services.EncryptService;
import nestexpress.nest.services.OrderItemsService;
import nestexpress.nest.services.OrdersService;
import nestexpress.nest.services.UserService;
import nestexpress.nest.session.SessionTrack;

@RestController
@RequestMapping("/userAccount")
public class UserController {

    final static Logger logger = Logger.getLogger(UserController.class);

    private final double MAX_AMOUNT           = 9999.99;     // Maximum funds
    private final double MINIMUM_BALANCE      = 0.0;         // Minimum funds
    private static final int PASSWORD_MIN     = 5;           // Minimum password
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
    private static final Integer EMPTY        = 0;           // Empty cart
    private static final Integer ITEMCOUNT    = 1;           // Increase for items being added

    private static final String CC_NUMBER  = "5045525345564552";
    private static final String CARD_NAME  = "Team Persevere";
    private static final String CARD_DATE  = "0508";
    private static final String CVC_NUMBER = "101";

    private final UserService       userService;
    private final EncryptService    passwordService;
    private final DormService       dormService;
    private final OrderItemsService orderItemsService;
    private final OrdersService     ordersService;

    @Autowired
    public UserController(UserService userService, EncryptService passwordService,
                          DormService dormService, OrderItemsService orderItemsService,
                          OrdersService ordersService) {
        this.userService           = userService;
        this.passwordService       = passwordService;
        this.dormService           = dormService;
        this.orderItemsService     = orderItemsService;
        this.ordersService         = ordersService;
    }

    @Autowired
    SessionTrack sessionTrack;

    // Gets the currently active user account.
    @GetMapping("/currentUser")
    public Map<String, String> getActiveUser(HttpServletRequest request)
                                             throws SerialException {

        HttpSession session = request.getSession(false);

        if(session != null && session.getAttribute("firstname") != null) {
            Map<String, String> userInfo = new HashMap<String, String>();

            userInfo.put("firstname", String.valueOf(session.getAttribute("firstname")));

            try {
                userInfo.put("balance", String.format("%.2f", session.getAttribute("userBalance")));
            } catch (IllegalFormatConversionException error) {
                userInfo.put("balance", session.getAttribute("userBalance").toString());
            }

            userInfo.put("cartQuantity", String.valueOf(session.getAttribute("cartQuantity")));

            return userInfo;
        } else if (session != null && session.getAttribute("firstname").equals("guest")) {
            Map<String, String> userInfo = new HashMap<String, String>();

            userInfo.put("firstname", String.valueOf(session.getAttribute("firstname")));
            userInfo.put("cartQuantity", String.valueOf(session.getAttribute("cartQuantity")));

            return userInfo;
        } else {
            Map<String, String> error = new HashMap<String, String>();

            error.put("error", "The session has timed out.");
            return error;
        }
    }

    // Gets the users information.
    @GetMapping("/getEditInfo")
    public Map<String, String> getEditInfo(HttpServletRequest request) throws SerialException {
        HttpSession session = request.getSession(false);

        if (session != null && session.getAttribute("firstname") != null) {
            User userAccount = userService.findById(Integer.valueOf(
                String.valueOf(session.getAttribute("userId"))));

            Map<String, String> userInfo = new HashMap<String, String>();

            userInfo.put("firstName", userAccount.getFirstName());
            userInfo.put("lastName", userAccount.getLastName());
            userInfo.put("dorm", dormService.findDormById(userAccount.getDorm()).getDormName());
            userInfo.put("roomNumber", String.valueOf(userAccount.getRoomNumber()));

            return userInfo;
        } else
            throw new SessionExceptions("Your session has timed out.");
    }

    // Adds funds to the user account.
    @PostMapping("/addFunds")
    public Map<String, String> addNewFunds(@RequestBody Map<String, String> funds,
                                        HttpServletRequest request, HttpServletResponse response)
                                        throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session != null && session.getAttribute("firstname") != null) {

            String ccNumber = funds.get("CC_Number").replaceAll("\\s+", "");
            String cardDate = funds.get("Card_Date").replaceAll("/", "");

            if (!ccNumber.equals(CC_NUMBER)){
                Map<String, String> error = new HashMap<String, String>();

                error.put("error","The given credit card is invalid.");
                return error;
            } else if (!funds.get("Card_Name").equals(CARD_NAME)) {
                Map<String, String> error = new HashMap<String, String>();

                error.put("error","The given name does not match the card.");
                return error;
            } else if (!cardDate.equals(CARD_DATE)) {
                Map<String, String> error = new HashMap<String, String>();

                error.put("error", "The expiration date is invalid.");
                return error;
            } else if (!funds.get("CVC_Number").equals(CVC_NUMBER)) {
                Map<String, String> error = new HashMap<String, String>();

                error.put("error", "The CVC number is invalid.");
                return error;
            }

            try {
                if ((Double.parseDouble(String.valueOf(session.getAttribute("userBalance")))
                        + Double.parseDouble(funds.get("funds"))) <= MAX_AMOUNT &&
                        Double.parseDouble(funds.get("funds")) > 0) {

                    Map<String, String> userBalance = new HashMap<String, String>();

                    User user = userService.findById((int) session.getAttribute("userId"));

                    user.setBalance(user.getBalance() + Double.valueOf(funds.get("funds")));
                    request.setAttribute("action", "addFunds");
                    request.setAttribute("addFunds", Double.valueOf(funds.get("funds")));

                    sessionTrack.doGet(request, response);
                    userService.updateUser(user);

                    userBalance.put("balance", String.format("%.2f", session.getAttribute("userBalance")));

                    return userBalance;
                } else {
                    Map<String, String> error = new HashMap<String, String>();

                    error.put("error","The amount being added must be no greater " +
                        "than $" + MAX_AMOUNT + ".");
                    return error;
                }
            } catch (NumberFormatException numberFormatException) {
                Map<String, String> error = new HashMap<String, String>();

                error.put("error", "Please enter an amount to add.");
                return error;
            }
        } else
            throw new SessionExceptions("Your session has timed out.");
    }

    // Edits user account (first name, last name, password, dorm, room number).
    @PostMapping("/editAccount")
    public Map<String,String> editAccount(@RequestBody Map<String, String> userEdits, HttpServletRequest request,
                               HttpServletResponse response)
                            throws ServletException, IOException, NoSuchAlgorithmException,
                               InvalidKeySpecException {

        HttpSession session = request.getSession(false);

        // Makes sure the user is still active.
        if (session != null && session.getAttribute("firstname") != null) {
            Map<String, String> userInfo = new HashMap<String, String>();
            User user       = userService.findById((int)session.getAttribute("userId"));
            Dorm validDorm  = new Dorm();

            if (!userEdits.get("dorm").equals(String.valueOf(user.getDorm()))) {
                validDorm = dormService.findDormById(Integer.valueOf(userEdits.get("dorm")));
            } else if (!userEdits.get("roomNumber").equals(String.valueOf(user.getRoomNumber()))) {
                validDorm = dormService.findDormById(Integer.parseInt(Integer.toString(Integer.valueOf(
                    userEdits.get("roomNumber"))).substring(0, 1)));

                if (!Integer.valueOf(userEdits.get("dorm")).equals(validDorm.getDormId())) {
                    Map<String, String> error = new HashMap<String, String>();

                    error.put("error",  "The dorm does not match the requested room change.");
                    return error;
                }
            }

            // Checks for an updated first name.
            if (userEdits.get("first_name") != null) {
                if (!userEdits.get("first_name").equals(user.getFirstName())) {
                    if (containsSpace(userEdits.get("first_name")) || isNotAlphanumeric(userEdits.get("first_name"))) {
                        Map<String, String> error = new HashMap<String, String>();

                        error.put("error", "The first name entered is invalid.");
                        return error;
                    } else {
                        user.setFirstName(Character.toString(userEdits.get("first_name").charAt(0)).toUpperCase()
                                        + userEdits.get("first_name").substring(1).toLowerCase());
                        userInfo.put("firstName",user.getFirstName());
                    }
                }
            }

            // Checks for an updates last name.
            if (userEdits.get("last_name") != null) {
                if (!userEdits.get("last_name").equals(user.getLastName())) {
                    if (containsSpace(userEdits.get("last_name")) || isNotAlphanumeric(userEdits.get("last_name"))){
                        Map<String, String> error = new HashMap<String, String>();

                        error.put("error", "The last name entered is invalid.");
                        return error;
                    } else {
                        user.setLastName(Character.toString(userEdits.get("last_name").charAt(0)).toUpperCase()
                                        + userEdits.get("last_name").substring(1).toLowerCase());
                        userInfo.put("lastName", user.getLastName());
                    }
                }
            }

            // Checks for an updated password.
            if (userEdits.get("password") != null) {
                if(userEdits.get("validPassword") != null) {
                    if (!passwordService.validatePassword(userEdits.get("password"), user.getPassword()) &&
                        !passwordService.validatePassword(userEdits.get("validPassword"), user.getPassword())) {
                        try {
                            if (userEdits.get("password").length() < PASSWORD_MIN) {
                                Map<String, String> error = new HashMap<String, String>();

                                error.put("error", "Your password must be at least five " +
                                    "characters long.");
                                return error;
                            } else if (containsSpace(userEdits.get("password"))) {
                                Map<String, String> error = new HashMap<String, String>();

                                error.put("error", "Your password cannot contain spaces.");
                                return error;
                            } else
                                user.setPassword(passwordService.encryptPassword(userEdits.get("password")));
                        } catch (Exception error) {
                            throw new InvalidUserInformationException(error.getMessage());
                        }
                    } else {
                        Map<String, String> error = new HashMap<String, String>();

                        error.put("error", "The requested update could not " +
                            "be performed due to password mismatch.");
                        return error;
                    }
                } else {
                    Map<String, String> error = new HashMap<String, String>();

                    error.put("error", "The requested update could not be " +
                        "performed due to password mismatch.");
                    return error;
                }
            }

            // Checks to for dorm changes.
            if (userEdits.get("dorm") != null) {
                if (Integer.valueOf(userEdits.get("dorm")) != user.getDorm()) {
                    // Dorm selected must be valid.
                    if (Integer.valueOf(userEdits.get("dorm")) < NO_DORM_SELECTED) {
                        Map<String, String> error = new HashMap<String, String>();

                        error.put("error", "Please select a dorm or select no dorm if applicable.");
                        return error;
                    }

                    if (Integer.valueOf(userEdits.get("dorm")) > NO_DORM_SELECTED &&
                        Integer.valueOf(userEdits.get("roomNumber")) == NO_ROOM_SELECTED) {
                            Map<String, String> error = new HashMap<String, String>();

                            error.put("error", "Please enter a room number in the dorm selected.");
                            return error;
                    }

                    if (Integer.valueOf(userEdits.get("dorm")) == NO_DORM_SELECTED)
                        user.setRoomNumber(NO_ROOM_SELECTED);

                    if (!userEdits.get("dorm").equals("0")) {
                        if (Integer.valueOf(userEdits.get("dorm")) != Integer.parseInt(Integer.toString(Integer.valueOf(
                                userEdits.get("roomNumber"))).substring(0, 1))) {
                                    Map<String, String> error = new HashMap<String, String>();

                                    error.put("error", "The dorm change does not match the room number.");
                                    return error;
                        } else {
                            user.setDorm(Integer.valueOf(userEdits.get("dorm")));
                            userInfo.put("dorm", dormService.findDormById(user.getDorm()).getDormName());
                        }
                    } else {
                        user.setDorm(Integer.valueOf(userEdits.get("dorm")));
                        userInfo.put("dorm", dormService.findDormById(user.getDorm()).getDormName());
                    }
                }
            }

            // Checks for room changes.
            if (userEdits.get("roomNumber") != null && !userEdits.get("dorm").equals("0")) {
                if (Integer.valueOf(userEdits.get("roomNumber")) != user.getRoomNumber()) {

                    // Rice Floor 1 Max:0-22 Floor 2-9 Max:0-22   Floor 10-11:50-72
                    // Room Number must contain 4 digits and the first digit must match the dorm selected.
                    if (Integer.valueOf(userEdits.get("roomNumber")) != NO_ROOM_SELECTED) {
                        if (String.valueOf(Integer.valueOf(userEdits.get("roomNumber"))).length() != ROOM_NUM_LENGTH) {
                            Map<String, String> error = new HashMap<String, String>();

                            error.put("error", "Your room number must be four digits.");
                            return error;
                        }

                        if (Integer.parseInt(Integer.toString(Integer.valueOf(
                            userEdits.get("roomNumber"))).substring(0, 1)) != Integer.valueOf(userEdits.get("dorm"))) {
                            Map<String, String> error = new HashMap<String, String>();

                            error.put("error","The room number entered is not in the dorm selected (" +
                                validDorm.getDormName() + ").");
                            return error;
                        } else if (Integer.parseInt(Integer.toString(Integer.valueOf(
                            userEdits.get("roomNumber"))).substring(1, 2)) > validDorm.getNumFloors()) {
                            Map<String, String> error = new HashMap<String, String>();

                            error.put("error","The floor entered is not a valid floor.");
                            return error;
                        } else if (Integer.parseInt(Integer.toString(Integer.valueOf(
                                 userEdits.get("roomNumber"))).substring(2, 4)) > validDorm.getMaxRoom()) {
                            Map<String, String> error = new HashMap<String, String>();

                            error.put("error","The room entered is a higher number than the " +
                                "highest room number in the dorm.");
                            return error;
                        }

                        // Ballard and Coberly start at floor 1.
                        else if ((Integer.parseInt(Integer.toString(Integer.valueOf(
                                    userEdits.get("roomNumber"))).substring(0, 1)) == BALLARD_DORM_ID ||
                                Integer.parseInt(Integer.toString(Integer.valueOf(
                                    userEdits.get("roomNumber"))).substring(0, 1)) == COBERLY_DORM_ID) &&
                                Integer.parseInt(Integer.toString(Integer.valueOf(
                                    userEdits.get("roomNumber"))).substring(1, 2)) < COBERLY_BALLARD_MIN_FLOOR) {

                            Map<String, String> error = new HashMap<String, String>();

                            error.put("error","The floor entered is not a valid floor.");
                            return error;
                        }

                        // Special cases for Rice.
                        else if (Integer.parseInt(Integer.toString(Integer.valueOf(
                                userEdits.get("roomNumber"))).substring(0, 1)) == RICE_DORM_ID) {
                            // If the floor entered is 2-9.
                            if ((Integer.parseInt(Integer.toString(Integer.valueOf(
                                    userEdits.get("roomNumber"))).substring(1, 2)) > RICE_MIN_FLOOR &&
                                Integer.parseInt(Integer.toString(Integer.valueOf(
                                    userEdits.get("roomNumber"))).substring(1, 2)) <= RICE_MAX_FLOOR) &&
                                Integer.parseInt(Integer.toString(Integer.valueOf(
                                    userEdits.get("roomNumber"))).substring(2, 4)) > RICE_LOW_FLOOR_MAX_ROOM) {

                                Map<String, String> error = new HashMap<String, String>();

                                error.put("error", "The room entered is invalid.");
                                return error;
                            }

                            // If the floor entered is 1 and the room entered is between 23-49.
                            if (Integer.parseInt(Integer.toString(Integer.valueOf(
                                    userEdits.get("roomNumber"))).substring(1, 2)) == RICE_MIN_FLOOR &&
                               (Integer.parseInt(Integer.toString(Integer.valueOf(
                                   userEdits.get("roomNumber"))).substring(2, 4)) > RICE_LOW_FLOOR_MAX_ROOM &&
                                Integer.parseInt(Integer.toString(Integer.valueOf(
                                    userEdits.get("roomNumber"))).substring(2, 4)) < RICE_HIGH_FLOOR_MIN_ROOM)) {

                                Map<String, String> error = new HashMap<String, String>();

                                error.put("error", "The room entered is invalid.");
                                return error;
                            }

                            // If the floor entered is 0 and the room is less than 50.
                            if (Integer.parseInt(Integer.toString(Integer.valueOf(
                                    userEdits.get("roomNumber"))).substring(1, 2)) == RICE_MIN_FLOOR-1 &&
                                Integer.parseInt(Integer.toString(Integer.valueOf(
                                    userEdits.get("roomNumber"))).substring(2, 4)) < RICE_HIGH_FLOOR_MIN_ROOM) {

                                Map<String, String> error = new HashMap<String, String>();

                                error.put("error", "The room entered is invalid.");
                                return error;
                            }
                        }
                        user.setRoomNumber(Integer.valueOf(userEdits.get("roomNumber")));
                        userInfo.put("roomNumber", String.valueOf(user.getRoomNumber()));
                    }
                }
            } else if (userEdits.get("roomNumber").equals(null) && userEdits.get("dorm").equals("No Dorm")) {
                user.setRoomNumber(NO_ROOM_SELECTED);
                userInfo.put("roomNumber", String.valueOf(user.getRoomNumber()));
            }

            userService.updateUser(user);
            request.setAttribute("firstname", user.getFirstName());
            request.setAttribute("action", "updateSession");
            sessionTrack.doGet(request, response);
            return userInfo;
        } else {
            throw new SessionExceptions("Your session has timed out.");
        }
    }

    // Adds items to the user cart.
    @SuppressWarnings("unchecked")
    @PostMapping("addItem/{productId}/{quantity}")
    public void addItemsToCart(@PathVariable("productId") int productId, @PathVariable("quantity") int quantity,
                               HttpServletRequest request, HttpServletResponse response)
                               throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        // Adds items to an users account.
        if (session != null && !session.getAttribute("firstname").equals("guest")) {

            OrderItems item = new OrderItems(productId,
                    (int) session.getAttribute("orderId"), quantity);

                orderItemsService.insertItem(item);

                request.setAttribute("quantity", quantity);
                request.setAttribute("action", "addToCart");

                sessionTrack.doGet(request, response);

        // Handles guest interactions after their cart has been created
        // and one item has been added.
        } else if (session != null && session.getAttribute("firstname").equals("guest")) {

            Map<String, Integer> item                 = new HashMap<String, Integer>();
            Map<String, Map<String, Integer>> itemMap = new HashMap<String, Map<String, Integer>>();
            boolean addedItem = false;

            // Handles all guest interaction with a cart.
            try {
                Map<String, Map<String, Integer>> sessionItems = (Map<String, Map<String, Integer>>)
                    session.getAttribute("cartItems");
                boolean updatedQuantity                        = false;
                int counter                                    = 0,
                    listSearch                                 = 0;

                // Searches the guest cart looking for an item to update quantity.
                // If no item is found the item is added into the cart.
                for (counter = 0; counter < sessionItems.size() + Integer.valueOf(
                        session.getAttribute("itemCount").toString()) && addedItem == false; counter++) {
                    Map<String, Integer> tempItem = new HashMap<String, Integer>();

                    // Handles updating quantity when a guest adds an item.
                    // Try...catch used to handle session item that had been removed.
                    try {
                        tempItem.put("productId", sessionItems.get("guestItem" + counter).get("productId"));

                        // Updates the quantity of an item or throws an error when an item cannot be found.
                        // UpdateQuantity is set to true so we don't add the item in again.
                        if(sessionItems.get("guestItem" + counter).get("productId").equals(productId)) {
                            tempItem.put("quantity", sessionItems.get("guestItem" + counter).get("quantity") + quantity);
                            updatedQuantity = true;
                        } else
                            tempItem.put("quantity", sessionItems.get("guestItem" + counter).get("quantity"));

                        itemMap.put("guestItem" + counter, tempItem);
                    } catch (NullPointerException e) {

                        // Searches the entire list looking for an item with the product number.
                        // If it finds the item it updates the quantity. This handles searching for
                        // every item.
                        for (listSearch = 0; listSearch <= Integer.valueOf(
                                session.getAttribute("itemCount").toString()) && updatedQuantity == false; listSearch++) {
                            try {
                                if (sessionItems.get("guestItem" + listSearch).get("productId").equals(productId)) {
                                    tempItem.put("productId", sessionItems.get("guestItem" +
                                        listSearch).get("productId"));
                                    tempItem.put("quantity",  quantity);
                                    itemMap.put("guestItem" + listSearch, tempItem);
                                    updatedQuantity = true;
                                }
                            } catch (NullPointerException nullPointer) {
                                logger.warn(nullPointer);
                                // Do nothing when index item is not found.
                            }
                        }

                        // Adds a new item into the list if the quantity is not updated.
                        if (updatedQuantity == false) {
                            addedItem = true;
                            item.put("productId", productId);
                            item.put("quantity", quantity);
                            itemMap.put("guestItem" + counter, item);
                        }
                    }
                }

                // Adds item into guest list if there is not an
                // item already added or updated.
                if (updatedQuantity == false && addedItem == false) {
                    item.put("productId", productId);
                    item.put("quantity", quantity);
                    itemMap.put("guestItem" + counter, item);
                    request.setAttribute("itemIncrease", counter);
                }

                // Combines old items and new items together to handle missing
                // items farther in the list. Increases the list size for searching
                // to update quantity.
                if (addedItem == true) {
                    itemMap.putAll(sessionItems);
                    if (counter > Integer.valueOf(session.getAttribute("itemCount").toString()))
                        request.setAttribute("itemIncrease", listSearch);
                }

                // Makes two merges to handle updating and adding items together.
                if (updatedQuantity == true) {
                    sessionItems.putAll(itemMap);
                    itemMap.putAll(sessionItems);
                }
            } catch (NullPointerException exception) {}

            request.setAttribute("quantity", quantity);
            request.setAttribute("items", itemMap);
            request.setAttribute("action", "addToGuestCart");

            sessionTrack.doGet(request, response);
        } else {
            // Handles when a guest first adds an item to their cart.

            Map<String, Integer> item                 = new HashMap<String, Integer>();
            Map<String, Map<String, Integer>> itemMap = new HashMap<String, Map<String, Integer>>();
            item.put("productId", productId);
            item.put("quantity", quantity);
            itemMap.put("guestItem0", item);

            request.setAttribute("itemIncrease",  ITEMCOUNT);
            request.setAttribute("quantity", quantity);
            request.setAttribute("items", itemMap);
            request.setAttribute("action", "addToGuestCart");

            sessionTrack.doGet(request, response);
        }
    }

    // Updates and items quantity or removes item if the item quantity is 0.
    @SuppressWarnings("unchecked")
    @PostMapping("updateQuantity/{productId}/{quantity}")
    public void updateItemQuantity(@PathVariable("productId") int productId, @PathVariable("quantity") int quantity,
                                      HttpServletRequest request, HttpServletResponse response)
                                      throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        // Removes an item in its entirety or removes only a specific quantity for a user.
        if (session != null && !session.getAttribute("firstname").equals("guest")) {
            OrderItems removable = new OrderItems(productId, Integer.valueOf(session.getAttribute("orderId").toString()), quantity);

            if (quantity != EMPTY)
                orderItemsService.updateQuantity(removable);
            else
                orderItemsService.deleteItem(removable);

            int updatedQuantity = orderItemsService.getQuantity((int) session.getAttribute("orderId"));

            request.setAttribute("quantity", updatedQuantity);
            request.setAttribute("action", "removeFromCart");

            sessionTrack.doGet(request, response);
        } else {
            Map<String, Map<String, Integer>> sessionItems = (Map<String, Map<String, Integer>>) session.getAttribute("cartItems");
            int updatedQuantity = 0;

            // Removes a specific items or updates its quantity.
            for (int itemCounter = 0; itemCounter < sessionItems.size() +
                   Integer.valueOf(session.getAttribute("itemCount").toString()); itemCounter++) {
                try {
                    if (sessionItems.get("guestItem" + itemCounter).get("productId").equals(productId)) {
                        if (quantity == EMPTY) {
                            sessionItems.remove("guestItem" + itemCounter);
                        } else
                            sessionItems.get("guestItem" + itemCounter).put("quantity", quantity);
                    }
                    updatedQuantity += sessionItems.get("guestItem" + itemCounter).get("quantity");
                } catch (NullPointerException exception) {
                    // Do nothing when the item index is not found.
                }
            }

            request.setAttribute("quantity", updatedQuantity);
            request.setAttribute("items", sessionItems);
            request.setAttribute("action", "removeFromGuestCartQuantity");

            sessionTrack.doGet(request, response);
        }
    }

    // Removes all items from the users cart.
    @PostMapping("removeAll")
    public void removeAllItems(HttpServletRequest request, HttpServletResponse response)
                               throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        if (session != null && !session.getAttribute("firstname").equals("guest")) {

            orderItemsService.deleteAllItems(Integer.valueOf(session.getAttribute("orderId").toString()));

            request.setAttribute("action", "emptyCart");

            sessionTrack.doGet(request, response);
        } else if (session != null && session.getAttribute("firstname").equals("guest")) {
            request.getSession().invalidate();
        } else
            throw new SessionExceptions("Your session has timed out.");
    }

    // Checks out an users cart.
    @PostMapping("checkout")
    public Map<String, String> completeOrder(HttpServletRequest request, HttpServletResponse response)
                                throws ServletException, IOException {

            HttpSession session = request.getSession(false);

            if (session != null && !session.getAttribute("firstname").equals("guest")) {
                if (Integer.parseInt(session.getAttribute("cartQuantity").toString()) > EMPTY) {
                    double balance = userService.getTotalPrice(
                        (int)session.getAttribute("userId"), (int)session.getAttribute("orderId"),
                        Double.parseDouble(String.valueOf(session.getAttribute("total"))));

                    if (balance >= MINIMUM_BALANCE) {
                        Orders currentOrder = new Orders();

                        currentOrder.setOrderId((int)session.getAttribute("orderId"));
                        currentOrder.setUserId((int)session.getAttribute("userId"));
                        currentOrder.setIsOrdered(true);
                        currentOrder.setOrderDate(session.getAttribute("orderDate").toString());
                        currentOrder.setLocation(session.getAttribute("deliveryAddress").toString());
                        currentOrder.setTotal(Double.parseDouble(session.getAttribute("total").toString()));

                        ordersService.updateOrders(currentOrder, balance, (int)session.getAttribute("userId"));

                        Orders newOrder = new Orders((int)session.getAttribute("userId"));

                        ordersService.insertOrder(newOrder);
                        newOrder = ordersService.getUncompletedOrder((int)session.getAttribute("userId"));

                        request.setAttribute("userBalance", String.format("%.2f", balance));
                        request.setAttribute("orderId", newOrder.getOrderId());
                        request.setAttribute("action","updateSession");

                        sessionTrack.doGet(request, response);

                        session.removeAttribute("authCheckout");
                        session.setAttribute("authConfirm", "authConfirm");

                        return null;
                    } else {
                        Map<String, String> error = new HashMap<String, String>();

                        error.put("error", "The account is lacking funds.");
                        return error;
                    }
                } else {
                    Map<String, String> error = new HashMap<String, String>();

                    error.put("error", "There are no items in the cart.");
                    return error;
                }
            } else
                throw new SessionExceptions("Your session has timed out.");
    }

    // Completes a guests order.
    @PostMapping("checkoutGuest")
    public Map<String, String> completeGuestOrder (@RequestBody Map<String, String> funds,
                                    HttpServletRequest request, HttpServletResponse response)
                                    throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        if (session != null && session.getAttribute("firstname").equals("guest")) {
            if (Integer.parseInt(session.getAttribute("cartQuantity").toString()) > EMPTY) {
                String ccNumber = funds.get("CC_Number").replaceAll("\\s+", "");
                String cardDate = funds.get("Card_Date").replaceAll("/", "");

                if (!ccNumber.equals(CC_NUMBER)) {
                    Map<String, String> error = new HashMap<String, String>();

                    error.put("error", "The given credit card is invalid.");
                    return error;
                 } else if (!funds.get("Card_Name").equals(CARD_NAME)) {
                    Map<String, String> error = new HashMap<String, String>();

                    error.put("error", "The given name does not match the card.");
                    return error;
                  } else if (!cardDate.equals(CARD_DATE)) {
                    Map<String, String> error = new HashMap<String, String>();

                    error.put("error", "The expiration date is invalid.");
                    return error;
                  } else if (!funds.get("CVC_Number").equals(CVC_NUMBER)) {
                    Map<String, String> error = new HashMap<String, String>();

                    error.put("error", "The CVC number is invalid.");
                    return error;
                  }

                request.setAttribute("name", funds.get("fullName"));
                request.setAttribute("recipient", funds.get("recipientName"));
                request.setAttribute("deliveryAddress", "Campus Store.");
                request.setAttribute("action", "updateDelivery");

                sessionTrack.doGet(request, response);

                session.removeAttribute("authCheckout");
                session.setAttribute("authConfirm", "authConfirm");
                return null;
            } else
                throw new SessionExceptions("There are no items in the cart.");

        } else
            throw new SessionExceptions("Your session has timed out.");
    }

    // Sets routing authentication into session.
    @PostMapping("/setAuCo")
    public void setAuthentication(HttpServletRequest request, HttpServletResponse response)
                                    throws ServletException, IOException {

        request.getSession(false).setAttribute("authCheckout", "authCheckout");
    }

    // Logs out an user.
    @GetMapping("/logoutUser")
    public void logoutActiveUser(HttpServletRequest request, HttpServletResponse response)
                                 throws ServletException, IOException{
        if (request.getSession(false) != null) {
            request.getSession().invalidate();
        } else
            throw new SessionExceptions("Your session has timed out.");
    }

     // Checks for spaces in a string
    private boolean containsSpace(String userInput) {
        char currentChar;

        if(userInput.length() < 1)
            return true;

        // Checks each character of a string for a space.
        for (int charCounter = 0; charCounter < userInput.length(); charCounter++) {
            currentChar = userInput.charAt(charCounter);
            if(currentChar == ' ' || currentChar == '\t')
                return true;
        }
        return false;
    }

    // Checks for non alphanumeric characters
    private boolean isNotAlphanumeric(String userInput) {
        return !(userInput.matches("[a-zA-Z0-9]+"));
    }
}