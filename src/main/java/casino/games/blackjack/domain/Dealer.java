package casino.games.blackjack.domain;

import lombok.Data;
import org.hibernate.annotations.Cascade;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@Data
@Component
@Entity(name = "dealer")
public class Dealer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "dealerId")
    private Long id;

    @OneToOne
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    @JoinColumn(name = "dealerHandId", foreignKey = @ForeignKey(name = "handId"))
    private Hand dealerHand;

    @OneToOne
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    @JoinColumn(name = "deckId", foreignKey = @ForeignKey(name = "deckId"))
    private Deck deck;

    public Dealer(Deck deck, Hand dealerHand) {
        this.deck = deck;
        this.dealerHand = dealerHand;
    }

    public Dealer() {

    }

    public Card getVisibleCards() {
        // retruns 1 card to the user
        return dealerHand.hand.get(0);
    }

    public int getVisibleScore() {
        //get te visible card score
        return getVisibleCards().getRank().getRankValue();
    }

    public Card drawCard() {
        return deck.sendCard();
    }

}
