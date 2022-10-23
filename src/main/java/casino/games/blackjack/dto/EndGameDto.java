package casino.games.blackjack.dto;

import casino.games.blackjack.domain.*;

import java.util.List;

public class EndGameDto {
    private Long id;
    private Dealer dealer;
    private Hand playerHand;
    private Long bet;
    private Long placedBet;
    private String username;
    private GameState gameState;

    public EndGameDto(Game game) {
        this.id = game.getId();
        this.username = game.getUsername();
        this.dealer = game.getDealer();
        this.playerHand = game.getPlayerHand();
        this.bet = game.getBet();
        this.placedBet = game.getPlacedBet();
        this.gameState = game.getGameState();
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public List<Card> getDealerCards() {
        return dealer.getDealerHand().getHand();
    }

    public int getDearlerScore() {
        return dealer.getDealerHand().getScore();
    }

    public Hand getPlayerHand() {
        return playerHand;
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
