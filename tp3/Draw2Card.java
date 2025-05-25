package Uno;

public class Draw2Card extends SpecialCard {
    public Draw2Card(String color) { super(color); }
    public void applyEffect(Game game){ game.playerDraw2(this); }
}
