package thrones.game.factory;

import thrones.game.strategy.PlayingStrategy;
import thrones.game.strategy.RandomStrategy;
import thrones.game.strategy.SmartStrategy;
import thrones.game.strategy.SimpleStrategy;


public class StrategyFactory {
    private static StrategyFactory self = new StrategyFactory();

    //make sure only create one strategyFactory
    public static StrategyFactory getInstance() {
        if (StrategyFactory.self == null) {
            StrategyFactory.self = new StrategyFactory();
        }
        return self;
    }
    
    public PlayingStrategy getStrategy(String strategyType){
        if (strategyType.equals("SMART")){
            return new SmartStrategy();
        }
        else if (strategyType.equals("RANDOM")) {
            return new RandomStrategy();
        }
        else {
            return new SimpleStrategy();
        }
    }

}
