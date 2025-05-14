import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class BuildMazo {
    public static List<Card> createCardsColor(String colorCards) {
        List<Card> newCardsNumbers = new ArrayList<>();
        for (int i = 0; i <= 9; i++) {
            newCardsNumbers.add(new NumberedCard(colorCards, i));
        }
        return newCardsNumbers;
    }
    public static List<Card> createCardsReverse(String... colorCards) {
        return Arrays.stream(colorCards)
                .map(ReverseCard::new)
                .collect(Collectors.toList());
    }

    public static List<Card> createCardsSkip(String... colorCards) {
        return Arrays.stream(colorCards)
                .map(SkipCard::new)
                .collect(Collectors.toList());
    }
    public static List<Card> createCardsDraw2(String... colorCards) {
        return Arrays.stream(colorCards)
                .map(Draw2Card::new)
                .collect(Collectors.toList());
    }

}
