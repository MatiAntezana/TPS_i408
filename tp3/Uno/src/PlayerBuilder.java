import java.util.*;

//public class PlayerBuilder {
//    private int id;
//    private List<Card> hand = new ArrayList<>();
//
//    public PlayerBuilder withId(int id) {
//        this.id = id;
//        return this;
//    }
//
//
//    private PlayerBuilder addCard(Card card) {
//        hand.add(card);
//        return this;
//    }
//
//
//    public PlayerBuilder addNumberedCard(String color, int number) { return addCard(new NumberedCard(color, number)); }
//    public PlayerBuilder addDraw2Card(String color) { return addCard(new Draw2Card(color)); }
//    public PlayerBuilder addReverseCard(String color) { return addCard(new ReverseCard(color)); }
//    public PlayerBuilder addSkipCard(String color) { return addCard(new SkipCard(color)); }
//    public PlayerBuilder addWildCard() { return addCard(new WildCard()); }
//
//    public Player build() { return new Player(id, hand); }
//
//}


//public class PlayerBuilder {
//    private int id;
//    private List<Card> hand = new ArrayList<>();
//
//    public PlayerBuilder withId(int id) {
//        this.id = id;
//        return this;
//    }
//
//    private PlayerBuilder addCard(Card card) {
//        hand.add(card);
//        return this;
//    }
//
//    public PlayerBuilder addCards(String... cards) {
//        for (String str : cards) {
//            String[] parts = str.trim().toLowerCase().split(" ");
//            if (parts.length == 1 && parts[0].equals("wild")) {
//                addCard(new WildCard());
//            } else if (parts.length == 2) {
//                String color = parts[0];
//                String type = parts[1];
//                switch (type) {
//                    case "skip" -> addCard(new SkipCard(color));
//                    case "draw2" -> addCard(new Draw2Card(color));
//                    case "reverse" -> addCard(new ReverseCard(color));
//                    default -> {
//                        try {
//                            int number = Integer.parseInt(type);
//                            addCard(new NumberedCard(color, number));
//                        } catch (NumberFormatException e) {
//                            throw new IllegalArgumentException("Tipo de carta desconocido: " + str);
//                        }
//                    }
//                }
//            } else {
//                throw new IllegalArgumentException("Formato de carta inválido: " + str);
//            }
//        }
//        return this;
//    }
//
//    public Player build() { return new Player(id, hand); }
//}

/*
public class PlayerBuilder {
    private int id;
    private List<Card> hand = new ArrayList<>();

    public PlayerBuilder withId(int id) {
        this.id = id;
        return this;
    }

    public PlayerBuilder addCards(List<Card> cards) {
        hand.addAll(cards);
        return this;
    }

    public Player build() { return new Player(id, hand); }
}
*/
