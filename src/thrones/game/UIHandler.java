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
    private final int handWidth = 400;
    private final int pileWidth = 40;

    private final Location[] handLocations = {
            new Location(350, 625),
            new Location(75, 350),
            new Location(350, 75),
            new Location(625, 350)
    };

    private final Location[] scoreLocations = {
            new Location(575, 675),
            new Location(25, 575),
            new Location(25, 25),
            new Location(575, 125)
    };
    private final Location[] pileLocations = {
            new Location(350, 280),
            new Location(350, 430)
    };
    private final Location[] pileStatusLocations = {
            new Location(250, 200),
            new Location(250, 520)
    };

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

    public Location[] getHandLocations() {
        return handLocations;
    }

    public Location[] getScoreLocations() {
        return scoreLocations;
    }

    public Location[] getPileLocations() {
        return pileLocations;
    }

    public Location[] getPileStatusLocations() {
        return pileStatusLocations;
    }

    public int getHandWidth() {
        return handWidth;
    }

    public int getPileWidth() {
        return pileWidth;
    }
}
