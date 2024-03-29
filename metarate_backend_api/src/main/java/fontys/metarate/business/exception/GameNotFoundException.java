package fontys.metarate.business.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class GameNotFoundException extends ResponseStatusException {
    public GameNotFoundException() {
        super(HttpStatus.NOT_FOUND,"Game not found");
    }
}
