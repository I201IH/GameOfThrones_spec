package thrones.game.player;

import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;
import thrones.game.GameOfThrones;
import thrones.game.logics.CardLogic;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static thrones.game.logics.CardLogic.*;

public class Smart extends PlayerType {

    private boolean isLose = false;
    private boolean isMagicE = false;
    private int selectedPile = -1;

    public Smart(int playerId, String playerType){
        super(playerId, playerType);
    }


    public Optional<Card> correctSuit(Hand hand, boolean isCharacter, int[] pile0ProcessRank,
                                      int[] pile1ProcessRank, Hand next, int playerIndex,
                                      Hand pile0, Hand pile1) {
        //start of code change
        Hand currentHand = hand;
        Card selectedCard;
        Optional<Card> cardSelected;
        int teamIndex = playerIndex %2;
        List<Card> shortListCards = new ArrayList<>();
        for (int i = 0; i < currentHand.getCardList().size(); i++) {
            Card card = currentHand.getCardList().get(i);
            CardLogic.Suit suit = (CardLogic.Suit) card.getSuit();

            if (suit.isCharacter() == isCharacter) {
                shortListCards.add(card);
            }

        }



        if (shortListCards.isEmpty() ) {
            cardSelected = Optional.empty();
        }
        else if (!isCharacter){
            //System.out.println("NOt heart turn");
            cardSelected = isLose(pile0ProcessRank, pile1ProcessRank, teamIndex, currentHand, next, pile0, pile1);
        }
        else {
            //System.out.println("In the else");
            selectedCard = shortListCards.get(GameOfThrones.random.nextInt(shortListCards.size()));
            setCard(selectedCard);
            cardSelected = Optional.of(selectedCard);
        }
        return cardSelected;

    }

