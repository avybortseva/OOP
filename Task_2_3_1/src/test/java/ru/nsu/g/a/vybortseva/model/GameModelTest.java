package ru.nsu.g.a.vybortseva.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import ru.nsu.g.a.vybortseva.config.GameConfig;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GameModelTest {

    private GameModel model;
    private GameConfig config;

    @BeforeEach
    void setUp() {
        config = new GameConfig(
                10,
                5,
                150L,
                Map.of(Food.FoodType.RED, 3),
                Map.of(3, 1),
                Map.of(Food.FoodType.RED, 2)
        );
        model = new GameModel(config);
    }

    @Test
    void testInitialization() {
        assertFalse(model.isGameOver());
        assertFalse(model.isGameWin());

        assertEquals(3, model.getFoods().size());

        Point head = model.getSnake().getBody().getFirst();
        assertEquals(0, head.getX());
        assertEquals(9, head.getY());
    }

    @Test
    void testEatSpecificFood() {
        Point nextStep = model.getSnake().nextHead();
        model.getFoods().clear();
        model.getFoods().add(new Food(nextStep, Food.FoodType.RED));

        model.update();

        int eatenRed = model.getEatenCount().get(Food.FoodType.RED);
        assertEquals(1, eatenRed);
        assertEquals(2, model.getSnake().getBody().size());
    }

    @Test
    void testGameWinConditionByColors() {
        feedSnakeWithColor(model, Food.FoodType.RED);
        assertFalse(model.isGameWin());

        feedSnakeWithColor(model, Food.FoodType.RED);
        assertTrue(model.isGameWin());
    }

    @Test
    void testGameOverOnWallCollision() {
        GameConfig smallConfig = new GameConfig(
                5,
                10,
                150L,
                Map.of(Food.FoodType.RED, 1),
                Map.of(),
                Map.of(Food.FoodType.RED, 10)
        );
        GameModel smallModel = new GameModel(smallConfig);

        for (int i = 0; i < 4; i++) {
            smallModel.update();
            assertFalse(smallModel.isGameOver());
        }

        smallModel.update();
        assertTrue(smallModel.isGameOver());
    }

    @Test
    void testObstacleCollision() {
        if (!model.getObstacles().isEmpty()) {
            Point obsPoint = model.getObstacles().getFirst().getPoints().getFirst();

            if (obsPoint.getY() < 9) {
                Point beforeObs = new Point(obsPoint.getX(), obsPoint.getY() + 1);
                model.getSnake().getBody().clear();
                model.getSnake().getBody().add(beforeObs);
                model.getSnake().setDirection(Direction.UP);

                model.update();
                assertTrue(model.isGameOver());
            }
        }
    }

    private void feedSnakeWithColor(GameModel targetModel, Food.FoodType type) {
        Point nextHead = targetModel.getSnake().nextHead();
        targetModel.getFoods().clear();
        targetModel.getFoods().add(new Food(nextHead, type));
        targetModel.update();
    }
}