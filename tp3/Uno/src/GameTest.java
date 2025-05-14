import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

//public class GameTest {
//
//    private Player player1;
//    private Player player2;
//    private Game game;
//
//    @BeforeEach
//    public void setUp() {
//        player1 = new PlayerBuilder()
//                .withId(1)
//                .addNumberedCard("red", 5)
//                .addSkipCard("blue")
//                .build();
//
//        player 2 = new PlayerBuilder()
//                .withId(2)
//                .addWildCard()
//                .build();
//
//        game = new Game(player1, player2);
//    }
//
//    @Test
//    public void testCannotCreateGameWithoutPlayers() {
//        assertThrows(IllegalArgumentException.class, Game::new);
//    }
//
//    @Test
//    public void testCannotCreatePlayerWithoutCards() {
//        assertThrows(IllegalArgumentException.class, () -> new Player(10, List.of()));
//    }
//
//    @Test
//    public void testGameIsCreatedWithPlayersHavingCards() {
//        List<Player> players = game.getPlayers();
//        assertTrue(players.contains(player1));
//        assertTrue(players.contains(player2));
//    }
//
//    @Test
//    public void testFirstPlayerIsCurrentPlayer() {
//        Player current = game.getCurrentPlayer();
//        assertEquals(player1, current);
//    }
//
//
//}


public class GameTest {

    private Game game;
    private Integer cantCardsPlayer = 1;
    private Integer cantPlayers = 4;
    private List<Card> SimpleDeck;
    private List<Card> cardsNumRed;
    private List<Card> cardsNumBlue;
    private List<Card> cardsNumYellow;
    private List<Card> cardsNumGreen;

    private List<Card> cardsSkip;
    private List<Card> cardsDraw2;
    private List<Card> cardsReverse;
    private WildCard wildCard;

    // Messages Exception
    private String expectedMessage = "No puedes jugar esa carta";

    @BeforeEach
    public void setUp() {
        BuildMazo constructMazo = new BuildMazo();
        cardsNumRed = constructMazo.createCardsColor("Red");
        cardsNumBlue = constructMazo.createCardsColor("Blue");
        cardsNumYellow = constructMazo.createCardsColor("Yellow");
        cardsNumGreen = constructMazo.createCardsColor("Green");

        cardsSkip = constructMazo.createCardsSkip("Red", "Blue", "Yellow", "Green");
        cardsDraw2 = constructMazo.createCardsDraw2("Red", "Blue", "Yellow", "Green");
        cardsReverse = constructMazo.createCardsReverse("Red", "Blue", "Yellow", "Green");


        wildCard = new WildCard();

        SimpleDeck = new ArrayList<>(Arrays.asList(cardsNumRed.get(0), cardsNumRed.get(1), cardsNumRed.get(2),
                cardsNumBlue.get(1), cardsNumBlue.get(2),
                wildCard, cardsSkip.get(0), cardsDraw2.get(1), cardsDraw2.get(0), cardsSkip.get(2), wildCard));
        // Red0, red1, red2, blue1, blue2, wild, skipRed, drawblue, drawRed, skipYellow, wild
    }


    @Test
    public void testCorrectInitializePlayers(){
        Game game = new Game(SimpleDeck, 2, "Pedro", "Juan","Mati","Juli");
        assertTrue(game.getCantCardsPlayer("Pedro"));
        assertTrue(game.getCantCardsPlayer("Juan"));
        assertTrue(game.getCantCardsPlayer("Mati"));
        assertTrue(game.getCantCardsPlayer("Juli"));

        assertFalse(game.getCantCardsPlayer("Tadeo"));
    }

    @Test
    public void testCheckCorrectWellCard(){
        Game game = new Game(SimpleDeck, 2, "Pedro", "Juan","Mati","Juli");
        assertEquals(game.getWellCard(), cardsNumRed.get(0));
    }

    @Test
    public void testCheckCorrectPlayPlayerWithInvalidCard(){
        // Test que prueba que tire excepción si se juega con una no aceptada
        Game game = new Game(SimpleDeck, 2, "Pedro", "Juan","Mati","Juli");
        game.Play(SimpleDeck.get(1)); // red1
        assertEquals(game.getWellCard(), SimpleDeck.get(1));
        game.Play(SimpleDeck.get(3)); // blue1
        assertEquals(game.getWellCard(), SimpleDeck.get(3));
        game.Play(wildCard.selectColor("Red")); // wild
        assertEquals(game.getWellCard(), wildCard);

        Exception exception = assertThrows(Exception.class, () -> game.Play(SimpleDeck.get(7)));
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void testCheckCorrectPlayPlayerWithCorrectsCards(){
        // Test que prueba que tire excepción si se juega con una no aceptada
        Game game = new Game(SimpleDeck, 2, "Pedro", "Juan","Mati","Juli");
        game.Play(SimpleDeck.get(1)); // red1
        game.Play(SimpleDeck.get(3)); // blue1
        game.Play(wildCard.selectColor("Red")); // wild
        game.Play(SimpleDeck.get(8)); // drawRed

        assertEquals(game.getWellCard(), SimpleDeck.get(8));
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