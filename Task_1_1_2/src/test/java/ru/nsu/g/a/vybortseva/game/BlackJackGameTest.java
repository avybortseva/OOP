package ru.nsu.g.a.vybortseva.game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
        assertNotNull(game.getPlayer(), "Игрок должен быть создан.");
        assertNotNull(game.getDealer(), "Дилер должен быть создан.");
        assertEquals(0, game.getRoundNumber(),
                "Номер раунда должен начинаться с нуля.");
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
        assertTrue(output.contains("Некорректный ввод"),
                "Должно отображаться сообщение об ошибке при некорректном вводе.");
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
    void testPlayerTurnPlayerStops() {
        String input = "0\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        game.initializeGame();
        game.playerTurn();

        String output = outputStream.toString();
        assertTrue(output.contains("Вы остановились"),
                "Нужно показывать сообщение об остановке");
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

        for (int i = 0; i < 3; i++) {
            game.getPlayer().incrementScore();
        }
        for (int i = 0; i < 2; i++) {
            game.getDealer().incrementScore();
        }

        game.printScore();

        String output = outputStream.toString();
        assertTrue(output.contains("3:2"), "Должен показывать счет 3:2");
    }

    @Test
    void testInitialDeal() {
        game.initializeGame();
        game.initialDeal();

        assertEquals(2, game.getPlayer().getHand().getCountCards(),
                "Игрок должен получить 2 карты");
        assertEquals(2, game.getDealer().getHand().getCountCards(),
                "Дилер должен получить 2 карты");

        assertTrue(game.getDealer().getHand().getCard(0).isHidden(),
                "У дилера должна быть одна скрытая карта");
    }

    @Test
    void testInitialDealDistributesCards() {
        game.initializeGame();
        game.initialDeal();

        assertEquals(2, game.getPlayer().getHand().getCountCards(),
                "Игрок должен получить 2 карты");
        assertEquals(2, game.getDealer().getHand().getCountCards(),
                "Дилер должен получить 2 карты");
    }

    @Test
    void testInitialDealDealerFirstCardHidden() {
        game.initializeGame();
        game.initialDeal();

        Card dealerFirstCard = game.getDealer().getHand().getCard(0);
        assertTrue(dealerFirstCard.isHidden(),
                "Первая карта дилера должна быть скрытой");
    }

    @Test
    void testInitialDealPlayerCardsVisible() {
        game.initializeGame();
        game.initialDeal();

        for (int i = 0; i < game.getPlayer().getHand().getCountCards(); i++) {
            assertFalse(game.getPlayer().getHand().getCard(i).isHidden(),
                    "Все карты игрока должны быть видимыми");
        }
    }

    @Test
    void testPlayerTurnTakesCard() {
        String input = "1\n0\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        game.initializeGame();
        game.initialDeal();

        int initialHandSize = game.getPlayer().getHand().getCountCards();
        game.playerTurn();

        assertTrue(game.getPlayer().getHand().getCountCards() > initialHandSize,
                "Игрок должен взять карту при выборе '1'");
    }

    @Test
    void testDealerTurnStopsAt17() {
        game.initializeGame();

        game.getDealer().addCard(new Card(Card.Suit.HEARTS, Card.Rank.TEN));
        game.getDealer().addCard(new Card(Card.Suit.DIAMONDS, Card.Rank.SEVEN));

        int initialHandSize = game.getDealer().getHand().getCountCards();
        game.dealerTurn();

        assertEquals(initialHandSize, game.getDealer().getHand().getCountCards(),
                "Дилер не должен брать карты при 17+ очках");
    }

    @Test
    void testDetermineRoundWinnerPlayerBlackjack() {
        game.initializeGame();

        game.getPlayer().addCard(new Card(Card.Suit.HEARTS, Card.Rank.ACE));
        game.getPlayer().addCard(new Card(Card.Suit.DIAMONDS, Card.Rank.KING));

        game.getDealer().addCard(new Card(Card.Suit.SPADES, Card.Rank.TEN));
        game.getDealer().addCard(new Card(Card.Suit.CLUBS, Card.Rank.SEVEN));

        game.determineRoundWinner();

        assertEquals(1, game.getPlayer().getScore(),
                "Игрок должен выиграть при блэкджеке");
        assertEquals(0, game.getDealer().getScore());
    }

    @Test
    void testDetermineRoundWinnerDealerBlackjack() {
        game.initializeGame();
        game.getPlayer().resetScore();
        game.getDealer().resetScore();

        game.getPlayer().addCard(new Card(Card.Suit.HEARTS, Card.Rank.TEN));
        game.getPlayer().addCard(new Card(Card.Suit.DIAMONDS, Card.Rank.SEVEN));

        game.getDealer().addCard(new Card(Card.Suit.SPADES, Card.Rank.ACE));
        game.getDealer().revealHiddenCard();
        game.getDealer().addCard(new Card(Card.Suit.CLUBS, Card.Rank.KING));

        game.determineRoundWinner();

        assertEquals(1, game.getDealer().getScore(),
                "Дилер должен выиграть при блэкджеке");
        assertEquals(0, game.getPlayer().getScore());

    }

    @Test
    void testDetermineRoundWinnerBothBlackjack() {
        game.initializeGame();

        game.getPlayer().addCard(new Card(Card.Suit.HEARTS, Card.Rank.ACE));
        game.getPlayer().addCard(new Card(Card.Suit.DIAMONDS, Card.Rank.KING));

        game.getDealer().addCard(new Card(Card.Suit.SPADES, Card.Rank.ACE));
        game.getDealer().revealHiddenCard();
        game.getDealer().addCard(new Card(Card.Suit.CLUBS, Card.Rank.KING));

        int playerScoreBefore = game.getPlayer().getScore();
        int dealerScoreBefore = game.getDealer().getScore();

        game.determineRoundWinner();

        assertEquals(playerScoreBefore, game.getPlayer().getScore(),
                "Ничья при обоих блэкджеках - очки не меняются");
        assertEquals(dealerScoreBefore, game.getDealer().getScore());
    }

    @Test
    void testDetermineRoundWinnerPlayerBusted() {
        game.initializeGame();

        game.getPlayer().addCard(new Card(Card.Suit.HEARTS, Card.Rank.TEN));
        game.getPlayer().addCard(new Card(Card.Suit.DIAMONDS, Card.Rank.TEN));
        game.getPlayer().addCard(new Card(Card.Suit.SPADES, Card.Rank.TEN));

        game.getDealer().addCard(new Card(Card.Suit.CLUBS, Card.Rank.TEN));
        game.getDealer().addCard(new Card(Card.Suit.HEARTS, Card.Rank.SEVEN));

        game.determineRoundWinner();

        assertEquals(0, game.getPlayer().getScore());
        assertEquals(1, game.getDealer().getScore(),
                "Дилер должен выиграть если игрок перебрал");
    }

    @Test
    void testDetermineRoundWinnerDealerBusted() {
        game.initializeGame();

        game.getPlayer().addCard(new Card(Card.Suit.HEARTS, Card.Rank.TEN));
        game.getPlayer().addCard(new Card(Card.Suit.DIAMONDS, Card.Rank.SEVEN));

        game.getDealer().addCard(new Card(Card.Suit.SPADES, Card.Rank.TEN));
        game.getDealer().addCard(new Card(Card.Suit.CLUBS, Card.Rank.TEN));
        game.getDealer().addCard(new Card(Card.Suit.DIAMONDS, Card.Rank.TEN));
        game.getDealer().revealHiddenCard();

        game.determineRoundWinner();

        assertEquals(1, game.getPlayer().getScore(),
                "Игрок должен выиграть если дилер перебрал");
        assertEquals(0, game.getDealer().getScore());
    }

    @Test
    void testPlayRoundIncrementsRoundNumber() {
        String input = "0\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        game.initializeGame();

        int initialRound = game.getRoundNumber();
        game.playRound();

        assertEquals(initialRound + 1, game.getRoundNumber(),
                "Номер раунда должен увеличиваться после playRound()");
    }

    @Test
    void testEndGamePlayerWins() {
        game.initializeGame();

        for (int i = 0; i < 3; i++) {
            game.getPlayer().incrementScore();
        }

        game.endGame();

        String output = outputStream.toString();
        assertTrue(output.contains("Поздравляем! Вы выиграли"),
                "Должно показывать сообщение о победе игрока");
    }

    @Test
    void testEndGameDealerWins() {
        game.initializeGame();

        for (int i = 0; i < 3; i++) {
            game.getDealer().incrementScore();
        }

        game.endGame();

        String output = outputStream.toString();
        assertTrue(output.contains("Дилер выиграл"),
                "Должно показывать сообщение о победе дилера");
    }

    @Test
    void testPrintGameState() {
        game.initializeGame();

        game.getPlayer().addCard(new Card(Card.Suit.HEARTS, Card.Rank.TEN));
        game.getDealer().addCard(new Card(Card.Suit.SPADES, Card.Rank.ACE));

        game.printGameState(false);

        String output = outputStream.toString();
        assertTrue(output.contains("Ваши карты:"),
                "Должно показывать карты игрока");
        assertTrue(output.contains("Карты дилера:"),
                "Должно показывать карты дилера");
    }

    @Test
    void testPrintGameStateShowsDealerHiddenCard() {
        game.initializeGame();

        Card hiddenCard = new Card(Card.Suit.HEARTS, Card.Rank.ACE);
        hiddenCard.setHidden(true);
        game.getDealer().addCard(hiddenCard);
        game.getDealer().revealHiddenCard();

        game.printGameState(true);

        String output = outputStream.toString();
        assertTrue(output.contains("Туз"),
                "Должно показывать скрытую карту дилера при showAll=true");
    }
}