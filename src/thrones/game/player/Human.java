package thrones.game.player;

import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.CardAdapter;
import ch.aplu.jcardgame.Hand;

import java.util.Optional;

public class Human extends PlayerType {

    private Hand hand;
    private Optional<Card> selected;
    private int pile;

    public Human(int playerId, String playerType) {
        super(playerId, playerType);
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
