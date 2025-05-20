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

    private void VerifyWin(){
        if (players.stream().anyMatch(p -> p.getNumberCards() == 0)){
            throw new IllegalArgumentException("Ya terminó el juego");
        }
    }

    public Integer getCantCardsPlayer(String playerName) {
        return players.stream().filter(p -> p.getName().equals(playerName)).findFirst().get().getNumberCards();
    }

    public boolean isPlayerInGame(String playerName) {
        return players.stream().anyMatch(player -> player.getName().equals(playerName));
    }

    public Card getPitCard() { return pitCard; }

    public void play(Card cardInHand){
        VerifyWin();
        if (!pitCard.acceptsCard(cardInHand)) {
            throw new IllegalArgumentException("No puedes jugar esa carta");
        }
        effects(cardInHand);
    }

    private void verifyUno(Card cardInHand, Player player) {
        if (player.getNumberCards() == 1 && !cardInHand.CheckVarUno()) {
                Draw2Deck(player);
        }
    }

    public void Draw2Deck(Player player){
        player.addCard(deck.removeFirst());
        player.addCard(deck.removeFirst());
    }


    public Card Grab(){
        VerifyWin();
        Card cardToGrab = deck.removeFirst();
        Player player = players.removeFirst();
        player.addCard(cardToGrab);
        players.addFirst(player);
        return cardToGrab;
    }

    public Player processPlayerTurn(Card CardToPlay){
        Player ActualPlayer = players.removeFirst();
        ActualPlayer.removeCard(CardToPlay);
        verifyUno(CardToPlay, ActualPlayer);
        return ActualPlayer;
    }

    public void playNormal(Card CardToPlay){
        Player ActualPlayer = processPlayerTurn(CardToPlay);
        players.add(ActualPlayer);
    }

    public void playerDraw2(Card CardToPlay) {
        Player ActualPlayer = processPlayerTurn(CardToPlay);
        Player NextPlayer = players.removeFirst();
        Draw2Deck(NextPlayer);
        players.add(ActualPlayer);
        players.add(NextPlayer);
    }

    public void skipTurn(Card CardToPlay){
        Player ActualPlayer = processPlayerTurn(CardToPlay);
        Player NextPlayer = players.removeFirst();
        players.add(ActualPlayer);
        players.add(NextPlayer);
    }

    public void reverseRound(Card CardToPlay){
        Player ActualPlayer = processPlayerTurn(CardToPlay);
        Collections.reverse(players);
        players.add(ActualPlayer);
    }

    private void effects(Card CardToPlay) {
        CardToPlay.applyEffect(this);
        pitCard = CardToPlay;
    }
}