package ru.nsu.g.a.vybortseva.game;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HandTest {

    @Test
    void addCard() {
        Card card = new Card(Card.Suit.HEARTS, Card.Rank.THREE);
        Hand hand = new Hand();
        hand.addCard(card);
        assertEquals("Тройка Червы ♡ (3)", hand.getCards().getLast().toString());
    }

    @Test
    void getCardsEmpty() {
        Hand hand = new Hand();
        List<Card> expectHand = new ArrayList<Card>();
        assertEquals(expectHand, hand.getCards());
    }

    @Test
    void getCardsOne() {
        Hand hand = new Hand();
        Card card1 = new Card(Card.Suit.HEARTS, Card.Rank.THREE);

        List<Card> expectHand = new ArrayList<Card>();
        expectHand.add(card1);

        hand.addCard(card1);

        assertEquals(expectHand, hand.getCards());
    }

    @Test
    void getCardsTwo() {
        Hand hand = new Hand();
        Card card1 = new Card(Card.Suit.HEARTS, Card.Rank.THREE);
        Card card2 = new Card(Card.Suit.CLUBS, Card.Rank.FOUR);

        List<Card> expectHand = new ArrayList<Card>();
        expectHand.add(card1);
        expectHand.add(card2);

        hand.addCard(card1);
        hand.addCard(card2);

        assertEquals(expectHand, hand.getCards());
    }

    @Test
    void getPointsEmpty() {
        Hand hand = new Hand();
        assertEquals(0, hand.getPoints());
    }

    @Test
    void getPointsOne() {
        Hand hand = new Hand();
        Card card1 = new Card(Card.Suit.HEARTS, Card.Rank.THREE);
        hand.addCard(card1);
        assertEquals(3, hand.getPoints());
    }

    @Test
    void getPointsTwo() {
        Hand hand = new Hand();
        Card card1 = new Card(Card.Suit.HEARTS, Card.Rank.THREE);
        Card card2 = new Card(Card.Suit.CLUBS, Card.Rank.FOUR);

        hand.addCard(card1);
        hand.addCard(card2);

        assertEquals(7, hand.getPoints());
    }

    @Test
    void getCountCardsEmpty() {
        Hand hand = new Hand();
        assertEquals(0, hand.getCountCards());
    }

    @Test
    void getCountCardsOne() {
        Hand hand = new Hand();
        Card card1 = new Card(Card.Suit.HEARTS, Card.Rank.THREE);
        hand.addCard(card1);
        assertEquals(1, hand.getCountCards());
    }

    @Test
    void getCountCardsTwo() {
        Hand hand = new Hand();
        Card card1 = new Card(Card.Suit.HEARTS, Card.Rank.THREE);
        Card card2 = new Card(Card.Suit.CLUBS, Card.Rank.FOUR);
        hand.addCard(card1);
        hand.addCard(card2);
        assertEquals(2, hand.getCountCards());
    }
}