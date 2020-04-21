package nestexpress.nest.controllers;

import nestexpress.nest.entity.Dorm;
import nestexpress.nest.entity.OrderItems;
import nestexpress.nest.entity.Orders;
import nestexpress.nest.entity.User;
import nestexpress.nest.exceptions.InvalidUserInformationException;
import nestexpress.nest.session.SessionTrack;
import nestexpress.nest.services.DormService;
import nestexpress.nest.services.EncryptService;
import nestexpress.nest.services.OrderItemsService;
import nestexpress.nest.services.OrdersService;
import nestexpress.nest.services.UserService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RestController()
@RequestMapping("/signup")
public class SignupController {

    private static final int NO_DORM_SELECTED = 0;
    private static final int NO_ROOM_SELECTED = 0;
    private static final int BALLARD_DORM_ID  = 1;
    private static final int COBERLY_DORM_ID  = 2;
    private static final int RICE_DORM_ID     = 7;
    private static final int COBERLY_BALLARD_MIN_FLOOR = 1;
    private static final int RICE_HIGH_FLOOR_MIN_ROOM  = 50;
    private static final int RICE_LOW_FLOOR_MAX_ROOM   = 22;
    private static final int RICE_MIN_FLOOR   = 1;
    private static final int RICE_MAX_FLOOR   = 9;
    private static final int ROOM_NUM_LENGTH  = 4;
    private static final int PASSWORD_MIN     = 5;
    private static final int USERNAME_MIN     = 3;

    private final DormService dormService;
    private final UserService userService;
    private final EncryptService passwordService;
    private final OrdersService ordersService;
    private final OrderItemsService orderItemsService;

    final static Logger logger = Logger.getLogger(SignupController.class);

    @Autowired
    public SignupController(UserService userService,
                            EncryptService passwordService,
                            DormService dormService,
                            OrdersService ordersService,
                            OrderItemsService orderItemsService) {
        this.userService       = userService;
        this.passwordService   = passwordService;
        this.dormService       = dormService;
        this.ordersService     = ordersService;
        this.orderItemsService = orderItemsService;
    }

    @Autowired
    SessionTrack sessionTrack;

