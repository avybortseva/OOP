package ru.nsu.g.a.vybortseva.game;

import java.util.ArrayList;
import java.util.List;

/**
 * The class for a hand of a participant.
 */
public class Hand {
    private List<Card> cards;
    public static final int blackjackEnd = 21;

    /**
     * The constructor for a hand.
     */
    public Hand() {
        cards = new ArrayList<Card>();
    }

    /**
     * The method equals.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Hand hand = (Hand) obj;
        if (cards.size() != hand.cards.size()) return false;

        for (int i = 0; i < cards.size(); i++) {
            Card thisCard = cards.get(i);
            Card otherCard = hand.cards.get(i);

            if (!thisCard.getSuit().equals(otherCard.getSuit()) ||
                    !thisCard.getRank().equals(otherCard.getRank()) ||
                    thisCard.isHidden() != otherCard.isHidden()) {
                return false;
            }
        }
        return true;
    }

    /**
     * The method for adding card.
     */
    public void addCard(Card card) {
        cards.add(card);
    }



    /**
     * The method for getting one card.
     */
    public Card getCard(int index) {
        if (index >= 0 && index < cards.size()) {
            return cards.get(index);
        }
        return null;
    }

    /**
     * The method for getting points.
     */
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

        while (points > blackjackEnd && aces > 0) {
            points -= 10;
            aces--;
        }

        return points;
    }

    /**
     * The method for clearing hand.
     */
    public void clearHand() {
        cards.clear();
    }

    /**
     * The method for getting countCards.
     */
    public int getCountCards() {
        return cards.size();
    }

    /**
     * The method for formating hand.
     */
    public String toString(boolean showAll) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < cards.size(); i++) {
            if (i > 0) {
                sb.append(", ");
            }
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