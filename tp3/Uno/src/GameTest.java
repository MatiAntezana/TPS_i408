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
                wildCard, skipCards.get(0), draw2Cards.get(1), draw2Cards.get(0), skipCards.get(2), wildCard));
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
    public void testCheckCorrectPlayPlayerWithInvalidCard(){
        // Test que prueba que tire excepción si se juega con una no aceptada
        Game game = new Game(SimpleDeck, 2, "Pedro", "Juan","Mati","Juli");
        Card red1 = new NumberedCard("Red",1);
        game.play(red1); // red1
        assertEquals(game.getPitCard(), red1);
        Card blue1 = new NumberedCard("Blue",1);
        game.play(blue1); // blue1
        assertEquals(game.getPitCard(), blue1);
        game.play(((WildCard) wildCard).asRed());
        assertEquals(game.getPitCard(), wildCard);

        Exception exception = assertThrows(Exception.class, () -> game.play(new Draw2Card("Blue")));
        assertEquals(expectedMessage, exception.getMessage());
    }


    @Test
    public void testCheckCorrectPlayPlayerWithCorrectsCards(){
        // Test que prueba que tire excepción si se juega con una no aceptada
        Game game = new Game(SimpleDeck, 2, "Pedro", "Juan","Mati","Juli");
        game.play(new NumberedCard("Red", 1)); // red1
        game.play(new NumberedCard("Blue", 1)); // blue1
        game.play(((WildCard) wildCard).asRed());
        Draw2Card drawRed = new Draw2Card("Red");// wild
        game.play(drawRed); // drawRed
        assertEquals(game.getPitCard(), drawRed);
    }
    @Test
    public void testSayUno(){
        Game game = new Game(SimpleDeck, 2, "Mati","Juli");
        game.play(new NumberedCard("Red", 1));
        game.play(new NumberedCard("Blue", 1));
        game.play(((WildCard) wildCard).asRed());
        // Red0, red1, red2, blue1, blue2, wild, skipRed, drawblue, drawRed, skipYellow, wild

    }


/*
    assertEquals();
        game.Play(SimpleDeck.get(3));

        game.Play(SimpleDeck.get(5));
        game.Play(SimpleDeck.get(7));
        game.Play(SimpleDeck.get(1));
        game.Play(SimpleDeck.get(5));

 */

}