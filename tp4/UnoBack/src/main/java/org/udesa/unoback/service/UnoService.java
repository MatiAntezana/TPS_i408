package org.udesa.unoback.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.udesa.unoback.model.Card;
import org.udesa.unoback.model.JsonCard;
import org.udesa.unoback.model.Match;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.List;

@Service
public class UnoService {
    @Autowired
    private Dealer dealer;
    private Map<UUID, Match> sessions = new HashMap<UUID, Match>();
    public UUID newMatch(List<String> players) {
        UUID newKey = UUID.randomUUID();

        // Que ya nos de un mazo de cartas mezclado y todo
        sessions.put(newKey, Match.fullMatch(dealer.fullDeck(), players));
        return newKey;
    }

    public List<Card> playerHand(UUID matchId) {
        Match match = sessions.get(matchId);
        return match.playerHand();
    }

    public void playCard(UUID matchId, String player, Card card) {
        Match match = sessions.get(matchId);
        match.play(player, card);

    }

    public void drawCard(UUID matchId, String player) {
        Match match = sessions.get(matchId);
        match.drawCard(player);
    }

    public Card getActiveCard(UUID matchId) {
        Match match = sessions.get(matchId);
        return match.activeCard();
    }

}
