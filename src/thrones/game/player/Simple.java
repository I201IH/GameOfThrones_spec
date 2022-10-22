package thrones.game.player;

import thrones.game.factory.StrategyFactory;

public class Simple extends PlayerType {

    public Simple(int playerId, String playerType){
        super(playerId, playerType);
        this.strategy = StrategyFactory.getInstance().getStrategy("SIMPLE");
    }
}
