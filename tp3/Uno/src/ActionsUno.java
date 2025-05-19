public abstract class ActionsUno {
    public abstract void playAction(Game game, Player player);
}

class ActionPlayNormal extends ActionsUno {
    public ActionPlayNormal() {super();}
    public void playAction(Game game, Player player) {}
    // Si es hay que finalizar el juego podria definir el comportamiento acá
}

class ActionPlayPenalty extends ActionsUno {
    public ActionPlayPenalty() {super();}
    public void playAction(Game game, Player player) { game.Draw2Deck(player); }
}