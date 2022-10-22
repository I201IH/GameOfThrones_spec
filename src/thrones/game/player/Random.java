package thrones.game.player;

import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;
import thrones.game.GameOfThrones;
import thrones.game.factory.StrategyFactory;
import thrones.game.logics.CardLogic;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Random extends PlayerType {

    public Random(int playerId, String playerType){
        super(playerId, playerType);
        this.strategy = StrategyFactory.getInstance().getStrategy("RANDOM");
    }

    public Optional<Card> correctSuit(Hand hand, boolean isCharacter,int[] pile0ProcessRank,
                                      int[] pile1ProcessRank, Hand next) {
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
        pileIndex = GameOfThrones.random.nextInt(2);
        return pileIndex;
    }
}
