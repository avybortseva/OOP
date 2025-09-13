package ru.nsu.g.a.vybortseva.game;

/**
 * The method for a participant.
 */
public class Participant {

    private final Hand hand;
    private int score;

    /**
     * The constructor for a participant.
     */
    public Participant() {
        this.hand = new Hand();
        this.score = 0;
    }

    /**
     * The method for getting hand.
     */
    public Hand getHand() {
        return hand;
    }

    /**
     * The method for adding card.
     */
    public void addCard(Card card) {
        hand.addCard(card);
    }

    /**
     * The method for clearing hand.
     */
    public void clearHand() {
        hand.clearHand();
    }

    /**
     * The method for checking if a participant has blackjack.
     */
    public boolean hasBlackJack() {
        return hand.getCountCards() == 2 && getHandValue() == Hand.blackjackEnd;
    }

    /**
     * The method for getting points of a hand.
     */
    public int getHandValue() {
        return hand.getPoints();
    }

    /**
     * The method for getting score.
     */
    public int getScore() {
        return score;
    }

    /**
     * The method for incrementing score.
     */
    public void incrementScore() {
        this.score = score + 1;
    }

    /**
     * The method for removing score.
     */
    public void resetScore() {
        score = 0;
    }

    /**
     * The method for checking if a participant is busted.
     */
    public boolean isBusted() {
        return hand.getPoints() > Hand.blackjackEnd;
    }


    /**
     * The method for showing cards.
     */
    public String showHand(boolean showAll) {
        return hand.toString(showAll);
    }
}
