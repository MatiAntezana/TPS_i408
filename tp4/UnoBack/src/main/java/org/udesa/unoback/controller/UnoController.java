package org.udesa.unoback.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.udesa.unoback.model.Card;
import org.udesa.unoback.model.JsonCard;
import org.udesa.unoback.service.UnoService;

import java.rmi.server.UID;
import java.util.UUID;
import java.util.List;

@RestController
public class UnoController {
    @Autowired
    private final UnoService unoService;

    public UnoController(UnoService unoService) {
        this.unoService = unoService;
    }

    @PostMapping("newmatch")
    public ResponseEntity newMatch(@RequestParam List<String> players) {
        // Quiero pasarle un newMatch a un servicio
        return ResponseEntity.ok(unoService.newMatch(players));
    }
    @PostMapping("play/{matchId}/{player}") public ResponseEntity play(@PathVariable UUID matchId, @PathVariable String player, @RequestBody JsonCard card ) {
        Card playableCard = card.asCard();
        unoService.playCard(matchId, player, playableCard);
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
        return ResponseEntity.ok(unoService.playerHand(matchId).stream().map(each -> each.asJson()));
    }
}
