package Uno;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Game {
    private List<Player> players;
    private List<Card> deck;
    private Card pitCard;

    public Game(List<Card> Deck, Integer numberCardsPlayer, String ... ListPlayers) {
        this.players = new ArrayList<>();
        deck = new LinkedList<>(Deck);
        pitCard = deck.removeFirst();
        initializePlayers(numberCardsPlayer, ListPlayers);
    }

    public void initializePlayers(Integer numberCardsPlayer, String... ListPlayers) {
        players = Arrays.stream(ListPlayers)
                .map(nombre -> {
                    Player player = new Player(nombre);
                    for (int i = 0; i < numberCardsPlayer; i++) {
                        player.addCard(deck.removeFirst());
                    }
                    return player;
                })
                .collect(Collectors.toList());
    }


    public Integer getNumberPlayersCards(String playerName) {
        return players.stream().filter(p -> p.getName().equals(playerName)).findFirst().get().getNumberCards();
    }


    public boolean isPlayerInGame(String playerName) {
        return players.stream().anyMatch(player -> player.getName().equals(playerName));
    }


    public Card getPitCard() { return pitCard; }


    public Player processPlayerTurn(Card CardToPlay){
        Player ActualPlayer = players.removeFirst();
        ActualPlayer.removeCard(CardToPlay);
        checkUno(CardToPlay, ActualPlayer);
        return ActualPlayer;
    }


    public void play(Card cardInHand){
        checkWin();
        if (!pitCard.acceptsCard(cardInHand)) {
            throw new IllegalArgumentException("No puedes jugar esa carta.");
        }
        effects(cardInHand);
    }


    private void effects(Card cardToPlay) {
        cardToPlay.applyEffect(this);
        pitCard = cardToPlay;
    }


    public void noEffectApplied(Card cardToPlay){
        Player ActualPlayer = processPlayerTurn(cardToPlay);
        players.add(ActualPlayer);
    }


    public void playerDraw2(Card CardToPlay) {
        Player ActualPlayer = processPlayerTurn(CardToPlay);
        Player NextPlayer = players.removeFirst();
        Draw2Deck(NextPlayer);
        players.add(ActualPlayer);
        players.add(NextPlayer);
    }


    public void Draw2Deck(Player player){
        IntStream.range(0, 2).forEach(i -> player.addCard(deck.removeFirst()));
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


    public Card Grab(){
        checkWin();
        Card cardToGrab = deck.removeFirst();
        Player player = players.removeFirst();
        player.addCard(cardToGrab);
        players.addFirst(player);
        return cardToGrab;
    }


    private void checkUno(Card cardInHand, Player player) {
        if (player.getNumberCards() == 1 && !cardInHand.checkForUno()) {
            Draw2Deck(player);
        }
    }


    private void checkWin(){
        if (players.stream().anyMatch(p -> p.getNumberCards() == 0)){
            throw new IllegalArgumentException("Juego terminado.");
        }
    }
}