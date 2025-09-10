package ru.nsu.g.a.vybortseva.blackjack;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.*;

class BlackJackGameTest {
    private BlackJackGame game;
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    @BeforeEach
    void setUp() {
        game = new BlackJackGame();
        System.setOut(new PrintStream(outputStream));
    }

    @Test
    void testInitializeGameCreatesObjects() {
        game.initializeGame();

        assertNotNull(game.getDeck(), "Колода должна быть создана.");
        assertNotNull(game.getPlayer(), "Игрок должен быть создан.");
        assertNotNull(game.getDealer(), "Дилер должен быть создан.");
        assertEquals(0, game.getRoundNumber(), "Номер раунда должен начинаться с нуля.");
    }

    @Test
    void testGetPlayerChoiceValidInput() {
        String input = "1\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        game.initializeGame();
        int choice = game.getPlayerChoice();

        assertEquals(1, choice, "Должен вернуть 1 при корректном вводе.");
    }

    @Test
    void testGetPlayerChoiceInvalidThenValidInput() {
        String input = "5\n0\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        game.initializeGame();
        int choice = game.getPlayerChoice();

        assertEquals(0, choice, "Должен вернуть 0 после некорректного ввода.");

        String output = outputStream.toString();
        assertTrue(output.contains("Некорректный ввод"), "Должно отображаться сообщение об ошибке при некорректном вводе.");
    }

    @Test
    void testDetermineRoundWinnerPlayerWinsByPoints() {
        game.initializeGame();
        game.getPlayer().addCard(new Card(Card.Suit.HEARTS, Card.Rank.TEN));
        game.getPlayer().addCard(new Card(Card.Suit.DIAMONDS, Card.Rank.TEN));

        game.getDealer().addCard(new Card(Card.Suit.SPADES, Card.Rank.TEN));
        game.getDealer().addCard(new Card(Card.Suit.CLUBS, Card.Rank.EIGHT));

        int playerScoreBefore = game.getPlayer().getScore();
        int dealerScoreBefore = game.getDealer().getScore();

        game.determineRoundWinner();

        assertEquals(playerScoreBefore + 1, game.getPlayer().getScore());
        assertEquals(dealerScoreBefore, game.getDealer().getScore());
    }

    @Test
    void testDetermineRoundWinnerDealerWinsByPoints() {
        game.initializeGame();

        game.getPlayer().clearHand();
        game.getDealer().clearHand();

        game.getPlayer().addCard(new Card(Card.Suit.HEARTS, Card.Rank.TEN));
        game.getPlayer().addCard(new Card(Card.Suit.DIAMONDS, Card.Rank.EIGHT));

        game.getDealer().addCard(new Card(Card.Suit.SPADES, Card.Rank.TEN));
        game.getDealer().addCard(new Card(Card.Suit.CLUBS, Card.Rank.TEN));
        game.getDealer().revealHiddenCard();

        game.determineRoundWinner();

        assertEquals(1, game.getDealer().getScore());
        assertEquals(0, game.getPlayer().getScore());

    }

    @Test
    void testDetermineRoundWinner_Tie() {
        game.initializeGame();

        game.getPlayer().clearHand();
        game.getDealer().clearHand();

        game.getPlayer().addCard(new Card(Card.Suit.HEARTS, Card.Rank.TEN));
        game.getPlayer().addCard(new Card(Card.Suit.DIAMONDS, Card.Rank.TEN));

        game.getDealer().addCard(new Card(Card.Suit.SPADES, Card.Rank.TEN));
        game.getDealer().addCard(new Card(Card.Suit.CLUBS, Card.Rank.TEN));
        game.getDealer().revealHiddenCard();

        int playerScoreBefore = game.getPlayer().getScore();
        int dealerScoreBefore = game.getDealer().getScore();

        game.determineRoundWinner();

        assertEquals(playerScoreBefore, game.getPlayer().getScore());
        assertEquals(dealerScoreBefore, game.getDealer().getScore());
    }

    @Test
    void testPlayerTurn_PlayerStops() {
        String input = "0\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        game.initializeGame();
        game.playerTurn();

        String output = outputStream.toString();
        assertTrue(output.contains("Вы остановились"), "Нужно показывать сообщение об остановке");
    }

    @Test
    void testDealerTurn_DealerTakesCards() {
        game.initializeGame();

        game.getDealer().addCard(new Card(Card.Suit.HEARTS, Card.Rank.TWO));
        game.getDealer().addCard(new Card(Card.Suit.DIAMONDS, Card.Rank.THREE));

        int initialHandSize = game.getDealer().getHand().getCountCards();

        game.dealerTurn();

        assertTrue(game.getDealer().getHand().getCountCards() > initialHandSize,
                "Дилер должен взять хотя бы одну карту");
    }

    @Test
    void testDealerTurn_DealerStops() {
        game.initializeGame();

        game.getDealer().addCard(new Card(Card.Suit.HEARTS, Card.Rank.TEN));
        game.getDealer().addCard(new Card(Card.Suit.DIAMONDS, Card.Rank.TEN));

        int initialHandSize = game.getDealer().getHand().getCountCards();

        game.dealerTurn();

        assertEquals(initialHandSize, game.getDealer().getHand().getCountCards(),
                "Дилер не должен брать карты при 20 очках");
    }

    @Test
    void testPrintScore() {
        game.initializeGame();

        for (int i = 0; i < 3; i++) game.getPlayer().incrementScore();
        for (int i = 0; i < 2; i++) game.getDealer().incrementScore();

        game.printScore();

        String output = outputStream.toString();
        assertTrue(output.contains("3:2"), "Должен показывать счет 3:2");
    }
}