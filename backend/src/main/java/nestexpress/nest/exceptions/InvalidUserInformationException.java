package nestexpress.nest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// Throws a 409 error when invalid user information is input.
@SuppressWarnings("serial")
@ResponseStatus(HttpStatus.CONFLICT)
public class InvalidUserInformationException extends RuntimeException {
    public InvalidUserInformationException(String message) {
        super(message);
    }
    public InvalidUserInformationException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
