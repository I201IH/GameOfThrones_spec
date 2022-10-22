package thrones.game.player;

import thrones.game.factory.StrategyFactory;

public class Smart extends PlayerType {
    public Smart(int playerId, String playerType){
        super(playerId, playerType);
        this.strategy = StrategyFactory.getInstance().getStrategy("SMART");
    }
}
