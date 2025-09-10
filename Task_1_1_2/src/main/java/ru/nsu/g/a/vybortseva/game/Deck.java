package ru.nsu.g.a.vybortseva.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
    private List<Card> cards;
    private final int countDecks = 1;

    public Deck() {
        initializeDeck();
        shuffle();
    }

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

    public void shuffle() {
        Collections.shuffle(cards);
    }

    public Card drawCard(){
        if (cards.isEmpty()){
            initializeDeck();
            shuffle();
        }
        return cards.removeLast();
    }

    public int remainingCards() {
        return cards.size();
    }
}
