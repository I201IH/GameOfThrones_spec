package thrones.game.logics;

import thrones.game.logics.CardInterface;

public class Heart implements CardInterface {
    private int value;

    public Heart(int value) {
        this.value = value;
    }

    @Override
    public int value() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
