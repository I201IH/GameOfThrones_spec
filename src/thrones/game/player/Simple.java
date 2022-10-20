package thrones.game.player;

import thrones.game.factory.StrategyFactory;

public class Simple extends PlayerType {

    public Simple(){
        this.strategy = StrategyFactory.getInstance().getStrategy("SIMPLE");
    }
}
