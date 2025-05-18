import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

public abstract class Card {
    private boolean verifyUno;

    public boolean compareString(String string1, String string2){
        return string1.equals(string2);
    }

    public boolean verifyAcceptCard(Card cardToVerify){
        return cardToVerify.acceptsPlayedCard(this);
    }

    public abstract boolean acceptsPlayedCard(Card card);

    protected String color;

    public String getColor(){
        return color;
    }

    public abstract boolean matchNumber(Integer number);

    public abstract void applyEffect(Game game);

    // public abstract void Uno(Player player){verifyUno = true;}

    // public boolean CheckUno(){return Uno}
}

class WildCard extends Card {
    public WildCard asRed() { color = "Red"; return this; } // debería ser así o devolver una ColoredCard
    public WildCard asBlue() { color = "Blue"; return this; }
    public WildCard asYellow() { color = "Yellow"; return this; }
    public WildCard asGreen() { color = "Green"; return this; }

    public boolean acceptsPlayedCard(Card card){
        return true;
    }

    public boolean matchNumber(Integer number){
        return false;
    }

    public void applyEffect(Game game){}

}

abstract class ColoredCard extends Card {
    public ColoredCard(String color) { this.color = color; }
}

class NumberedCard extends ColoredCard {
    private Integer number;

    public NumberedCard(String color, int number) {
        super(color);
        this.number = number;
    }

    public void applyEffect(Game game){}

    public boolean matchNumber(Integer numberToVerify){
        return numberToVerify.equals(number);
    }

    public boolean acceptsPlayedCard(Card cardInHand) {
        return compareString(color, cardInHand.getColor()) || cardInHand.matchNumber(number);
    }

    public int getNumber() { return number; }

}

