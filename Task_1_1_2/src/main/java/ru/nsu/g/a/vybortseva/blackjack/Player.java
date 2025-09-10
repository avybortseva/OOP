package ru.nsu.g.a.vybortseva.blackjack;

public class Player extends Participant{

    @Override
    public void addCard(Card card) {
        card.setHidden(false);
        super.addCard(card);
    }
}
