package ru.nsu.g.a.vybortseva.config;

import ru.nsu.g.a.vybortseva.model.Food;
import java.util.Map;

/**
 * Game configuration and level settings.
 */
public class GameConfig {
    private final int width;
    private final int height;
    private final int targetLength;
    private final long tickDelayNanos;

    private final Map<Food.FoodType, Integer> foodDistribution;
    private final Map<Integer, Integer> obstacleDistribution;
    private final Map<Food.FoodType, Integer> targetFoodCounts;

    /**
     * Creates a new game configuration.
     */
    public GameConfig(int size, int targetLength, long tickDelayMs,
                      Map<Food.FoodType, Integer> foodDistribution,
                      Map<Integer, Integer> obstacleDistribution,
                      Map<Food.FoodType, Integer> targetFoodCounts) {
        this.width = size;
        this.height = size;
        this.targetLength = targetLength;
        this.tickDelayNanos = tickDelayMs * 1000000L;
        this.foodDistribution = foodDistribution;
        this.obstacleDistribution = obstacleDistribution;
        this.targetFoodCounts = Map.copyOf(targetFoodCounts);
    }

    /**
     * Returns grid width.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Returns grid height.
     */
    public int getHeight() {
        return height;
    }

    /**
     * Returns required snake length.
     */
    public int getTargetLength() {
        return targetLength;
    }

    /**
     * Returns game tick delay.
     */
    public long getTickDelayNanos() {
        return tickDelayNanos;
    }

    /**
     * Returns obstacle size distribution.
     */
    public Map<Integer, Integer> getObstacleDistribution() {
        return obstacleDistribution;
    }

    /**
     * Returns food type distribution.
     */
    public Map<Food.FoodType, Integer> getFoodDistribution() {
        return foodDistribution;
    }

    /**
     * Returns required food amounts.
     */
    public Map<Food.FoodType, Integer> getTargetFoodCounts() {
        return targetFoodCounts;
    }

    /**
     * Creates an easy difficulty level.
     */
    public static GameConfig createEasyLevel() {
        return new GameConfig(
                20,
                20,
                200,
                Map.of(Food.FoodType.RED, 10, Food.FoodType.BLUE, 3, Food.FoodType.ORANGE, 2),
                Map.of(3, 2, 4, 1),
                Map.of(Food.FoodType.RED, 25, Food.FoodType.BLUE, 15, Food.FoodType.ORANGE, 10)
        );
    }
}
