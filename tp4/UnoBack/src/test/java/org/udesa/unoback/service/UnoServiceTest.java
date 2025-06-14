package org.udesa.unoback.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.udesa.unoback.model.*;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UnoServiceTest {

    @Autowired
    private UnoService unoService;
    @MockBean
    private Dealer dealer;

    private List<Card> predictableDeck;

    @BeforeEach
    void setUp() {
        predictableDeck = Arrays.asList(
                new NumberCard("Yellow", 0),

                new NumberCard("Red", 1), new NumberCard("Green", 5), new NumberCard("Blue", 9), // J1 mano inicial
                new NumberCard("Red", 3), new NumberCard("Yellow", 7), new NumberCard("Green", 1),
                new WildCard(),

                new NumberCard("Blue", 1), new NumberCard("Red", 5), new NumberCard("Yellow", 9), // J2 mano inicial
                new NumberCard("Blue", 3), new NumberCard("Green", 7), new NumberCard("Red", 1),
                new WildCard(),

                new NumberCard("Green", 8)

        );

        when(dealer.fullDeck()).thenReturn(predictableDeck);
    }


    @Test
    void createsAndStoresNewMatch() {
        UUID matchId = unoService.newMatch(List.of("PlayerA", "PlayerB"));

        assertNotNull(matchId, "El UUID de la partida no debería ser nulo");

        Map<UUID, Match> sessionsMap = unoService.getSessionsForTesting();
        assertTrue(sessionsMap.containsKey(matchId), "La partida debería estar en el mapa de sesiones");
        assertNotNull(sessionsMap.get(matchId), "La partida almacenada no debería ser nula");
    }


    @Test
    void createsManyMatchesSimultaneouslyAndStoresAndPlayThem() {
        Map<UUID, Match> sessionsMap = unoService.getSessionsForTesting();

        int sessionsMapSizeBeforeNewMatchs = sessionsMap.size();
        UUID matchId1 = unoService.newMatch(List.of("PlayerA", "PlayerB"));

        UUID matchId2 = unoService.newMatch(List.of("PlayerA", "PlayerB"));

        UUID matchId3 = unoService.newMatch(List.of("PlayerA", "PlayerB"));

        // Chequeo que se crearon los 3 juegos
        assertNotNull(matchId1);
        assertNotNull(matchId2);
        assertNotNull(matchId3);

        verify(dealer, times(3)).fullDeck();

        assertEquals(sessionsMapSizeBeforeNewMatchs + 3, sessionsMap.size(), "El mapa de sesiones debería contener el número esperado de partidas.");

        // Verificar que todos los UUIDs creados están en el mapa y son únicos
        assertTrue(sessionsMap.containsKey(matchId1), "La partida con ID " + matchId1 + " debería estar en el mapa de sesiones.");
        assertTrue(sessionsMap.containsKey(matchId2), "La partida con ID " + matchId2 + " debería estar en el mapa de sesiones.");
        assertTrue(sessionsMap.containsKey(matchId3), "La partida con ID " + matchId3 + " debería estar en el mapa de sesiones.");

        assertNotNull(sessionsMap.get(matchId1), "La partida con ID " + matchId1 + " almacenada no debería ser nula.");
        assertNotNull(sessionsMap.get(matchId2), "La partida con ID " + matchId2 + " almacenada no debería ser nula.");
        assertNotNull(sessionsMap.get(matchId3), "La partida con ID " + matchId3 + " almacenada no debería ser nula.");

        // Verificar que los UUIDs generados son únicos entre sí
        assertEquals(3, new ArrayList<>(Arrays.asList(matchId1, matchId2, matchId3)).stream().distinct().count(), "Todos los IDs de las partidas creadas deberían ser únicos.");

        // Verificar que se pueda hacer play en multiples partidas en simultaneo
        assertDoesNotThrow(()->unoService.playCard(matchId1, "PlayerA", new WildCard().asBlue()), "PlayerA debería poder jugar una WildCard");
        assertDoesNotThrow(()->unoService.playCard(matchId2, "PlayerA", new WildCard().asBlue()), "PlayerA debería poder jugar una WildCard");

        // Juego de nuevo en con un juego anterior
        assertDoesNotThrow(() -> unoService.playCard(matchId1, "PlayerB", new NumberCard("Blue", 1)), "PlayerB debería poder jugar una Blue NumberedCard sobre una Blue WildCard.");

        assertDoesNotThrow(()->unoService.playCard(matchId3, "PlayerA", new WildCard().asBlue()), "PlayerA debería poder jugar una WildCard");

        assertDoesNotThrow(() -> unoService.playCard(matchId2, "PlayerB", new NumberCard("Blue", 1)), "PlayerB debería poder jugar una Blue NumberedCard sobre una Blue WildCard.");

        assertDoesNotThrow(() -> unoService.playCard(matchId3, "PlayerB", new NumberCard("Blue", 1)), "PlayerB debería poder jugar una Blue NumberedCard sobre una Blue WildCard.");

        // Verificar que se pueda hacer playerHand en multiples partidas en simultaneo
        assertDoesNotThrow(() -> unoService.playerHand(matchId1), "Se deberia poder obtener la Hand del jugador");
        assertDoesNotThrow(() -> unoService.playerHand(matchId2), "Se deberia poder obtener la Hand del jugador");
        assertDoesNotThrow(() -> unoService.playerHand(matchId3), "Se deberia poder obtener la Hand del jugador");

        // Verificar que se pueda hacer drawCard en multiples partidas en simultaneo
        assertDoesNotThrow(() -> unoService.drawCard(matchId1, "PlayerA"), "PlayerA deberia poder robar 2 cartas");
        assertDoesNotThrow(() -> unoService.drawCard(matchId2, "PlayerA"), "PlayerA deberia poder robar 2 cartas");
        assertDoesNotThrow(() -> unoService.drawCard(matchId3, "PlayerA"), "PlayerA deberia poder robar 2 cartas");

        // Verificar que se puede getActiveCard en multiples partidas en simultaneo
        assertDoesNotThrow(() -> unoService.getActiveCard(matchId1), "Debería haber una carta activa al inicio de la partida");
        assertDoesNotThrow(() -> unoService.getActiveCard(matchId2), "Debería haber una carta activa al inicio de la partida");
        assertDoesNotThrow(() -> unoService.getActiveCard(matchId3), "Debería haber una carta activa al inicio de la partida");

    }


    @Test
    void throwsExceptionWhenMatchNotFound() {
        UUID nonExistentMatchId = UUID.randomUUID();
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> unoService.getMatch(nonExistentMatchId));
        assertEquals("Match with ID " + nonExistentMatchId + " not found.", thrown.getMessage());
    }


    @Test
    void returnsExistingMatch() {
        assertNotNull(unoService.getMatch(unoService.newMatch(List.of("PlayerA", "PlayerB"))), "La partida recuperada no debería ser nula.");
    }


    @Test
    void returnsPlayerHandFromExistingMatch() {
        assertFalse(unoService.playerHand(unoService.newMatch(List.of("PlayerA", "PlayerB"))).isEmpty(), "La mano inicial del jugador no debería estar vacía.");

    }


    @Test
    void returnsActiveCardFromExistingMatch() {
        assertNotNull(unoService.getActiveCard(unoService.newMatch(List.of("PlayerA", "PlayerB"))), "Debería haber una carta activa al inicio de la partida.");
    }

}