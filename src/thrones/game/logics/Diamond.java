package thrones.game.logics;

public class Diamond extends CardDecorator {
    public Diamond(CardInterface decoratedCard, int value) {
        super(decoratedCard, value);
    }

    @Override
    public int value() {
        int decoratedCardValue = super.getDecoratedCard().value();
        decoratedCardValue -= super.value();
        return decoratedCardValue;
    }
}
