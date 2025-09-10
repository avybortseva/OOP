package ru.nsu.g.a.vybortseva.blackjack;

import java.util.ArrayList;
import java.util.List;


public class Hand {
    private List<Card> cards;

    public Hand() {
        cards = new ArrayList<Card>();
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    public List<Card> getCards() {
        return cards;
    }

    public int getPoints() {
        int points = 0;
        int aces = 0;

        for (Card card : cards) {
            if (!card.isHidden()) {
                if (card.getRank() == Card.Rank.ACE) {
                    aces++;
                    points += 11;
                } else {
                    points += card.getBaseValue();
                }
            }
        }

        while (points > 21 && aces > 0) {
            points -= 10;
            aces--;
        }

        return points;
    }

    public void clearHand(){
        cards.clear();
    }

    public int getCountCards() {
        return cards.size();
    }

    public String toString(boolean showAll) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < cards.size(); i++) {
            if (i > 0) sb.append(", ");
            if (showAll || !cards.get(i).isHidden()) {
                sb.append(cards.get(i).toString());
            } else {
                sb.append("<закрытая карта>");
            }
        }
        sb.append("]");
        return sb.toString();
    }
}