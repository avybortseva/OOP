package ru.nsu.g.a.vybortseva.game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ParticipantTest {

    private Participant participant;
    private Deck deck;

    @BeforeEach
    void setUp() {
        participant = new Participant();
        deck = new Deck();
    }

    @Test
    void getHand() {
        assertNotNull(participant.getHand());
        assertEquals(0, participant.getHand().getCountCards());
    }

    @Test
    void addCard() {
        Card card = deck.drawCard();
        participant.addCard(card);

        assertEquals(1, participant.getHand().getCountCards());
        assertEquals(card, participant.getHand().getCards().get(0));
    }

    @Test
    void clearHand() {
        participant.addCard(deck.drawCard());
        participant.addCard(deck.drawCard());
        assertEquals(2, participant.getHand().getCountCards());

        participant.clearHand();
        assertEquals(0, participant.getHand().getCountCards());
    }

    @Test
    void hasBlackJackTrue() {
        Card ace = new Card(Card.Suit.HEARTS, Card.Rank.ACE);
        Card king = new Card(Card.Suit.SPADES, Card.Rank.KING);

        participant.addCard(ace);
        participant.addCard(king);

        assertTrue(participant.hasBlackJack());
        assertEquals(21, participant.getHandValue());
    }

    @Test
    void hasBlackJackFalse() {
        Card seven = new Card(Card.Suit.HEARTS, Card.Rank.SEVEN);
        Card eight = new Card(Card.Suit.SPADES, Card.Rank.EIGHT);
        Card six = new Card(Card.Suit.DIAMONDS, Card.Rank.SIX);

        participant.addCard(seven);
        participant.addCard(eight);
        participant.addCard(six);

        assertFalse(participant.hasBlackJack());
        assertEquals(21, participant.getHandValue());
    }

    @Test
    void getHandValue() {
        Card two = new Card(Card.Suit.HEARTS, Card.Rank.TWO);
        Card three = new Card(Card.Suit.SPADES, Card.Rank.THREE);

        participant.addCard(two);
        participant.addCard(three);

        assertEquals(5, participant.getHandValue());
    }

    @Test
    void getHandValueWithAces() {
        Card ace1 = new Card(Card.Suit.HEARTS, Card.Rank.ACE);
        Card nine = new Card(Card.Suit.DIAMONDS, Card.Rank.NINE);

        participant.addCard(ace1);
        participant.addCard(nine);
        assertEquals(20, participant.getHandValue());


        Card ace2 = new Card(Card.Suit.SPADES, Card.Rank.ACE);
        participant.addCard(ace2);
        assertEquals(21, participant.getHandValue());
    }

    @Test
    void getScore() {
        assertEquals(0, participant.getScore());

        participant.incrementScore();
        assertEquals(1, participant.getScore());

        participant.incrementScore();
        assertEquals(2, participant.getScore());
    }

    @Test
    void incrementScore() {
        assertEquals(0, participant.getScore());

        participant.incrementScore();
        assertEquals(1, participant.getScore());

        participant.incrementScore();
        assertEquals(2, participant.getScore());
    }

    @Test
    void resetScore() {
        participant.incrementScore();
        participant.incrementScore();
        assertEquals(2, participant.getScore());

        participant.resetScore();
        assertEquals(0, participant.getScore());
    }


    @Test
    void isBusted() {
        Card ten = new Card(Card.Suit.HEARTS, Card.Rank.TEN);
        Card five = new Card(Card.Suit.SPADES, Card.Rank.FIVE);

        participant.addCard(ten);
        participant.addCard(five);
        assertFalse(participant.isBusted());

        Card anotherTen = new Card(Card.Suit.DIAMONDS, Card.Rank.TEN);
        participant.addCard(anotherTen);
        assertTrue(participant.isBusted());
    }

    @Test
    void isBustedWithAces() {
        Card ace = new Card(Card.Suit.HEARTS, Card.Rank.ACE);
        Card nine = new Card(Card.Suit.SPADES, Card.Rank.NINE);
        Card eight = new Card(Card.Suit.DIAMONDS, Card.Rank.EIGHT);

        participant.addCard(ace);
        participant.addCard(nine);
        participant.addCard(eight);

        assertFalse(participant.isBusted());
        assertEquals(18, participant.getHandValue());
    }

    @Test
    void showHand() {
        Card card1 = new Card(Card.Suit.HEARTS, Card.Rank.ACE);
        Card card2 = new Card(Card.Suit.SPADES, Card.Rank.KING);

        participant.addCard(card1);
        participant.addCard(card2);

        String handString = participant.showHand(true);
        assertTrue(handString.contains("Туз"));
        assertTrue(handString.contains("Король"));
        assertTrue(handString.startsWith("["));
        assertTrue(handString.endsWith("]"));
    }

    @Test
    void showHandHiddenCards() {
        Card hiddenCard = new Card(Card.Suit.HEARTS, Card.Rank.ACE);
        hiddenCard.setHidden(true);
        Card visibleCard = new Card(Card.Suit.SPADES, Card.Rank.KING);
        visibleCard.setHidden(false);

        participant.addCard(hiddenCard);
        participant.addCard(visibleCard);

        String handString = participant.showHand(false);
        assertTrue(handString.contains("<закрытая карта>"));
        assertTrue(handString.contains("Король"));

        hiddenCard.setHidden(false);
        String handStringAll = participant.showHand(true);
        assertTrue(handStringAll.contains("Туз"));
        assertTrue(handStringAll.contains("Король"));
    }
}