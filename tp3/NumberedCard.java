package Uno;

public class NumberedCard extends ColoredCard {
    private final int number;

    public NumberedCard(String color, int number) {
        super(color);
        this.number = number;
    }
    protected boolean likesNumber( int aNumber ) { return number == aNumber; }
    public boolean acceptsCard(Card cardInHand) { return cardInHand.likesColor( color ) || cardInHand.likesNumber( number ); }

    public void applyEffect(Game game) {game.noEffectApplied(this);}
    public boolean equalsCard(Card cardInHand) { return cardInHand.likesColor( color ) && cardInHand.likesNumber( number ); }
}
