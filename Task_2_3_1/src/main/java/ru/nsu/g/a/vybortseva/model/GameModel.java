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
        this.obstacles = new ArrayList<>();
        this.eatenCount = new EnumMap<>(Food.FoodType.class);

        generateAllObstacles();

        this.snake = new Snake(new Point(0, config.getHeight() - 1));

        config.getFoodDistribution().forEach((type, count) -> {
            for (int i = 0; i < count; i++) {
                generateSpecificFood(type);
            }
        });

        for (Food.FoodType type : Food.FoodType.values()) {
            eatenCount.put(type, 0);
        }
    }

    private void generateSpecificFood(Food.FoodType type) {
        Random random = new Random();
        Point foodPoint;
        boolean invalid;

        do {
            foodPoint = new Point(random.nextInt(config.getWidth()),
                    random.nextInt(config.getHeight()));
            invalid = isPointOccupied(foodPoint);
        } while (invalid);

        foods.add(new Food(foodPoint, type));
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
            generateSpecificFood(typeToRespawn);
            checkWinCondition();
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

    private boolean isPointOccupied(Point p) {
        if (snake.getBody().contains(p)) {
            return true;
        }
        for (Food f : foods) {
            if (f.getPosition().equals(p)) {
                return true;
            }
        }
        for (Obstacle obs : obstacles) {
            if (obs.getPoints().contains(p)) {
                return true;
            }
        }
        return false;
    }

    private void generateAllObstacles() {
        Map<Integer, Integer> setup = config.getObstacleDistribution();

        setup.forEach((length, count) -> {
            for (int i = 0; i < count; i++) {
                createAndAddObstacle(length);
            }
        });
    }

    /**
     * Tries to create and add a single obstacle of specific length to the game.
     * Uses a while loop to keep trying until a valid obstacle shape is formed.
     */
    private void createAndAddObstacle(int targetLen) {
        Random random = new Random();
        boolean obstacleCreated = false;

        while (!obstacleCreated) {
            Obstacle newObs = new Obstacle();
            Point start = null;
            int startAttempts = 0;

            do {
                Point p = new Point(random.nextInt(config.getWidth()),
                        random.nextInt(config.getHeight()));
                if (Obstacle.isValidPoint(p, config.getWidth(),
                        config.getHeight(), obstacles, newObs.getPoints())) {
                    start = p;
                }
                startAttempts++;
            } while (start == null && startAttempts < 100);

            if (start == null) {
                continue;
            }

            newObs.addPoint(start);
            for (int i = 1; i < targetLen; i++) {
                Point lastPoint = newObs.getPoints().getLast();
                List<Point> neighbors = getNeighbors(lastPoint);
                Collections.shuffle(neighbors);

                boolean pointAdded = false;
                for (Point neighbor : neighbors) {
                    if (Obstacle.isValidPoint(neighbor, config.getWidth(), config.getHeight(),
                            obstacles, newObs.getPoints())) {
                        newObs.addPoint(neighbor);
                        pointAdded = true;
                        break;
                    }
                }
                if (!pointAdded) {
                    break;
                }
            }
            if (newObs.getPoints().size() == targetLen) {
                obstacles.add(newObs);
                obstacleCreated = true;
            }
        }
    }

    private List<Point> getNeighbors(Point p) {
        List<Point> neighbors = new ArrayList<>();
        neighbors.add(new Point(p.getX() + 1, p.getY()));
        neighbors.add(new Point(p.getX() - 1, p.getY()));
        neighbors.add(new Point(p.getX(), p.getY() + 1));
        neighbors.add(new Point(p.getX(), p.getY() - 1));
        return neighbors;
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
}
