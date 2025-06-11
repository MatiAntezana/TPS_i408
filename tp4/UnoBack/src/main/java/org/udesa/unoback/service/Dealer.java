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
        List<String> colors = List.of("Red", "Blue", "Green", "Yellow");
        for (String color : colors) {
            deck.addAll(createNumberedCards(color));
            deck.addAll(createActionCards(color));
        }
        deck.addAll(createWildCards(4));
        Collections.shuffle(deck);
        return deck;
    }

    private List<Card> createNumberedCards(String color) {
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(new NumberCard(color, 0));
        for (int i = 1; i <= 9; i++) {
            cards.add(new NumberCard(color, i));
            cards.add(new NumberCard(color, i));
        }
        return cards;
    }

    private List<Card> createActionCards(String color) {
        return List.of(
            new Draw2Card(color), new Draw2Card(color),
            new ReverseCard(color), new ReverseCard(color),
            new SkipCard(color), new SkipCard(color)
        );
    }

    private List<Card> createWildCards(int quantity) {
        ArrayList<Card> cards = new ArrayList<>();
        for (int i = 0; i < quantity; i++) {
            cards.add(new WildCard());
        }
        return cards;
    }

}