package org.udesa.unoback.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.udesa.unoback.model.Card;
import org.udesa.unoback.model.JsonCard;
import org.udesa.unoback.service.UnoService;

import java.util.UUID;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class UnoController {
    @Autowired private UnoService unoService;

    @ExceptionHandler(IllegalArgumentException.class) public ResponseEntity<String> handleIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error Ilegal Argument"+ex.getMessage());
    }

//    @ExceptionHandler(RuntimeException.class)
//    public ResponseEntity<String> handleIllegalArgument(RuntimeException ex) {
//        return ResponseEntity.internalServerError().body( ex.getMessage());
//    }

    @ExceptionHandler(RuntimeException.class) public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error RunTime "+ex.getMessage());
    }

    @PostMapping("newmatch")
    public ResponseEntity newMatch(@RequestParam List<String> players) {
        return ResponseEntity.ok(unoService.newMatch(players));
    }


    @PostMapping("play/{matchId}/{player}") public ResponseEntity play(@PathVariable UUID matchId, @PathVariable String player, @RequestBody JsonCard card ) {
        unoService.playCard(matchId, player, card.asCard());
        return ResponseEntity.ok().build();
    }


    @PostMapping("draw/{matchId}/{player}") public ResponseEntity drawCard( @PathVariable UUID matchId, @PathVariable String player ) {
        unoService.drawCard(matchId, player);
        return ResponseEntity.ok().build();
    }


    @GetMapping("activecard/{matchId}") public ResponseEntity activeCard( @PathVariable UUID matchId ) {
        return ResponseEntity.ok(unoService.getActiveCard(matchId).asJson());
    }


    @GetMapping("playerhand/{matchId}")
    public ResponseEntity playerHand(@PathVariable UUID matchId) {
        List<JsonCard> handAsJson = unoService.playerHand(matchId).stream()
                .map(each -> each.asJson())
                .collect(Collectors.toList()); // Convertir a List<JsonCard>
        return ResponseEntity.ok(handAsJson);
    }
}
