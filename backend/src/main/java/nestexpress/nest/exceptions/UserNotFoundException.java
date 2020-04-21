package nestexpress.nest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// Throws a 404 error for user not found.
@SuppressWarnings("serial")
@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
		super(message);
	}
	public UserNotFoundException(String message, Throwable throwable) {
		super(message, throwable);
	}
}