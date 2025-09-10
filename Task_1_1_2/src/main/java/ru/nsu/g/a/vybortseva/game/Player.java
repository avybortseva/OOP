package ru.nsu.g.a.vybortseva.game;

public class Player extends Participant{

    @Override
    public void addCard(Card card) {
        card.setHidden(false);
        super.addCard(card);
    }
}
