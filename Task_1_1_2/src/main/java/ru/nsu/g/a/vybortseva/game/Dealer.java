package ru.nsu.g.a.vybortseva.game;

/**
 * The class for dealer.
 */
public class Dealer extends Participant {

    /**
     * The method for revealing hiddenCard.
     */
    public void revealHiddenCard() {
        for (int i = 0; i < getHand().getCountCards(); i++) {
            Card card = getHand().getCard(i);
            card.setHidden(false);
        }
    }

    /**
     * The method for dealer if he should hit.
     */
    public boolean shouldHit() {
        return getHandValue() < 17;
    }

    /**
     * The method for adding card.
     */
    @Override
    public void addCard(Card card) {
        int currentHandSize = getHand().getCountCards();

        card.setHidden(currentHandSize == 0);

        super.addCard(card);
    }
}
