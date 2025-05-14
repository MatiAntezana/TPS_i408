import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
/*
@Test
public void testGameIsCreatedWithPlayersHavingCards() {
    List<Player> players = game.getPlayers();
    assertEquals(2, players.size());
    assertEquals(1, players.get(0).getId());
    assertEquals(2, players.get(1).getId());
}

@Test
public void testFirstPlayerIsCurrentPlayer() {
    Player current = game.getCurrentPlayer();
    assertEquals(1, current.getId());
}


@Test
public void testPlayersHaveCorrectCards() {
    List<Player> players = game.getPlayers();

    // Verificamos jugador 1: "red 5", "blue skip"
    Player player1 = players.getFirst();
    List<Card> hand1 = player1.getHand();
    assertEquals(2, hand1.size());

    assertInstanceOf(NumberedCard.class, hand1.getFirst());
    NumberedCard card1 = (NumberedCard) hand1.getFirst(); //preguntar el tema del casteo.
    assertEquals("red", card1.getColor());
    assertEquals(5, card1.getNumber());

    assertInstanceOf(SkipCard.class, hand1.get(1));
    SkipCard card2 = (SkipCard) hand1.get(1);
    assertEquals("blue", card2.getColor());

    // Verificamos jugador 2: "wild"
    Player player2 = players.get(1);
    List<Card> hand2 = player2.getHand();
    assertEquals(1, hand2.size());
    assertInstanceOf(WildCard.class, hand2.getFirst());
}


@Test
public void testPlayersCardsRemovedFromDeck() {
    List<Card> deckCards = game.getDeckCards();

    assertFalse(deckCards.contains(new NumberedCard("red", 5)));
    assertFalse(deckCards.contains(new SkipCard("blue")));
    assertFalse(deckCards.contains(new WildCard()));
}


@Test
public void testDiscardCardIsCorrectAndRemovedFromDeck() {
    Card topDiscard = game.getTopDiscardCard();
    assertInstanceOf(NumberedCard.class, topDiscard);

    NumberedCard discard = (NumberedCard) topDiscard;
    assertEquals("green", discard.getColor().toLowerCase());
    assertEquals(9, discard.getNumber());

    assertFalse(game.getDeckCards().contains(discard));
}


@Test
public void testDeckSizeDecreasesAfterInitialization() {
    int fullDeckSize = 104; // o el que uses en tu lógica
    int expectedRemovals = 4; // 3 cartas a jugadores + 1 al descarte

    assertEquals(fullDeckSize - expectedRemovals, game.getDeckSize());
}
 */