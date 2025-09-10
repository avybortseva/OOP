package ru.nsu.g.a.vybortseva.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    private Player player;
    private Deck deck;

    @BeforeEach
    void setUp() {
        player = new Player();
        deck = new Deck();
    }

    @Test
    void addCardVisible() {
        Card card = deck.drawCard();

        card.setHidden(true);
        assertTrue(card.isHidden());

        player.addCard(card);
        assertFalse(card.isHidden());

        assertEquals(1, player.getHand().getCountCards());
        assertEquals(card, player.getHand().getCards().getFirst());
    }
}