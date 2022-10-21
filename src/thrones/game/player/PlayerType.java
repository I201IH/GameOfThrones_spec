package thrones.game.player;
import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;
import thrones.game.strategy.PlayingStrategy;

public abstract class PlayerType {

    private Hand hand;
    private int score;
    private int playerIndex;

    protected PlayingStrategy strategy;

    public PlayerType(){}



    public Hand getHand(){
        return hand;
    }

    public void setHand(Hand hand){
        this.hand = hand;
    }
}
