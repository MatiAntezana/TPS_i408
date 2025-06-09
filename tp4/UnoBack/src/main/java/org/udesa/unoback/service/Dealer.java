package org.udesa.unoback.service;
import org.springframework.stereotype.Component;
import org.udesa.unoback.model.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class Dealer {

    public List<Card> fullDeck() {
        ArrayList<Card> deck = new ArrayList<>();
        deck.addAll(CardOn("Red"));
        deck.addAll(CardOn("Blue"));
        deck.addAll(CardOn("Green"));
        deck.addAll(CardOn("Yellow"));
        return deck;
    }

    public List<Card> CardOn(String color) {
        return List.of(new SkipCard(color),
                new ReverseCard(color), new Draw2Card(color),
                new NumberCard(color, 0),
                new NumberCard(color,1),
                new NumberCard(color, 2),
                new NumberCard(color, 3),
                new NumberCard(color, 4),
                new NumberCard(color, 5),
                new NumberCard(color, 6),
                new NumberCard(color, 7),
                new NumberCard(color, 8),
                new NumberCard(color, 9),
                new WildCard()
                );
    }

}
