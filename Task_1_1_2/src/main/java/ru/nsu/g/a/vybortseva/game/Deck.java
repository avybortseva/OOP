package ru.nsu.g.a.vybortseva.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The class for deck.
 */
public class Deck {
    private List<Card> cards;
    private final int countDecks = 1;

    /**
     * The constructor for deck.
     */
    public Deck() {
        initializeDeck();
        shuffle();
    }

    /**
     * The method for initializing deck.
     */
    public void initializeDeck() {
        cards = new ArrayList<Card>();
        for (int i = 0; i < countDecks; i++) {
            for (Card.Suit suit : Card.Suit.values()) {
                for (Card.Rank rank : Card.Rank.values()) {
                    cards.add(new Card(suit, rank));
                }
            }
        }
    }

    /**
     * The method for shuffling.
     */
    public void shuffle() {
        Collections.shuffle(cards);
    }

    /**
     * The method for drawing a new card.
     */
    public Card drawCard() {
        if (cards.isEmpty()){
            initializeDeck();
            shuffle();
        }
        return cards.remove(cards.size() - 1);
    }

    /**
     * The method for getting count of cards.
     */
    public int remainingCards() {
        return cards.size();
    }
}
