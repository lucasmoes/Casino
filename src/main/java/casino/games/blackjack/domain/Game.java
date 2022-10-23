package casino.games.blackjack.domain;

import casino.games.blackjack.domain.exception.WrongMoveException;
import lombok.Data;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;

@Data
@Entity(name = "game")
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "gameId")
    private Long id;

    @Column(name = "username")
    private String username;

    @OneToOne
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    @JoinColumn(name = "playerHandId", foreignKey = @ForeignKey(name = "handId"))
    private Hand playerHand;

    @OneToOne
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    @JoinColumn(name = "dealerId", foreignKey = @ForeignKey(name = "dealerId"))
    private Dealer dealer;

    @Column(name = "bet")
    private Long bet;

    @Column(name = "placesBet")
    private Long placedBet;

    @Column(name = "gameState")
    @Enumerated(EnumType.STRING)
    private GameState gameState;

    public Game(Dealer dealer, Hand playerHand, String username, Long bet) {
        this.dealer = dealer;
        this.playerHand = playerHand;
        this.username = username;
        this.bet = bet;
        this.placedBet = bet;
    }

    public Game() {

    }

    public void determineGameStateStart() {
        if (playerHand.getScore() == 21) {
            //BLACKJACK get 1.5x back
            gameState = GameState.BLACKJACK;
            chipCalculator();
        } else {
            gameState = GameState.PLAYING;
        }
    }

    public void determineGameStateHit() {

        int playerHandScore = playerHand.getScore();

        if (playerHandScore > 21) {
            //BUST (Lost) gets nothinng back
            gameState = GameState.BUST;
            chipCalculator();

        } else if (playerHandScore == 21) {
            // WON 2X their bet
            gameState = GameState.WON;
            chipCalculator();

        } else {
            gameState = GameState.PLAYING;
        }
    }

    public void determineGameStateStand() {
        int dealerHandScore = dealer.getDealerHand().getScore();
        int playerHandScore = playerHand.getScore();

        if (playerHandScore == dealerHandScore) {
            // PUSH (tie) get their bet back
            gameState = GameState.PUSH;

        } else if (playerHandScore > dealerHandScore && playerHandScore <= 21) {
            // WON 2X their bet
            gameState = GameState.WON;

        } else if (dealerHandScore > 21 && playerHandScore <= 21) {
            // WON 2X their bet
            gameState = GameState.WON;

        } else if (playerHandScore < dealerHandScore && dealerHandScore <= 21) {
            // Lost gets nothinng back
            gameState = GameState.LOST;

        } else if (playerHandScore > 21 && dealerHandScore > 21) {
            // Lost gets nothinng back
            gameState = GameState.LOST;
        }
        chipCalculator();
    }

    public GameState getGameState() {
        return gameState;
    }

    public void chipCalculator() {
        long newBet;

        if (gameState == GameState.BLACKJACK) {
            newBet = (long) (bet * 1.5);
        } else if (gameState == GameState.WON) {
            newBet = (bet * 2);
        } else if (gameState == GameState.SURRENDER) {
            newBet = (long) (bet * 0.5);
        } else if (gameState == GameState.BUST || gameState == GameState.LOST) {
            newBet = 0;
        } else {
            newBet = bet;
        }

        this.bet = newBet;
    }

    public void startGame() {
        gameState = GameState.PLAYING;

        // user getting two cards
        // dealer getting two cards
        playerHand.addCardToHand(dealer.drawCard());
        dealer.getDealerHand().addCardToHand(dealer.drawCard());
        playerHand.addCardToHand(dealer.drawCard());
        dealer.getDealerHand().addCardToHand(dealer.drawCard());

        // check game state if blackjack or not
        determineGameStateStart();
        dealer.getDealerHand().getScore();
        //return the whole game object
    }

    //Actions==================================================
    public void move(String moveType){
        if (gameState != GameState.PLAYING) {
            return;
        }
        if (moveType.equals("hit")) {
            hit();
        } else if (moveType.equals("stand")) {
            stand();
        } else if (moveType.equals("double down")) {
            doubleDown();
        } else if (moveType.equals("surrender")) {
            surrender();
        } else {
            throw new WrongMoveException("Move must be hit, stand, double down, surrender, you have: " + moveType);
        }
    }

    public void hit() {
        playerHand.addCardToHand(dealer.drawCard());

        //check game state to see if you can play after the initial hit
        determineGameStateHit();
    }

    public void stand() {
        int score = dealer.getDealerHand().getScore();

        while (score < 17 && score <= playerHand.getScore()) {
            //dealer keeps hitting until above 17
            dealer.getDealerHand().addCardToHand(dealer.drawCard());
            score = dealer.getDealerHand().getScore();
        }

        // check game state after stand but also ends it
        determineGameStateStand();
    }

    public void doubleDown() {
        this.bet = bet * 2;

        playerHand.addCardToHand(dealer.drawCard());

        if (playerHand.getScore() > 21) {
            //BUST (Lost) gets nothinng back
            gameState = GameState.BUST;
            chipCalculator();
            return;
        }

        stand();
    }

    public void surrender() {
        // when surrendering score for dealer was not appearing in the db no it is
        dealer.getDealerHand().getScore();
        // get half the bet back (getBackHalfBet)
        // end round
        this.gameState = GameState.SURRENDER;
        chipCalculator();

    }
}
