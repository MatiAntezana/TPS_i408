public abstract class Card {
    public abstract boolean acceptsCard(Card cardInHand);
    protected boolean likesType(Card cardInHand) { return false; }
    protected boolean likesColor(String aColor) { return false; }
    protected boolean likesNumber(int aNumber) { return false; }

    public abstract void applyEffect(Game game);
    public abstract boolean equalsCard(Card cardInHand);

    public ActionsUno TypeActionGame;
    public Card (){ TypeActionGame = new ActionPlayPenalty(); }
    public Card Uno(){ TypeActionGame = new ActionPlayNormal(); return this; }
    public void CheckSayUno(Game game, Player player){ TypeActionGame.playAction(game, player); }

    public void initialEffect(Game game){}; //Está bien que esto este acá, por mas de que WildCard y NumberCard no tiene efecto?
}


class WildCard extends Card {
    private String selectedColor;

    public WildCard asRed() { this.selectedColor = "Red"; return this; } // debería ser así o devolver una ColoredCard
    public WildCard asBlue() { this.selectedColor = "Blue"; return this; }
    public WildCard asYellow() { this.selectedColor = "Yellow"; return this; }
    public WildCard asGreen() { this.selectedColor = "Green"; return this; }

    protected boolean likesType(Card cardInHand) { return cardInHand.getClass().getSimpleName().equals(WildCard.class.getSimpleName()); }
    protected boolean likesColor(String aColor) { return true; }
    public boolean acceptsCard(Card cardInHand) { return cardInHand.likesType(this) || cardInHand.likesColor( selectedColor ); }

    public void applyEffect(Game game) {game.playNormal(this);}
    public boolean equalsCard(Card cardInHand) { return cardInHand.likesType(this); }

}


abstract class ColoredCard extends Card {
    public String color;

    public ColoredCard(String color) { this.color = color; }
    protected boolean likesColor( String aColor ) { return color.equals( aColor ); }
}


class NumberedCard extends ColoredCard {
    private final int number;

    public NumberedCard(String color, int number) {
        super(color);
        this.number = number;
    }
    protected boolean likesNumber( int aNumber ) { return number == aNumber; }
    public boolean acceptsCard(Card cardInHand) { return cardInHand.likesType(this) || cardInHand.likesColor( color ) || cardInHand.likesNumber( number ); }

    public boolean equalsCard(Card cardInHand) { return cardInHand.likesColor( color ) && cardInHand.likesNumber( number ); }

    public void applyEffect(Game game) {game.playNormal(this);}
}


abstract class SpecialCard extends ColoredCard {
    public SpecialCard(String color) { super(color); }
    protected boolean likesType(Card cardInHand) { return cardInHand.getClass().getSimpleName().equals(this.getClass().getSimpleName()); } //cardInHand.getClass().equals(this.getClass())
    public boolean acceptsCard(Card cardInHand) { return cardInHand.likesType(this) || cardInHand.likesColor( color ); }

    public boolean equalsCard(Card cardInHand) { return cardInHand.likesType(this) && cardInHand.likesColor( color );}

}


class Draw2Card extends SpecialCard {
    public Draw2Card(String color) { super(color); }
    public void applyEffect(Game game){ game.playerDraw2(this); }
    public void initialEffect(Game game){ game.playerDraw2Initial();}
}


class ReverseCard extends SpecialCard {
    public ReverseCard(String color) { super(color); }
    public void applyEffect(Game game){ game.reverseRound(this); }
    public void initialEffect(Game game){ game.ReverseEffect();}
}


class SkipCard extends SpecialCard {
    public SkipCard(String color) { super(color);}
    public void applyEffect(Game game){ game.skipTurn(this); }
    public void initialEffect(Game game){ game.skipInitial();}
}


