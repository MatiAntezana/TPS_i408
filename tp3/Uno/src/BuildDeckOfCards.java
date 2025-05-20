import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.IntStream;

public class BuildDeckOfCards {
    public Map<Integer, Card> createNumberedCards(String color) {
        return IntStream.rangeClosed(0, 9)
                .boxed()
                .collect(Collectors.toMap(
                        Function.identity(),               // clave: el propio Integer (0,1,2…9)
                        i -> new NumberedCard(color, i)    // valor: nueva instancia para ese número
                ));
    }

    public Map<String, Card> createReverseCards(String... colorCards) {
        return Arrays.stream(colorCards)
                // la clave es el nombre del color, el valor es la nueva ReverseCard
                .collect(Collectors.toMap(
                        Function.identity(),
                        ReverseCard::new
                ));
    }
    public Map<String, Card> createSkipCards(String... colorCards) {
            return Arrays.stream(colorCards)
                    .collect(Collectors.toMap(
                            Function.identity(),   // clave: el propio String (el color)
                            SkipCard::new          // valor: nueva instancia de SkipCard(color)
                    ));
    }

    public Map<String, Card> createDraw2Cards(String... colorCards) {
        return Arrays.stream(colorCards)
                .collect(Collectors.toMap(
                        Function.identity(),   // clave: el propio String (el color)
                        Draw2Card::new         // valor: nueva instancia de Draw2Card(color)
                ));
    }


    public static Card createWildCard() { return new WildCard(); }
}