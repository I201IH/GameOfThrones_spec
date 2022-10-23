package thrones.game.player;

import ch.aplu.jcardgame.Card;
import thrones.game.GameOfThrones;
import ch.aplu.jcardgame.*;
import thrones.game.logics.*;
import java.util.*;

import static thrones.game.logics.CardLogic.*;


/**
 * Workshop 4 Friday 9:00, Team 12
 * Yi Wei 1166107
 * Thanh Nguyen Pham 1166068
 * Ian Han 1180762
 */

public class Simple extends PlayerType {

    /**
     * Constructor used to represent the random player selecting card behaviour
     * @param playerId the index f player
     * @param playerType the type of player, should be Simple
     */
    public Simple(int playerId, String playerType){
        super(playerId, playerType);
    }


    /**
     * correctSuit method used to pick card with correct suit for random player
     * Divides into play heart and non-heart two main aspects
     * @param hand the current hand for player
     * @param isCharacter if card is allowed to be heart
     * @param pile0ProcessRank the attack and defence value for character 0
     * @param pile1ProcessRank the attack and defence value for character 1
     * @param next the next Player hand
     * @param playerIndex the current player index
     * @param pile0 the hand of pile 0
     * @param pile1 the hand of pile 0
     * @return the selected card with type Optional<Card>
     */
    public Optional<Card> correctSuit(Hand hand, boolean isCharacter, int[] pile0ProcessRank,
                                      int[] pile1ProcessRank, Hand next, int playerIndex,
                                      Hand pile0, Hand pile1) {
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

    /**
     * this method used to select the pile for selected card
     * @param card the selected card
     * @param playerIndex the index of player
     * @return the index of selected pile
     */
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
