package ru.nsu.g.a.vybortseva.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.nsu.g.a.vybortseva.config.GameConfig;
import java.util.Map;

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
        assertFalse(model.isGameOver(), "Game should not be over at start");
        assertFalse(model.isGameWin(), "Game should not be won at start");

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
        assertEquals(1, eatenRed, "Red food counter should increment");
        assertEquals(2, model.getSnake().getBody().size());
    }

    @Test
    void testGameWinConditionByColors() {
        feedSnakeWithColor(model, Food.FoodType.RED);
        assertFalse(model.isGameWin(), "Should not win after only 1/2 apples");

        feedSnakeWithColor(model, Food.FoodType.RED);
        assertTrue(model.isGameWin(), "Should win after reaching color targets");
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
        assertTrue(smallModel.isGameOver(), "Should be game over on wall collision");
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
                assertTrue(model.isGameOver(), "Should be game over on obstacle collision");
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