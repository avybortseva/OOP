package ru.nsu.g.a.vybortseva.game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
        assertEquals(card, player.getHand().getCards().get(0));
    }
}