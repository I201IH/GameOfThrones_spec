package thrones.game;

// Oh_Heaven.java

import ch.aplu.jcardgame.*;
import ch.aplu.jgamegrid.*;
import thrones.game.logics.*;
import thrones.game.logics.CardLogic.*;
import thrones.game.factory.PlayerFactory;
import thrones.game.player.PlayerType;

import java.awt.Color;
import java.awt.Font;
import java.util.*;

import static thrones.game.logics.CardLogic.*;

@SuppressWarnings("serial")
public class GameOfThrones extends CardGame {
    public static final String DEFAULT_PATH = "properties/got.properties";
    static public int seed;
    public static Random random;
    private PlayerType[] players;


    // return random Card from Hand
    public static Card randomCard(Hand hand) {
        assert !hand.isEmpty() : " random card from empty hand.";
        int x = random.nextInt(hand.getNumberOfCards());
        return hand.get(x);
    }

    private void dealingOut(Hand[] hands, int nbPlayers, int nbCardsPerPlayer) {

        //start of code change
        //change all hands to players[i].getHand()
        Hand pack = deck.toHand(false);
        assert pack.getNumberOfCards() == 52 : " Starting pack is not 52 cards.";
        // Remove 4 Aces
        List<Card> aceCards = pack.getCardsWithRank(Rank.ACE);
        for (Card card : aceCards) {
            card.removeFromHand(false);
        }
        assert pack.getNumberOfCards() == 48 : " Pack without aces is not 48 cards.";
        // Give each player 3 heart cards
        for (int i = 0; i < nbPlayers; i++) {
            for (int j = 0; j < 3; j++) {
                List<Card> heartCards = pack.getCardsWithSuit(Suit.HEARTS);
                int x = random.nextInt(heartCards.size());
                Card randomCard = heartCards.get(x);
                randomCard.removeFromHand(false);
                players[i].getHand().insert(randomCard, false);
            }
        }
        assert pack.getNumberOfCards() == 36 : " Pack without aces and hearts is not 36 cards.";
        // Give each player 9 of the remaining cards
        for (int i = 0; i < nbCardsPerPlayer; i++) {
            for (int j = 0; j < nbPlayers; j++) {
                assert !pack.isEmpty() : " Pack has prematurely run out of cards.";
                Card dealt = randomCard(pack);
                dealt.removeFromHand(false);
                players[j].getHand().insert(dealt, false);
            }
        }
        for (int j = 0; j < nbPlayers; j++) {
            assert players[j].getHand().getNumberOfCards() == 12 : " Hand does not have twelve cards.";
        }


        /*
        Hand pack = deck.toHand(false);
        assert pack.getNumberOfCards() == 52 : " Starting pack is not 52 cards.";
        // Remove 4 Aces
        List<Card> aceCards = pack.getCardsWithRank(Rank.ACE);
        for (Card card : aceCards) {
            card.removeFromHand(false);
        }
        assert pack.getNumberOfCards() == 48 : " Pack without aces is not 48 cards.";
        // Give each player 3 heart cards
        for (int i = 0; i < nbPlayers; i++) {
            for (int j = 0; j < 3; j++) {
                List<Card> heartCards = pack.getCardsWithSuit(Suit.HEARTS);
                int x = random.nextInt(heartCards.size());
                Card randomCard = heartCards.get(x);
                randomCard.removeFromHand(false);
                hands[i].insert(randomCard, false);
            }
        }
        assert pack.getNumberOfCards() == 36 : " Pack without aces and hearts is not 36 cards.";
        // Give each player 9 of the remaining cards
        for (int i = 0; i < nbCardsPerPlayer; i++) {
            for (int j = 0; j < nbPlayers; j++) {
                assert !pack.isEmpty() : " Pack has prematurely run out of cards.";
                Card dealt = randomCard(pack);
                dealt.removeFromHand(false);
                hands[j].insert(dealt, false);
            }
        }
        for (int j = 0; j < nbPlayers; j++) {
            assert hands[j].getNumberOfCards() == 12 : " Hand does not have twelve cards.";
        }
        */
        //end of orginal code
    }

