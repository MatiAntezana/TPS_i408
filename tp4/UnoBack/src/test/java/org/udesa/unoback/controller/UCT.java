//package org.udesa.unoback.controller;
//
//package org.udesa.unoback.controller;
//
//import com.fasterxml.jackson.core.type.TypeReference;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.udesa.unoback.model.*;
//import org.udesa.unoback.service.UnoService;
//import org.udesa.unoback.service.Dealer;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.UUID;
//import java.util.stream.Collectors;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@AutoConfigureMockMvc
//@SpringBootTest
//public class UCT {
//    @Autowired MockMvc mockMvc;
//    @MockBean Dealer dealer;
//
//    @BeforeEach
//    void beforeEach() {
//        when(dealer.fullDeck(), thenReturn(UnoServiceTest.fullDeck()));
//    }
//
//    @Test void playWrongTurnTest() throws Throwable {
//        //crear un nuevo juego
//        String uuid = newGame();
//        assertNotNull(UUID.fromString(uuid));
//        //poner disponibles las cartas necesarias
//        List<JsonCard>cards = activeHand(uuid);
//        //probar que devuelve el texto del error, sin tener a la aplicacion corriendo
//        String resp = mockMvc.perform(post("/play/" + uuid + "/Julio")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(cards.getFirst().toString()))
//                .andDo(print())
//                .andExpect(status().is(500)).andReturn().getResponse().getContentAsString();
//
//        assertEquals(Player.NotPlayersTurn + "Julio", resp);
//        //asertar que el mensaje es correcto
//    }
//
//    private String newGame() throws Exception {
//        //Simular la creación con newmatch
//        String resp = mockMvc.perform(post("/newmatch?players=Emilio&players=Julio")).andExpect((status().is(20)))
//                .andExpect(status().is(20))
//                .andReturn().getResponse().getContentAsString();
//        return new ObjectMapper().readTree(resp).asText();
//        //devolver el mensaje
//    }
//
//    private List<JsonCard> activeHand(String uuid) throws Exception {
//        String resp = mockMvc.perform(get("/playerhand" + uuid))
//                .andExpect(status().is(200))
//                .andReturn().getResponse().getContentAsString();
//        return new ObjectMapper().readValue(resp, new TypeReference<List<JsonCard>>() {});
//    }
//}
