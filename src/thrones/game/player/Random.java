package thrones.game.player;

import ch.aplu.jcardgame.Card;
import thrones.game.GameOfThrones;
import thrones.game.factory.StrategyFactory;

public class Random extends PlayerType {

    public Random(int playerId, String playerType){
        super(playerId, playerType);
        this.strategy = StrategyFactory.getInstance().getStrategy("RANDOM");
    }

    @Override
    public int selectPile(Card card, int playerIndex){
        int pileIndex = -1;
        pileIndex = GameOfThrones.random.nextInt(2);
        System.out.println("pileIndex is "+pileIndex);
        return pileIndex;
    }
}
