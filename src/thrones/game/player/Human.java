package thrones.game.player;

import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.CardAdapter;
import ch.aplu.jcardgame.Hand;
import thrones.game.GameOfThrones;
import thrones.game.logics.CardLogic;

import java.util.Optional;
import java.util.ArrayList;
import java.util.List;

/**
 * Workshop 4 Friday 9:00, Team 12
 * Yi Wei 1166107
 * Thanh Nguyen Pham 1166068
 * Ian Han 1180762
 */

public class Human extends PlayerType {

    private Hand hand;
    private Optional<Card> selected;
    private int pile;

    public Human(int playerId, String playerType) {
        super(playerId, playerType);
    }

    @Override
    public int selectPile(Card card, int playerIndex){
        int pileIndex = -1;
        //If magic put on opposing
        //If Defence & Attack, put own team

        pileIndex = GameOfThrones.random.nextInt(2);
        return pileIndex;
    }

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



}
