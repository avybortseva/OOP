package ru.nsu.g.a.vybortseva.game;

public class Participant {

    private final Hand hand;
    private int score;

    public Participant() {
        this.hand = new Hand();
        this.score = 0;
    }

    public Hand getHand() {
        return hand;
    }

    public void addCard(Card card){
        hand.addCard(card);
    }

    public void clearHand(){
        hand.clearHand();
    }

    public boolean hasBlackJack(){
        return hand.getCountCards() == 2 && getHandValue() == 21;
    }


    public int getHandValue() {
        return hand.getPoints();
    }

    public int getScore() {
        return score;
    }

    public void incrementScore() {
        this.score = score + 1;
    }

    public void resetScore() {
        score = 0;
    }

    public boolean isBusted(){
        return hand.getPoints() > 21;
    }


    public String showHand(boolean showAll) {
        return hand.toString(showAll);
    }
}
