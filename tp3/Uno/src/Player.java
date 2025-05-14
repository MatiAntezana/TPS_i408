import java.util.*;

public class Player {
    private String name;
    private List<Card> hand;

    public Player(String nameNewPlayer) {
        name = nameNewPlayer;
        hand = new ArrayList<>();
        // if (initialHand == null || initialHand.isEmpty()) { throw new IllegalArgumentException("El jugador debe tener cartas."); }
        // this.hand = new ArrayList<>(initialHand); // Copia defensiva
    }

    public void addCardsPlayer(Card newCard){
        hand.add(newCard);
    }

    public String getName(){return name;}

    public List<Card> getHand() { return hand; }

    public void drawCard(Card drawnCard) { hand.add(drawnCard); }

    public Card drawAndTryPlay(Card drawnCard, Card topDiscard) {
        if (topDiscard.acceptsCard(drawnCard)) {
            return drawnCard;
        } else {
            drawCard(drawnCard);
            return null;
        }
    }
}
