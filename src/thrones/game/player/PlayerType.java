package thrones.game.player;
import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;
import thrones.game.GameOfThrones;
import thrones.game.logics.CardLogic;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Workshop 4 Friday 9:00, Team 12
 * Yi Wei 1166107
 * Thanh Nguyen Pham 1166068
 * Ian Han 1180762
 */

/**
 * PlayerType class as an abstract class with all common method for a player
 */

public abstract class PlayerType {

    private Hand hand;
    private int score;
    private int playerIndex;
    private String playerType;

    private Card card;

    public PlayerType(int playerIndex, String playerType){
        this.playerIndex = playerIndex;
        this.playerType =playerType;
    }

    public boolean isLegal(Card card, Hand selectedPile){
        int numPile = selectedPile.getNumberOfCards();
        CardLogic.Suit cardSuit = (CardLogic.Suit) card.getSuit();
        if (numPile == 0 && !cardSuit.isCharacter()){
            return false;
        }
        if (numPile > 0 &&  cardSuit.isCharacter()) {
            return false;
        }
        if (numPile == 1 &&  cardSuit.isMagic()) {
            //System.out.println("check");
            return false;
        }

        return true;
    }

    public abstract Optional<Card> correctSuit(Hand current, boolean isCharacter, int[] pile0ProcessRank,
                    int[] pile1ProcessRank, Hand next, int playerIndex, Hand pile0, Hand pile1);


    public abstract int selectPile(Card card, int index);

    public Hand getHand(){
        return hand;
    }

    /**
     * setter to set the hand
     * @param hand
     */
    public void setHand(Hand hand){
        this.hand = hand;
    }

    /**
     * getter to get the selected card
     * @return the selected card
     */
    public Card getCard(){
        return card;
    }

    /**
     * setter to set the card
     * @param card the selected card
     */
    public void setCard(Card card){
        this.card = card;
    }
}
