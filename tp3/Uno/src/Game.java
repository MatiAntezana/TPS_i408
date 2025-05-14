
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.function.IntFunction;


//    public Game(Player... players) {
//        if (players == null || players.length == 0) {
//            throw new IllegalArgumentException("El juego debe tener al menos un jugador.");
//        }
//        this.players = new ArrayList<>(List.of(players)); // copia defensiva
//        this.currentPlayerIndex = 0;
//    }
//
//    public void addPlayer(int id, String... cards) {
//        Player player = new PlayerBuilder().withId(id).addCards(cards).build();
//        players.add(player);
//    }


public class Game {
    private List<Player> players;
    private Deque<Card> discardPile = new ArrayDeque<>();
    private List<Card> deck;
    private Card wellCard;

    public Game(List<Card> Deck, Integer cantCardsPlayer, String ... ListPlayers) {
        this.players = new ArrayList<>();
        deck = new LinkedList<>(Deck);
        wellCard = deck.removeFirst();
        initializePlayers(cantCardsPlayer, ListPlayers);
        applyEffect(wellCard);
    }

    private void applyEffect(Card CardToPlay){
        players = CardToPlay.applyEffectPlayer(players, deck);
        deck = CardToPlay.applyEffectDeck(deck);
    }

    private void effects(Card CardToPlay) {
        applyEffect(CardToPlay);
        wellCard = CardToPlay;
    }

    public void initializePlayers(Integer cantCardsPlayer, String... ListPlayers) {
        players = Arrays.stream(ListPlayers)
                .map(nombre -> {
                    Player p = new Player(nombre);
                    for (int i = 0; i < cantCardsPlayer; i++) {
                        p.addCardsPlayer(deck.removeFirst());
                    }
                    return p;
                })
                .collect(Collectors.toList());
    }


    public Boolean getCantCardsPlayer(String namePlayer) {
        return players.stream().anyMatch(p -> p.getName().equals(namePlayer));
    }

    public Card getWellCard() {
        return wellCard;
    }

    public void Play(Card CardToPlay){
        wellCard.acceptsCard(CardToPlay);
        Player player = players.removeFirst();
        players.add(player);
        effects(CardToPlay);
    }

}

/*
    private void addPlayer(int id, List<Card> cardObjects) {
        Player player = new Player(id, cardObjects);
        players.add(player);
    }


    public void initializeDiscardPile(String card) {
        Card discardCard = convertSingleStringToCard(card);
        deck.remove(discardCard);
        discardPile.add(discardCard);
    }
*/
    // public List<Player> getPlayers() { return players; }
    // public Player getCurrentPlayer() { return players.get(currentPlayerIndex); }
    // public void advanceTurn() { currentPlayerIndex = (currentPlayerIndex + 1) % players.size(); }


    // public List<Card> getDeckCards() { return new ArrayList<>(deck); }  // Devolvemos una copia para no permitir modificaciones externas
    // public Card getTopDiscardCard() { return discardPile.peekLast(); } // El último agregado al discardPile es el que está en la cima
    // public int getDeckSize() { return deck.size(); }



    // public void initializeDeck(long seed) {
    //     String[] colors = {"Red", "Green", "Blue", "Yellow"};
    //     for (String color : colors) {
    //         deck.add(new NumberedCard(color, 0));  // Agregar la carta con el número 0
    //         addCardsMultipleTimes(9, 2, i -> new NumberedCard(color, i + 1));  // 1-9
    //         addCardsMultipleTimes(1, 2, i -> new Draw2Card(color));   // 2 Draw2
    //         addCardsMultipleTimes(1, 2, i -> new ReverseCard(color)); // 2 Reverse
    //         addCardsMultipleTimes(1, 2, i -> new SkipCard(color));    // 2 Skip
    //     }

    //     addCardsMultipleTimes(1, 4, i -> new WildCard());  // 4 WildCards
    //     Collections.shuffle((List<?>) deck, new Random(seed));  // Mezclar las cartas
    // }


    // private void addCardsMultipleTimes(int range, int times, IntFunction<Card> constructor) {
    //     for (int i = 0; i < times; i++) {  // Usar "times" como el número de repeticiones
    //         List<Card> cards = IntStream.range(0, range)  // Rango de 0 a (range - 1)
    //                 .mapToObj(constructor)
    //                 .toList();
    //         deck.addAll(cards);
    //     }
    // }
    // public void initializePlayer(int id, String... cards) {
    //     List<Card> cardObjects = convertStringToCard(cards);
    //     addPlayer(id, cardObjects);
    //     for (Card card : cardObjects) {
    //         deck.remove(card);
    //     }
    // }
/*
private Stack<Card> convertStringToCard(String... cardStrings) {
    Stack<Card> cardObjects = new Stack<>();
    for (String str : cardStrings) {
        String[] parts = str.trim().toLowerCase().split(" ");
        if (parts.length == 1 && parts[0].equals("wild")) {
            cardObjects.push(new WildCard());
        } else if (parts.length == 2) {
            String color = parts[0];
            String type = parts[1];
            switch (type) {
                case "Skip" -> cardObjects.push(new SkipCard(color));
                case "Draw2" -> cardObjects.push(new Draw2Card(color));
                case "Reverse" -> cardObjects.push(new ReverseCard(color));
                default -> {
                    try {
                        int number = Integer.parseInt(type);
                        cardObjects.push(new NumberedCard(color, number));
                    } catch (NumberFormatException e) {
                        throw new IllegalArgumentException("Tipo de carta desconocido: " + str);
                    }
                }
            }
        } else {
            throw new IllegalArgumentException("Formato de carta inválido: " + str);
        }
    }
    return cardObjects;
}
    private Card convertSingleStringToCard(String cardString) {
        List<Card> cards = convertStringToCard(cardString);
        if (cards.size() != 1) {
            throw new IllegalArgumentException("Se esperaba una única carta, pero se recibieron múltiples.");
        }
        return cards.getFirst();
    }
*/