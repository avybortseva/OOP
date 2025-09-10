package ru.nsu.g.a.vybortseva.game;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CardTest {

    @Test
    void getRank() {
        Card three = new Card(Card.Suit.HEARTS, Card.Rank.THREE);
        assertEquals("Тройка", three.getRank().getRusName());
        assertEquals(3, three.getRank().getBaseValue());
    }

    @Test
    void isHiddenDefault() {
        Card three = new Card(Card.Suit.HEARTS, Card.Rank.THREE);
        assertFalse(three.isHidden());
    }

    @Test
    void isHiddenSet() {
        Card three = new Card(Card.Suit.HEARTS, Card.Rank.THREE);
        three.setHidden(true);
        assertTrue(three.isHidden());
    }

    @Test
    void setHidden() {
        Card three = new Card(Card.Suit.HEARTS, Card.Rank.THREE);
        three.setHidden(true);
        assertTrue(three.isHidden());
    }

    @Test
    void getBaseValue() {
        Card four = new Card(Card.Suit.CLUBS, Card.Rank.FOUR);
        assertEquals(4, four.getBaseValue());
    }

    @Test
    void testToStringFalse() {
        Card four = new Card(Card.Suit.CLUBS, Card.Rank.FOUR);
        assertEquals("Четверка Трефы ♧ (4)", four.toString());
    }

    @Test
    void testToStringTrue() {
        Card three = new Card(Card.Suit.HEARTS, Card.Rank.THREE);
        three.setHidden(true);
        assertEquals("<закрытая карта>", three.toString());
    }
}