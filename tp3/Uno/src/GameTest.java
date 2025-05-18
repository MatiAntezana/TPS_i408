import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

import java.util.ArrayList;
import java.util.Arrays;

public class GameTest {

    private Game game;
    private Integer cantCardsPlayer = 1;
    private Integer cantPlayers = 4;
    private List<Card> SimpleDeck;
    private List<Card> numberedRedCards;
    private List<Card> numberedBlueCards;
    private List<Card> numberedYellowCards;
    private List<Card> numberedGreenCards;

    private List<Card> skipCards;
    private List<Card> draw2Cards;
    private List<Card> reverseCards;
    private Card wildCard;

    // Messages Exception
    private String expectedMessage = "No puedes jugar esa carta";

    @BeforeEach
    public void setUp() {
        BuildDeckOfCards deckOfCards = new BuildDeckOfCards();
        numberedRedCards = deckOfCards.createNumberedCards("Red");
        numberedBlueCards = deckOfCards.createNumberedCards("Blue");
        numberedYellowCards = deckOfCards.createNumberedCards("Yellow");
        numberedGreenCards = deckOfCards.createNumberedCards("Green");

        skipCards = deckOfCards.createSkipCards("Red", "Blue", "Yellow", "Green");
        draw2Cards = deckOfCards.createDraw2Cards("Red", "Blue", "Yellow", "Green");
        reverseCards = deckOfCards.createReverseCards("Red", "Blue", "Yellow", "Green");


        wildCard = deckOfCards.createWildCard();

        SimpleDeck = new ArrayList<>(Arrays.asList(numberedRedCards.get(0), numberedRedCards.get(1), numberedRedCards.get(2),
                numberedBlueCards.get(1), numberedBlueCards.get(2),
                wildCard, skipCards.get(0), draw2Cards.get(1), draw2Cards.get(0), skipCards.get(2), wildCard, wildCard));
        // Red0, red1, red2, blue1, blue2, wild, skipRed, drawblue, drawRed, skipYellow, wild
    }

    //Deberíamos testear si se puede crear un juego vació?

    @Test
    public void testPlayersAreCorrectlyAddedToGame(){
        Game game = new Game(SimpleDeck, 2, "Pedro", "Juan","Mati","Juli");

        assertTrue(game.isPlayerInGame("Pedro"));
        assertTrue(game.isPlayerInGame("Juan"));
        assertTrue(game.isPlayerInGame("Mati"));
        assertTrue(game.isPlayerInGame("Juli"));

        assertFalse(game.isPlayerInGame("Tadeo"));
    }


    @Test
    public void testInitialPitCardIsFirstCardInDeck(){
        Game game = new Game(SimpleDeck, 2, "Pedro", "Juan","Mati","Juli");
        assertEquals(game.getPitCard(), numberedRedCards.getFirst());
    }


    //------------------------------------------------------------------------------------------------

    @Test
    public void test1() {
        Game game = new Game(SimpleDeck, 3, "Mati", "Juli");

        game.play(SimpleDeck.get(1)); // red1
        assertEquals(SimpleDeck.get(1), game.getPitCard());

        WildCard wildCard = (WildCard) SimpleDeck.get(10); // wildCard rojo
        game.play(wildCard.asBlue());
        assertEquals(wildCard, game.getPitCard());

        Card previousPitCard = game.getPitCard(); // guardamos el estado actual del pozo

        // Intentamos jugar una carta inválida (no aceptada por wild rojo)
        assertThrows(Exception.class, () -> game.play(new NumberedCard("Red",2))); //red2
    }


    @Test
    public void test2() {
        Game game = new Game(SimpleDeck, 3, "Mati", "Juan","Juli");

        Card red1 = new NumberedCard("Red",2);
        game.play(red1); // red1

        WildCard wildCard = new WildCard();

        game.play(wildCard.asBlue());
        assertEquals(wildCard, game.getPitCard());

        // Intentamos jugar una carta válida
        Card drawblue = new Draw2Card("Blue");
        game.play(drawblue); // red2
        assertEquals(drawblue, game.getPitCard());
    }

