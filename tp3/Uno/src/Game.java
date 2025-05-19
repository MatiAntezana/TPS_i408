import java.util.*;
import java.util.stream.Collectors;

public class Game {
    public List<Player> players;
    public List<Card> deck;
    private Card pitCard;

    public Game(List<Card> Deck, Integer cantCardsPlayer, String ... ListPlayers) {
        this.players = new ArrayList<>();
        deck = new LinkedList<>(Deck);
        pitCard = deck.removeFirst();
        initializePlayers(cantCardsPlayer, ListPlayers);
        pitCard.initialEffect(this);
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

    public Integer getCantCardsPlayer(String playerName) {
        return players.stream().filter(p -> p.getName().equals(playerName)).findFirst().get().getNumberCards();
    }

    public Boolean isPlayerInGame(String playerName) {
        return players.stream().anyMatch(player -> player.getName().equals(playerName));
    }

    public Card getPitCard() { return pitCard; }

    public void play(Card cardInHand){
        if (!pitCard.acceptsCard(cardInHand)) {
            throw new IllegalArgumentException("No puedes jugar esa carta");
        }
        effects(cardInHand);
    }

    private void verifyUnoorWin(Card cardInHand, Player player) {
        if (player.getNumberCards() == 1) {
            cardInHand.CheckSayUno(this, player);
        }
    }

    public void Draw2Deck(Player player){
        player.addCard(deck.removeFirst());
        player.addCard(deck.removeFirst());
    }


    public Card Grab(){ //el jugador solo puede jugar la carta que agarro
        Card cardToGrab = deck.removeFirst();
        Player player = players.removeFirst();
        player.addCard(cardToGrab);
        players.addFirst(player);
        return cardToGrab;
    }

    //carta = game.GrabCard
    //game.play(carta)

    public void playerDraw2Initial(){
        Player ActualPlayer = players.removeFirst();
        Draw2Deck(ActualPlayer);
        players.add(ActualPlayer);
    }

    public void ReverseEffect(){
        Collections.reverse(players);
    }

    public void skipInitial(){
        Player ActualPlayer = players.removeFirst();
        players.add(ActualPlayer);
    }


    public Player processPlayerTurn(Card CardToPlay){
        Player ActualPlayer = players.removeFirst();
        ActualPlayer.removeCard(CardToPlay);
        verifyUnoorWin(CardToPlay, ActualPlayer);
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
        ReverseEffect();
        players.add(ActualPlayer);
    }

    private void effects(Card CardToPlay) {
        CardToPlay.applyEffect(this);
        pitCard = CardToPlay;
    }
}



//public class Game {
//    private List<Player> players;
//    private List<Card> deck;
//    private Card pitCard;
//
//    public Game(List<Card> Deck, Integer cantCardsPlayer, String ... ListPlayers) {
//        this.players = new ArrayList<>();
//        deck = new LinkedList<>(Deck);
//        pitCard = deck.removeFirst();
//        initializePlayers(cantCardsPlayer, ListPlayers);
//        applyEffect(pitCard);
//    }
//
//    public void initializePlayers(Integer cantCardsPlayer, String... ListPlayers) {
//        players = Arrays.stream(ListPlayers)
//                .map(nombre -> {
//                    Player player = new Player(nombre);
//                    for (int i = 0; i < cantCardsPlayer; i++) {
//                        player.addCard(deck.removeFirst());
//                    }
//                    return player;
//                })
//                .collect(Collectors.toList());
//    }
//
//
//    public Boolean isPlayerInGame(String playerName) {
//        return players.stream().anyMatch(player -> player.getName().equals(playerName)); }
//
//
//    public Card getPitCard() { return pitCard; }
//
//
////    public void play(Card cardInHand){
////        pitCard.acceptsCard(cardInHand);
////        Player player = players.removeFirst();
////        players.add(player);
////        effects(cardInHand);
////    }
//
////    public void play(Card cardInHand){
////        pitCard.acceptsCard(cardInHand);
////        Player player = players.removeFirst();
////        players.add(player); // no le sacas la carta al jugador una vez que la juega o si?
////        effects(cardInHand);
////    }
//
////    public void play(Card cardInHand) {
////        Optional.of(cardInHand)
////                .filter(c -> pitCard.acceptsCard(c))
////                .ifPresent(c -> {
////                    Player player = players.removeFirst();
////                    players.add(player);
////                    effects(c); // solo se ejecuta si la carta es aceptada
////                });
////    }
//
//
//    public void play(Card cardInHand){
//        if (!pitCard.acceptsCard(cardInHand)) {
//            throw new IllegalArgumentException("No puedes jugar esa carta");
//        }
//        Player player = players.removeFirst();
//        //verifyUno(cardInHand, player);
//        //player.removeCard(cardInHand);
//        players.add(player);
//        effects(cardInHand);
//    }
//
//
//    private void applyEffect(Card CardToPlay){
////        players = CardToPlay.applyEffectPlayer(players, deck);
////        deck = CardToPlay.applyEffectDeck(deck);
//    }
//
//
//    private void effects(Card CardToPlay) {
//        applyEffect(CardToPlay);
//        pitCard = CardToPlay;
//    }
//}
