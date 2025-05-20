import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;


public class GameTest {

    private Game game;
    private Integer cantCardsPlayer = 1;
    private Integer cantPlayers = 4;
    private List<Card> SimpleDeck;
    private List<Card> DeckTestWildCards;
    private List<Card> DeckWithSkipCard;
    private List<Card> DeckWithReverseCard;
    private List<Card> DeckWithDraw2;
    private List<Card> DeckCaseGrab;
    private List<Card> DeckCombinationSpecialCard;
    private List<Card> DeckrepeatedCards;
    private List<Card> DeckSimpleWinPlayer;

    private Map<Integer, Card> numberedRedCards;
    private Map<Integer, Card> numberedBlueCards;
    private Map<Integer, Card> numberedYellowCards;
    private Map<Integer, Card> numberedGreenCards;


    private Map<String, Card> skipCards;
    private Map<String, Card> draw2Cards;
    private Map<String, Card> reverseCards;
    private Card wildCard;
    private Card wildCard2;
    private Card wildCard3;


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
        wildCard2 = deckOfCards.createWildCard();
        wildCard3 = deckOfCards.createWildCard();

        SimpleDeck = new ArrayList<>(Arrays.asList(numberedRedCards.get(0),
                numberedRedCards.get(1), numberedRedCards.get(2), numberedBlueCards.get(1),
                numberedBlueCards.get(2), wildCard, skipCards.get("Red"),
                draw2Cards.get("Blue"), draw2Cards.get("Red"), skipCards.get("Yellow"),
                wildCard, wildCard));

        // Red0, red1, red2, blue1, blue2, wild, skipRed, drawblue, drawRed, skipYellow, wildCard, WildCard

        DeckTestWildCards = new ArrayList<>(Arrays.asList(numberedBlueCards.get(0), numberedBlueCards.get(1),numberedYellowCards.get(1),numberedRedCards.get(8), numberedGreenCards.get(2), numberedYellowCards.get(9), numberedRedCards.get(7),
                wildCard, wildCard, numberedGreenCards.get(0), numberedGreenCards.get(3), numberedRedCards.get(8), wildCard));
        // Blue0, Blue1, Yellow1, Red8, Green2, Yellow7, Red7, WildCard, WildCard, Green0, Green3, Red8, WildCard

        DeckWithSkipCard = new ArrayList<>(Arrays.asList(numberedRedCards.get(6),
                numberedRedCards.get(4), numberedGreenCards.get(5), numberedYellowCards.get(7), numberedGreenCards.get(9), skipCards.get("Blue"), numberedBlueCards.get(2), wildCard,
                numberedBlueCards.get(3), skipCards.get("Blue"), skipCards.get("Red"), numberedGreenCards.get(4), skipCards.get("Green"), numberedRedCards.get(9), skipCards.get("Yellow")));

        DeckWithReverseCard = new ArrayList<>(Arrays.asList(reverseCards.get("Red"),
                numberedBlueCards.get(0), numberedRedCards.get(3), numberedBlueCards.get(8), reverseCards.get("Yellow"),
                reverseCards.get("Yellow"), numberedRedCards.get(6), reverseCards.get("Red"), reverseCards.get("Green"),
                wildCard, numberedRedCards.get(3), numberedGreenCards.get(3), wildCard));

        DeckWithDraw2 = new ArrayList<>(Arrays.asList(draw2Cards.get("Red"),
                draw2Cards.get("Blue"), numberedBlueCards.get(2), draw2Cards.get("Green"), numberedRedCards.get(9), draw2Cards.get("Yellow"),
                draw2Cards.get("Red"), numberedYellowCards.get(1), numberedGreenCards.get(2), numberedRedCards.get(9), numberedBlueCards.get(6),
                numberedGreenCards.get(9), numberedYellowCards.get(3), numberedBlueCards.get(4),
                numberedBlueCards.get(2), numberedYellowCards.get(8), numberedYellowCards.get(7),
                numberedYellowCards.get(1), numberedYellowCards.get(5)));

        DeckCaseGrab = new ArrayList<>(Arrays.asList(numberedRedCards.get(2),
                numberedGreenCards.get(3), numberedGreenCards.get(4), numberedYellowCards.get(8),
                numberedBlueCards.get(5), numberedBlueCards.get(3), wildCard,
                numberedGreenCards.get(9), numberedYellowCards.get(8), numberedRedCards.get(8)));

