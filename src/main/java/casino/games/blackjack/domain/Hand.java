package casino.games.blackjack.domain;

import casino.games.blackjack.data.infrastructure.CardListConverter;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.List;

@Component
@Entity(name = "hand")
public class Hand {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "handId")
    private Long id;

    @Column(name = "score")
    private int score;

    @Convert(converter = CardListConverter.class)
    @Column(columnDefinition = "TEXT")
    protected List<Card> hand;

    public Hand(List<Card> hand) {
        this.hand = hand;
    }

    public Hand() {

    }

    public int getScore() {
        int score = 0;
        int aceCount = 0;
        for (Card c : hand) {
            score = score + c.getRank().getRankValue();
            if (c.getRank().equals(Rank.ACE)) {
                aceCount += 1;
            }
        }

        if (score > 21) {
            for (Card card : hand) {
                if (card.getRank().equals(Rank.ACE) && score > 21 && aceCount != 0) {
                    score = score - 10;
                    aceCount = -1;
                }
            }
        }
        this.score = score;
        return score;
    }

    public void addCardToHand(Card card) {
        hand.add(card);
    }

    public List<Card> getHand() {
        return hand;
    }
}
