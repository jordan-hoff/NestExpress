package nestexpress.nest.session;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;

@SuppressWarnings("serial")
@Component
public class SessionTrack extends HttpServlet {

    private final int inactiveTime = 1800; // 30 minute in seconds
    private final int EMPTY        = 0;

    // Activates the session and adds all needed attributes.
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
                      throws ServletException, IOException {

        HttpSession session = request.getSession(true);

        String action = (String) request.getAttribute("action");

        if(session.isNew() && request.getAttribute("firstname") != null) {
            session.setAttribute("firstname", request.getAttribute("firstname"));
            session.setAttribute("userId", request.getAttribute("userId"));
            session.setAttribute("userBalance", request.getAttribute("userBalance"));
            session.setAttribute("orderId", request.getAttribute("orderId"));
            session.setAttribute("cartQuantity", request.getAttribute("cartQuantity"));
            session.setAttribute("couponEntered", "false");
        } else if (session.isNew()) {
            session.setAttribute("cartQuantity", EMPTY);
            session.setAttribute("firstname", "guest");
            session.setAttribute("itemCount", EMPTY);
            session.setAttribute("couponEntered", "false");
        }

        if (action == null){}
        else if (action.equals("addFunds")) {
            doGet_AddFunds(request, response);
        } else if (action.equals("addToCart")) {
            doGet_AddToCart(request, response);
        } else if (action.equals("addToGuestCart")) {
            doGet_AddToGuestCart(request, response);
        } else if (action.equals("removeFromCart")) {
            doGet_RemoveFromCart(request, response);
        }  else if (action.equals("removeFromGuestCartQuantity")) {
            doGet_RemoveFromGuestCartQuantity(request, response);
        } else if (action.equals("emptyCart")) {
            doGet_EmptyCart(request, response);
        } else if (action.equals("updateSession")) {
            doGet_UpdateSessionAttributes(request, response);
        } else if (action.equals("guestToLogin")) {
            doGet_SignInAccount(request, response);
        } else if (action.equals("guestToSignUp")) {
            doGet_SignUpGuest(request, response);
        } else if (action.equals("updateDelivery")) {
            doGet_UpdateDelivery(request, response);
        } else if (action.equals("setCostData")) {
            doGet_setCostData(request, response);
        } else if (action.equals("enterCoupon")) {
            doGet_EnterCoupon(request, response);
        }

        // Sets the inactive session limit before expiring. Kept in terms of seconds.
        session.setMaxInactiveInterval(inactiveTime);

    }

    private void doGet_setCostData(HttpServletRequest request, HttpServletResponse response) {
        request.getSession(false).setAttribute("subtotal", request.getAttribute("subtotal"));
        request.getSession(false).setAttribute("tax", request.getAttribute("taxTotal"));
        request.getSession(false).setAttribute("total", request.getAttribute("totalCost"));
        request.getSession(false).setAttribute("couponEntered", "false");
    }

    private void doGet_UpdateDelivery(HttpServletRequest request, HttpServletResponse response) {
        request.getSession(false).setAttribute("name", request.getAttribute("name"));
        request.getSession(false).setAttribute("recipient", request.getAttribute("recipient"));
        request.getSession(false).setAttribute("deliveryAddress", request.getAttribute("deliveryAddress"));
    }

    // Sets up account for a user that had inserted items before creating an account.
    private void doGet_SignUpGuest(HttpServletRequest request, HttpServletResponse response) {
        request.getSession(false).setAttribute("firstname", request.getAttribute("firstname"));
        request.getSession(false).setAttribute("userId", request.getAttribute("userId"));
        request.getSession(false).setAttribute("userBalance", request.getAttribute("userBalance"));
        request.getSession(false).setAttribute("orderId", request.getAttribute("orderId"));
        request.getSession(false).setAttribute("cartQuantity", request.getAttribute("cartQuantity"));
    }

    // Activates all necessary parts for a guest logging into an account.
    private void doGet_SignInAccount(HttpServletRequest request, HttpServletResponse response) {
        request.getSession(false).setAttribute("firstname", request.getAttribute("firstname"));
        request.getSession(false).setAttribute("userId", request.getAttribute("userId"));
        request.getSession(false).setAttribute("userBalance", request.getAttribute("userBalance"));
        request.getSession(false).setAttribute("orderId", request.getAttribute("orderId"));
        request.getSession(false).setAttribute("cartQuantity", request.getAttribute("cartQuantity"));
    }

