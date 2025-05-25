package Uno;

public abstract class SpecialCard extends ColoredCard {
    public SpecialCard(String color) { super(color); }
    public boolean acceptsCard(Card cardInHand) { return cardInHand.likesMyType(this) || cardInHand.likesColor( color ); }
    public boolean equalsCard(Card cardInHand) { return cardInHand.likesMyType(this) && cardInHand.likesColor( color );}
}
