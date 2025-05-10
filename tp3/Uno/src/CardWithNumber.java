class CardWithNumber extends NonWildCard{
    private int number;

    public CardWithNumber(String ColorCard, int number) {
        super(ColorCard);
        this.number = number;
    }

    public int getNumber() { return this.number; }
    public boolean isCompatibleWith (Card anotherCard) { return true; }

}