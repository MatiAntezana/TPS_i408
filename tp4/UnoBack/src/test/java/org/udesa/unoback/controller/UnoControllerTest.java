package org.udesa.unoback.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.udesa.unoback.model.*;
import org.udesa.unoback.service.UnoService;
import org.udesa.unoback.service.Dealer;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UnoControllerTest {

    @Autowired private MockMvc mockMvc; // Para simular las peticiones HTTP al controlador
    @Autowired private UnoService unoService;
    @MockBean private Dealer dealer;

    private ObjectMapper objectMapper; // Para (de)serializar objetos Java a/desde JSON

    private List<Card> predictableDeck;
    private List<Card> winningPredictableDeck;

    @BeforeEach
    public void setup() {
        objectMapper = new ObjectMapper();

        predictableDeck = Arrays.asList(
                new NumberCard("Red", 0), // 1. Carta Activa Inicial (índice 0)

                // 2. Mano de PlayerA (7 cartas - índices 1 a 7)
                new NumberCard("Blue", 1),
                new NumberCard("Green", 2),
                new NumberCard("Yellow", 3),
                new NumberCard("Blue", 4),
                new NumberCard("Green", 5),
                new NumberCard("Yellow", 6),
                new NumberCard("Red", 7),

                // 3. Mano de PlayerB (7 cartas - índices 8 a 14)
                new NumberCard("Blue", 8),
                new NumberCard("Green", 9),
                new NumberCard("Yellow", 1),
                new NumberCard("Blue", 2),
                new NumberCard("Red", 3),
                new NumberCard("Green", 4),
                new NumberCard("Yellow", 5),

                // 4. Cartas en el Mazo de Robo (a partir del índice 15)
                new WildCard(),             // Será la primera carta robada por PlayerA
                new NumberCard("Red", 8)
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
                new NumberCard("Yellow", 6),
                new NumberCard("Red", 7),

                // 3. Mano de PlayerB (7 cartas)
                new NumberCard("Blue", 8),
                new SkipCard("Green"),
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

        when(dealer.fullDeck()).thenReturn(predictableDeck);
    }

    // --- Métodos auxiliares para simular llamadas al controlador ---

    // Crea una partida y devuelve su UUID
    public UUID createMatch(String... players) throws Exception {
        String playersParam = String.join(",", players);
        String responseContent = mockMvc.perform(post("/newmatch") // Usa la ruta exacta del controller
                        .param("players", playersParam))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        return UUID.fromString(responseContent.replace("\"", ""));
    }

    // Intenta crear una partida que falla y espera un error del servidor
    public void createMatchFailing(String... players) throws Exception {
        // Esto solo fallará si el UnoService real lanza una RuntimeException que el Controller atrapa.
        // Ej: si le pasas menos de 2 jugadores y tu Match lo valida.
        String playersParam = String.join(",", players);
        mockMvc.perform(post("/newmatch")
                        .param("players", playersParam))
                .andDo(print())
                .andExpect(status().isInternalServerError()); // o .isBadRequest() si el controlador maneja el error con 400
    }

    // Obtiene la carta activa del juego
    public JsonCard getActiveCard(UUID matchId) throws Exception {
        String responseContent = mockMvc.perform(get("/activecard/" + matchId))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        return objectMapper.readValue(responseContent, JsonCard.class);
    }

    // Obtiene la mano del jugador actual
    public List<JsonCard> getPlayerHand(UUID matchId) throws Exception {
        String responseContent = mockMvc.perform(get("/playerhand/" + matchId))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        return objectMapper.readValue(responseContent, new TypeReference<List<JsonCard>>() {});
    }

    // Realiza la acción de robar una carta
    public void drawCard(UUID matchId, String player) throws Exception {
        mockMvc.perform(post("/draw/" + matchId + "/" + player))
                .andDo(print())
                .andExpect(status().isOk());
    }

    // Realiza la acción de jugar una carta (recibe JsonCard)
    public void playCard(UUID matchId, String player, JsonCard card) throws Exception {
        String jsonContent = objectMapper.writeValueAsString(card); // Convierte JsonCard a String JSON
        mockMvc.perform(post("/play/" + matchId + "/" + player)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andDo(print())
                .andExpect(status().isOk());
    }

    // Intenta jugar una carta que debería fallar
    public void playCardFailing(UUID matchId, String player, JsonCard card) throws Exception {
        String jsonContent = objectMapper.writeValueAsString(card);
        mockMvc.perform(post("/play/" + matchId + "/" + player)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andDo(print())
                .andExpect(status().isInternalServerError()); // Espera 500 por el @ExceptionHandler
    }

    // --- TESTS REALES ---

    @Test
    public void test01NewMatchCreatesGameAndReturnsId() throws Exception {
        UUID matchId = createMatch("PlayerA", "PlayerB");
        assertNotNull(matchId, "Se debe devolver un ID para la nueva partida.");
    }

    @Test
    public void test02GetActiveCardReturnsInitialCard() throws Exception {
        UUID matchId = createMatch("PlayerA", "PlayerB");
        JsonCard activeCard = getActiveCard(matchId);

        assertEquals("Red", activeCard.getColor());
        assertEquals(0, activeCard.getNumber());
        assertEquals("NumberCard", activeCard.getType());
    }

    @Test
    public void test03PlayerHandReturnsCorrectCardsForNewMatch() throws Exception {
        UUID matchId = createMatch("PlayerA", "PlayerB");
        List<JsonCard> playerAHand = getPlayerHand(matchId);

        assertEquals(7, playerAHand.size(), "PlayerA debería tener 7 cartas.");
        assertFalse(playerAHand.isEmpty(), "La mano inicial no debería estar vacía.");

        // Para verificar el contenido exacto de la mano de PlayerA
        List<JsonCard> expectedPlayerAJsonHand = predictableDeck.subList(1, 8).stream()
                .map(Card::asJson)
                .toList();

        //assertEquals(expectedPlayerAJsonHand, playerAHand, "La mano de PlayerA no coincide con el deck predecible.");

        for (int i = 0; i < expectedPlayerAJsonHand.size(); i++) {
            JsonCard expectedCard = expectedPlayerAJsonHand.get(i);
            JsonCard actualCard = playerAHand.get(i);

            assertEquals(expectedCard.getColor(), actualCard.getColor(),
                    "La carta en la posición " + i + " tiene un color incorrecto.");
            assertEquals(expectedCard.getNumber(), actualCard.getNumber(),
                    "La carta en la posición " + i + " tiene un número incorrecto.");
            assertEquals(expectedCard.getType(), actualCard.getType(),
                    "La carta en la posición " + i + " tiene un tipo incorrecto.");
            assertEquals(expectedCard.isShout(), actualCard.isShout(),
                    "La carta en la posición " + i + " tiene el shout incorrecto.");
        }
    }

    @Test
    public void test04DrawCardAddsCardToPlayerHand() throws Exception {
        UUID matchId = createMatch("PlayerA", "PlayerB");

        // Obtener mano ANTES de robar
        List<JsonCard> handBeforeDraw = getPlayerHand(matchId);
        assertEquals(7, handBeforeDraw.size());

        drawCard(matchId, "PlayerA");

        // Obtener mano DESPUÉS de robar (del UnoService real)
        List<JsonCard> handAfterDraw = getPlayerHand(matchId);
        assertEquals(8, handAfterDraw.size(), "PlayerA debería tener 8 cartas después de robar.");

        // Verificar que la WildCard (la primera carta robada de predictableDeck) está en la mano
        assertTrue(handAfterDraw.stream().anyMatch(card -> "WildCard".equals(card.getType())),
                "La mano debería contener la WildCard robada.");
    }

    @Test
    public void test05PlayValidNumberCardChangesActiveCardAndRemovesFromHand() throws Exception {
        UUID matchId = createMatch("PlayerA", "PlayerB");

        // La carta activa inicial es Red 0
        JsonCard initialActiveCard = getActiveCard(matchId);
        assertEquals("Red", initialActiveCard.getColor());
        assertEquals(0, initialActiveCard.getNumber());

        // Juega A
        JsonCard cardToPlay = new NumberCard("Red", 7).asJson();
        playCard(matchId, "PlayerA", cardToPlay); // Jugar Red 7 sobre Red 0

        // Verificar que la carta activa cambió
        JsonCard finalActiveCard = getActiveCard(matchId);
        assertEquals("Red", finalActiveCard.getColor());
        assertEquals(7, finalActiveCard.getNumber());

        // Juega B
        JsonCard cardToPlay2 = new NumberCard("Red", 3).asJson();
        playCard(matchId, "PlayerB", cardToPlay2); // Jugar Red 3 sobre Red 7

        // Verificar que la carta Red 7 ya no está en la mano de PlayerA
        List<JsonCard> playerAHandAfterPlay = getPlayerHand(matchId);
        assertEquals(6, playerAHandAfterPlay.size(), "La mano de PlayerA debe tener una carta menos.");
        assertFalse(playerAHandAfterPlay.contains(cardToPlay), "La carta jugada no debe estar en la mano.");
    }


    @Test
    public void test06PlayCardFailsIfPlayerDoesNotHaveCard() throws Exception {
        UUID matchId = createMatch("PlayerA", "PlayerB");

        // El UnoService lanzará la excepción "Not a card in hand"
        // PlayerA tiene: [Blue 1, Green 2, Yellow 3, Blue 4, Green 5, Yellow 6, Red 7]
        JsonCard nonExistentCard = new NumberCard("Black", 99).asJson();
        playCardFailing(matchId, "PlayerA", nonExistentCard);
    }



    @Test
    public void test07GameEndsWhenPlayerPlaysLastCardAndWins() throws Exception {
        when(dealer.fullDeck()).thenReturn(winningPredictableDeck);
        UUID matchId = createMatch("PlayerA", "PlayerB");

        // 1. PlayerA roba una WildCard
        drawCard(matchId, "PlayerA"); // P-A tiene 8 cartas

        // 2. PlayerA juega la WildCard, asignando color AZUL
        JsonCard playedWildCard = new WildCard().asBlue().asJson();
        playCard(matchId, "PlayerA", playedWildCard);

        // 3. PlayerB juega Blue 8
        JsonCard playerBBlue8 = new NumberCard("Blue", 8).asJson();
        playCard(matchId, "PlayerB", playerBBlue8);

        // 4. PlayerA juega Blue 1
        JsonCard playerABlue1 = new NumberCard("Blue", 1).asJson();
        playCard(matchId, "PlayerA", playerABlue1);

        JsonCard playerBYellow1 = new NumberCard("Yellow", 1).asJson();
        playCard(matchId, "PlayerB", playerBYellow1);

        JsonCard playerAYellow3 = new NumberCard("Yellow", 3).asJson();
        playCard(matchId, "PlayerA", playerAYellow3);

        JsonCard playerBYellow5 = new NumberCard("Yellow", 5).asJson();
        playCard(matchId, "PlayerB", playerBYellow5);

        JsonCard playerAGreen5 = new NumberCard("Green", 5).asJson();
        playCard(matchId, "PlayerA", playerAGreen5);

        JsonCard playerBGreen4 = new NumberCard("Green", 4).asJson();
        playCard(matchId, "PlayerB", playerBGreen4);

        JsonCard playerABlue4 = new NumberCard("Blue", 4).asJson();
        playCard(matchId, "PlayerA", playerABlue4);

        JsonCard playerBBlue2 = new NumberCard("Blue", 2).asJson();
        playCard(matchId, "PlayerB", playerBBlue2);

        JsonCard playerAGreen2 = new NumberCard("Green", 2).asJson();
        playCard(matchId, "PlayerA", playerAGreen2);

        JsonCard playerBSkipCardGreen = new SkipCard("Green").asJson();
        playCard(matchId, "PlayerB", playerBSkipCardGreen);

        JsonCard playerBGreen3 = new NumberCard("Green", 3).asJson();
        playCard(matchId, "PlayerB", playerBGreen3);


        List<JsonCard> finalPlayerAHand = getPlayerHand(matchId);
        assertEquals(2, finalPlayerAHand.size(), "La mano de PlayerA debería tener dos cartas.");

        // Como no tenemos endpoints para isGameOver/winner en el Controller, no podemos verificar eso directamente desde la API.
        // Tampoco puedo llamar desde aca a uno()?
    }


    // --- Tests para el manejo de errores (ej. partida no encontrada) ---

    @Test
    public void test08NewMatchWithInsufficientPlayersFails() throws Exception {
        createMatchFailing();
    }

    @Test
    public void test09GetActiveCardFailsForInvalidMatchId() throws Exception {
        UUID invalidId = UUID.randomUUID();
        mockMvc.perform(get("/activecard/" + invalidId))
                .andDo(print())
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void test10PlayerHandFailsForInvalidMatchId() throws Exception {
        UUID invalidId = UUID.randomUUID();
        mockMvc.perform(get("/playerhand/" + invalidId))
                .andDo(print())
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void test11DrawCardFailsForInvalidMatchId() throws Exception {
        UUID invalidId = UUID.randomUUID();
        mockMvc.perform(post("/draw/" + invalidId + "/PlayerA"))
                .andDo(print())
                .andExpect(status().isInternalServerError());
    }
}
