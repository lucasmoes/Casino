package casino.games.blackjack.dto;


import casino.games.blackjack.domain.Game;
import casino.games.blackjack.domain.GameState;

public class GameToRepresentInAListDto {
    private Long id;
    private Long bet;
    private Long placedBet;

    private int dealerHandScore;

    private int playerHandScore;
    private GameState gameState;

    public GameToRepresentInAListDto(Game game) {
        this.id = game.getId();
        this.dealerHandScore = game.getDealer().getDealerHand().getScore();
        this.playerHandScore = game.getPlayerHand().getScore();
        this.bet = game.getBet();
        this.placedBet = game.getPlacedBet();
        this.gameState = game.getGameState();
    }

    public Long getId() {
        return id;
    }

    public Object getDealerHandScore() {
        if (gameState == GameState.PLAYING) {
            return "Game is still playing!";

        } else {
            return dealerHandScore;
        }
    }

    public int getPlayerHandScore() {
        return playerHandScore;
    }

    public Long getBet() {
        return bet;
    }

    public GameState getGameState() {
        return gameState;
    }

    public Long getPlacedBet() {
        return placedBet;
    }
}