    private final String version = "1.0";
    public final int nbPlayers = 4;
    public final int nbStartCards = 9;
	public final int nbPlays = 6;
	public final int nbRounds = 3;
    private final int handWidth = 400;
    private final int pileWidth = 40;
    private Deck deck = new Deck(CardLogic.Suit.values(), CardLogic.Rank.values(), "cover");
    private final Location[] handLocations = {
            new Location(350, 625),
            new Location(75, 350),
            new Location(350, 75),
            new Location(625, 350)
    };

    private final Location[] scoreLocations = {
            new Location(575, 675),
            new Location(25, 575),
            new Location(25, 25),
            new Location(575, 125)
    };
    private final Location[] pileLocations = {
            new Location(350, 280),
            new Location(350, 430)
    };
    private final Location[] pileStatusLocations = {
            new Location(250, 200),
            new Location(250, 520)
    };

    private Actor[] pileTextActors = { null, null };
    private Actor[] scoreActors = {null, null, null, null};
    private int watchingTime = 5000;
    private Hand[] hands;
    private Hand[] piles;
    private final String[] playerTeams = { "[Players 0 & 2]", "[Players 1 & 3]"};
    private int nextStartingPlayer = random.nextInt(nbPlayers);

    private int[] scores = new int[nbPlayers];

    Font bigFont = new Font("Arial", Font.BOLD, 36);
    Font smallFont = new Font("Arial", Font.PLAIN, 10);

    // boolean[] humanPlayers = { true, false, false, false};
    boolean[] humanPlayers = { false, false, false, false};


    private void initScore() {
        for (int i = 0; i < nbPlayers; i++) {
             scores[i] = 0;
            String text = "P" + i + "-0";
            scoreActors[i] = new TextActor(text, Color.WHITE, bgColor, bigFont);
            addActor(scoreActors[i], scoreLocations[i]);
        }

        String text = "Attack: 0 - Defence: 0";
        for (int i = 0; i < pileTextActors.length; i++) {
            pileTextActors[i] = new TextActor(text, Color.WHITE, bgColor, smallFont);
            addActor(pileTextActors[i], pileStatusLocations[i]);
        }
    }

    private void updateScore(int player) {
        removeActor(scoreActors[player]);
        String text = "P" + player + "-" + scores[player];
        scoreActors[player] = new TextActor(text, Color.WHITE, bgColor, bigFont);
        addActor(scoreActors[player], scoreLocations[player]);
    }

    private void updateScores() {
        for (int i = 0; i < nbPlayers; i++) {
            updateScore(i);
        }
        System.out.println(playerTeams[0] + " score = " + scores[0] + "; " + playerTeams[1] + " score = " + scores[1]);
    }

    private Optional<Card> selected;
    private final int NON_SELECTION_VALUE = -1;
    private int selectedPileIndex = NON_SELECTION_VALUE;
    private final int UNDEFINED_INDEX = -1;
    private final int ATTACK_RANK_INDEX = 0;
    private final int DEFENCE_RANK_INDEX = 1;