    @Test
    public void testCorrectSayUno() {
        Game game = new Game(SimpleDeck, 2, "Mati", "Juli");
        game.play(SimpleDeck.get(1).Uno());
        assertEquals(1, game.getCantCardsPlayer("Mati"));
    }

    @Test
    public void testNoSayUno() {
        Game game = new Game(SimpleDeck, 2, "Mati", "Juli");
        game.play(SimpleDeck.get(1));
        assertEquals(3, game.getCantCardsPlayer("Mati"));
    }


    //------------------------------------------------------------------------------------------------


/*

    @Test
    public void test3() {
        Game game = new Game(SimpleDeck, 2, "Pedro", "Juan", "Mati", "Juli");

        game.play(SimpleDeck.get(1)); // red1
        assertEquals(SimpleDeck.get(1), game.getPitCard());

        game.play(SimpleDeck.get(3)); // blue1
        assertEquals(SimpleDeck.get(3), game.getPitCard());

        Draw2Card draw2CardBlue = (Draw2Card) SimpleDeck.get(7); // Draw2Card blue
        game.play(draw2CardBlue);
        assertEquals(draw2CardBlue, game.getPitCard());

        Draw2Card draw2CardRed = (Draw2Card) SimpleDeck.get(8); // Draw2Card red
        game.play(draw2CardRed);
        assertEquals(draw2CardRed, game.getPitCard());

        // Intentamos jugar una carta válida
        NumberedCard red0 = (NumberedCard) SimpleDeck.getFirst();
        game.play(red0); // red0
        assertEquals(red0, game.getPitCard());
    }

        @Test
    public void test4() {
        Game game = new Game(SimpleDeck, 2, "Pedro", "Juan", "Mati", "Juli");

        game.play(SimpleDeck.get(1)); // red1
        assertEquals(SimpleDeck.get(1), game.getPitCard());

        game.play(SimpleDeck.get(3)); // blue1
        assertEquals(SimpleDeck.get(3), game.getPitCard());

        Draw2Card draw2CardBlue = (Draw2Card) SimpleDeck.get(7); // Draw2Card blue
        game.play(draw2CardBlue);
        assertEquals(draw2CardBlue, game.getPitCard());

        Draw2Card draw2CardRed = (Draw2Card) SimpleDeck.get(8); // Draw2Card red
        game.play(draw2CardRed);
        assertEquals(draw2CardRed, game.getPitCard());

        WildCard wildCardBlue = (WildCard) SimpleDeck.get(11); // wildCard azul
        game.play(wildCardBlue.asBlue());
        assertEquals(wildCardBlue, game.getPitCard());
    }

        @Test
    public void test5() {
        Game game = new Game(SimpleDeck, 2, "Pedro", "Juan", "Mati", "Juli");

        game.play(SimpleDeck.get(1)); // red1
        assertEquals(SimpleDeck.get(1), game.getPitCard());

        game.play(SimpleDeck.get(3)); // blue1
        assertEquals(SimpleDeck.get(3), game.getPitCard());

        WildCard wildCardRed = (WildCard) SimpleDeck.get(10); // wildCard rojo
        game.play(wildCardRed.asRed());
        assertEquals(wildCardRed, game.getPitCard());

        WildCard wildCardBlue = (WildCard) SimpleDeck.get(11); // wildCard azul
        game.play(wildCardBlue.asBlue());
        assertEquals(wildCardBlue, game.getPitCard());

    }
        @Test
    public void testCheckCorrectPlayPlayerWithInvalidCard(){
        // Test que prueba que tire excepción si se juega con una no aceptada
        Game game = new Game(SimpleDeck, 2, "Pedro", "Juan","Mati","Juli");
        game.play(SimpleDeck.get(1)); // red1
        assertEquals(game.getPitCard(), SimpleDeck.get(1));
        game.play(SimpleDeck.get(3)); // blue1
        assertEquals(game.getPitCard(), SimpleDeck.get(3));
        game.play(((WildCard) wildCard).asRed());
        assertEquals(game.getPitCard(), wildCard);

        Exception exception = assertThrows(Exception.class, () -> game.play(SimpleDeck.get(7)));
        assertEquals(expectedMessage, exception.getMessage());
    }

 */

}