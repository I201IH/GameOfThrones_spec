package thrones.game;

public class Spade extends CardDecorator {
    public Spade(CardInterface decoratedCard, int value) {
        super(decoratedCard, value);
    }

    @Override
    public int value() {
        int decoratedCardValue = super.getDecoratedCard().value();
        decoratedCardValue += super.value();
        return decoratedCardValue;
    }
}
