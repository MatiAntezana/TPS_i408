package Uno;


public abstract class Card {
    private boolean saidUno;

    public abstract boolean acceptsCard(Card cardInHand);
    protected boolean likesColor(String aColor) { return false; }
    protected boolean likesNumber(int aNumber) { return false; }
    protected String type() { return getClass().getSimpleName(); }
    protected boolean likesMyType(Card cardInHand) { return cardInHand.type().equals(type()); }

    public abstract void applyEffect(Game game);
    public abstract boolean equalsCard(Card cardInHand);

    public Card uno(){ saidUno = true; return this; }
    public boolean checkForUno(){ return saidUno; }
}


