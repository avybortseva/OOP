package ru.nsu.g.a.vybortseva.blackjack;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DealerTest {

    private Dealer dealer;
    private Deck deck;

    @BeforeEach
    void setUp() {
        dealer = new Dealer();
        deck = new Deck();
    }

    @Test
    void revealHiddenCard() {
        Card hiddenCard = new Card(Card.Suit.HEARTS, Card.Rank.ACE);
        hiddenCard.setHidden(true);
        Card visibleCard = new Card(Card.Suit.SPADES, Card.Rank.KING);
        visibleCard.setHidden(false);

        dealer.addCard(hiddenCard);
        dealer.addCard(visibleCard);

        dealer.revealHiddenCard();

        assertFalse(hiddenCard.isHidden());
        assertFalse(visibleCard.isHidden());
    }

    @Test
    void shouldHit() {
        Card lowCard = new Card(Card.Suit.HEARTS, Card.Rank.SIX);
        Card highCard = new Card(Card.Suit.SPADES, Card.Rank.KING);

        dealer.addCard(lowCard);
        assertTrue(dealer.shouldHit());

        dealer.addCard(highCard);
        assertTrue(dealer.shouldHit());

        Card ace = new Card(Card.Suit.DIAMONDS, Card.Rank.ACE);
        dealer.addCard(ace);

        assertFalse(dealer.shouldHit());
    }

    @Test
    void addCardFirstCardHidden() {
        dealer.getHand().clearHand();
        Card firstCard = deck.drawCard();
        dealer.addCard(firstCard);

        assertEquals(1, dealer.getHand().getCountCards());
        assertTrue(firstCard.isHidden());

    }
}