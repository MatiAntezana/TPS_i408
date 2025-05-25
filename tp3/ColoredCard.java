package Uno;

public abstract class ColoredCard extends Card {
    protected String color;

    public ColoredCard(String color) { this.color = color; }
    protected boolean likesColor( String aColor ) { return color.equals( aColor ); }
}
