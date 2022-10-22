package thrones.game.player;

import thrones.game.factory.StrategyFactory;

public class Random extends PlayerType {
    public Random(int playerId, String playerType){
        super(playerId, playerType);
        this.strategy = StrategyFactory.getInstance().getStrategy("RANDOM");
    }
}
