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
import org.springframework.test.web.servlet.RequestBuilder;
import org.udesa.unoback.model.*;
import org.udesa.unoback.service.Dealer;
import org.udesa.unoback.service.UnoService;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UnoControllerTest {

    @Autowired private MockMvc mockMvc;
    @MockBean private Dealer dealer;
    @Autowired private UnoService unoService;

    private ObjectMapper objectMapper;

    private List<Card> predictableDeck;

    @BeforeEach
    public void setup() {
        objectMapper = new ObjectMapper();

        predictableDeck = Arrays.asList(
                new NumberCard("Red", 0),


                new NumberCard("Blue", 1),
                new NumberCard("Green", 2),
                new NumberCard("Yellow", 3),
                new NumberCard("Blue", 4),
                new NumberCard("Green", 5),
                new NumberCard("Yellow", 6),
                new NumberCard("Red", 7),


                new NumberCard("Blue", 8),
                new NumberCard("Green", 9),
                new NumberCard("Yellow", 1),
                new NumberCard("Blue", 2),
                new NumberCard("Red", 3),
                new NumberCard("Green", 4),
                new NumberCard("Yellow", 5),

                new WildCard(),
                new NumberCard("Red", 8)
        );

        when(dealer.fullDeck()).thenReturn(predictableDeck);
    }

    @Test
    public void newMatchCreatesGame() throws Exception {
        createMatch("PlayerA", "PlayerB");
    }


    @Test
    public void getActiveCard() throws Exception {
        getActiveCard(createMatch("PlayerA", "PlayerB"));
    }

    @Test
    public void getPlayerHand() throws Exception {
        assertFalse(getPlayerHand(createMatch("PlayerA", "PlayerB")).isEmpty(), "La mano inicial no debería estar vacía.");
    }


    @Test
    public void getDrawCard() throws Exception {
        drawCard(createMatch("PlayerA", "PlayerB"), "PlayerA");
    }

    @Test
    public void playCorrectTurn() throws Exception {
        UUID matchId = createMatch("PlayerA", "PlayerB");
        Match retrievedMatch = unoService.getMatch(matchId);

        retrievedMatch.draw();
        retrievedMatch.play("PlayerA", new WildCard().asBlue());

        playCard(matchId, "PlayerB", new JsonCard("Blue", 8, "NumberCard", false));

        retrievedMatch.play("PlayerA", new NumberCard("Blue", 1));

        playCard(matchId, "PlayerB", new JsonCard("Yellow", 1, "NumberCard", false));
    }


    // --- Tests para el manejo de errores ---

    @Test
    public void newMatchWithInsufficientPlayersFails() throws Exception {
        createMatchFailing("PlayerA");
    }

    @Test
    public void newMatchWithNonePlayersFails() throws Exception {
        createMatchFailing();
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
    public void playWithInvalidNumberCardFails() throws Exception {
        playCardFailingRunTimeException(createMatch("PlayerA", "PlayerB"), "PlayerA", new JsonCard("Red", 99, "NumberCard", false));
    }

    @Test
    public void playWithNoCardsFails() throws Exception {
        playCardFailingRunTimeException(createMatch("PlayerA", "PlayerB"), "PlayerA", null);
    }


    // --- Métodos auxiliares para simular llamadas al controlador ---

    private String performAndExpectStatus(RequestBuilder requestBuilder, int expectedStatus) throws Exception {
        return mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().is(expectedStatus))
                .andReturn()
                .getResponse()
                .getContentAsString();
    }


    private void performWithoutBody(RequestBuilder requestBuilder, int expectedStatus) throws Exception {
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().is(expectedStatus));
    }


    public UUID createMatch(String... players) throws Exception {
        String playersParam = String.join(",", players);
        String response = performAndExpectStatus(post("/newmatch").param("players", playersParam), 200);
        return UUID.fromString(response.replace("\"", ""));
    }


    public void createMatchFailing(String... players) throws Exception {
        String playersParam = String.join(",", players);
        performWithoutBody(post("/newmatch").param("players", playersParam), 404);
    }


    public JsonCard getActiveCard(UUID matchId) throws Exception {
        String response = performAndExpectStatus(get("/activecard/" + matchId), 200);
        return objectMapper.readValue(response, JsonCard.class);
    }


    public List<JsonCard> getPlayerHand(UUID matchId) throws Exception {
        String response = performAndExpectStatus(get("/playerhand/" + matchId), 200);
        return objectMapper.readValue(response, new TypeReference<List<JsonCard>>() {});
    }


    public void drawCard(UUID matchId, String player) throws Exception {
        performWithoutBody(post("/draw/" + matchId + "/" + player), 200);
    }


    public void playCard(UUID matchId, String player, JsonCard card) throws Exception {
        String json = objectMapper.writeValueAsString(card);
        performAndExpectStatus(post("/play/" + matchId + "/" + player, json).contentType(MediaType.APPLICATION_JSON).content(json), 200);
    }


    public void playCardFailingRunTimeException(UUID matchId, String player, JsonCard card) throws Exception {
        String json = objectMapper.writeValueAsString(card);
        performAndExpectStatus(post("/play/" + matchId + "/" + player, json).contentType(MediaType.APPLICATION_JSON).content(json), 404);
    }


    public void playCardFailingIllegalArgument(UUID matchId, String player, JsonCard card) throws Exception {
        String json = objectMapper.writeValueAsString(card);
        performAndExpectStatus(post("/play/" + matchId + "/" + player, json).contentType(MediaType.APPLICATION_JSON).content(json), 400);
    }


    private void failsDueToInvalidId(String url, UUID invalidId) throws Exception {
        performWithoutBody(get(url + invalidId), 404);
    }
}
