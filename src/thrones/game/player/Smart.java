package thrones.game.player;

import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;
import thrones.game.GameOfThrones;
import thrones.game.factory.StrategyFactory;
import thrones.game.logics.CardLogic;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static thrones.game.logics.CardLogic.*;

public class Smart extends PlayerType {

    private boolean isLose = false;
    private boolean isMagicE = false;
    private int selectedPile = -1;

    public Smart(int playerId, String playerType){
        super(playerId, playerType);
        this.strategy = StrategyFactory.getInstance().getStrategy("SMART");
    }
}
