package casino.games.blackjack.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class WrongMoveException extends RuntimeException {
    public WrongMoveException(String message){
        super(message);
    }
}
