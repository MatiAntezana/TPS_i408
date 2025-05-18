import java.util.*;
import java.util.stream.Collectors;

public class Game {
    private List<Player> players;
    private List<Card> deck;
    private Card pitCard;

    public Game(List<Card> Deck, Integer cantCardsPlayer, String ... ListPlayers) {
        this.players = new ArrayList<>();
        deck = new LinkedList<>(Deck);
        pitCard = deck.removeFirst();
        initializePlayers(cantCardsPlayer, ListPlayers);
        pitCard.applyEffect(this);
    }

    public void initializePlayers(Integer cantCardsPlayer, String... ListPlayers) {
        players = Arrays.stream(ListPlayers)
                .map(nombre -> {
                    Player player = new Player(nombre);
                    for (int i = 0; i < cantCardsPlayer; i++) {
                        player.addCard(deck.removeFirst());
                    }
                    return player;
                })
                .collect(Collectors.toList());
    }

    public Integer getCantCardsPlayer(String playerName){
        return players.stream().filter(p -> p.getName().equals(playerName)).findFirst().get().getCantCards();
    }

    public Boolean isPlayerInGame(String playerName) {
        return players.stream().anyMatch(player -> player.getName().equals(playerName));
    }

    public Card getPitCard() { return pitCard; }

    public void playround(Card cardInHand, Player player) {
        player.removeCard(cardInHand);
        players.add(player);
        effects(cardInHand);
    }

    public void play(Card cardInHand){
        if (pitCard.acceptsCard(cardInHand) == false) {
            throw new IllegalArgumentException("No puedes jugar esa carta");
        }
        Player player = players.removeFirst();
        playround(cardInHand, player);
        verifyUno(cardInHand, player);
    }

    private void verifyUno(Card cardInHand, Player player) {
        if (player.getCantCards() == 1) {
            cardInHand.CheckSayUno(this, player);
        }
    }

    public void Draw2Deck(Player player){
        player.addCard(deck.removeFirst());
        player.addCard(deck.removeFirst());
    }

    public void Grab(){
        Card cardToGrab = deck.removeFirst();
        Player player = players.removeFirst();
        player.addCard(cardToGrab);
        players.addFirst(player);
    }

    public void playerDraw2() {
        Player player = players.removeFirst();
        Draw2Deck(player);
        players.add(player);
    }

    public void skipTurn(){
        Player player = players.removeFirst();
        players.add(player);
    }

    public void reverseRound(){
        Player player = players.removeFirst();
        players.reversed();
        players.add(player);
    }

    private void effects(Card CardToPlay) {
        CardToPlay.applyEffect(this);
        pitCard = CardToPlay;
    }
}
