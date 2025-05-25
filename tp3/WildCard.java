package Uno;

public class WildCard extends Card {
    private String selectedColor;

    public WildCard asRed() { this.selectedColor = "Red"; return this; }
    public WildCard asBlue() { this.selectedColor = "Blue"; return this; }
    public WildCard asYellow() { this.selectedColor = "Yellow"; return this; }
    public WildCard asGreen() { this.selectedColor = "Green"; return this; }

    protected boolean likesColor(String aColor) { return true; }
    public boolean acceptsCard(Card cardInHand) { return cardInHand.likesColor( selectedColor ); }

    public void applyEffect(Game game) {game.noEffectApplied(this);}
    public boolean equalsCard(Card cardInHand) { return cardInHand.likesMyType(this); }
}
