package nestexpress.nest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// Throws a 409 error for invalid wishlist items.
@SuppressWarnings("serial")
@ResponseStatus(HttpStatus.CONFLICT)
public class InvalidWishlistItemException extends RuntimeException {
    public InvalidWishlistItemException(String message) {
        super(message);
    }
    public InvalidWishlistItemException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
