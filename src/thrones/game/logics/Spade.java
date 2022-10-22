package thrones.game.logics;

import thrones.game.logics.CardDecorator;
import thrones.game.logics.CardInterface;

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
