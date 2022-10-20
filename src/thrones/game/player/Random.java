package thrones.game.player;

import thrones.game.factory.StrategyFactory;

public class Random extends PlayerType {
    public Random(){
        this.strategy = StrategyFactory.getInstance().getStrategy("RANDOM");
    }
}