    private void setupGame(Properties properties) {

        //initial with property file
        initProperties(properties);

        //Create player by PlayerFactory
        players = new PlayerType[nbPlayers];
        PlayerFactory playerFactory = PlayerFactory.getInstance();
        //create different types of player from property file
        for (int i = 0; i < nbPlayers; i++) {
            players[i] = playerFactory.getPlayer(i,properties.getProperty(String.format("players.%d", i)));
        }
        //give each player a deck as the original hand
        for (int i = 0; i < nbPlayers; i++) {
            players[i].setHand(new Hand(deck));
        }
        //deal out
        //dealingOut method also need to change
        dealingOut(hands, nbPlayers, nbStartCards);

        // get Hand
        for (int i = 0; i < nbPlayers; i++) {
            players[i].getHand().sort(Hand.SortType.SUITPRIORITY, true);
            System.out.println("hands[" + i + "]: " + canonical(players[i].getHand()));
        }


        // for human player
        for (int i=0; i< nbPlayers; i++) {
            // Set up human player for interaction
            final Hand currentHand = players[i].getHand();
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

        // graphics
        RowLayout[] layouts = new RowLayout[nbPlayers];
        for (int i = 0; i < nbPlayers; i++) {
            layouts[i] = new RowLayout(handLocations[i], handWidth);
            layouts[i].setRotationAngle(90 * i);
            players[i].getHand().setView(this, layouts[i]);
            players[i].getHand().draw();
        }
        // End graphics

        // end of change

        /*
        hands = new Hand[nbPlayers];
        for (int i = 0; i < nbPlayers; i++) {
            hands[i] = new Hand(deck);
        }
        dealingOut(hands, nbPlayers, nbStartCards);

        // get Hand
        for (int i = 0; i < nbPlayers; i++) {
            hands[i].sort(Hand.SortType.SUITPRIORITY, true);
            System.out.println("hands[" + i + "]: " + canonical(hands[i]));
        }

        // for human player
        for (final Hand currentHand : hands) {
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

        // graphics
        RowLayout[] layouts = new RowLayout[nbPlayers];
        for (int i = 0; i < nbPlayers; i++) {
            layouts[i] = new RowLayout(handLocations[i], handWidth);
            layouts[i].setRotationAngle(90 * i);
            hands[i].setView(this, layouts[i]);
            hands[i].draw();
        }
        */
        // End graphics
    }

    private void resetPile() {
        if (piles != null) {
            for (Hand pile : piles) {
                pile.removeAll(true);
            }
        }
        piles = new Hand[2];
        for (int i = 0; i < 2; i++) {
            piles[i] = new Hand(deck);
            piles[i].setView(this, new RowLayout(pileLocations[i], 8 * pileWidth));
            piles[i].draw();
            final Hand currentPile = piles[i];
            final int pileIndex = i;
            piles[i].addCardListener(new CardAdapter() {
                public void leftClicked(Card card) {
                    selectedPileIndex = pileIndex;
                    currentPile.setTouchEnabled(false);
                }
            });
        }

        updatePileRanks();
    }

    private void pickACorrectSuit(int playerIndex, boolean isCharacter) {


        //start of code change
        Hand currentHand = players[playerIndex].getHand();
        List<Card> shortListCards = new ArrayList<>();
        for (int i = 0; i < currentHand.getCardList().size(); i++) {
            Card card = currentHand.getCardList().get(i);
            Suit suit = (Suit) card.getSuit();
            if (suit.isCharacter() == isCharacter) {
                shortListCards.add(card);
            }
        }
        if (shortListCards.isEmpty() || !isCharacter && random.nextInt(3) == 0) {
            selected = Optional.empty();
        } else {
            selected = Optional.of(shortListCards.get(random.nextInt(shortListCards.size())));

        }

        //end of code change


        //Start of original code
        /*
        Hand currentHand = hands[playerIndex];
        List<Card> shortListCards = new ArrayList<>();
        for (int i = 0; i < currentHand.getCardList().size(); i++) {
            Card card = currentHand.getCardList().get(i);
            Suit suit = (Suit) card.getSuit();
            if (suit.isCharacter() == isCharacter) {
                shortListCards.add(card);
            }
        }
        if (shortListCards.isEmpty() || !isCharacter && random.nextInt(3) == 0) {
            selected = Optional.empty();
        } else {
            selected = Optional.of(shortListCards.get(random.nextInt(shortListCards.size())));
        }

         */
        //end of original code
    }

    private void selectRandomPile() {
        selectedPileIndex = random.nextInt(2);
    }

    private void waitForCorrectSuit(int playerIndex, boolean isCharacter) {
        if (hands[playerIndex].isEmpty()) {
            selected = Optional.empty();
        } else {
            selected = null;
            hands[playerIndex].setTouchEnabled(true);
            do {
                if (selected == null) {
                    delay(100);
                    continue;
                }
                Suit suit = selected.isPresent() ? (Suit) selected.get().getSuit() : null;
                if (isCharacter && suit != null && suit.isCharacter() ||         // If we want character, can't pass and suit must be right
                        !isCharacter && (suit == null || !suit.isCharacter())) { // If we don't want character, can pass or suit must not be character
                    // if (suit != null && suit.isCharacter() == isCharacter) {
                    break;
                } else {
                    selected = null;
                    hands[playerIndex].setTouchEnabled(true);
                }
                delay(100);
            } while (true);
        }
    }

    private void waitForPileSelection() {
        selectedPileIndex = NON_SELECTION_VALUE;
        for (Hand pile : piles) {
            pile.setTouchEnabled(true);
        }
        while(selectedPileIndex == NON_SELECTION_VALUE) {
            delay(100);
        }
        for (Hand pile : piles) {
            pile.setTouchEnabled(false);
        }
    }

    private int[] calculatePileRanks(int pileIndex) {
        Hand currentPile = piles[pileIndex];

        Heart attackHeart = new Heart(0);
        Heart defenceHeart = new Heart(0);


        // empty pile
        if (currentPile.isEmpty()) {
            return new int[] { attackHeart.value(), defenceHeart.value() };
        }

        // otherwise, more than one card
        else {

            // starts with character card
            int characterValue = ((Rank) currentPile.get(0).getRank()).getRankValue();
            attackHeart.setValue(characterValue);
            defenceHeart.setValue(characterValue);

            int i = 1;

            while (i < currentPile.getNumberOfCards()) {

                Suit checkedCard = (Suit) currentPile.get(i).getSuit();
                int checkedCardValue = ((Rank) currentPile.get(i).getRank()).getRankValue();
                int previousCardValue = ((Rank) currentPile.get(i - 1).getRank()).getRankValue();

                // double value
                checkedCardValue = canDoubleValue(checkedCardValue, previousCardValue);

                // adjust attack score
                if (checkedCard.isAttack()) {
                    Club attack = new Club(attackHeart, checkedCardValue);
                    attackHeart.setValue(attack.value());
                    int[] result = checkForDiamond(i, currentPile, attackHeart);
                    i = result[0];
                    attackHeart.setValue(result[1]);

                // adjust defence score
                } else if (checkedCard.isDefence()) {
                    Spade defence = new Spade(defenceHeart, checkedCardValue);
                    defenceHeart.setValue(defence.value());
                    int[] result = checkForDiamond(i, currentPile, defenceHeart);
                    i = result[0];
                    defenceHeart.setValue(result[1]);
                }
                i++;
            }
        }

        if (attackHeart.value() < 0) {
            attackHeart.setValue(0);
        }
        if (defenceHeart.value() < 0) {
            defenceHeart.setValue(0);
        }
        return new int[] { attackHeart.value(), defenceHeart.value() };
    }

    private int canDoubleValue(int checkedCardValue, int previousCardValue) {
        if (checkedCardValue == previousCardValue) {
            checkedCardValue *= 2;
        }
        return checkedCardValue;
    }

    private int[] checkForDiamond(int i, Hand currentPile, Heart heart) {
        int j;
        for (j = i + 1; j < currentPile.getNumberOfCards(); j++) {
            Suit checkForDiamond = (Suit) currentPile.get(j).getSuit();

            if (checkForDiamond.isMagic()) {
                int checkForDiamondValue = ((Rank) currentPile.get(j).getRank()).getRankValue();
                int prev = ((Rank) currentPile.get(j-1).getRank()).getRankValue();
                checkForDiamondValue = canDoubleValue(checkForDiamondValue, prev);
                heart.setValue(new Diamond(heart, checkForDiamondValue).value());

            } else {
                break;
            }
        }
        i = j - 1;
        return new int[] {i, heart.value()};
    }

    private void updatePileRankState(int pileIndex, int attackRank, int defenceRank) {
        TextActor currentPile = (TextActor) pileTextActors[pileIndex];
        removeActor(currentPile);
        String text = playerTeams[pileIndex] + " Attack: " + attackRank + " - Defence: " + defenceRank;
        pileTextActors[pileIndex] = new TextActor(text, Color.WHITE, bgColor, smallFont);
        addActor(pileTextActors[pileIndex], pileStatusLocations[pileIndex]);
    }

    private void updatePileRanks() {
        for (int j = 0; j < piles.length; j++) {
            int[] ranks = calculatePileRanks(j);
            updatePileRankState(j, ranks[ATTACK_RANK_INDEX], ranks[DEFENCE_RANK_INDEX]);
        }
    }

    private int getPlayerIndex(int index) {
        return index % nbPlayers;
    }

    private void executeAPlay() {
        resetPile();

        nextStartingPlayer = getPlayerIndex(nextStartingPlayer);
        if (players[nextStartingPlayer].getHand().getNumberOfCardsWithSuit(Suit.HEARTS) == 0)
            nextStartingPlayer = getPlayerIndex(nextStartingPlayer + 1);
        assert players[nextStartingPlayer].getHand().getNumberOfCardsWithSuit(Suit.HEARTS) != 0 : " Starting player has no hearts.";
        //Original
        /*
        if (hands[nextStartingPlayer].getNumberOfCardsWithSuit(Suit.HEARTS) == 0)
            nextStartingPlayer = getPlayerIndex(nextStartingPlayer + 1);
        assert hands[nextStartingPlayer].getNumberOfCardsWithSuit(Suit.HEARTS) != 0 : " Starting player has no hearts.";

         */
        //end

        // 1: play the first 2 hearts
        for (int i = 0; i < 2; i++) {
            int playerIndex = getPlayerIndex(nextStartingPlayer + i);
            setStatusText("Player " + playerIndex + " select a Heart card to play");
            if (humanPlayers[playerIndex]) {
                waitForCorrectSuit(playerIndex, true);
            } else {
                selected = players[playerIndex].correctSuit(players[playerIndex].getHand(), true);
                //pickACorrectSuit(playerIndex, true);
            }

            int pileIndex = playerIndex % 2;
            assert selected.isPresent() : " Pass returned on selection of character.";
            System.out.println("Player " + playerIndex + " plays " + canonical(selected.get()) + " on pile " + pileIndex);
            selected.get().setVerso(false);
            selected.get().transfer(piles[pileIndex], true); // transfer to pile (includes graphic effect)
            updatePileRanks();
        }

        // 2: play the remaining nbPlayers * nbRounds - 2
        int remainingTurns = nbPlayers * nbRounds - 2;
        int nextPlayer = nextStartingPlayer + 2;

        while(remainingTurns > 0) {
            nextPlayer = getPlayerIndex(nextPlayer);
            setStatusText("Player" + nextPlayer + " select a non-Heart card to play.");
            if (humanPlayers[nextPlayer]) {
                waitForCorrectSuit(nextPlayer, false);
            } else {
                selected = players[nextPlayer].correctSuit(players[nextPlayer].getHand(), false);
                //pickACorrectSuit(nextPlayer, false);
            }

            if (selected.isPresent()) {
                setStatusText("Selected: " + canonical(selected.get()) + ". Player" + nextPlayer + " select a pile to play the card.");
                if (humanPlayers[nextPlayer]) {
                    waitForPileSelection();
                } else {
                    selectRandomPile();
                }
                System.out.println("Player " + nextPlayer + " plays " + canonical(selected.get()) + " on pile " + selectedPileIndex);

                selected.get().setVerso(false);
                selected.get().transfer(piles[selectedPileIndex], true); // transfer to pile (includes graphic effect)
                updatePileRanks();
            } else {
                setStatusText("Pass.");
            }
            nextPlayer++;
            remainingTurns--;
        }

        // 3: calculate winning & update scores for players
        updatePileRanks();
        int[] pile0Ranks = calculatePileRanks(0);
        int[] pile1Ranks = calculatePileRanks(1);
        System.out.println("piles[0]: " + canonical(piles[0]));
        System.out.println("piles[0] is " + "Attack: " + pile0Ranks[ATTACK_RANK_INDEX] + " - Defence: " + pile0Ranks[DEFENCE_RANK_INDEX]);
        System.out.println("piles[1]: " + canonical(piles[1]));
        System.out.println("piles[1] is " + "Attack: " + pile1Ranks[ATTACK_RANK_INDEX] + " - Defence: " + pile1Ranks[DEFENCE_RANK_INDEX]);
        Rank pile0CharacterRank = (Rank) piles[0].getCardList().get(0).getRank();
        Rank pile1CharacterRank = (Rank) piles[1].getCardList().get(0).getRank();
        String character0Result;
        String character1Result;

        if (pile0Ranks[ATTACK_RANK_INDEX] > pile1Ranks[DEFENCE_RANK_INDEX]) {
            scores[0] += pile1CharacterRank.getRankValue();
            scores[2] += pile1CharacterRank.getRankValue();
            character0Result = "Character 0 attack on character 1 succeeded.";
        } else {
            scores[1] += pile1CharacterRank.getRankValue();
            scores[3] += pile1CharacterRank.getRankValue();
            character0Result = "Character 0 attack on character 1 failed.";
        }

        if (pile1Ranks[ATTACK_RANK_INDEX] > pile0Ranks[DEFENCE_RANK_INDEX]) {
            scores[1] += pile0CharacterRank.getRankValue();
            scores[3] += pile0CharacterRank.getRankValue();
            character1Result = "Character 1 attack on character 0 succeeded.";
        } else {
            scores[0] += pile0CharacterRank.getRankValue();
            scores[2] += pile0CharacterRank.getRankValue();
            character1Result = "Character 1 attack character 0 failed.";
        }
        updateScores();
        System.out.println(character0Result);
        System.out.println(character1Result);
        setStatusText(character0Result + " " + character1Result);

        // 5: discarded all cards on the piles
        nextStartingPlayer += 1;
        delay(watchingTime);
    }

    public GameOfThrones(Properties properties) {
        super(700, 700, 30);

        setTitle("Game of Thrones (V" + version + ") Constructed for UofM SWEN30006 with JGameGrid (www.aplu.ch)");
        setStatusText("Initializing...");
        initScore();

        setupGame(properties);
        for (int i = 0; i < nbPlays; i++) {
            executeAPlay();
            updateScores();
        }

        String text;
        if (scores[0] > scores[1]) {
            text = "Players 0 and 2 won.";
        } else if (scores[0] == scores[1]) {
            text = "All players drew.";
        } else {
            text = "Players 1 and 3 won.";
        }
        System.out.println("Result: " + text);
        setStatusText(text);

        refresh();
    }

    //Put this in setUp method
    private void initProperties(Properties properties){
        this.seed = Integer.parseInt(properties.getProperty("seed"));
        this.random = new Random(seed);
        this.watchingTime = Integer.parseInt(properties.getProperty("watchingTime"));
        this.nextStartingPlayer = random.nextInt(nbPlayers);
    }

    public static void main(String[] args) {
         System.out.println("Working Directory = " + System.getProperty("user.dir"));
        Properties properties = new Properties();
        if (args == null || args.length == 0) {
            properties = PropertiesLoader.loadPropertiesFile(DEFAULT_PATH);
        } else {
            properties = PropertiesLoader.loadPropertiesFile(args[0]);
        }
        GameOfThrones.seed = Integer.parseInt(properties.getProperty("seed"));
        GameOfThrones.random = new Random(seed);
        new GameOfThrones(properties);//check later
    }

}
