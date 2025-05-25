package Uno;

public class ReverseCard extends SpecialCard {
    public ReverseCard(String color) { super(color); }
    public void applyEffect(Game game){ game.reverseRound(this); }
}