    @Override
    public int selectPile(Card card, int playerIndex){

        //when select suit is Attack & defence, to own team
        //when magic to another team

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

    public Optional<Card> isLose(int[] pile0rank, int[] pile1rank, int teamIndex, Hand currentPlayer, Hand nextPlayer,
                                 Hand pile0, Hand pile1){

        int score =0;
        Optional<Card> chosen;
        ArrayList<Card> attackCardList = new ArrayList<>();
        ArrayList<Card> defenceCardList = new ArrayList<>();
        ArrayList<Card> magicOnAttack = new ArrayList<>();
        ArrayList<Card> magicOnDefence = new ArrayList<>();
        ArrayList<Card> requiredCardList = new ArrayList<>();
        ArrayList<Card> notMagicEList = new ArrayList<>();
        Card cardSelected;

        Card last0 = pile0.get(pile0.getNumberOfCards()-1);
        Card last1 = pile1.get(pile1.getNumberOfCards()-1);

        //if teamIndex = 0, pile 0
        if (teamIndex % 2 == 0){
            //System.out.println("Check run");
            if (pile0rank[0] < pile1rank[1]){
                //System.out.println("Choose attack");
                isLose = true;
                //find rank(attack) + pile0rank[0] > pile1rank[1] in currentPlayer
                //Or find pile0rank[0] > pile1rank[1] - rank[magic], put card to opposing team
                //randomly select one of it

                for (int i = 0; i < currentPlayer.getCardList().size(); i++) {
                    Card card = currentPlayer.getCardList().get(i);
                    Suit cardSuit = (Suit) card.getSuit();
                    //find attack card in the current hand
                    if (cardSuit.isAttack()){
                        //find the rank(attack) + pile0rank[0] (attack of pile0) > pile1rank[1] (defence of pile1)
                        //Another situation is Double Rule
                        // rank(Attack) == rank(lastCard in team pile)
                        //This is pile 0, check rank(last0)
                        if (((Rank)card.getRank()).getRankValue() + pile0rank[0] > pile1rank[1]) {
                            attackCardList.add(card);
                        }
                        else if (((Rank)card.getRank()).getRankValue() == ((Rank)last0.getRank()).getRankValue()){
                            //check 2 * rank(card) + pile0rank[0] (attack of pile0) > pile1rank[1] (defence of pile1)
                            if (((Rank)card.getRank()).getRankValue()*2 + pile0rank[0] > pile1rank[1]){
                                attackCardList.add(card);
                            }

                        }
                    }
                    //Or we can use magic card to reduce opponent defence
                    //want pile 0 win, reduce pile 1 defence if last card of pile1 is defence card
                    else if (cardSuit.isMagic()){
                        //Also need to set selected pile
                        if (((Suit)last1.getSuit()).isDefence()){
                            //pile0rank[0] (attack of pile0) > pile1rank[1] (defence of pile1) - rank(magic)
                            if(pile0rank[0] > pile1rank[1] - ((Rank)card.getRank()).getRankValue()){
                                attackCardList.add(card);
                            }
                            else if ( ((Rank)card.getRank()).getRankValue() == ((Rank)last1.getRank()).getRankValue()){
                                if (pile0rank[0] > pile1rank[1] - 2* ((Rank)card.getRank()).getRankValue()){
                                    attackCardList.add(card);
                                }
                            }
                        }
                        else if (((Suit)last1.getSuit()).isMagic()){
                            //

                        }
                    }
                }
                //cardSelected = attackCardList.get(GameOfThrones.random.nextInt(attackCardList.size()));
                requiredCardList = attackCardList;

            }
            // pile 0 defence < pile 1 attack
            if ( pile0rank[1] < pile1rank[0]){
                //System.out.println("Choose defence");
                isLose = true;
                for (int i = 0; i < currentPlayer.getCardList().size(); i++){
                    Card card = currentPlayer.getCardList().get(i);
                    Suit cardSuit = (Suit) card.getSuit();
                    //find the defence effect card
                    if (cardSuit.isDefence()){
                        //find rank(defence) + pile0rank[1] (defence of pile0) > pile1rank[0] (attack of pile1)
                        if (((Rank)card.getRank()).getRankValue() + pile0rank[1] > pile1rank[0]) {
                            defenceCardList.add(card);
                        }
                        //check for double rule
                        //Want more defence
                        else if (((Rank)card.getRank()).getRankValue() == ((Rank)last0.getRank()).getRankValue()){
                            //check 2 * rank(card) + pile0rank[0] (defence of pile0) > pile1rank[1] (attack of pile1)
                            if (((Rank)card.getRank()).getRankValue()*2 + pile0rank[1] > pile1rank[0]){
                                defenceCardList.add(card);
                            }

                        }
                    }
                    //reduce pile 1 attack by applying magic card on pile 1
                    else if (cardSuit.isMagic()){
                        if (((Suit)last1.getSuit()).isAttack()){
                            //pile0rank[1] (defence of pile0) > pile1rank[0] (attack of pile1) - rank(magic)
                            if(pile0rank[1] > pile1rank[0] - ((Rank)card.getRank()).getRankValue()){
                                defenceCardList.add(card);
                            }
                            //check double rule
                            else if ( ((Rank)card.getRank()).getRankValue() == ((Rank)last1.getRank()).getRankValue()){
                                if (pile0rank[1] > pile1rank[0] - 2* ((Rank)card.getRank()).getRankValue()){
                                    defenceCardList.add(card);
                                }
                            }
                        }
                    }
                }
                // pile 0 attack < pile 1 defence and pile 0 defence < pile 1 attack
                if (pile0rank[0] < pile1rank[1]){
                    //System.out.println("Meet two lose");
                    isLose = true;
                    //choose randomly either of two
                    if (GameOfThrones.random.nextInt(2)%2==0){
                        requiredCardList = attackCardList;

                    }
                    else {
                        requiredCardList = defenceCardList;
                    }
                    //requiredCardList = attackCardList + defenceCardList;
                }
                else {
                    //Not meet both lose situation
                    //put cardList in required cardList
                    requiredCardList = defenceCardList;
                }



            }
        }


        //for player 1& 3, teamIndex = 1
        //lose when pile 1 lose
        if (teamIndex % 2 == 1){
            // pile 1 attack < pile 0 defence
            if (pile1rank[0] < pile0rank[1]){
                isLose = true;
                //find rank(attack) + pile1rank[0] > pile0rank[1] in currentPlayer
                //randomly select one of it

                for (int i = 0; i < currentPlayer.getCardList().size(); i++) {
                    Card card = currentPlayer.getCardList().get(i);
                    Suit cardSuit = (Suit) card.getSuit();
                    //find attack card in the current hand
                    if (cardSuit.isAttack()){
                        //find the rank(attack) + pile1rank[0] (attack of pile1) > pile0rank[1] (defence of pile0)
                        if (((Rank)card.getRank()).getRankValue() + pile1rank[0] > pile0rank[1]) {
                            attackCardList.add(card);
                        }
                        // want pile 1 wins, want pile 1 attack > pile 0 defence
                        //check double rule for last card in pile 1--last1
                        else if (((Rank)card.getRank()).getRankValue() == ((Rank)last1.getRank()).getRankValue()){
                            //check 2 * rank(card) + pile1rank[0] (attack of pile1) > pile0rank[1] (defence of pile0)
                            if (((Rank)card.getRank()).getRankValue()*2 + pile1rank[0] > pile0rank[1]){
                                attackCardList.add(card);
                            }

                        }
                    }
                    else if (cardSuit.isMagic()){
                        //want pile 1 wins
                        //reduce pile 0 defence by apply magic on pile 0
                        //check last card of pile 0 is defence
                        if(((Suit)last0.getSuit()).isDefence()){
                            //check attack(pile 1) > defence(pile 0) - rank (magic)
                            if(pile1rank[0] > pile0rank[1] - ((Rank)card.getRank()).getRankValue()){
                                //add to attack
                                attackCardList.add(card);
                            }
                            //check double rule
                            //the rank(card) == rank(last0)
                            else if(((Rank)card.getRank()).getRankValue() == ((Rank)last0.getRank()).getRankValue()){
                                //check attack(pile 1) > defence(pile 0) - 2* rank (magic)
                                if(pile1rank[0] > pile0rank[1] - 2* ((Rank)card.getRank()).getRankValue()){
                                    //add to attack
                                    attackCardList.add(card);
                                }
                            }
                        }
                    }

                }
                //cardSelected = attackCardList.get(GameOfThrones.random.nextInt(attackCardList.size()));
                requiredCardList = attackCardList;

            }
            // pile 1 defence < pile 0 attack
            if ( pile1rank[1] < pile0rank[0]){
                isLose = true;
                for (int i = 0; i < currentPlayer.getCardList().size(); i++){
                    Card card = currentPlayer.getCardList().get(i);
                    Suit cardSuit = (Suit) card.getSuit();
                    //find the defence effect card
                    if (cardSuit.isDefence()){
                        //find rank(defence) + pile1rank[1] (defence of pile1) > pile0rank[0] (attack of pile0)
                        if (((Rank)card.getRank()).getRankValue() + pile1rank[1] > pile0rank[0]) {
                            defenceCardList.add(card);
                        }
                        // want pile 1 wins, want pile 1 defence > pile 0 attack
                        //check double rule for last card in pile 1--last1
                        else if (((Rank)card.getRank()).getRankValue() == ((Rank)last1.getRank()).getRankValue()){
                            //check 2 * rank(card) + pile1rank[1] (defence of pile1) > pile0rank[0] (attack of pile0)
                            if (((Rank)card.getRank()).getRankValue()*2 + pile1rank[1] > pile0rank[0]){
                                defenceCardList.add(card);
                            }

                        }
                    }
                    else if (cardSuit.isMagic()){
                        //want pile 1 wins, reduce attack of pile0
                        //defence[pile1] > attack [pile0] - rank(card) if last0 is attack
                        if (((Suit)last0.getSuit()).isAttack()){
                            if(pile1rank[1] > pile0rank[0] - ((Rank)card.getRank()).getRankValue()){
                                defenceCardList.add(card);
                            }
                            //double rule
                            else if (((Rank)card.getRank()).getRankValue() == ((Rank)last0.getRank()).getRankValue()){
                                if(pile1rank[1] > pile0rank[0] - 2* ((Rank)card.getRank()).getRankValue()){
                                    defenceCardList.add(card);
                                }
                            }
                        }

                    }
                }

                // pile 1 attack < pile 0 defence
                if (pile1rank[0] < pile0rank[1]){
                    isLose = true;
                    //both battle lose, randomly choose one can change one battle outcome
                    if (GameOfThrones.random.nextInt(2)%2==0){
                        requiredCardList = attackCardList;
                    }
                    else {
                        requiredCardList = defenceCardList;
                    }
                }
                else {
                    //put cardList in required cardList
                    requiredCardList = defenceCardList;
                }

            }
        }


        setIsLose(isLose);
        if (requiredCardList.isEmpty() || !isLose){
            chosen = Optional.empty();
            setSelectedPile(-1);
        }
        else {

            boolean magicE = false;
            //filter rank(E) = rank(Magic) in next player
            for (int i=0; i< requiredCardList.size();i++){
                magicE = false;
                Card cardE = requiredCardList.get(i);
                int cardERank = ((Rank) cardE.getRank()).getRankValue();
                for (int j=0; j<nextPlayer.getNumberOfCards(); j++){
                    Card compareE = nextPlayer.get(j);
                    int compareERank = ((Rank) compareE.getRank()).getRankValue();
                    if(((Suit)compareE.getSuit()).isMagic()){
                        if (cardERank==compareERank){
                            magicE = true;
                        }
                    }

                }
                //System.out.println("magicE after loop + "+ magicE);
                if(!magicE){
                    //System.out.println("magicE is false");
                    notMagicEList.add(cardE);
                }
            }

            if (!notMagicEList.isEmpty()){
                cardSelected = notMagicEList.get(GameOfThrones.random.nextInt(notMagicEList.size()));
                setCard(cardSelected);
                chosen = Optional.of(cardSelected);
                setSelectedPile(teamIndex);
            }
            else {
                chosen = Optional.empty();
            }
        }
        return chosen;
    }


    public boolean getIsLose(){
        return isLose;
    }
    public void setIsLose(boolean isLose){
        this.isLose = isLose;
    }


    public void setMagicE(boolean isMagicE){
        this.isMagicE = isMagicE;
    }

    public boolean getMagicE(){
        return isMagicE;
    }

    public void setSelectedPile(int selectedPile){
        this.selectedPile = selectedPile;
    }

    public int getSelectedPile(){
        return selectedPile;
    }


}