abstract class SpecialCard extends ColoredCard {
    public SpecialCard(String color) { super(color); }
    public boolean matchNumber(Integer numberToVerify){return false;} // Ya que no tienen numeros
    public boolean acceptsPlayedCard(Card cardInHand) {
        return compareString(cardInHand.getClass().toString(), this.getClass().toString()) || compareString(color, cardInHand.getColor());
    }

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



//import java.util.List;
//
//public abstract class Card {
//    public abstract boolean acceptsCard(Card cardInHand); // ¿Aceptas que te juegue esta carta encima?
//    public abstract boolean compareCard(Card cardToPlay);
//
//    public boolean compareString(String string1, String string2){
//        return string1.equals(string2);
//    }
//
//    public boolean compareInstance(Card cardInHand, Card cardInstance){
//        return cardInHand.getClass().equals(cardInstance.getClass());
//    } // Ver si puedo crear el Wild con un color negro neutro
//
//    public abstract List<Player> applyEffectPlayer(List<Player> players, List<Card> deck);
//
//    public abstract List<Card> applyEffectDeck(List<Card> deck);
//}
//
//
//class WildCard extends Card {
//    private String selectedColor;
//
//    public Card selectColor(String color) {
//        this.selectedColor = color;
//        return this;
//    }
//    public String getSelectedColor() { return selectedColor; }
//
//    public boolean acceptsCard(Card cardInHand) {
//        if (cardInHand instanceof WildCard) { return true; }
//        if (cardInHand instanceof ColoredCard coloredCard) {
//            System.out.println(coloredCard.getColor()); // Debe ser igual a cantCardsPlayer
//            System.out.println(selectedColor); // Debe ser igual a cantCardsPlayer
//            if (selectedColor != null && selectedColor.equals(coloredCard.getColor())) {
//                return true;
//            } else {
//                throw new IllegalArgumentException("No puedes jugar esa carta");
//
//            }
//        }
//        return false;
//    }
//
//    public boolean compareCard(Card cardToPlay) {
//        return true; // hacer
//    }
//
//    public List<Player> applyEffectPlayer(List<Player> players, List<Card> deck){
//        return players;
//    }
//
//    public List<Card> applyEffectDeck(List<Card> deck){
//        return deck;
//    }
//
//}
//
//
//abstract class ColoredCard extends Card {
//    protected String color;
//
//    public ColoredCard(String color) { this.color = color; }
//    public String getColor() { return color; }
//    protected boolean sameColor(Card cardInHand) { return cardInHand instanceof ColoredCard coloredCard && this.color.equals(coloredCard.getColor()); }
//}
//
//
//class NumberedCard extends ColoredCard {
//    private Integer number;
//
//    public boolean compareNumber(Integer NumberCard){
//        return NumberCard.equals(number);
//    }
//
//    public NumberedCard(String color, Integer number) {
//        super(color);
//        this.number = number;
//    }
//
//    public boolean compareCard(Card cardToPlay){
//        return true; // Hacer
//    }
//
//    public int getNumber() { return number; }
//
//    public boolean acceptsCard(Card cardInHand) {
//        if (cardInHand instanceof WildCard) return true;
//        if (sameColor(cardInHand)) return true;
//        if (cardInHand instanceof NumberedCard numberedCard) { return this.number == numberedCard.getNumber(); }
//        throw new IllegalArgumentException("No es aceptado");
//    }
//
//    public List<Player> applyEffectPlayer(List<Player> players, List<Card> deck) {
//        return players;
//    }
//
//    public List<Card> applyEffectDeck(List<Card> deck){
//        return deck;
//    }
//}
//
//
//abstract class SpecialCard extends ColoredCard {
//    public SpecialCard(String color) { super(color); }
//    protected abstract Class<? extends SpecialCard> typeOfThisSpecialCard(); // Subclases deben decir cuál es su tipo específico
//    public boolean acceptsCard(Card cardInHand) {
//        if (typeOfThisSpecialCard().isInstance(cardInHand) || sameColor(cardInHand) || cardInHand instanceof WildCard) return true;
//        throw new IllegalArgumentException("No es aceptado");
//    }
//}
//
//
//class Draw2Card extends SpecialCard {
//    public Draw2Card(String color) { super(color); }
//    protected Class<? extends SpecialCard> typeOfThisSpecialCard() { return Draw2Card.class; }
//
//    public boolean compareCard(Card CardToPlay) {
//        return true;
//    }
//
//    public List<Player> applyEffectPlayer(List<Player> players, List<Card> deck) {
//        Player player = players.removeFirst();
//        player.addCard(deck.get(0));
//        player.addCard(deck.get(1));
//        players.add(player);
//        return players;
//    }
//    public List<Card> applyEffectDeck(List<Card> deck){
//        deck.removeFirst();
//        deck.removeFirst();
//        return deck;
//    }
//}
//
//
//class ReverseCard extends SpecialCard {
//    public ReverseCard(String color) { super(color); }
//    protected Class<? extends SpecialCard> typeOfThisSpecialCard() { return ReverseCard.class; }
//    public void applyEffect() {}
//    public boolean compareCard(Card cardToPlay) {
//        return true; // Hacer
//    }
//
//    public List<Player> applyEffectPlayer(List<Player> players, List<Card> deck) {
//        Player player = players.removeFirst();
//        players.reversed();
//        players.add(player);
//        return players;
//    }
//
//    public List<Card> applyEffectDeck(List<Card> deck){
//        return deck;
//    }
//}
//
//
//class SkipCard extends SpecialCard {
//    public SkipCard(String color) { super(color);}
//    protected Class<? extends SpecialCard> typeOfThisSpecialCard() { return SkipCard.class;}
//    public void applyEffect() {}
//    public boolean compareCard(Card cardToPlay) {
//        return true; // Hacer
//    }
//
//    public List<Player> applyEffectPlayer(List<Player> players, List<Card> deck) {
//        Player player = players.removeFirst();
//        players.add(player);
//        return players;
//    }
//
//    public List<Card> applyEffectDeck(List<Card> deck){
//        return deck;
//    }
//}
