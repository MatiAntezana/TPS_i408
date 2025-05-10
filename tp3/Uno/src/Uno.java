import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Game {
    List<Card> PoolCard = new ArrayList<Card>();

    public Game(){
        PoolCard.addAll(IntStream.range(0,9)
                .mapToObj(i -> new CardWithNumber("Red", i))
                .collect(Collectors.toList()));
        PoolCard.addAll(IntStream.range(0,9)
                .mapToObj(i -> new CardWithNumber("Green", i))
                .collect(Collectors.toList()));
        PoolCard.addAll(IntStream.range(0,9)
                .mapToObj(i -> new CardWithNumber("Yellow", i))
                .collect(Collectors.toList()));
        PoolCard.addAll(IntStream.range(0,9)
                .mapToObj(i -> new CardWithNumber("Blue", i))
                .collect(Collectors.toList()));

        PoolCard.addAll(IntStream.range(0,3)
                .mapToObj(i -> new ReverseCard("Red"))
                .collect(Collectors.toList()));
        PoolCard.addAll(IntStream.range(0,3)
                .mapToObj(i -> new ReverseCard("Green"))
                .collect(Collectors.toList()));
        PoolCard.addAll(IntStream.range(0,3)
                .mapToObj(i -> new ReverseCard("Yellow"))
                .collect(Collectors.toList()));
        PoolCard.addAll(IntStream.range(0,3)
                .mapToObj(i -> new ReverseCard("Blue"))
                .collect(Collectors.toList()));

        PoolCard.addAll(IntStream.range(0,3)
                .mapToObj(i -> new DrawCard("Red"))
                .collect(Collectors.toList()));
        PoolCard.addAll(IntStream.range(0,3)
                .mapToObj(i -> new DrawCard("Green"))
                .collect(Collectors.toList()));
        PoolCard.addAll(IntStream.range(0,3)
                .mapToObj(i -> new DrawCard("Yellow"))
                .collect(Collectors.toList()));
        PoolCard.addAll(IntStream.range(0,3)
                .mapToObj(i -> new DrawCard("Blue"))
                .collect(Collectors.toList()));

        PoolCard.addAll(IntStream.range(0,3)
                .mapToObj(i -> new SkipCard("Red"))
                .collect(Collectors.toList()));
        PoolCard.addAll(IntStream.range(0,3)
                .mapToObj(i -> new SkipCard("Green"))
                .collect(Collectors.toList()));
        PoolCard.addAll(IntStream.range(0,3)
                .mapToObj(i -> new SkipCard("Yellow"))
                .collect(Collectors.toList()));
        PoolCard.addAll(IntStream.range(0,3)
                .mapToObj(i -> new SkipCard("Blue"))
                .collect(Collectors.toList()));

        PoolCard.addAll(IntStream.range(0,3)
                .mapToObj(i -> new WildCard())
                .collect(Collectors.toList()));


    }
}
