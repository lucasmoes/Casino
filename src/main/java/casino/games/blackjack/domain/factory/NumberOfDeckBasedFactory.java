package casino.games.blackjack.domain.factory;


import casino.games.blackjack.domain.Deck;
import casino.games.blackjack.domain.exception.NumberOfDeckException;

public class NumberOfDeckBasedFactory implements DeckFactory {
    private int numberOfDecks;

    public NumberOfDeckBasedFactory(int numberOfDecks) {
        this.numberOfDecks = numberOfDecks;
    }

    public Deck createDeck() {

        Deck deck = new Deck();

        //if numberOfDecks is not specified makes a standard game with 6 decks
        if (numberOfDecks == 0) {
            numberOfDecks = 6;
        }
        if (numberOfDecks > 10 || numberOfDecks < 1) {
            throw new NumberOfDeckException("Number of decks must be between 1-10, you have: " + numberOfDecks);
        }

        for (int n = 0; n < numberOfDecks; n++) {
            deck.createDeck();
        }

        deck.shuffleDeck();

        return deck;
    }

}
