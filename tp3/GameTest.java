package Uno;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;


public class GameTest {
    private static String GameOver = "Juego terminado.";
    private static String InvalidCard = "No puedes jugar esa carta.";

    static Card Red(int n) { return new NumberedCard("Red", n); }
    static Card Blue(int n) { return new NumberedCard("Blue", n); }
    static Card Yellow(int n) { return new NumberedCard("Yellow", n); }
    static Card Green(int n) { return new NumberedCard("Green", n); }

    static Card red1 = Red(1);
    static Card red2 = Red(2);
    static Card red3 = Red(3);
    static Card red4 = Red(4);
    static Card red5 = Red(5);
    static Card red6() { return Red(6); }

    static Card redDraw2 = new Draw2Card("Red");
    static Card redSkip = new SkipCard("Red");
    static Card redReverse = new ReverseCard("Red");

    static Card blue1 = Blue(1);
    static Card blue2 = Blue(2);
    static Card blue3 = Blue(3);
    static Card blue4 = Blue(4);

    static Card blueDraw2 = new Draw2Card("Blue");
    static Card blueSkip = new SkipCard("Blue");
    static Card blueReverse = new ReverseCard("Blue");

    static Card green1 = Green(1);
    static Card green5 = Green(5);
    static Card green2 = Green(2);
    static Card green3() { return Green(3); }

    static Card greenSkip = new SkipCard("Green");
    static Card greenReverse = new ReverseCard("Green");
    static Card greenDraw2 = new Draw2Card("Green");

    static Card yellow2 = Yellow(2);
    static Card yellow3 = Yellow(3);
    static Card yellow5 = Yellow(5);

    static Card yellowReverse = new ReverseCard("Yellow");
    static Card yellowSkip = new SkipCard("Yellow");
    static Card yellowDraw2 = new Draw2Card("Yellow");

    static WildCard wildCard() { return new WildCard(); }

    private List<Card> smallDeck;
    private List<Card> mediumDeck;
    private List<Card> largeDeck;
    private List<Card> wildCardsDeck;
    private List<Card> skipCardsDeck;
    private List<Card> reverseCardsDeck;
    private List<Card> draw2CardsDeck;
    private List<Card> grabCardsDeck;
    private List<Card> combinationDeck;
    private List<Card> repeatedCardsDeck;
    private List<Card> winPlayerDeck;

    @BeforeEach
    public void setUp() {
        // Mazos para tests básicos
        smallDeck = List.of(red1, red2, red6(), blue2, wildCard());
        mediumDeck = List.of(red1, red2, red6(), green3(), wildCard(), redSkip, blueDraw2);
        largeDeck = List.of(red1, red2, blue1, blue2, wildCard(), redSkip, blueDraw2, redDraw2, yellowSkip, greenSkip);

        // Mazo para tests de WildCards
        wildCardsDeck = List.of(blue1, blue2, yellow3, red5, green5, yellow5, wildCard(),
                wildCard(), wildCard(), green1, green3(), red5, wildCard());

        // Mazo para tests de Skip
        skipCardsDeck = List.of(red5, red4, green5, yellow5, yellow2, wildCard(), blue2,
                wildCard(), wildCard(), blue3, blueSkip, redSkip, green1, greenSkip, red5, yellowSkip, wildCard());

        // Mazo para tests de Reverse
        reverseCardsDeck = List.of(redReverse, green1, red3, blue4, yellowReverse, wildCard(),
                blueReverse, red5, redReverse, greenReverse, wildCard(),
                wildCard(), red2, yellow3, wildCard(), yellow2);

        // Mazo para tests de Draw2
        draw2CardsDeck = List.of(redDraw2, blueDraw2, blue2, greenDraw2, red5,
                yellowDraw2, redDraw2, yellow3, green5, red5,
                blue3, green2, yellow3, blue4, blue2, yellow5,
                yellow5, yellow3, yellow5);

        // Mazo para tests de Grab
        grabCardsDeck = List.of(red2, green3(), green5, yellow5, blue4, blue3,
                wildCard(), green5, yellow5, red5);

        // Mazo para combinaciones especiales
        combinationDeck = List.of(green2, greenDraw2, greenSkip, greenReverse,
                green5, yellow2, blue3, red3, blue2, yellow5,
                yellow2, wildCard(), red1, red5, blue1, red2);

        // Mazo para cartas repetidas
        repeatedCardsDeck = List.of(red5, red3, redSkip, redDraw2, yellow3,
                redSkip, yellow2, blue2, yellow5, yellow3,
                red3, redDraw2, blue4, wildCard(), yellowSkip,
                red1, redReverse, yellow3, yellow3);

        // Mazo simple para test de victoria
        winPlayerDeck = List.of(green5, green1, green3(), green3(), green3(), blue1);
    }

