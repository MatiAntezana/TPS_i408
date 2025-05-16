import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class BuildDeckOfCards {
    public static List<Card> createNumberedCards(String colorCards) {
        List<Card> newNumberedCards = new ArrayList<>();
        for (int i = 0; i <= 9; i++) {
            newNumberedCards.add(new NumberedCard(colorCards, i));
        }
        return newNumberedCards;
    }


    public static List<Card> createReverseCards(String... colorCards) {
        return Arrays.stream(colorCards)
                .map(ReverseCard::new)
                .collect(Collectors.toList());
    }


    public static List<Card> createSkipCards(String... colorCards) {
        return Arrays.stream(colorCards)
                .map(SkipCard::new)
                .collect(Collectors.toList());
    }


    public static List<Card> createDraw2Cards(String... colorCards) {
        return Arrays.stream(colorCards)
                .map(Draw2Card::new)
                .collect(Collectors.toList());
    }


    public static Card createWildCard() { return new WildCard(); }
}