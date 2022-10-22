package thrones.game.player;

import ch.aplu.jcardgame.Card;
import thrones.game.GameOfThrones;
import thrones.game.factory.StrategyFactory;
import ch.aplu.jcardgame.*;
import ch.aplu.jgamegrid.*;
import thrones.game.logics.*;
import thrones.game.factory.PlayerFactory;
import thrones.game.player.PlayerType;

import java.awt.Color;
import java.awt.Font;
import java.util.*;

import static thrones.game.logics.CardLogic.*;

public class Simple extends PlayerType {

    public Simple(int playerId, String playerType){
        super(playerId, playerType);
        this.strategy = StrategyFactory.getInstance().getStrategy("SIMPLE");
    }

    @Override
    public int selectPile(Card card, int playerIndex){
        int pileIndex = -1;
        int teamPile = playerIndex % 2;
        //If magic put on opposing
        //If Defence & Attack, put own team
        Suit cardSuit = (Suit) card.getSuit();
        if (cardSuit.isMagic()){
            //System.out.println("Magic card");
            pileIndex = (playerIndex +1) % 2;
        }
        if (cardSuit.isAttack() || cardSuit.isDefence()){
            pileIndex = teamPile;
        }

        return pileIndex;
    }
}
