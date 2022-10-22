package thrones.game.player;

import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.CardAdapter;
import ch.aplu.jcardgame.Hand;
import thrones.game.GameOfThrones;

import java.util.Optional;

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
    /*
    public void setInteraction(){
        Hand currentHand = getHand();

            // Set up human player for interaction
              currentHand.addCardListener(new CardAdapter() {
                public void leftDoubleClicked(Card card) {
                    selected = Optional.of(card);
                    currentHand.setTouchEnabled(false);
                }
                public void rightClicked(Card card) {
                    selected = Optional.empty(); // Don't care which card we right-clicked for player to pass
                    currentHand.setTouchEnabled(false);
                }
            });

    }
    */


}
