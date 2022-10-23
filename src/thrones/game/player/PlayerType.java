package thrones.game.player;
import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;
import thrones.game.GameOfThrones;
import thrones.game.logics.CardLogic;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public void setHand(Hand hand){
        this.hand = hand;
    }

    public Card getCard(){
        return card;
    }
    public void setCard(Card card){
        this.card = card;
    }
}
