package ru.nsu.g.a.vybortseva.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import java.util.Map;
import ru.nsu.g.a.vybortseva.model.Food;

class GameConfigTest {

    @Test
    void testConstructor() {
        Map<Food.FoodType, Integer> foodDist = Map.of(Food.FoodType.RED, 5);
        Map<Integer, Integer> obsDist = Map.of(3, 1);
        Map<Food.FoodType, Integer> targets = Map.of(Food.FoodType.RED, 10);

        GameConfig config = new GameConfig(15, 25, 100, foodDist, obsDist, targets);

        assertEquals(15, config.getWidth());
        assertEquals(15, config.getHeight());
        assertEquals(25, config.getTargetLength());
        assertEquals(100_000_000L, config.getTickDelayNanos());
    }

    @Test
    void testCreateEasyLevel() {
        GameConfig easy = GameConfig.createEasyLevel();

        assertNotNull(easy);
        assertEquals(20, easy.getWidth());
        assertEquals(200_000_000L, easy.getTickDelayNanos());
        Map<Food.FoodType, Integer> targets = easy.getTargetFoodCounts();
        assertTrue(targets.containsKey(Food.FoodType.RED));
        assertEquals(25, targets.get(Food.FoodType.RED));
    }

    @Test
    void testMapImmutability() {
        Map<Food.FoodType, Integer> targets = Map.of(Food.FoodType.RED, 10);
        GameConfig config = new GameConfig(10, 10, 100, Map.of(), Map.of(), targets);
        Map<Food.FoodType, Integer> returnedTargets = config.getTargetFoodCounts();
        assertThrows(UnsupportedOperationException.class, () -> {
            returnedTargets.put(Food.FoodType.BLUE, 5);
        });
    }
}