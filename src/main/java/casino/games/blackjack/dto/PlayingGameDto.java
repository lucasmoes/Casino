package casino.games.blackjack.dto;

import casino.games.blackjack.domain.*;

public class PlayingGameDto {
    private Long id;
    private Dealer dealer;
    private Hand playerHand;
    private Long bet;
    private Long placedBet;
    private String username;
    private GameState gameState;

    public PlayingGameDto(Game game) {
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

    public Card getDealerVisibleCards() {
        return dealer.getVisibleCards();
    }

    public int getDearlerVisibleScore() {
        return dealer.getVisibleScore();
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