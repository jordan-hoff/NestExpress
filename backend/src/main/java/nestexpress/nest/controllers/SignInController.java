package nestexpress.nest.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import nestexpress.nest.entity.Orders;
import nestexpress.nest.entity.User;
import nestexpress.nest.entity.OrderItems;
import nestexpress.nest.exceptions.PasswordNotCorrectException;
import nestexpress.nest.exceptions.UserNotFoundException;
import nestexpress.nest.services.EncryptService;
import nestexpress.nest.services.OrderItemsService;
import nestexpress.nest.services.OrdersService;
import nestexpress.nest.services.UserService;
import nestexpress.nest.session.SessionTrack;

@RestController
@RequestMapping("/signin")
public class SignInController {
    private final UserService userService;
    private final EncryptService passwordService;
    private final OrdersService ordersService;
    private final OrderItemsService orderItemsService;

    final static Logger logger = Logger.getLogger(SignInController.class);

    @Autowired
    public SignInController(UserService userService,
                            EncryptService encryptService,
                            OrdersService ordersService,
                            OrderItemsService orderItemsService) {
        this.userService       = userService;
        this.passwordService   = encryptService;
        this.ordersService     = ordersService;
        this.orderItemsService = orderItemsService;
    }

    @Autowired
    SessionTrack session;

    // Gets the username and password from the frontend and checks the database for an
    // account with a matching username. Then it compares the password of that account
    // with the password received from the frontend.
    @SuppressWarnings("unchecked")
    @PostMapping(value = "/user")
    public Map<String, String> validateUser(@RequestBody Map<String,String> user,
            HttpServletRequest request, HttpServletResponse response)
            throws UserNotFoundException, PasswordNotCorrectException, ServletException, IOException {
        
        User validUser               = userService.findByUsername(user.get("username").toLowerCase());
        Map<String, String> userInfo = new HashMap<String,String>();

        try {
            if (!validUser.getUsername().equals(validUser.getUsername().toLowerCase())) {
                userInfo.put("error", "The username or password entered was not correct.");
                return userInfo;
            } else if (passwordService.validatePassword(user.get("password"), validUser.getPassword())) {
                HttpSession session = request.getSession(false);

                // Logs the user in and handles adding previously added items before logging in.
                if (session != null && session.getAttribute("firstname").equals("guest")) {
                    List<OrderItems> guestOrder                    = new ArrayList<OrderItems>();
                    Map<String, Map<String, Integer>> sessionItems =
                       (Map<String, Map<String, Integer>>)session.getAttribute("cartItems");

                    Orders userOrder = ordersService.getUncompletedOrder(validUser.getUserId());

                    if (userOrder == null) {
                        userOrder = new Orders(validUser.getUserId());
                        ordersService.insertOrder(userOrder);
                    }

                    // Converts all guestItems into items that can be sent to the db.
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
                            logger.error(exception);
                        }
                    }

                    orderItemsService.insertListItems(guestOrder, userOrder.getOrderId());

                    request.setAttribute("firstname", validUser.getFirstName());
                    request.setAttribute("userId", validUser.getUserId());
                    request.setAttribute("userBalance", validUser.getBalance());
                    request.setAttribute("orderId", userOrder.getOrderId());
                    request.setAttribute("cartQuantity", orderItemsService.getQuantity(userOrder.getOrderId()));
                    request.setAttribute("action", "guestToLogin");

                    this.session.doGet(request, response);

                    userInfo.put("success", "success");
                    return userInfo;

                // Logs the user in normally is they have not added items to their cart first.
                } else {
                    Orders order = ordersService.getUncompletedOrder(validUser.getUserId());

                    if (order == null) {
                        order = new Orders(validUser.getUserId());
                        ordersService.insertOrder(order);
                    }

                    request.setAttribute("firstname", validUser.getFirstName());
                    request.setAttribute("userId", validUser.getUserId());
                    request.setAttribute("userBalance", validUser.getBalance());
                    request.setAttribute("orderId", order.getOrderId());
                    request.setAttribute("cartQuantity", orderItemsService.getQuantity(order.getOrderId()));

                    this.session.doGet(request, response);
                }
                userInfo.put("success", "success");
                return userInfo;
            } else {
                userInfo.put("error", "The username or password entered was not correct.");
                return userInfo;
            }
        } catch (NullPointerException e) {
            userInfo.put("error", "The username or password entered was not correct.");
            return userInfo;
        } catch (Exception error) {
            logger.error(error);
            throw new UserNotFoundException(error.getMessage());
        }
    }
}