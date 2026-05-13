package ru.nsu.g.a.vybortseva.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import ru.nsu.g.a.vybortseva.config.GameConfig;

/**
 * Main game logic.
 */
public class GameModel {
    private final GameConfig config;
    private final Snake snake;
    private final List<Food> foods;
    private final List<Obstacle> obstacles;

    private boolean gameWin = false;
    private boolean gameOver = false;

    private Map<Food.FoodType, Integer> eatenCount = new EnumMap<>(Food.FoodType.class);

    private boolean canChangeDirection = true;

    /**
     * Initializes game world.
     */
    public GameModel(GameConfig config) {
        this.config = config;
        this.foods = new ArrayList<>();
        this.eatenCount = new EnumMap<>(Food.FoodType.class);
        this.obstacles = ObstaclesGenerator.generateAll(config);
        this.snake = new Snake(new Point(0, config.getHeight() - 1));

        config.getFoodDistribution().forEach((type, count) -> {
            for (int i = 0; i < count; i++) {
                FoodGenerator.generateSpecificFood(config, snake, obstacles, foods, type);
            }
        });

        for (Food.FoodType type : Food.FoodType.values()) {
            eatenCount.put(type, 0);
        }
    }

    /**
     * Updates game state.
     */
    public void update() {
        if (gameOver || gameWin) {
            return;
        }

        canChangeDirection = true;

        Point nextHead = snake.nextHead();
        if (isCollision(nextHead)) {
            gameOver = true;
            return;
        }

        Food eatenFood = null;
        for (Food f : foods) {
            if (f.getPosition().equals(nextHead)) {
                eatenFood = f;
                break;
            }
        }

        if (eatenFood != null) {
            snake.grow();
            Food.FoodType typeToRespawn = eatenFood.getType();
            eatenCount.put(typeToRespawn, eatenCount.getOrDefault(typeToRespawn, 0) + 1);

            foods.remove(eatenFood);

            boolean foodSpawned = FoodGenerator.generateSpecificFood(config,
                    snake, obstacles, foods, typeToRespawn);
            checkWinCondition();

            if (!this.gameWin && !foodSpawned && foods.isEmpty()) {
                this.gameWin = true;
            }
        } else {
            snake.move();
        }
    }

    private void checkWinCondition() {
        Map<Food.FoodType, Integer> targets = config.getTargetFoodCounts();

        boolean allGoalsMet = true;
        for (Map.Entry<Food.FoodType, Integer> entry : targets.entrySet()) {
            int currentEaten = eatenCount.getOrDefault(entry.getKey(), 0);
            if (currentEaten < entry.getValue()) {
                allGoalsMet = false;
                break;
            }
        }

        this.gameWin = allGoalsMet;
    }

    /**
     * Checks for collisions.
     */
    private boolean isCollision(Point p) {
        if (p.getX() < 0 || p.getX() >= config.getWidth() || p.getY() < 0
                || p.getY() >= config.getHeight()) {
            return true;
        }
        if (snake.getBody().contains(p)) {
            return true;
        }
        for (Obstacle obs : obstacles) {
            if (obs.getPoints().contains(p)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Updates snake direction with input protection.
     */
    public void handleDirectionChange(Direction newDir) {
        if (!canChangeDirection) {
            return;
        }

        if (snake.canSetDirection(newDir)) {
            snake.setDirection(newDir);
            canChangeDirection = false;
        }
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
    public List<Food> getFoods() {
        return foods;
    }

    /**
     * Gets obstacles list.
     */
    public List<Obstacle> getObstacles() {
        return obstacles;
    }

    /**
     * Returns active configuration.
     */
    public GameConfig getConfig() {
        return config;
    }

    /**
     * Returns collected food statistics.
     */
    public Map<Food.FoodType, Integer> getEatenCount() {
        return eatenCount;
    }

    /**
     * Get the snake's size.
     */
    public int getCurrentScore() {
        return getSnake().getBody().size();
    }
}
