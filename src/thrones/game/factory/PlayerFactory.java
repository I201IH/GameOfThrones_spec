package thrones.game.factory;

import thrones.game.player.Random;
import thrones.game.player.Human;
import thrones.game.player.PlayerType;
import thrones.game.player.Simple;
import thrones.game.player.Smart;

public class PlayerFactory {
    private static PlayerFactory playerFactory = new PlayerFactory();

    public static PlayerFactory getInstance() {
        if (PlayerFactory.playerFactory == null) {
            PlayerFactory.playerFactory = new PlayerFactory();
        }
        return playerFactory;
    }

    public PlayerType getPlayer (String playerType){
        if (playerType.equals("human")){
            return new Human();
        }
        else if (playerType.equals("random")){
            return new Random();
        }
        else if (playerType.equals("smart")){
            return new Smart();
        }
        else {
            return new Simple();
        }

    }
}