        DeckCombinationSpecialCard = new ArrayList<>(Arrays.asList(numberedGreenCards.get(3),
                draw2Cards.get("Green"), skipCards.get("Green"), reverseCards.get("Green"), numberedGreenCards.get(5), numberedYellowCards.get(0), numberedBlueCards.get(6),
                numberedRedCards.get(3), numberedBlueCards.get(2), numberedYellowCards.get(8), numberedYellowCards.get(2), wildCard, numberedRedCards.get(0),
                numberedRedCards.get(8), numberedBlueCards.get(1)
                ));

        DeckrepeatedCards = new ArrayList<>(Arrays.asList(numberedRedCards.get(8),
                numberedRedCards.get(3), skipCards.get("Red"), draw2Cards.get("Red"), numberedYellowCards.get(3),
                skipCards.get("Red"), numberedYellowCards.get("Yellow"), numberedBlueCards.get(2), numberedYellowCards.get(9),
                numberedYellowCards.get(3), numberedRedCards.get(3), draw2Cards.get("Red"), numberedBlueCards.get(8),
                wildCard, skipCards.get("Yellow"), numberedRedCards.get(1), reverseCards.get("Red"), numberedYellowCards.get(4), numberedYellowCards.get(4)));

        DeckSimpleWinPlayer = new ArrayList<>(Arrays.asList(numberedRedCards.get(0),
                numberedRedCards.get(1), numberedGreenCards.get(1),
                numberedBlueCards.get(1), numberedGreenCards.get(3), numberedBlueCards.get(3)));

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
        assertEquals(game.getPitCard(), numberedRedCards.get(0));
    }


    //------------------------------------------------------------------------------------------------

    //
    @Test
    public void testInvalidCard() {
        Game game = new Game(SimpleDeck, 3, "Mati", "Juli");

        Card red1 = new NumberedCard("Red",1);
        game.play(red1); // red1
        assertEquals(red1, game.getPitCard());

        WildCard wildCard = new WildCard(); // wildCard blue
        game.play(wildCard.asBlue());
        assertEquals(wildCard, game.getPitCard());

        // Intentamos jugar una carta inválida (no aceptada por wild rojo)
        assertThrows(Exception.class, () -> game.play(new NumberedCard("Red",2))); //red2
    }


    @Test
    public void testValidCard() {
        Game game = new Game(SimpleDeck, 3, "Mati", "Juan","Juli");

        Card red2 = new NumberedCard("Red",2);
        game.play(red2);

        WildCard wildCard = new WildCard();
        game.play(wildCard.asBlue());
        assertEquals(wildCard, game.getPitCard());
        // Intentamos jugar una carta válida
        Draw2Card drawBlue = new Draw2Card("Blue");
        game.play(drawBlue);
        assertEquals(drawBlue, game.getPitCard());
    }


    @Test
    public void testCorrectRemoveCardPlayer() {
        Game game = new Game(SimpleDeck, 3, "Mati", "Juli");

        NumberedCard red1 = new NumberedCard("Red",1);
        game.play(red1);
        assertEquals(red1, game.getPitCard());

        WildCard wildCard = new WildCard();
        game.play(wildCard.asBlue());
        assertEquals(wildCard, game.getPitCard());

        assertThrows(Exception.class, () -> game.play(red1));
    }

    @Test
    public void testWildCardDiferentColors(){
        Game game = new Game(DeckTestWildCards, 6, "Mati", "Juli");
        NumberedCard blue1 = new NumberedCard("Blue",1);
        game.play(blue1);

        WildCard wildCard = new WildCard();
        game.play(wildCard.asYellow());

        NumberedCard yellow1 = new NumberedCard("Yellow",1);
        game.play(yellow1);

        // Chequeo que si se puede jugar yellow por la WildCard
        assertEquals(yellow1, game.getPitCard());

        WildCard wildCard2 = new WildCard();
        game.play(wildCard2.asGreen());

        NumberedCard green2 = new NumberedCard("Green",2);

        game.play(green2);
        assertEquals(green2, game.getPitCard());

        WildCard wildCard3 = new WildCard();
        game.play(wildCard3.asRed());

        NumberedCard red8 = new NumberedCard("Red",8);
        game.play(red8);
        assertEquals(red8, game.getPitCard());
    }

    @Test
    public void testWithSkipCard(){
        Game game = new Game(DeckWithSkipCard, 7, "Mati", "Juli");

        NumberedCard red4 = new NumberedCard("Red",4);
        game.play(red4);

        assertEquals(red4, game.getPitCard());

        // Juega Juli y saltea a mati
        SkipCard skipRed = new SkipCard("Red");
        game.play(skipRed);

        // Juega Juli de nuevo
        NumberedCard red9 = new NumberedCard("Red",9);
        game.play(red9);

        assertEquals(red9, game.getPitCard());

        // Juega mati
        NumberedCard green9 = new NumberedCard("Green",9);
        game.play(green9);

        // Juega Juli y lo saltea a Mati
        SkipCard skipGreen = new SkipCard("Green");
        game.play(skipGreen);

        assertEquals(skipGreen, game.getPitCard());

        // Juli juega de nuevo y saltea a Mati
        SkipCard skipYellow = new SkipCard("Yellow");
        game.play(skipYellow);

        // Pongo un Skip de color Amarillo
        assertEquals(skipYellow, game.getPitCard());

        // Juega Juli de nuevo y saltea a Mati
        SkipCard skipBlue = new SkipCard("Blue");
        game.play(skipBlue);

        // Pongo un Skip de color Blue
        assertEquals(skipBlue, game.getPitCard());
    }

    @Test
    public void testInitialReverseCard(){
        Game game = new Game(DeckWithReverseCard, 4 ,"Mati", "Juli");
        NumberedCard red3 = new NumberedCard("Red", 3);
        game.play(red3);
        // Juega Mati ya que no tiene efecto el Reverse initial
        assertEquals(red3, game.getPitCard());
    }

    @Test
    public void testWithReverseCard(){
        Game game = new Game(DeckWithReverseCard, 4 ,"Mati", "Juan", "Juli");
        NumberedCard red3 = new NumberedCard("Red",3);
        game.play(red3);

        assertEquals(red3, game.getPitCard());

        // Juega Juan
        ReverseCard reverseCardRed = new ReverseCard("Red");
        game.play(reverseCardRed);

        assertEquals(reverseCardRed, game.getPitCard());

        // Juega Mati por el Reverse de Juan
        ReverseCard reverseRed = new ReverseCard("Yellow");
        game.play(reverseRed);

        // Juega Juan en vez de Juli por el Reverse de Mati
        ReverseCard reverseCardGreen = new ReverseCard("Green");
        game.play(reverseCardGreen);

        assertEquals(reverseCardGreen, game.getPitCard());
    }

    @Test
    public void testWithDraw2Card(){
        Game game = new Game(DeckWithDraw2, 5, "Mati", "Juli");
        // Mati tiene 5 cartas (no tiene efecto el Draw del pozo)
        assertEquals(5, game.getCantCardsPlayer("Mati"));

        // Juega Mati
        Draw2Card draw2Blue = new Draw2Card("Blue");
        game.play(draw2Blue);

        // Juli agarra 2
        assertEquals(7, game.getCantCardsPlayer("Juli"));

        // Juega Mati de nuevo
        NumberedCard blue2 = new NumberedCard("Blue",2);
        game.play(blue2);

        assertEquals(blue2, game.getPitCard());

        // Juega Juli
        NumberedCard green2 = new NumberedCard("Green",2);
        game.play(green2);

        // Juega Mati
        Draw2Card draw2green = new Draw2Card("Green");
        game.play(draw2green);

        // Juli agarra 2 cartas
        assertEquals(8, game.getCantCardsPlayer("Juli"));

        // Juega Mati de nuevo
        Draw2Card draw2yellow = new Draw2Card("Yellow");
        game.play(draw2yellow);

        // Juli agarró 2 cartas
        assertEquals(10, game.getCantCardsPlayer("Juli"));

    }

    @Test
    public void testGrabCard(){
        Game game = new Game(DeckCaseGrab, 3, "Mati", "Juli");

        // Mati no tiene para jugar
        // Agarra
        Card cardToPlay = game.Grab();

        // No puede jugar la carta que agarró
        assertThrows(Exception.class, () -> game.play(cardToPlay));

        // Agarra otra
        Card cardToPlay2 = game.Grab();

        // No puede jugar la carta que agarró
        assertThrows(Exception.class, () -> game.play(cardToPlay2));

        Card cardToPlay3 = game.Grab();
        game.play(cardToPlay3);

        // Pudo jugar la carta que agarró
        assertEquals(cardToPlay3, game.getPitCard());
    }

    @Test
    public void testCombinationCardsSpecial(){
        Game game = new Game(DeckCombinationSpecialCard, 6, "Mati", "Juli");

        // Juega Mati
        Draw2Card draw2green = new Draw2Card("Green");
        game.play(draw2green);

        assertEquals(8, game.getCantCardsPlayer("Juli"));

        // Juega Mati
        SkipCard skipGreen = new SkipCard("Green");
        game.play(skipGreen);

        assertEquals(skipGreen, game.getPitCard());

        // Juega Mati
        ReverseCard reverseGreen = new ReverseCard("Green");
        game.play(reverseGreen);

        assertEquals(reverseGreen, game.getPitCard());

        WildCard wildCard = new WildCard();

        // Juega Juli
        game.play(wildCard.asRed());

        assertEquals(wildCard, game.getPitCard());

    }

    @Test
    public void testPlayCardsrepeated(){
        Game game = new Game(DeckrepeatedCards, 4, "Mati", "Juan", "Juli");

        // Juega Mati
        NumberedCard red3 = new NumberedCard("Red", 3);
        game.play(red3);

        // Juega Juan
        SkipCard skipRed = new SkipCard("Red");
        game.play(skipRed);

        assertEquals(skipRed, game.getPitCard());
        // Se saltea Juli y juega Mati

        game.play(skipRed);
        assertEquals(skipRed, game.getPitCard());
        // Se saltea Juan y juega Juli

        game.play(red3);

        // Juega Mati
        Draw2Card draw2red = new Draw2Card("Red");
        game.play(draw2red);

        // Mati no canto Uno y tiene 2 más
        assertEquals(3, game.getCantCardsPlayer("Mati"));

        // Juan agarra 2 y juega Juli
        assertEquals(draw2red, game.getPitCard());

        game.play(draw2red);
        assertEquals(draw2red, game.getPitCard());
    }

    @Test
    public void testCorrectSayUno() {
        Game game = new Game(SimpleDeck, 2, "Mati", "Juli");
        game.play( new NumberedCard("Red",1).Uno());
        assertEquals(1, game.getCantCardsPlayer("Mati"));
    }

    @Test
    public void testNoSayUno() {
        Game game = new Game(SimpleDeck, 2, "Mati", "Juli");
        game.play( new NumberedCard("Red",1));
        assertEquals(3, game.getCantCardsPlayer("Mati"));
    }

    @Test
    public void testWinPlayer() {
        Game game = new Game(DeckSimpleWinPlayer, 2, "Mati", "Juli");
        game.play( new NumberedCard("Red",1).Uno());
        assertEquals(1, game.getCantCardsPlayer("Mati"));
        game.play( new NumberedCard("Blue",1).Uno());
        assertEquals(1, game.getCantCardsPlayer("Juli"));
        game.play( new NumberedCard("Green",1));

        // Ganó Mati
        assertEquals(0, game.getCantCardsPlayer("Mati"));

        // Intenta Juli y tira error al ganar Mati
        assertThrows(Exception.class, () -> game.play( new NumberedCard("Green",3))); //red2

        // Intenta agarrar Juli y tira error al ganar MAti
        assertThrows(Exception.class, () -> game.Grab()); //red2

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

//public class GameTest {
//
//    private Game game;
//    private Integer cantCardsPlayer = 1;
//    private Integer cantPlayers = 4;
//    private List<Card> SimpleDeck;
//    private List<Card> numberedRedCards;
//    private List<Card> numberedBlueCards;
//    private List<Card> numberedYellowCards;
//    private List<Card> numberedGreenCards;
//
//    private List<Card> skipCards;
//    private List<Card> draw2Cards;
//    private List<Card> reverseCards;
//    private Card wildCard;
//
//    // Messages Exception
//    private String expectedMessage = "No puedes jugar esa carta";
//
//    @BeforeEach
//    public void setUp() {
//        BuildDeckOfCards deckOfCards = new BuildDeckOfCards();
//        numberedRedCards = deckOfCards.createNumberedCards("Red");
//        numberedBlueCards = deckOfCards.createNumberedCards("Blue");
//        numberedYellowCards = deckOfCards.createNumberedCards("Yellow");
//        numberedGreenCards = deckOfCards.createNumberedCards("Green");
//
//        skipCards = deckOfCards.createSkipCards("Red", "Blue", "Yellow", "Green");
//        draw2Cards = deckOfCards.createDraw2Cards("Red", "Blue", "Yellow", "Green");
//        reverseCards = deckOfCards.createReverseCards("Red", "Blue", "Yellow", "Green");
//
//
//        wildCard = deckOfCards.createWildCard();
//
//        SimpleDeck = new ArrayList<>(Arrays.asList(numberedRedCards.get(0), numberedRedCards.get(1), numberedRedCards.get(2),
//                numberedBlueCards.get(1), numberedBlueCards.get(2),
//                wildCard, skipCards.get(0), draw2Cards.get(1), draw2Cards.get(0), skipCards.get(2), wildCard, wildCard));
//        // Red0, red1, red2, blue1, blue2, wild, skipRed, drawblue, drawRed, skipYellow, wild
//    }
//
//    //Deberíamos testear si se puede crear un juego vació?
//
//    @Test
//    public void testPlayersAreCorrectlyAddedToGame(){
//        Game game = new Game(SimpleDeck, 2, "Pedro", "Juan","Mati","Juli");
//
//        assertTrue(game.isPlayerInGame("Pedro"));
//        assertTrue(game.isPlayerInGame("Juan"));
//        assertTrue(game.isPlayerInGame("Mati"));
//        assertTrue(game.isPlayerInGame("Juli"));
//
//        assertFalse(game.isPlayerInGame("Tadeo"));
//    }
//
//
//    @Test
//    public void testInitialPitCardIsFirstCardInDeck(){
//        Game game = new Game(SimpleDeck, 2, "Pedro", "Juan","Mati","Juli");
//        assertEquals(game.getPitCard(), numberedRedCards.getFirst());
//    }
//
//
//    //------------------------------------------------------------------------------------------------
//
//    @Test
//    public void test1() {
//        Game game = new Game(SimpleDeck, 2, "Pedro", "Juan", "Mati", "Juli");
//
//        game.play(SimpleDeck.get(1)); // red1
//        assertEquals(SimpleDeck.get(1), game.getPitCard());
//
//        game.play(SimpleDeck.get(3)); // blue1
//        assertEquals(SimpleDeck.get(3), game.getPitCard());
//
//        WildCard wildCard = (WildCard) SimpleDeck.get(10); // wildCard rojo
//        game.play(wildCard.asRed());
//        assertEquals(wildCard, game.getPitCard());
//
//        Card previousPitCard = game.getPitCard(); // guardamos el estado actual del pozo
//
//        // Intentamos jugar una carta inválida (no aceptada por wild rojo)
//        assertThrows(Exception.class, () -> game.play(SimpleDeck.get(7))); //drawblue
//    }
//
//
//    @Test
//    public void test2() {
//        Game game = new Game(SimpleDeck, 2, "Pedro", "Juan", "Mati", "Juli");
//
//        game.play(SimpleDeck.get(1)); // red1
//        assertEquals(SimpleDeck.get(1), game.getPitCard());
//
//        game.play(SimpleDeck.get(3)); // blue1
//        assertEquals(SimpleDeck.get(3), game.getPitCard());
//
//        WildCard wildCardRed = (WildCard) SimpleDeck.get(10); // wildCard rojo
//        game.play(wildCardRed.asRed());
//        assertEquals(wildCardRed, game.getPitCard());
//
//        // Intentamos jugar una carta válida
//        NumberedCard red0 = (NumberedCard) SimpleDeck.getFirst();
//        game.play(red0); // red0
//        assertEquals(red0, game.getPitCard());
//    }
//
//    @Test
//    public void test3() {
//        Game game = new Game(SimpleDeck, 2, "Pedro", "Juan", "Mati", "Juli");
//
//        game.play(SimpleDeck.get(1)); // red1
//        assertEquals(SimpleDeck.get(1), game.getPitCard());
//
//        game.play(SimpleDeck.get(3)); // blue1
//        assertEquals(SimpleDeck.get(3), game.getPitCard());
//
//        Draw2Card draw2CardBlue = (Draw2Card) SimpleDeck.get(7); // Draw2Card blue
//        game.play(draw2CardBlue);
//        assertEquals(draw2CardBlue, game.getPitCard());
//
//        Draw2Card draw2CardRed = (Draw2Card) SimpleDeck.get(8); // Draw2Card red
//        game.play(draw2CardRed);
//        assertEquals(draw2CardRed, game.getPitCard());
//
//        // Intentamos jugar una carta válida
//        NumberedCard red0 = (NumberedCard) SimpleDeck.getFirst();
//        game.play(red0); // red0
//        assertEquals(red0, game.getPitCard());
//    }
//
//
//    @Test
//    public void test4() {
//        Game game = new Game(SimpleDeck, 2, "Pedro", "Juan", "Mati", "Juli");
//
//        game.play(SimpleDeck.get(1)); // red1
//        assertEquals(SimpleDeck.get(1), game.getPitCard());
//
//        game.play(SimpleDeck.get(3)); // blue1
//        assertEquals(SimpleDeck.get(3), game.getPitCard());
//
//        Draw2Card draw2CardBlue = (Draw2Card) SimpleDeck.get(7); // Draw2Card blue
//        game.play(draw2CardBlue);
//        assertEquals(draw2CardBlue, game.getPitCard());
//
//        Draw2Card draw2CardRed = (Draw2Card) SimpleDeck.get(8); // Draw2Card red
//        game.play(draw2CardRed);
//        assertEquals(draw2CardRed, game.getPitCard());
//
//        WildCard wildCardBlue = (WildCard) SimpleDeck.get(11); // wildCard azul
//        game.play(wildCardBlue.asBlue());
//        assertEquals(wildCardBlue, game.getPitCard());
//    }
//
//
//    @Test
//    public void test5() {
//        Game game = new Game(SimpleDeck, 2, "Pedro", "Juan", "Mati", "Juli");
//
//        game.play(SimpleDeck.get(1)); // red1
//        assertEquals(SimpleDeck.get(1), game.getPitCard());
//
//        game.play(SimpleDeck.get(3)); // blue1
//        assertEquals(SimpleDeck.get(3), game.getPitCard());
//
//        WildCard wildCardRed = (WildCard) SimpleDeck.get(10); // wildCard rojo
//        game.play(wildCardRed.asRed());
//        assertEquals(wildCardRed, game.getPitCard());
//
//        WildCard wildCardBlue = (WildCard) SimpleDeck.get(11); // wildCard azul
//        game.play(wildCardBlue.asBlue());
//        assertEquals(wildCardBlue, game.getPitCard());
//
//    }
//
//
//    //------------------------------------------------------------------------------------------------
//
//
//    @Test
//    public void testCheckCorrectPlayPlayerWithInvalidCard(){
//        // Test que prueba que tire excepción si se juega con una no aceptada
//        Game game = new Game(SimpleDeck, 2, "Pedro", "Juan","Mati","Juli");
//        game.play(SimpleDeck.get(1)); // red1
//        assertEquals(game.getPitCard(), SimpleDeck.get(1));
//        game.play(SimpleDeck.get(3)); // blue1
//        assertEquals(game.getPitCard(), SimpleDeck.get(3));
//        game.play(((WildCard) wildCard).asRed());
//        assertEquals(game.getPitCard(), wildCard);
//
//        Exception exception = assertThrows(Exception.class, () -> game.play(SimpleDeck.get(7)));
//        assertEquals(expectedMessage, exception.getMessage());
//    }
//
//
//    @Test
//    public void testCheckCorrectPlayPlayerWithCorrectsCards(){
//        // Test que prueba que tire excepción si se juega con una no aceptada
//        Game game = new Game(SimpleDeck, 2, "Pedro", "Juan","Mati","Juli");
//        game.play(SimpleDeck.get(1)); // red1
//        game.play(SimpleDeck.get(3)); // blue1
//        game.play(((WildCard) wildCard).asRed()); // wild
//        game.play(SimpleDeck.get(8)); // drawRed
//
//        assertEquals(game.getPitCard(), SimpleDeck.get(8));
//    }
//
//}