package casino.games.blackjack.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class NumberOfDeckException extends RuntimeException {
    public NumberOfDeckException(String message){
        super(message);
    }
}
