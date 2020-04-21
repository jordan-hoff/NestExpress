package nestexpress.nest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// Throws a 404 error for incorrect password.
@SuppressWarnings("serial")
@ResponseStatus(HttpStatus.NOT_FOUND)
public class PasswordNotCorrectException extends RuntimeException {
    public PasswordNotCorrectException(String message) {
        super(message);
    }
    public PasswordNotCorrectException(String message, Throwable throwable) {
        super(message);
    }
}