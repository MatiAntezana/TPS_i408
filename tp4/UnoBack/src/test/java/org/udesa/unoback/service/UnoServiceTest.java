package org.udesa.unoback.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.udesa.unoback.model.*;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UnoServiceTest {

    @Autowired
    private UnoService unoService;
    @MockBean
    private Dealer dealer;

    private List<Card> predictableDeck;
    private List<Card> winningPredictableDeck;

    @BeforeEach
    void setUp() {
        predictableDeck = Arrays.asList(
                new NumberCard("Yellow", 0), // Esta será la primera carta ACTIVA de la partida

                new NumberCard("Red", 1), new NumberCard("Green", 5), new NumberCard("Blue", 9), // J1 mano inicial
                new NumberCard("Red", 3), new NumberCard("Yellow", 7), new NumberCard("Green", 1),
                new WildCard(), // Total 7 para J1

                new NumberCard("Blue", 1), new NumberCard("Red", 5), new NumberCard("Yellow", 9), // J2 mano inicial
                new NumberCard("Blue", 3), new NumberCard("Green", 7), new NumberCard("Red", 1),
                new WildCard(), // Total 7 para J2

                new NumberCard("Green", 8)   // Una carta más en el mazo de robo

        );

        // --- Mazo específico para el test de victoria ---
        winningPredictableDeck = Arrays.asList(
                new NumberCard("Red", 0), // 1. Carta Activa Inicial

                // 2. Mano de PlayerA (7 cartas)
                new NumberCard("Blue", 1),
                new NumberCard("Green", 2),
                new NumberCard("Yellow", 3),
                new NumberCard("Blue", 4),
                new NumberCard("Green", 5),
                new NumberCard("Yellow", 3),
                new NumberCard("Red", 7),

                // 3. Mano de PlayerB (7 cartas)
                new NumberCard("Blue", 8),
                //new SkipCard("Green"),
                new NumberCard("Yellow", 6),
                new NumberCard("Yellow", 1),
                new NumberCard("Blue", 2),
                new NumberCard("Green", 3),
                new NumberCard("Green", 4),
                new NumberCard("Yellow", 5),

                // 4. Cartas en el Mazo de Robo
                new WildCard(),
                new NumberCard("Red", 8),
                new NumberCard("Blue", 9)
        );

        when(dealer.fullDeck()).thenReturn(predictableDeck); // El dealer mock devolverá nuestro mazo predecible
    }


    // --- Test para newMatch ---
    @Test
    void createsAndStoresNewMatch() {
        UUID matchId = unoService.newMatch(List.of("PlayerA", "PlayerB"));

        assertNotNull(matchId, "El UUID de la partida no debería ser nulo");
        verify(dealer, times(1)).fullDeck();  // Verifica que fullDeck() del dealer fue llamado exactamente una vez

        Map<UUID, Match> sessionsMap = unoService.getSessionsForTesting();
        assertTrue(sessionsMap.containsKey(matchId), "La partida debería estar en el mapa de sesiones");
        assertNotNull(sessionsMap.get(matchId), "La partida almacenada no debería ser nula");
    }


    @Test
    void createsManyMatchesSimultaneouslyAndStoresThem() {
        int numberOfMatchesToCreate = 100;

        Map<UUID, Match> sessionsMap = unoService.getSessionsForTesting();
        int sessionsMapSize = sessionsMap.size();

        List<UUID> createdMatchIds = IntStream.range(0, numberOfMatchesToCreate)
                .mapToObj(i -> unoService.newMatch(List.of("PlayerA", "PlayerB")))
                .collect(Collectors.toList());


        assertEquals(numberOfMatchesToCreate, createdMatchIds.size(), "Deberían haberse creado el número esperado de partidas.");

        verify(dealer, times(numberOfMatchesToCreate)).fullDeck();

        assertEquals(sessionsMapSize + numberOfMatchesToCreate, sessionsMap.size(), "El mapa de sesiones debería contener el número esperado de partidas.");

        // Verificar que todos los UUIDs creados están en el mapa y son únicos
        for (UUID matchId : createdMatchIds) {
            assertTrue(sessionsMap.containsKey(matchId), "La partida con ID " + matchId + " debería estar en el mapa de sesiones.");
            assertNotNull(sessionsMap.get(matchId), "La partida con ID " + matchId + " almacenada no debería ser nula.");
        }

        // Además, podemos verificar que los UUIDs generados son únicos entre sí
        long distinctIds = createdMatchIds.stream().distinct().count();
        assertEquals(numberOfMatchesToCreate, distinctIds, "Todos los IDs de las partidas creadas deberían ser únicos.");
    }


    // --- Test para getMatch ---
    //Esta bien testear esto?
    @Test
    void throwsExceptionWhenMatchNotFound() {
        UUID nonExistentMatchId = UUID.randomUUID();
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> unoService.getMatch(nonExistentMatchId));
        assertEquals("Match with ID " + nonExistentMatchId + " not found.", thrown.getMessage());
    }


    @Test
    void returnsExistingMatch() {
        UUID matchId = unoService.newMatch(List.of("PlayerA", "PlayerB"));
        assertNotNull(unoService.getMatch(matchId), "La partida recuperada no debería ser nula.");
    }


    // --- Tests para métodos que interactúan con una partida existente ---

    @Test
    void returnsPlayerHandFromExistingMatch() {
        UUID matchId = unoService.newMatch(List.of("PlayerA", "PlayerB"));
        assertFalse(unoService.playerHand(matchId).isEmpty(), "La mano inicial del jugador no debería estar vacía.");

    }


    @Test
    void returnsActiveCardFromExistingMatch() {
        UUID matchId = unoService.newMatch(List.of("PlayerA", "PlayerB"));
        Card activeCard = unoService.getActiveCard(matchId);
        assertNotNull(activeCard, "Debería haber una carta activa al inicio de la partida.");
        assertEquals(new NumberCard("Yellow", 0), activeCard, "La carta activa debería ser Yellow 0.");
    }


    @Test
    void delegatesToMatchPlayMethod() {
        UUID matchId = unoService.newMatch(List.of("PlayerA", "PlayerB"));

        Card initialActiveCard = unoService.getActiveCard(matchId);
        assertEquals(new NumberCard("Yellow", 0), initialActiveCard, "La carta activa inicial debería ser Yellow 0.");

        Card cardToPlay = new WildCard().asBlue();
        assertDoesNotThrow(() -> unoService.playCard(matchId, "PlayerA", cardToPlay),
                "PlayerA debería poder jugar una WildCard sobre Yellow 0.");

        Card activeCard = unoService.getActiveCard(matchId);
        assertEquals(cardToPlay, activeCard);

        assertThrows(RuntimeException.class, () -> unoService.playCard(matchId, "PlayerA", new NumberCard("Blue", 9)),
                "PlayerA no debería poder jugar dos veces seguidas.");

        assertDoesNotThrow(() -> unoService.playCard(matchId, "PlayerB", new NumberCard("Blue", 1)),
                "PlayerB debería poder jugar una Blue NumberedCard sobre una Blue WildCard.");
    }

    @Test
    void delegatesToMatchDrawCardMethod() {
        UUID matchId = unoService.newMatch(List.of("PlayerA", "PlayerB"));
        int initialHandSize = unoService.playerHand(matchId).size(); // Debería ser 7

        unoService.drawCard(matchId, "PlayerA");

        // Verificar que el tamaño de la mano aumentó en 1
        assertEquals(initialHandSize + 1, unoService.playerHand(matchId).size(),
                "El jugador debería tener una carta más después de robar.");

        // Verificar que la carta robada se encuentra en el mazo del jugador (Green 8)
        List<Card> currentHand = unoService.playerHand(matchId);
        assertTrue(currentHand.contains(new NumberCard("Green", 8)), "La mano debería contener la carta robada (Green 8).");

        // Verificar que no puede robar de nuevo si no es su turno (lógica de Match)
        assertThrows(RuntimeException.class, () -> unoService.drawCard(matchId, "PlayerA"),
                "PlayerA no debería poder robar dos veces seguidas sin jugar.");
    }
}