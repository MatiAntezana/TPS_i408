package Uno;

public class SkipCard extends SpecialCard {
    public SkipCard(String color) { super(color);}
    public void applyEffect(Game game){ game.skipTurn(this); }
}
