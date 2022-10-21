package thrones.game.strategy;

import java.util.Optional;
import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.CardAdapter;
import ch.aplu.jcardgame.Hand;


public abstract class PlayingStrategy {
    protected Optional<Card> selected;
    protected Hand[] piles;


}
