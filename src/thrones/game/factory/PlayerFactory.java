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

    public PlayerType getPlayer (int playerIndex, String playerType){
        if (playerType.equals("human")){
            return new Human(playerIndex, playerType);
        }
        else if (playerType.equals("random")){
            return new Random(playerIndex, playerType);
        }
        else if (playerType.equals("smart")){
            return new Smart(playerIndex, playerType);
        }
        else {
            return new Simple(playerIndex, playerType);
        }

    }
}
