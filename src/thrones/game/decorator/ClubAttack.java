package thrones.game.decorator;

import ch.aplu.jcardgame.Card;

public class ClubAttack extends Character{

    @Override
    public int getAttack() {
        return super.getAttack() + ;
    }

    @Override
    public int getDefence() {
        return 0;
    }

    @Override
    public Card getCard() {
        return null;
    }
}
