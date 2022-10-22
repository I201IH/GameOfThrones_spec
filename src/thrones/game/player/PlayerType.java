package thrones.game.player;
import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;
import thrones.game.GameOfThrones;
import thrones.game.logics.CardLogic;
import thrones.game.strategy.PlayingStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class PlayerType {

    private Hand hand;
    private int score;
    private int playerIndex;
    private String playerType;

    protected PlayingStrategy strategy;
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

    public abstract Optional<Card> correctSuit(Hand hand, boolean isCharacter);
    /*
    public Optional<Card> correctSuit(Hand hand, boolean isCharacter) {
        //start of code change
        Hand currentHand = hand;
        Card selectedCard;
        Optional<Card> cardSelected;
        List<Card> shortListCards = new ArrayList<>();
        for (int i = 0; i < currentHand.getCardList().size(); i++) {
            Card card = currentHand.getCardList().get(i);
            CardLogic.Suit suit = (CardLogic.Suit) card.getSuit();

            if (suit.isCharacter() == isCharacter) {
                shortListCards.add(card);
            }

        }
        if (shortListCards.isEmpty()){
        }
        if (shortListCards.isEmpty() || !isCharacter && GameOfThrones.random.nextInt(3) == 0) {
            cardSelected = Optional.empty();
        } else {
            selectedCard = shortListCards.get(GameOfThrones.random.nextInt(shortListCards.size()));
            setCard(selectedCard);
            cardSelected = Optional.of(selectedCard);
        }
        return cardSelected;

    }

     */


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
