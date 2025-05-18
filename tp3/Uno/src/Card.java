public abstract class Card {
    public abstract boolean acceptsCard(Card cardInHand);
    protected boolean likesType(Card cardInHand) { return false; }
    protected boolean likesColor(String aColor) { return false; }
    protected boolean likesNumber(int aNumber) { return false; }
    public abstract void applyEffect(Game game);
}


class WildCard extends Card {
    private String selectedColor;

    public String getSelectedColor() { return selectedColor; }

    public WildCard asRed() { this.selectedColor = "Red"; return this; } // debería ser así o devolver una ColoredCard
    public WildCard asBlue() { this.selectedColor = "Blue"; return this; }
    public WildCard asYellow() { this.selectedColor = "Yellow"; return this; }
    public WildCard asGreen() { this.selectedColor = "Green"; return this; }

    protected boolean likesType(Card cardInHand) { return cardInHand.getClass().getSimpleName().equals(WildCard.class.getSimpleName()); }
    protected boolean likesColor(String aColor) { return true; }
    public boolean acceptsCard(Card cardInHand) { return cardInHand.likesType(this) || cardInHand.likesColor( selectedColor ); }
    public void applyEffect(Game game){}
}


abstract class ColoredCard extends Card {
    protected String color;

    public ColoredCard(String color) { this.color = color; }
    public String getColor() { return color; }
    protected boolean likesColor( String aColor ) { return color.equals( aColor ); }
}


class NumberedCard extends ColoredCard {
    private final int number;

    public NumberedCard(String color, int number) {
        super(color);
        this.number = number;
    }

    public int getNumber() { return number; }
    protected boolean likesNumber( int aNumber ) { return number == aNumber; }
    public boolean acceptsCard(Card cardInHand) { return cardInHand.likesType(this) || cardInHand.likesColor( color ) || cardInHand.likesNumber( number ); }
    public void applyEffect(Game game){}

}


abstract class SpecialCard extends ColoredCard {
    public SpecialCard(String color) { super(color); }
    protected boolean likesType(Card cardInHand) { return cardInHand.getClass().getSimpleName().equals(this.getClass().getSimpleName()); } //cardInHand.getClass().equals(this.getClass())
    public boolean acceptsCard(Card cardInHand) { return cardInHand.likesType(this) || cardInHand.likesColor( color ); }

}


class Draw2Card extends SpecialCard {
    public Draw2Card(String color) { super(color); }
    public void applyEffect(Game game){
        game.playerDraw2();
    }
}


class ReverseCard extends SpecialCard {
    public ReverseCard(String color) { super(color); }
    public void applyEffect(Game game){
        game.reverseRound();
    }
}


class SkipCard extends SpecialCard {
    public SkipCard(String color) { super(color);}
    public void applyEffect(Game game){
        game.skipTurn();
    }
}