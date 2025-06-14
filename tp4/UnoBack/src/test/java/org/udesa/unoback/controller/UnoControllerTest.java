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
import org.springframework.stereotype.Service;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.udesa.unoback.model.*;
import org.udesa.unoback.service.Dealer;
import org.udesa.unoback.service.UnoService;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

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
    @MockBean private Dealer dealer;
    @Autowired private UnoService unoService;

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
        String playersParam = String.join(",", players);
        mockMvc.perform(post("/newmatch")
                        .param("players", playersParam))
                .andDo(print())
                .andExpect(status().isNotFound());
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
    public void playCardFailingRunTimeException(UUID matchId, String player, JsonCard card) throws Exception {
        String jsonContent = objectMapper.writeValueAsString(card);
        mockMvc.perform(post("/play/" + matchId + "/" + player)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    public void playCardFailingIllegalArgument(UUID matchId, String player, JsonCard card) throws Exception {
        String jsonContent = objectMapper.writeValueAsString(card);
        mockMvc.perform(post("/play/" + matchId + "/" + player)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }


    private void failsDueToInvalidId(String url, UUID invalidId) throws Exception {
        mockMvc.perform(get(url + invalidId))
                .andDo(print())
                .andExpect(status().isNotFound());
    }


    // --- Tests para el manejo de errores (ej. partida no encontrada) ---

    @Test
    public void newMatchWithInsufficientPlayersFails() throws Exception {
        createMatchFailing("PlayerA");
    }

    @Test
    public void getActiveCardFailsForInvalidMatchId() throws Exception {
        failsDueToInvalidId("/activecard/",UUID.randomUUID());
    }

    @Test
    public void playerHandFailsForInvalidMatchId() throws Exception {
        failsDueToInvalidId("/playerhand/",UUID.randomUUID());
    }

    @Test
    public void drawCardFailsForInvalidMatchId() throws Exception {
        failsDueToInvalidId("/draw/",UUID.randomUUID());
    }

    @Test
    public void playCardFailsForInvalidMatchId() throws Exception {
        failsDueToInvalidId("/play/",UUID.randomUUID());
    }

    @Test
    public void playWrongTurnTest() throws Exception {
        playCardFailingRunTimeException(createMatch("PlayerA", "PlayerB"), "PlayerB", new JsonCard("Red", 7, "NumberCard", false));
    }

    @Test
    public void playWithNoPlayerFails() throws Exception {
        playCardFailingRunTimeException(createMatch("PlayerA", "PlayerB"), "", new JsonCard("Red", 7, "NumberCard", false));
    }

    @Test
    public void playWithInvalidColorCardFails() throws Exception {
        playCardFailingIllegalArgument(createMatch("PlayerA", "PlayerB"), "PlayerA", new JsonCard("Purple", 9, "NumberCard", false));
    }

    @Test
    public void playWithInvalidNumberCardFails() throws Exception {playCardFailingRunTimeException(createMatch("PlayerA", "PlayerB"), "PlayerA", new JsonCard("Red", 99, "NumberCard", false));
    }
}
