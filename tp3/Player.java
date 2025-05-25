package Uno;

import java.util.*;

public class Player {
    private final String name;
    private final List<Card> hand;

    public Player(String nameNewPlayer) {
        name = nameNewPlayer;
        hand = new ArrayList<>();
    }

    public void addCard(Card newCard){ hand.add(newCard); }
    public String getName(){ return name; }
    public Integer getNumberCards(){ return hand.size(); }
    public void removeCard(Card cardToRemove) {
        hand.remove(hand.stream()
                .filter(card -> card.equalsCard(cardToRemove))
                .findFirst()
                .get());

    }
}