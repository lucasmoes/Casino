package casino.games.blackjack.data.infrastructure;

import casino.games.blackjack.domain.Card;
import casino.games.blackjack.domain.Rank;
import casino.games.blackjack.domain.Suit;
import org.apache.logging.log4j.util.Strings;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.ArrayList;
import java.util.List;

@Converter
public class CardListConverter implements AttributeConverter<List<Card>, String> {

    @Override
    public String convertToDatabaseColumn(List<Card> cards) {
        List<String> cardStrings = new ArrayList<>();
        for (Card card : cards) {
            String encodedCard = card.getSuit() + "," + card.getRank();
            cardStrings.add(encodedCard);
        }
        return Strings.join(cardStrings, ';');
    }

    @Override
    public List<Card> convertToEntityAttribute(String data) {
        String[] cardStrings = data.split(";");
        List<Card> cards = new ArrayList<>();
        for (String encodedCard : cardStrings) {
            String[] parts = encodedCard.split(",");
            Suit suit = Suit.valueOf(parts[0]);
            Rank rank = Rank.valueOf(parts[1]);
            cards.add(new Card(suit, rank));
        }
        return cards;
    }
}