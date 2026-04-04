package ru.nsu.g.a.vybortseva.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Main game logic.
 */
public class GameModel {
    private final int width;
    private final int height;

    private Snake snake;
    private List<Point> foods;
    private int targetLength;

    private boolean gameWin = false;
    private boolean gameOver = false;

    /**
     * Initializes game world.
     */
    public GameModel(int width, int height, int foodCount, int targetLength) {
        this.width = width;
        this.height = height;
        this.targetLength = targetLength;

        this.foods = new ArrayList<>();
        this.snake = new Snake(new Point(width / 2, height / 2));

        for (int i = 0; i < foodCount; i++) {
            generateFood();
        }
    }

    /**
     * Updates game state.
     */
    public void update() {
        if (gameOver || gameWin) {
            return;
        }

        Point nextHead = snake.nextHead();
        if (isCollision(nextHead)) {
            gameOver = true;
            return;
        }

        if (foods.contains(nextHead)) {
            snake.grow();
            foods.remove(nextHead);
            generateFood();
        } else {
            snake.move();
        }

        if (snake.getBody().size() >= targetLength) {
            gameWin = true;
        }
    }

    /**
     * Generates food randomly.
     */
    private void generateFood() {
        Random random = new Random();
        Point foodPoint;
        do {
            foodPoint = new Point(random.nextInt(width), random.nextInt(height));
        } while (snake.getBody().contains(foodPoint) || foods.contains(foodPoint));

        foods.add(foodPoint);
    }

    /**
     * Checks for collisions.
     */
    private boolean isCollision(Point p) {
        if (p.getX() < 0 || p.getX() >= width || p.getY() < 0 || p.getY() >= height) {
            return true;
        }
        return snake.getBody().contains(p);
    }

    /**
     * Returns lose status.
     */
    public boolean isGameOver() {
        return gameOver;
    }

    /**
     * Returns win status.
     */
    public boolean isGameWin() {
        return gameWin;
    }

    /**
     * Gets snake instance.
     */
    public Snake getSnake() {
        return snake;
    }

    /**
     * Gets food list.
     */
    public List<Point> getFoods() {
        return foods;
    }
}