    private void assertNumberPlayersCards(Card expectedCard, Game game, Integer numberPlayersCards, String playersName){
        assertPlayedCardCheckPitCard(expectedCard, game);
        assertEquals(numberPlayersCards, game.getNumberPlayersCards(playersName));
    }


    private void assertInvalidPlayAction(Card expectedCard, Game game, String messageError){
        Exception exception = assertThrows(Exception.class, () -> game.play(expectedCard));
        assertEquals(messageError, exception.getMessage());
    }


    private void assertPlayedCardCheckPitCard(Card expectedCard, Game game) {
        game.play(expectedCard);
        assertEquals(expectedCard, game.getPitCard());
    }

    @Test
    public void testPlayersAreCorrectlyAddedToGame() {
        Game game = new Game(largeDeck, 3, "Pedro", "Mati", "Juli");

        assertTrue(game.isPlayerInGame("Pedro"));
        assertTrue(game.isPlayerInGame("Mati"));
        assertTrue(game.isPlayerInGame("Juli"));

        assertFalse(game.isPlayerInGame("Tadeo"));
    }


    @Test
    public void testInitialPitCardIsFirstCardInDeck() {
        Game game = new Game(smallDeck, 2, "Pedro", "Juan");
        assertEquals(red1, game.getPitCard());
    }


    @Test
    public void testInvalidCard() {
        Game game = new Game(mediumDeck, 3, "Mati", "Juli");

        assertPlayedCardCheckPitCard(red2, game);

        assertPlayedCardCheckPitCard(wildCard().asGreen(), game);

        assertInvalidPlayAction(blue1, game, InvalidCard);

    }


    @Test
    public void testValidCard() {
        Game game = new Game(largeDeck, 3, "Mati", "Juan", "Juli");

        // Juega Mati
        assertPlayedCardCheckPitCard(red2, game);

        // Juega Juan
        assertPlayedCardCheckPitCard(wildCard().asGreen(), game);

        // Juega Juli y saltea a Mati
        assertPlayedCardCheckPitCard(greenSkip, game);

    }


    @Test
    public void testPlayCardNotInHand() {
        Game game = new Game(mediumDeck, 3, "Mati", "Juli");

        // Juega Mati
        assertPlayedCardCheckPitCard(red2, game);

        // Juega Juli
        assertPlayedCardCheckPitCard(wildCard().asBlue(), game);

        // Error al intentar jugar con carta que no tiene
        assertInvalidPlayAction(red2, game, InvalidCard);

    }


    @Test
    public void testWildCardDifferentColors() {
        Game game = new Game(wildCardsDeck, 6, "Mati", "Juli");

        // Juega Mati
        assertPlayedCardCheckPitCard(blue2, game);

        // Juega Juli
        assertPlayedCardCheckPitCard(wildCard().asYellow(), game);

        // Juega Mati
        assertPlayedCardCheckPitCard(yellow3, game);

        // Juega Juli
        assertPlayedCardCheckPitCard(wildCard().asGreen(), game);

        // Juega Mati
        assertPlayedCardCheckPitCard(wildCard().asRed(), game);

        // Juega Juli
        assertPlayedCardCheckPitCard(red5, game);

    }

    @Test
    public void testWithSkipCard() {
        Game game = new Game(skipCardsDeck, 8, "Mati", "Juli");

        // Juega Mati
        assertPlayedCardCheckPitCard(red4, game);

        // Juega Juli y saltea a mati
        assertPlayedCardCheckPitCard(redSkip, game);

        // Juega Juli de nuevo
        assertPlayedCardCheckPitCard(red5, game);

        // Juega mati
        assertPlayedCardCheckPitCard(green5, game);

        // Juega Juli y lo saltea a Mati
        assertPlayedCardCheckPitCard(greenSkip, game);

        // Juli juega de nuevo y saltea a Mati
        assertPlayedCardCheckPitCard(yellowSkip, game);

        // Juega Juli de nuevo y saltea a Mati
        assertPlayedCardCheckPitCard(blueSkip, game);

        // Juega Juli de nuevo
        assertPlayedCardCheckPitCard(blue3, game);

        //Juega Mati
        assertPlayedCardCheckPitCard(wildCard().asRed(), game);

    }