    // Updates the users cart with the proper quantity changes.
    private void doGet_RemoveFromGuestCartQuantity(HttpServletRequest request, HttpServletResponse response) {
        request.getSession(false).setAttribute("cartItems", request.getAttribute("items"));
        request.getSession(false).setAttribute("cartQuantity", (int) request.getAttribute("quantity"));
    }

    // Adds items to guest cart and updates cart quantity.
    private void doGet_AddToGuestCart(HttpServletRequest request, HttpServletResponse response)
                                        throws ServletException, IOException{
        request.getSession(false).setAttribute("cartQuantity",
            (int) request.getSession(false).getAttribute("cartQuantity") +
            (int) request.getAttribute("quantity"));

        request.getSession(false).setAttribute("cartItems", request.getAttribute("items"));

        // Checks to see if a new item was added. (This only works for new items and not quantity changes.)
        if (request.getAttribute("itemIncrease") != null) {
            request.getSession(false).setAttribute("itemCount",  (int) request.getAttribute("itemIncrease"));
        }
    }

    // Empties the cart of all items.
    private void doGet_EmptyCart(HttpServletRequest request, HttpServletResponse response)
                                  throws ServletException, IOException {
        request.getSession(false).setAttribute("cartQuantity", EMPTY);
    }

    // Removes items from the session cart.
    private void doGet_RemoveFromCart(HttpServletRequest request, HttpServletResponse response)
                                      throws ServletException, IOException {
        request.getSession(false).setAttribute("cartQuantity", request.getAttribute("quantity"));
    }

    //  Adds items to the session cart.
    private void doGet_AddToCart(HttpServletRequest request, HttpServletResponse response)
                                 throws ServletException, IOException {
        request.getSession(false).setAttribute("cartQuantity",
            (int) request.getSession(false).getAttribute("cartQuantity") +
            (int) request.getAttribute("quantity"));
    }

    // Adds funds to the session.
    private void doGet_AddFunds(HttpServletRequest request, HttpServletResponse response)
                                throws ServletException, IOException {
        request.getSession(false).setAttribute("userBalance",
            Double.parseDouble(String.valueOf(request.getSession(false).getAttribute("userBalance"))) +
            Double.parseDouble(String.valueOf(request.getAttribute("addFunds"))));
    }

    // Updates session with user edits.
    private void doGet_UpdateSessionAttributes(HttpServletRequest request,
                                               HttpServletResponse response)
                                               throws ServletException, IOException {
        if (request.getAttribute("firstname") != null)
            request.getSession(false).setAttribute("firstname", request.getAttribute("firstname"));

        if (request.getAttribute("orderId") != null) {
            request.getSession(false).setAttribute("orderId",      request.getAttribute("orderId"));
            request.getSession(false).setAttribute("cartQuantity", EMPTY);
            request.getSession(false).setAttribute("userBalance",  request.getAttribute("userBalance"));
            request.getSession(false).setAttribute("couponEntered", "false");
        }

        if (request.getAttribute("previousOrderId") != null) {
            request.getSession(false).setAttribute("previousOrderId",  request.getAttribute("previousOrderId"));
            request.getSession(false).setAttribute("deliveryTime",     request.getAttribute("deliveryTime"));
            request.getSession(false).setAttribute("orderDate",        request.getAttribute("orderDate"));
            request.getSession(false).setAttribute("deliveryLocation", request.getAttribute("deliveryLocation"));
        }
    }

    // Updates total after user enters a coupon.
    private void doGet_EnterCoupon(HttpServletRequest request, HttpServletResponse response)
                                                        throws ServletException, IOException {
        request.getSession(false).setAttribute("subtotal", request.getAttribute("subtotal"));
        request.getSession(false).setAttribute("tax", request.getAttribute("tax"));
        request.getSession(false).setAttribute("total", request.getAttribute("total"));
        request.getSession(false).setAttribute("couponEntered", request.getAttribute("couponEntered"));
    }
}