package thrones.game;

public abstract class CardDecorator implements CardInterface {
    private CardInterface decoratedCard;
    private int value;

    public CardDecorator(CardInterface decoratedCard, int value) {
        this.decoratedCard = decoratedCard;
        this.value = value;
    }

    public int value(){
        return value;
    }

    public CardInterface getDecoratedCard() {
        return decoratedCard;
    }
}
