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


    public Boolean isPlayerInGame(String playerName) {
        return players.stream().anyMatch(player -> player.getName().equals(playerName)); }


    public Card getPitCard() { return pitCard; }


    public void play(Card cardInHand){
        if (pitCard.verifyAcceptCard(cardInHand) == false) {
            throw new IllegalArgumentException("No puedes jugar esa carta");
        }
        Player player = players.removeFirst();
        verifyUno(cardInHand, player);
        player.removeCard(cardInHand);
        players.add(player);
        effects(cardInHand);
    }

    private void verifyUno(Card cardInHand, Player player) {
    }

    public void Grab(){
        Card cardToGrab = deck.removeFirst();
        Player player = players.removeFirst();
        player.addCard(cardToGrab);
        players.addFirst(player);
    }

    public void playerDraw2() {
        Player player = players.removeFirst();
        player.addCard(deck.removeFirst());
        player.addCard(deck.removeFirst());
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
