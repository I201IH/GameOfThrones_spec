package thrones.game.logics;

import thrones.game.logics.CardDecorator;
import thrones.game.logics.CardInterface;

/**
 * Workshop 4 Friday 9:00, Team 12
 * Yi Wei 1166107
 * Thanh Nguyen Pham 1166068
 * Ian Han 1180762
 */

/**
 * Spade class as a decorator for defence score
 */

public class Spade extends CardDecorator {
    public Spade(CardInterface decoratedCard, int value) {
        super(decoratedCard, value);
    }

    /**
     * Add defence value
     * @return new value of the decorated object
     */
    @Override
    public int value() {
        int decoratedCardValue = super.getDecoratedCard().value();
        decoratedCardValue += super.value();
        return decoratedCardValue;
    }
}
