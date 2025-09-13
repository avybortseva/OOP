package ru.nsu.g.a.vybortseva.game;

/**
 * The class for a player.
 */
public class Player extends Participant {

    /**
     * The method for adding card.
     */
    @Override
    public void addCard(Card card) {
        card.setHidden(false);
        super.addCard(card);
    }
}