    @Test
    public void testWithReverseCard() {
        Game game = new Game(reverseCardsDeck, 5, "Mati", "Juan", "Juli");

        //Juega Mati
        assertPlayedCardCheckPitCard(red3, game);

        //Juega Juan
        assertPlayedCardCheckPitCard(redReverse, game);

        //Juega Mati
        assertPlayedCardCheckPitCard(yellowReverse, game);

        //Juega Juan
        assertPlayedCardCheckPitCard(greenReverse, game);

        //Juega Mati
        assertPlayedCardCheckPitCard(green1, game);

    }


    @Test
    public void testWithDraw2Card() {
        Game game = new Game(draw2CardsDeck, 5, "Mati", "Juli");

        // Mati juega draw2 y juli agarra 2 cartas
        assertNumberPlayersCards(blueDraw2, game, 7, "Juli");

        // Juega Mati de nuevo
        assertPlayedCardCheckPitCard(blue2, game);

        // Juega Juli
        assertPlayedCardCheckPitCard(green2, game);

        // Mati juega draw2 y juli agarra 2 cartas
        assertNumberPlayersCards(greenDraw2, game, 8, "Juli");

        // Juega Mati de nuevo draw2 y juli agarra 2 cartas
        assertNumberPlayersCards(yellowDraw2, game, 10, "Juli");

    }


    @Test
    public void testGrabCard() {
        Game game = new Game(grabCardsDeck, 3, "Mati", "Juli");

        // Mati no tiene para jugar y agarra
        Card cardToPlay = game.Grab();
        // No puede jugar la carta que agarró
        assertInvalidPlayAction(cardToPlay, game, InvalidCard);

        // Agarra otra
        Card cardToPlay2 = game.Grab();
        // No puede jugar la carta que agarró
        assertInvalidPlayAction(cardToPlay2, game, InvalidCard);

        // Agarra otra
        Card cardToPlay3 = game.Grab();
        // Pudo jugar la carta que agarró
        assertPlayedCardCheckPitCard(cardToPlay3, game);
    }


    @Test
    public void testCombinationSpecialCards() {
        Game game = new Game(combinationDeck, 6, "Mati", "Juli");

        // Juega Mati draw2 y juli tiene 8 cartas ahora
        assertNumberPlayersCards(greenDraw2, game, 8, "Juli");

        // Juega Mati
        assertPlayedCardCheckPitCard(greenSkip, game);

        // Juega Mati
        assertPlayedCardCheckPitCard(greenReverse, game);

        //Juega Juli
        assertPlayedCardCheckPitCard(wildCard().asRed(), game);

        // Mati no tiene para jugar y agarra
        Card cardToPlay = game.Grab();
        assertPlayedCardCheckPitCard(cardToPlay, game);
    }


    @Test
    public void testPlayRepeatedCards() {
        Game game = new Game(repeatedCardsDeck, 4, "Mati", "Juan", "Juli");

        // Juega Mati
        assertPlayedCardCheckPitCard(red3, game);

        // Juega Juan
        assertPlayedCardCheckPitCard(redSkip, game);

        // Se saltea Juli y juega Mati
        assertPlayedCardCheckPitCard(redSkip, game);

        // Se saltea Juan y juega Juli
        assertPlayedCardCheckPitCard(red3, game);

        // Mati juega sin cantar Uno y agarra 2 cartas
        assertNumberPlayersCards(redDraw2, game, 3, "Mati");

        // Juan agarra 2 cartas y juega Juli
        assertPlayedCardCheckPitCard(redDraw2, game);
    }


    @Test
    public void testUnoSaid() {
        Game game = new Game(mediumDeck, 2, "Mati", "Juli");

        // Mati juega y canta Uno. Se queda con 1 sola carta
        assertNumberPlayersCards(red6().uno(), game, 1, "Mati");

    }


    @Test
    public void testNotUnoSaid() {
        Game game = new Game(mediumDeck, 2, "Mati", "Juli");

        // Mati juega sin cantar Uno y levanta 2 cartas
        assertNumberPlayersCards(red2, game, 3, "Mati");

    }


    @Test
    public void testWinPlayer() {
        Game game = new Game(winPlayerDeck, 2, "Mati", "Juli");

        // Juega Mati y canta Uno. Se queda con 1 sola carta
        assertNumberPlayersCards(green3().uno(), game, 1, "Mati");

        // Juega Juli y canta Uno. Se queda con 1 sola carta
        assertNumberPlayersCards(green3().uno(), game, 1, "Juli");

        // Juega Mati y gana
        assertNumberPlayersCards(green1, game, 0, "Mati");

        // Intenta Juli y tira error al ganar Mati
        assertInvalidPlayAction(blue1, game, GameOver);

        // Intenta agarrar Juli y tira error al ganar Mati
        assertThrows(Exception.class, game::Grab);;
    }
}
