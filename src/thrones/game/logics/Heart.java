package thrones.game.logics;

import thrones.game.logics.CardInterface;

/**
 * Workshop 4 Friday 9:00, Team 12
 * Yi Wei 1166107
 * Thanh Nguyen Pham 1166068
 * Ian Han 1180762
 */

/**
 * Heart class as the original object to be decorated
 */

public class Heart implements CardInterface {
    private int value;

    public Heart(int value) {
        this.value = value;
    }

    /**
     * Value of the decorated object
     * @return value of the decorated object
     */
    @Override
    public int value() {
        return value;
    }

    /**
     * Set the value for the decorated object
     */
    public void setValue(int value) {
        this.value = value;
    }
}
