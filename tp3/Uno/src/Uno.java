import java.util.function.IntFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Uno {
    Stack<Card> PoolCard = new Stack<>();
    List<Player> Players = new ArrayList<>();

    Card WellCard;

    public Uno(long SeedShuffleCard){
        addCardsMultipleTimes(10, 2, i -> new CardWithNumber("Red", i));
        addCardsMultipleTimes(10, 2, i -> new CardWithNumber("Green", i));
        addCardsMultipleTimes(10, 2, i -> new CardWithNumber("Yellow", i));
        addCardsMultipleTimes(10, 2, i -> new CardWithNumber("Blue", i));

        addCardsMultipleTimes(2, 1, i -> new ReverseCard("Red"));
        addCardsMultipleTimes(2, 1, i -> new ReverseCard("Green"));
        addCardsMultipleTimes(2, 1, i -> new ReverseCard("Yellow"));
        addCardsMultipleTimes(2, 1, i -> new ReverseCard("Blue"));

        addCardsMultipleTimes(2, 1, i -> new SkipCard("Red"));
        addCardsMultipleTimes(2, 1, i -> new SkipCard("Green"));
        addCardsMultipleTimes(2, 1, i -> new SkipCard("Yellow"));
        addCardsMultipleTimes(2, 1, i -> new SkipCard("Blue"));

        addCardsMultipleTimes(2, 1, i -> new DrawCard("Red"));
        addCardsMultipleTimes(2, 1, i -> new DrawCard("Green"));
        addCardsMultipleTimes(2, 1, i -> new DrawCard("Yellow"));
        addCardsMultipleTimes(2, 1, i -> new DrawCard("Blue"));

        addCardsMultipleTimes(4, 1, i -> new WildCard());

        Collections.shuffle(PoolCard, new Random(SeedShuffleCard));

        WellCard = PoolCard.pop();
    }

    public void addPlayers(Integer CantCardsForPlayer, String ... PlayersNames){
        Players.addAll(
                Stream.of(PlayersNames)
                        .map(Player::new)
                        .collect(Collectors.toList())
        );
    }

    private void addCardsMultipleTimes(int CantCard, int Times, IntFunction<Card> constructor) {
        Stream.generate(() -> IntStream.range(0, CantCard)
                        .mapToObj(constructor)
                        .collect(Collectors.toList()))
                .limit(Times)
                .forEach(PoolCard::addAll);
    }
}
