package thrones.game.player;

import thrones.game.factory.StrategyFactory;

public class Smart extends PlayerType {
    public Smart(){
        this.strategy = StrategyFactory.getInstance().getStrategy("SMART");
    }
}
