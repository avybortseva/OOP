package ru.nsu.g.a.vybortseva.game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DeckTest {

    private Deck deck;

    @Test
    void initializeDeck() {
        Deck deck = new Deck();
        assertEquals(52, deck.remainingCards());
    }

    @Test
    void drawCard() {
        Deck deck = new Deck();
        int initialCount = deck.remainingCards();
        Card card = deck.drawCard();

        assertNotNull(card);
        assertEquals(initialCount - 1, deck.remainingCards());

        assertNotNull(card.getSuit());
        assertNotNull(card.getRank());
        assertFalse(card.isHidden());
    }
}