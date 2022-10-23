package thrones.game.logics;

/**
 * Workshop 4 Friday 9:00, Team 12
 * Yi Wei 1166107
 * Thanh Nguyen Pham 1166068
 * Ian Han 1180762
 */

/**
 * Club class as a decorator for attack score
 */

public class Club extends CardDecorator {

    public Club(CardInterface decoratedCard, int value) {
        super(decoratedCard, value);
    }

    /**
     * Add attack value
     * @return new value of the decorated object
     */
    @Override
    public int value() {
        int decoratedCardValue = super.getDecoratedCard().value();
        decoratedCardValue += super.value();
        return decoratedCardValue;
    }
}
