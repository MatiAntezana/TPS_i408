package org.udesa.unoback.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.udesa.unoback.model.Card;
import org.udesa.unoback.model.Match;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.List;

@Service
public class UnoService {
    @Autowired private Dealer dealer;
    private Map<UUID, Match> sessions = new HashMap<UUID, Match>();

    public UUID newMatch(List<String> players) {
        UUID newKey = UUID.randomUUID();
        sessions.put(newKey, Match.fullMatch(dealer.fullDeck(), players));
        return newKey;
    }


    public Match getMatch(UUID matchId) {
        Match match = sessions.get(matchId);
        if (match == null) {
            throw new RuntimeException("Match with ID " + matchId + " not found.");
        }
        return match;
    }


    public Map<UUID, Match> getSessionsForTesting() { return this.sessions; }


    public List<Card> playerHand(UUID matchId) {
        Match match = getMatch(matchId);
        return match.playerHand();
    }


    public void playCard(UUID matchId, String player, Card card) {
        Match match = getMatch(matchId);
        match.play(player, card);
    }


    public void drawCard(UUID matchId, String player) {
        Match match = getMatch(matchId);
        match.drawCard(player);
    }


    public Card getActiveCard(UUID matchId) {
        Match match = getMatch(matchId);
        return match.activeCard();
    }
}