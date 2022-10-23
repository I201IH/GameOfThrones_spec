package thrones.game.factory;

import thrones.game.player.Random;
import thrones.game.player.Human;
import thrones.game.player.PlayerType;
import thrones.game.player.Simple;
import thrones.game.player.Smart;

/**
 * Workshop 4 Friday 9:00, Team 12
 * Yi Wei 1166107
 * Thanh Nguyen Pham 1166068
 * Ian Han 1180762
 */

/**
 * PlayerFactory class to produce different player types
 */

public class PlayerFactory {
    private static PlayerFactory playerFactory = new PlayerFactory();

    public static PlayerFactory getInstance() {
        if (PlayerFactory.playerFactory == null) {
            PlayerFactory.playerFactory = new PlayerFactory();
        }
        return playerFactory;
    }

    /**
     * create different player by the property player string
     * @param playerIndex the index of player
     * @param playerType the type of player
     * @return the class of specified player
     */
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