    @SuppressWarnings("unchecked")
    @PostMapping(path="/createUser")
    public Map<String, String> createUser (@RequestBody Map<String,String> user, HttpServletRequest request, HttpServletResponse response)
                              throws ServletException, IOException {
        HttpSession session       = request.getSession(false);
        User validUser            = new User(user);
        Dorm validDorm            = new Dorm();
        Map<String, String> error = new HashMap<String, String>();

        validDorm = dormService.findDormById(validUser.getDorm());

        // First and Last name cannot contain spaces or be null.
        if (validUser.getFirstName() == null || containsSpace(validUser.getFirstName())
                                             || isNotAlphanumeric(validUser.getFirstName())) {
            error.put("error", "The first name entered is invalid.");

            return error;
        } else if (validUser.getLastName() == null || containsSpace(validUser.getLastName())
                                                   || isNotAlphanumeric(validUser.getLastName())) {
            error.put("error", "The last name entered is invalid.");

            return error;
        } else {
            validUser.setFirstName(Character.toString(validUser.getFirstName().charAt(0)).toUpperCase()
                                      + validUser.getFirstName().substring(1).toLowerCase());
            validUser.setLastName(Character.toString(validUser.getLastName().charAt(0)).toUpperCase()
                                      + validUser.getLastName().substring(1).toLowerCase());
        }

        // Username must contain at least three characters and cannot contain spaces or non-alphanumeric characters.
        // When a null user is not returned from the database, then the username entered is already being used.
        if (validUser.getUsername().isEmpty()) {
            error.put("error", "Please enter a username.");

            return error;
        } else if (containsSpace(validUser.getUsername())) {
            error.put("error", "Your username cannot contain spaces.");

            return error;
        } else if (isNotAlphanumeric(validUser.getUsername())) {
            error.put("error", "Your username can only contain alphanumeric characters.");

            return error;
        } else if (validUser.getUsername().length() < USERNAME_MIN) {
            error.put("error", "Your username must be at least " +
                    USERNAME_MIN + " characters long.");

            return error;
        } else if (userService.findByUsername(validUser.getUsername().toLowerCase()) != null) {
            error.put("error", "This username cannot be used.");

            return error;
        } else
            validUser.setUsername(validUser.getUsername().toLowerCase());

        // Password must be at least five characters long, contain no spaces, and match
        // the re-entered password.
        try {
            if (user.get("password").length() < PASSWORD_MIN) {
                error.put("error", "Your password must be at least " +
                        PASSWORD_MIN + " characters long.");

                return error;
            } else if (containsSpace(user.get("password"))) {
                error.put("error", "Your password cannot contain spaces.");

                return error;
            } else if (!(user.get("password").equals(user.get("validPassword")))) {
                error.put("error", "The passwords entered do not match.");

                return error;
            } else
                validUser.setPassword(passwordService.encryptPassword(user.get("validPassword")));
        } catch (Exception e) {
            error.put("error", e.getMessage());
        }

        // Dorm selected must be valid.
        if (validUser.getDorm() < NO_DORM_SELECTED) {
            error.put("error", "Please select a dorm or select no dorm if applicable.");

            return error;
        }

        if (validUser.getDorm() > NO_DORM_SELECTED && validUser.getRoomNumber() == NO_ROOM_SELECTED) {
            error.put("error", "Please enter a room number in the dorm selected.");

            return error;
        }

        if (validUser.getDorm() == NO_DORM_SELECTED)
            validUser.setRoomNumber(NO_ROOM_SELECTED);

        // Rice Floor 1 Max:0-22 Floor 2-9 Max:0-22   Floor 10-11:50-72
        // Room Number must 4 digits and the first digit must match the dorm selected.
        if (validUser.getRoomNumber() != NO_ROOM_SELECTED) {
            if (String.valueOf(validUser.getRoomNumber()).length() != ROOM_NUM_LENGTH) {
                error.put("error", "Your room number must be four digits.");

                return error;
            } else if (Integer.parseInt(Integer.toString(validUser.getRoomNumber()).substring(0, 1)) != validUser.getDorm()) {
                error.put("error", "The room number entered is not in the dorm selected (" +
                        validDorm.getDormName() + ").");

                return error;
            } else if (Integer.parseInt(Integer.toString(validUser.getRoomNumber()).substring(1, 2)) > validDorm.getNumFloors()) {
                error.put("error", "The floor entered is not a valid floor.");

                return error;
            } else if (Integer.parseInt(Integer.toString(validUser.getRoomNumber()).substring(2, 4)) > validDorm.getMaxRoom()) {
                error.put("error", "The room entered is a higher number than the highest room number " +
                        "in the dorm.");

                return error;
            }

            // Ballard and Coberly start at floor 1.
            else if ((Integer.parseInt(Integer.toString(validUser.getRoomNumber()).substring(0, 1)) == BALLARD_DORM_ID ||
                      Integer.parseInt(Integer.toString(validUser.getRoomNumber()).substring(0, 1)) == COBERLY_DORM_ID) &&
                      Integer.parseInt(Integer.toString(validUser.getRoomNumber()).substring(1, 2)) < COBERLY_BALLARD_MIN_FLOOR) {
                error.put("error", "The floor entered is not a valid floor.");

                return error;
            // Special cases for Rice.
            } else if (Integer.parseInt(Integer.toString(validUser.getRoomNumber()).substring(0, 1)) == RICE_DORM_ID) {
                // If the floor entered is 2-9.
                if ((Integer.parseInt(Integer.toString(validUser.getRoomNumber()).substring(1, 2)) > RICE_MIN_FLOOR &&
                        Integer.parseInt(Integer.toString(validUser.getRoomNumber()).substring(1, 2)) <= RICE_MAX_FLOOR) &&
                        Integer.parseInt(Integer.toString(validUser.getRoomNumber()).substring(2, 4)) > RICE_LOW_FLOOR_MAX_ROOM) {
                    error.put("error", "The room entered is invalid.");

                    return error;
                }

                // Rooms 7123-7149 should give an error.
                if (Integer.parseInt(Integer.toString(validUser.getRoomNumber()).substring(1, 2)) == RICE_MIN_FLOOR &&
                    (Integer.parseInt(Integer.toString(validUser.getRoomNumber()).substring(2, 4)) > RICE_LOW_FLOOR_MAX_ROOM &&
                     Integer.parseInt(Integer.toString(validUser.getRoomNumber()).substring(2, 4)) < RICE_HIGH_FLOOR_MIN_ROOM)) {
                    error.put("error", "The room entered is invalid.");

                    return error;
                }

                // If the floor entered is 0 and the room is less than 50.
                if (Integer.parseInt(Integer.toString(validUser.getRoomNumber()).substring(1, 2)) == RICE_MIN_FLOOR-1 &&
                    Integer.parseInt(Integer.toString(validUser.getRoomNumber()).substring(2, 4)) < RICE_HIGH_FLOOR_MIN_ROOM) {
                    error.put("error", "The room entered is invalid.");

                    return error;
                }
            }
        } else {
            if (validUser.getDorm() != NO_DORM_SELECTED) {
                error.put("error", "Please enter a room number in the dorm selected.");

                return error;
            }
        }

        userService.insertUser(validUser);
        ordersService.insertOrder(new Orders(validUser.getUserId()));

        Orders userOrder = ordersService.getUncompletedOrder(validUser.getUserId());

        // Adds items into the new users account.
        if (session != null && session.getAttribute("firstname").equals("guest")) {
            List<OrderItems> guestOrder                    = new ArrayList<OrderItems>();
            Map<String, Map<String, Integer>> sessionItems =
                       (Map<String, Map<String, Integer>>)session.getAttribute("cartItems");

            for (int itemCount = 0; itemCount <=
            Integer.valueOf(session.getAttribute("itemCount").toString()); itemCount++) {
                try {
                    if (sessionItems.get("guestItem" + itemCount) != null) {
                        guestOrder.add(new
                            OrderItems(Integer.valueOf(sessionItems.get("guestItem" + itemCount)
                                .get("productId")),
                                userOrder.getOrderId(),
                                Integer.valueOf(sessionItems.get("guestItem" + itemCount).get("quantity"))));
                    }
                } catch (NullPointerException exception) {
                    logger.warn(exception);
                }
            }

            orderItemsService.insertListItems(guestOrder, userOrder.getOrderId());
            request.setAttribute("action", "guestToSignUp");
        }

        request.setAttribute("firstname", validUser.getFirstName());
        request.setAttribute("userId", validUser.getUserId());
        request.setAttribute("userBalance", validUser.getBalance());
        request.setAttribute("orderId", userOrder.getOrderId());
        request.setAttribute("cartQuantity", orderItemsService.getQuantity(userOrder.getOrderId()));
        sessionTrack.doGet(request, response);

        return null;
    }

    // Gets all dorm information.
    @GetMapping(path = "/dorm")
    public List<String> getDormList() {
        List<String> dormList = new ArrayList<String>();
        List<Dorm> allDorms = dormService.getAllDorms();
        for (int i = 0; i < allDorms.size(); i++)
            dormList.add((allDorms.get(i).getDormName()));
        return dormList;
    }

    // Checks for spaces in a string and returns true if a space or null string is found.
    private boolean containsSpace(String userInput) {
        char currentChar;

        // If a string has no characters, it is invalid.
        if (userInput.length() < 1)
            return true;

        // Checks each character of the string until a space is found or the end of the string is reached.
        for (int charCounter = 0; charCounter < userInput.length(); charCounter++) {
            currentChar = userInput.charAt(charCounter);
            if (currentChar == ' ' || currentChar == '\t')
                return true;
        }
        return false;
    }

    // Checks for alphanumeric characters and returns true if a non-alphanumeric character is found.
    private boolean isNotAlphanumeric(String userInput) {
        return !(userInput.matches("[a-zA-Z0-9]+"));
    }
}