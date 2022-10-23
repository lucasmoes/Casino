package casino.games.blackjack.dto;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class GameDto {

    @Positive
    @NotNull
    public Long bet;

    public int numberOfDecks;
}