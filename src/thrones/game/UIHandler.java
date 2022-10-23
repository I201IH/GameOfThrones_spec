package thrones.game;

import ch.aplu.jcardgame.*;
import ch.aplu.jgamegrid.*;

import thrones.game.logics.*;
import thrones.game.factory.PlayerFactory;
import thrones.game.player.PlayerType;

import java.awt.Color;
import java.awt.Font;
import java.util.*;

import static thrones.game.logics.CardLogic.*;

public class UIHandler extends CardGame {
    Font bigFont = new Font("Arial", Font.BOLD, 36);
    Font smallFont = new Font("Arial", Font.PLAIN, 10);

    public void setUpGameUI(int nbPlayers, Location[] handLocations, int handWidth, PlayerType[] players,
                            CardGame cardgame) {
        RowLayout[] layouts = new RowLayout[nbPlayers];
        for (int i = 0; i < nbPlayers; i++) {
            layouts[i] = new RowLayout(handLocations[i], handWidth);
            layouts[i].setRotationAngle(90 * i);
            players[i].getHand().setView(cardgame, layouts[i]);
            players[i].getHand().draw();
        }
    }

    public TextActor scoreUI(String text, Font font) {
        return new TextActor(text, Color.WHITE, new Color(0,0,0,0), font);
    }

    public void removeFromHandUI(Card card, boolean bool) {
        card.removeFromHand(bool);
    }

    public void insertUI(Hand hand, Card card, boolean bool) {
        hand.insert(card, bool);
    }

    public void waitForPileSelectionUI(int selectedPileIndex, int val, Hand[] piles) {
        selectedPileIndex = val;
        for (Hand pile : piles) {
            pile.setTouchEnabled(true);
        }
        while(selectedPileIndex == val) {
            delay(100);
        }
        for (Hand pile : piles) {
            pile.setTouchEnabled(false);
        }
    }

}
