package thrones.game.logics;

public class Club extends CardDecorator {

    public Club(CardInterface decoratedCard, int value) {
        super(decoratedCard, value);
    }

    @Override
    public int value() {
        int decoratedCardValue = super.getDecoratedCard().value();
        decoratedCardValue += super.value();
        return decoratedCardValue;
    }
}
