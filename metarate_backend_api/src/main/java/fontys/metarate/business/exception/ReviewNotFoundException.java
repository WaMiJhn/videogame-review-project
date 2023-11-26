package fontys.metarate.business.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ReviewNotFoundException extends ResponseStatusException {
    public ReviewNotFoundException() { super(HttpStatus.NOT_FOUND, "Review not found");}
}
