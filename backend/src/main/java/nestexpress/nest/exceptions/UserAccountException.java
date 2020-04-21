package nestexpress.nest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// Throws a 409 error for invalid user account information.
@SuppressWarnings("serial")
@ResponseStatus(HttpStatus.CONFLICT)
public class UserAccountException extends RuntimeException {
    public UserAccountException(String message) {
		super(message);
	}
	public UserAccountException(String message, Throwable throwable) {
		super(message, throwable);
	}
}