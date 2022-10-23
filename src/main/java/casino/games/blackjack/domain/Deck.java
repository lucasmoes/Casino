package casino.games.blackjack.domain;

import casino.games.blackjack.data.infrastructure.CardListConverter;
import org.hibernate.annotations.Cascade;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
@Entity(name = "deck")
public class Deck {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    @Column(name = "deckId")
    private Long id;

    @Convert(converter = CardListConverter.class)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    @Column(columnDefinition = "TEXT")
    private List<Card> deck = new ArrayList<>();

    public Deck() {
    }

    public Card sendCard() {
        // get the first card on the deck
        Card card = deck.get(0);
        //removing the card from the deck
        deck.remove(card);
        //returning the card for the player or dealer to use
        return card;
    }

    public void createDeck() {

        for (int i = 0; i < 13; i++) {
            Rank rank = Rank.values()[i];

            for (int j = 0; j < 4; j++) {
                Card card = new Card(Suit.values()[j], rank);
                this.deck.add(card);
            }
        }
    }

    public void shuffleDeck() {
        Collections.shuffle(deck);
    }
}
