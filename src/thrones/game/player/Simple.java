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
