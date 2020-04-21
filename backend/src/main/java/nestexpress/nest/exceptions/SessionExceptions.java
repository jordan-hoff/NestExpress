package nestexpress.nest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// Throws a 409 error for conflicts in the session.
@SuppressWarnings("serial")
@ResponseStatus(HttpStatus.CONFLICT)
public class SessionExceptions extends RuntimeException {
    public SessionExceptions(String message) {
		super(message);
	}
	public SessionExceptions(String message, Throwable throwable) {
		super(message, throwable);
	}
}