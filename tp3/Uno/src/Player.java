import java.util.*;

//public class Player {
//    private int id;
//    private List<Card> hand;
//
//    public Player(int id, List<Card> initialHand) {
//        this.id = id;
//        if (initialHand == null || initialHand.isEmpty()) { throw new IllegalArgumentException("El jugador debe tener cartas."); }
//        this.hand = new ArrayList<>(initialHand); // Copia defensiva
//    }
//
//    public int getId() { return id; }
//    public List<Card> getHand() { return hand; }
//    public boolean hasWon() { return hand.isEmpty(); }
//    public void drawCard(Card drawnCard) { hand.add(drawnCard); }
//
//
//    // Devuelve la primera carta que puede jugar, o null si ninguna aplica
//    public Card choosePlayableCard(Card topDiscard) {
//        return hand.stream()
//                .filter(topDiscard::acceptsCard)
//                .findFirst()
//                .orElse(null);
//    }
//
//    public Card playCard(Card card) {
//        hand.remove(card);
//        return card;
//    }
//
//    public Card drawAndTryPlay(Card drawnCard, Card topDiscard) {
//        if (topDiscard.acceptsCard(drawnCard)) {
//            return drawnCard;
//        } else {
//            drawCard(drawnCard);
//            return null;
//        }
//    }
//}

public class Player {
    private String name;
    private List<Card> hand;

    public Player(String nameNewPlayer) {
        name = nameNewPlayer;
        hand = new ArrayList<>();
    }

    public void addCard(Card newCard){ hand.add(newCard); }

    public String getName(){ return name; }

    public Card playCard(Card card) {
        hand.remove(card);
        return card;
    }

    public Integer getCantCards(){return hand.size(); }

    public void removeCard(Card cardToRemove) {
        hand.remove(cardToRemove);
    }

    //public List<Card> getHand() { return hand; }

    //no modelamos el hecho de que si no tiene agarra del mazo y juega, porque le pasamos nosotros la carta desde afuera?

//    public Card drawAndTryPlay(Card drawnCard, Card topDiscard) { /
//        if (topDiscard.acceptsCard(drawnCard)) {
//            return drawnCard;
//        } else {
//            drawCard(drawnCard);
//            return null;
//        }
//    }
}