//public abstract class Card {
//    public abstract boolean acceptsCard(Card cardInHand);
//    protected boolean likesType(Card cardInHand) { return false; }
//    protected boolean likesColor(String aColor) { return false; }
//    protected boolean likesNumber(int aNumber) { return false; }
//    // metodo Uno se podría implementar acá
//}
//
//
//class WildCard extends Card {
//    private String selectedColor;
//
//    public String getSelectedColor() { return selectedColor; }
//
//    public WildCard asRed() { this.selectedColor = "red"; return this; } // debería ser así o devolver una ColoredCard
//    public WildCard asBlue() { this.selectedColor = "blue"; return this; }
//    public WildCard asYellow() { this.selectedColor = "yellow"; return this; }
//    public WildCard asGreen() { this.selectedColor = "green"; return this; }
//
//    protected boolean likesType(Card cardInHand) { return cardInHand.getClass().getSimpleName().equals(WildCard.class.getSimpleName()); }
//    protected boolean likesColor(String aColor) { return true; }
//    public boolean acceptsCard(Card cardInHand) { return cardInHand.likesType(this) || cardInHand.likesColor( selectedColor ); }
//}
//
//
//abstract class ColoredCard extends Card {
//    protected String color;
//
//    public ColoredCard(String color) { this.color = color; }
//    public String getColor() { return color; }
//    protected boolean likesColor( String aColor ) { return color.equals( aColor ); }
//}
//
//
//class NumberedCard extends ColoredCard {
//    private int number;
//
//    public NumberedCard(String color, int number) {
//        super(color);
//        this.number = number;
//    }
//
//    public int getNumber() { return number; }
//    protected boolean likesNumber( int aNumber ) { return number == aNumber; }
//    public boolean acceptsCard(Card cardInHand) { return cardInHand.likesType(this) || cardInHand.likesColor( color ) || cardInHand.likesNumber( number ); }
//}
//
//
//abstract class SpecialCard extends ColoredCard {
//    public SpecialCard(String color) { super(color); }
//    public boolean acceptsCard(Card cardInHand) { return cardInHand.likesType(this) || cardInHand.likesColor( color ); }
//    public abstract void applyEffect();
//
//}
//
//
//class Draw2Card extends SpecialCard {
//    public Draw2Card(String color) { super(color); }
//    protected boolean likesType(Card cardInHand){ return cardInHand.getClass().getSimpleName().equals(Draw2Card.class.getSimpleName()); }
//    public void applyEffect() {}
//}
//
//
//class ReverseCard extends SpecialCard {
//    public ReverseCard(String color) { super(color); }
//    protected boolean likesType(Card cardInHand){ return cardInHand.getClass().getSimpleName().equals(ReverseCard.class.getSimpleName()); }
//    public void applyEffect() {}
//}
//
//
//class SkipCard extends SpecialCard {
//    public SkipCard(String color) { super(color);}
//    protected boolean likesType(Card cardInHand){ return cardInHand.getClass().getSimpleName().equals(SkipCard.class.getSimpleName()); }
//    public void applyEffect() {}
//}



//package Uno;
//
//
//public abstract class Card {
//    public abstract boolean acceptsCard(Card cardInHand);
//    // metodo Uno se podría implementar acá
//}
//
//
//class WildCard extends Card {
//    private String selectedColor;
//
//    public String getSelectedColor() { return selectedColor; }
//
//    public WildCard asRed() { this.selectedColor = "red"; return this; } // debería ser así o devolver una ColoredCard
//    public WildCard asBlue() { this.selectedColor = "blue"; return this; }
//    public WildCard asYellow() { this.selectedColor = "yellow"; return this; }
//    public WildCard asGreen() { this.selectedColor = "green"; return this; }
//
//    public boolean acceptsCard(Card cardInHand) {
//        if (cardInHand instanceof WildCard) { return true; } //getclassintcanceof
//        if (cardInHand instanceof ColoredCard coloredCard) { return selectedColor.equals(coloredCard.getColor()); }
//        return false;
//    }
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
//    private int number;
//
//    public NumberedCard(String color, int number) {
//        super(color);
//        this.number = number;
//    }
//
//    public int getNumber() { return number; }
//
//    public boolean acceptsCard(Card cardInHand) {
//        if (cardInHand instanceof WildCard) return true;
//        if (sameColor(cardInHand)) return true;
//        if (cardInHand instanceof NumberedCard numberedCard) { return this.number == numberedCard.getNumber(); }
//        return false;
//    }
//}
//
//
//abstract class SpecialCard extends ColoredCard {
//    public SpecialCard(String color) { super(color); }
//    protected abstract Class<? extends SpecialCard> typeOfThisSpecialCard(); // Subclases deben decir cuál es su tipo específico
//    public boolean acceptsCard(Card cardInHand) { return typeOfThisSpecialCard().isInstance(cardInHand) || sameColor(cardInHand) || cardInHand instanceof WildCard; }
//
//}
//
//
//class Draw2Card extends SpecialCard {
//    public Draw2Card(String color) { super(color); }
//    protected Class<? extends SpecialCard> typeOfThisSpecialCard() { return Draw2Card.class; }
//    public void applyEffect() {}
//}
//
//
//class ReverseCard extends SpecialCard {
//    public ReverseCard(String color) { super(color); }
//    protected Class<? extends SpecialCard> typeOfThisSpecialCard() { return ReverseCard.class; }
//    public void applyEffect() {}
//}
//
//
//class SkipCard extends SpecialCard {
//    public SkipCard(String color) { super(color);}
//    protected Class<? extends SpecialCard> typeOfThisSpecialCard() { return SkipCard.class;}
//    public void applyEffect() {}
//}



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
