package thrones.game.decorator;

import thrones.game.GameOfThrones.*;
import ch.aplu.jcardgame.Card;

public abstract class Character {
    public abstract int getAttack();
    public abstract int getDefence();
    public abstract Card getCard();

    public Rank getRank(Card card){
        return (Rank) card.getRank();
    }

}
