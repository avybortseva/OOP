package ru.nsu.g.a.vybortseva.game;

import java.util.Scanner;

/**
 * The main class for the play.
 */
public class BlackJackGame {

    private Deck deck;
    private Player player;
    private Dealer dealer;
    private int roundNumber;
    private Scanner scanner;
    private int neededScore = 3;

    /**
     * The method for getting of the player.
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * The method for getting of the current deck.
     */
    public Deck getDeck() {
        return deck;
    }

    /**
     * The method for getting of the dealer.
     */
    public Dealer getDealer() {
        return dealer;
    }

    /**
     * The method for the getting of the roundNumber.
     */
    public int getRoundNumber() {
        return roundNumber;
    }

    /**
     * The method of game logic.
     */
    public static void main(String[] args) {
        BlackJackGame game = new BlackJackGame();
        game.startGame();
    }

    private void startGame() {

        System.out.println("Добро пожаловать в Блэкджек!");
        System.out.println("Играем до трех побед!");
        initializeGame();

        while (player.getScore() < neededScore
                && dealer.getScore() < neededScore) {
            playRound();
        }
        endGame();
    }

    /**
     * The method of initializing of the play.
     */
    public void initializeGame() {
        deck = new Deck();
        player = new Player();
        dealer = new Dealer();
        roundNumber = 0;
        scanner = new Scanner(System.in);
    }

    /**
     * The method of playing round.
     */
    public void playRound() {
        roundNumber++;
        System.out.println("\nРаунд " + roundNumber);

        player.clearHand();
        dealer.clearHand();

        deck.initializeDeck();
        deck.shuffle();

        initialDeal();

        if (player.hasBlackJack()) {
            System.out.println("Блэкджек! Ход переходит к дилеру.");
        }else {
            playerTurn();
        }

        dealerTurn();

        determineRoundWinner();
        printScore();
    }

    private void initialDeal() {
        System.out.println("Дилер раздал карты");

        player.addCard(deck.drawCard());
        player.addCard(deck.drawCard());

        dealer.addCard(deck.drawCard());
        dealer.addCard(deck.drawCard());

        printGameState(false);
    }

    /**
     * The method for the player's turn.
     */
    public void playerTurn() {
        System.out.println("Ваш ход");
        System.out.println("-------");

        while (true) {
            System.out.println("Введите \"1\", чтобы взять карту, "
                    + "и \"0\", чтобы остановиться.");
            int choice = getPlayerChoice();

            if (choice == 1) {
                Card newCard = deck.drawCard();
                player.addCard(newCard);
                System.out.println("Вы открыли карту " + newCard.toString());
                printGameState(false);


                if (player.isBusted()) {
                    System.out.println("Перебор! Сумма очков: " + player.getHandValue());
                    break;
                } else if (player.getHandValue() == 21) {
                    System.out.println("У вас 21 очко!");
                    break;
                }
            } else if (choice == 0) {
                System.out.println("Вы остановились.");
                break;
            }
        }
    }

    /**
     * The method for the dealer's turn.
     */
    public void dealerTurn() {
        System.out.println("\nХод дилера");
        System.out.println("-------");

        dealer.revealHiddenCard();
        System.out.println("Дилер открывает закрытую карту "
                + dealer.getHand().getCards().get(1).toString());
        printGameState(true);

        if (dealer.hasBlackJack()) {
            System.out.println("У дилера блэкджек!");
            return;
        }

        while (dealer.shouldHit() && !dealer.isBusted()) {
            Card newCard = deck.drawCard();
            dealer.addCard(newCard);
            System.out.println("Дилер открывает карту " + newCard.toString());

            printGameState(true);
        }

        if (dealer.isBusted()) {
            System.out.println("Дилер перебрал! Сумма очков: "
                    + dealer.getHandValue());
        } else {
            System.out.println("Дилер остановился. Сумма очков: "
                    + dealer.getHandValue());
        }
    }

    /**
     * The method for getting of player's turn.
     */
    public int getPlayerChoice() {
        while (true) {
            String input = scanner.nextLine().trim();
            if (input.equals("0") || input.equals("1")) {
                return Integer.parseInt(input);
            }else {
                System.out.println("Некорректный ввод. Пожалуйста, "
                        + "введите '0' или '1'");
            }
        }
    }

    private void printGameState(boolean showAllDealerCards) {
        System.out.println("Ваши карты: " + player.showHand(true)
                + " ==> " + player.getHandValue());

        System.out.println("Карты дилера: " + dealer.showHand(showAllDealerCards)
                + (showAllDealerCards ? " ==> " + dealer.getHandValue() : ""));
        System.out.println();
    }

    /**
     * The method for printing of the score.
     */
    public void printScore() {
        System.out.println("\nСчет " + player.getScore() + ":" + dealer.getScore()
                + (player.getScore() > dealer.getScore() ? " в вашу пользу."
                : player.getScore() < dealer.getScore() ? " в пользу дилера." : " ничья."));
    }

    /**
     * The method for determining of round winner.
     */
    void determineRoundWinner() {
        int playerPoints = player.getHand().getPoints();
        int dealerPoints = dealer.getHand().getPoints();

        if (player.hasBlackJack() && dealer.hasBlackJack()) {
            System.out.println("Блэкджек у обоих! Ничья!");
        } else if (player.hasBlackJack() && !dealer.hasBlackJack()) {
            System.out.println("Блэкджек! Вы выиграли раунд!");
            player.incrementScore();
        } else if (dealer.hasBlackJack() && !player.hasBlackJack()) {
            System.out.println("Блэкджек у дилера! Дилер выиграл раунд!");
            dealer.incrementScore();
        } else if (player.isBusted() && dealer.isBusted()) {
            System.out.println("Перебор у обоих! Ничья!");
        } else if (player.isBusted()) {
            System.out.println("Дилер выиграл раунд!");
            dealer.incrementScore();
        } else if (dealer.isBusted()) {
            System.out.println("Вы выиграли раунд!");
            player.incrementScore();
        } else if (playerPoints > dealerPoints) {
            System.out.println("Вы выиграли раунд!");
            player.incrementScore();
        } else if (dealerPoints > playerPoints) {
            System.out.println("Дилер выиграл раунд!");
            dealer.incrementScore();
        } else {
            System.out.println("Ничья!");
        }
    }

    private void endGame() {
        System.out.println("\n=== ИГРА ОКОНЧЕНА ===");
        if (player.getScore() >= 3) {
            System.out.println("Поздравляем! Вы выиграли игру со счетом "
                    + player.getScore() + ":" + dealer.getScore());
        } else {
            System.out.println("Дилер выиграл игру со счетом "
                    + dealer.getScore() + ":" + player.getScore());
        }

        scanner.close();
    }
}
