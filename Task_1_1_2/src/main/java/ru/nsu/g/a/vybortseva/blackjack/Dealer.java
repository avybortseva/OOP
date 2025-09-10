package ru.nsu.g.a.vybortseva.blackjack;

public class Dealer extends Participant{

    public void revealHiddenCard(){
        for (Card card : getHand().getCards()){
            card.setHidden(false);
        }
    }

    public boolean shouldHit(){
        return getHandValue() < 17;
    }

    @Override
    public void addCard(Card card) {
        int currentHandSize = getHand().getCountCards();

        card.setHidden(currentHandSize == 0);

        super.addCard(card);
    }
}
