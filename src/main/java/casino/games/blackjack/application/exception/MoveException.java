package casino.games.blackjack.application.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class MoveException extends RuntimeException {
    public MoveException(String message) {
        super(message);
    }
}

