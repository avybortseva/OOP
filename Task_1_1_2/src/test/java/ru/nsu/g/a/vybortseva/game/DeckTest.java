package ru.nsu.g.a.vybortseva.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DeckTest {

    private Deck deck;

    @BeforeEach
    void setUp() {
        deck = new Deck();
    }

    @Test
    void initializeDeck() {
        deck.initializeDeck();

        assertEquals(52, deck.remainingCards());
    }

    @Test
    void shuffle() {
    }

    @Test
    void drawCard() {
        int initialCount = deck.remainingCards();
        Card card = deck.drawCard();

        assertNotNull(card);
        assertEquals(initialCount - 1, deck.remainingCards());

        assertNotNull(card.getSuit());
        assertNotNull(card.getRank());
        assertFalse(card.isHidden());
    }
}