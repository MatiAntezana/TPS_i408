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

        DeckWithSkipCard = new ArrayList<>(Arrays.asList(skipCards.get("Red"),
                numberedRedCards.get(4), numberedGreenCards.get(5), skipCards.get("Red"), numberedGreenCards.get(9), skipCards.get("Blue"), skipCards.get("Green"), skipCards.get("Yellow"),
                numberedBlueCards.get(3), skipCards.get("Blue"), numberedRedCards.get(5), numberedGreenCards.get(4), numberedBlueCards.get(8), numberedRedCards.get(9), wildCard));
        // SkipRed, Red4, Green5, SkipRed, Green9, SkipBlue, SkipGreen, SkipYellow, SkipBlue, Red5, Green4, Blue8, Red9, WildCard

        DeckWithReverseCard = new ArrayList<>(Arrays.asList(reverseCards.get("Red"),
                numberedBlueCards.get(0), numberedGreenCards.get(0), reverseCards.get("Red"), skipCards.get("Red"),
                reverseCards.get("Yellow"), numberedRedCards.get(6), numberedBlueCards.get(8), wildCard,
                reverseCards.get("Red"), numberedRedCards.get(3), numberedGreenCards.get(3), skipCards.get("Green")));
        // ReverseRed, Blue0, Green0, ReverseBlue, skipRed, ReverseYellow, Yellow0, Blue8, WildCard, ReverseRed, Red3, Green3, skipYellow

        DeckWithDraw2 = new ArrayList<>(Arrays.asList(draw2Cards.get("Red"),
                draw2Cards.get("Blue"), numberedBlueCards.get(2), draw2Cards.get("Green"), numberedRedCards.get(9), numberedBlueCards.get(4),
                draw2Cards.get("Red"), draw2Cards.get("Yellow"), numberedGreenCards.get(4), numberedRedCards.get(9), numberedBlueCards.get(6),
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
    public void testInitialSkipCard(){
        Game game = new Game(DeckWithSkipCard, 7, "Mati", "Juli");

        NumberedCard red5 = new NumberedCard("Red",5);
        game.play(red5);

        // Se salteo el turno de Mati por el Skip inicial del pozo y jugó Juli su rojo5
        assertEquals(red5, game.getPitCard());

        SkipCard skipRed = new SkipCard("Red");
        game.play(skipRed);

        NumberedCard red4 = new NumberedCard("Red",4);
        game.play(red4);

        // Se salteo el turno de Juli y juega mati
        assertEquals(red4, game.getPitCard());

        NumberedCard green4 = new NumberedCard("Green",4);
        game.play(green4);

        SkipCard skipGreen = new SkipCard("Green");
        game.play(skipGreen);

        assertEquals(skipGreen, game.getPitCard());

        // Salteo a Juli
        SkipCard skipYellow = new SkipCard("Yellow");
        game.play(skipYellow);

        // Pongo un Skip de color Amarillo
        assertEquals(skipYellow, game.getPitCard());

        SkipCard skipBlue = new SkipCard("Blue");
        game.play(skipBlue);

        // Pongo un Skip de color Blue
        assertEquals(skipBlue, game.getPitCard());
    }

    @Test
    public void testInitialReverseCard(){
        Game game = new Game(DeckWithReverseCard, 4 ,"Mati", "Juli");
        Card red6 = new NumberedCard("Red",6);
        game.play(red6);
        // Jugo Juli y no mati por el Reverse del Pozo
        assertEquals(red6, game.getPitCard());
    }

    @Test
    public void testWithReverseCard(){
        Game game = new Game(DeckWithReverseCard, 4 ,"Juan", "Mati", "Juli");
        NumberedCard red3 = new NumberedCard("Red",3);
        game.play(red3);
        // Jugo Juli y no Juan por el Reverse del Pozo
        assertEquals(red3, game.getPitCard());

        NumberedCard red6 = new NumberedCard("Red",6);
        game.play(red6);

        // Jugó Mati
        assertEquals(red6, game.getPitCard());
        ReverseCard reverseRed = new ReverseCard("Red");

        game.play(reverseRed);

        WildCard wildCard = new WildCard();
        game.play(wildCard);

        // Jugó Mati en vez de Juli al cambiar el orden de nuevo
        assertEquals(wildCard, game.getPitCard());
    }

    @Test
    public void testWithDraw2Card(){
        Game game = new Game(DeckWithDraw2, 5, "Mati", "Juli");
        // Mati tiene 7 cartas debido a que tuvo que agarrar por Draw2 del pozo
        System.out.println(game.deck.getFirst());
        assertEquals(7, game.getCantCardsPlayer("Mati"));

        // Juega Juli
        Draw2Card draw2red = new Draw2Card("Red");
        game.play(draw2red);

        // Mati agarra 2 más
        assertEquals(9, game.getCantCardsPlayer("Mati"));

        // Juega juli de nuevo
        NumberedCard red9 = new NumberedCard("Red",9);
        game.play(red9);

        assertEquals(red9, game.getPitCard());

        // Juega Mati con carta que agarró por Draw2
        NumberedCard green9 = new NumberedCard("Green",9);
        game.play(green9);

        // Juega Juli
        NumberedCard green4 = new NumberedCard("Green",4);
        game.play(green4);

        // Juega Mati
        Draw2Card draw2green = new Draw2Card("Green");
        game.play(draw2green);

        // Juli agarró 2 cartas
        assertEquals(4, game.getCantCardsPlayer("Juli"));

        //System.out.println(game.players.getFirst().getHand());
        Draw2Card draw2Blue = new Draw2Card("Blue");
        game.play(draw2Blue);

        // Juli agarró 2 cartas
        assertEquals(6, game.getCantCardsPlayer("Juli"));

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
        //game.play(SimpleDeck.get(1).Uno());
        game.play( new NumberedCard("Red",1).Uno());
        assertEquals(1, game.getCantCardsPlayer("Mati"));
    }

    @Test
    public void testNoSayUno() {
        Game game = new Game(SimpleDeck, 2, "Mati", "Juli");
        game.play( new NumberedCard("Red",1));
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