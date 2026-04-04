package ru.nsu.g.a.vybortseva.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GameModelTest {

    private GameModel model;

    @BeforeEach
    void setUp() {
        model = new GameModel(10, 10, 3, 5);
    }

    @Test
    void testInitialization() {
        assertFalse(model.isGameOver());
        assertFalse(model.isGameWin());

        assertEquals(3, model.getFoods().size());

        Point head = model.getSnake().getBody().getFirst();
        assertEquals(5, head.getX());
        assertEquals(5, head.getY());
        assertEquals(1, model.getSnake().getBody().size());
    }

    @Test
    void testNormalMoveWithoutFood() {
        model.getFoods().clear();
        model.update();

        assertEquals(1, model.getSnake().getBody().size());
        Point newHead = model.getSnake().getBody().getFirst();
        assertEquals(5, newHead.getX());
        assertEquals(4, newHead.getY());
    }

    @Test
    void testEatFoodAndGrow() {
        Point nextStep = model.getSnake().nextHead();
        model.getFoods().clear();
        model.getFoods().add(nextStep);
        model.update();

        assertEquals(2, model.getSnake().getBody().size());
        assertTrue(model.getSnake().getBody().contains(nextStep));

        assertFalse(model.getFoods().contains(nextStep));
        assertEquals(1, model.getFoods().size());
    }

    @Test
    void testGameOverOnWallCollision() {
        GameModel smallModel = new GameModel(5, 5, 1, 10);
        smallModel.update();
        smallModel.update();
        assertFalse(smallModel.isGameOver());

        smallModel.update();
        assertTrue(smallModel.isGameOver());
    }

    private void feedSnakeDirectly(GameModel targetModel) {
        targetModel.getFoods().clear();
        targetModel.getFoods().add(targetModel.getSnake().nextHead());
        targetModel.update();
    }

    @Test
    void testGameOverOnSelfCollision() {
        feedSnakeDirectly(model);
        feedSnakeDirectly(model);

        model.getSnake().setDirection(Direction.DOWN);
        feedSnakeDirectly(model);
        model.getSnake().setDirection(Direction.RIGHT);

        assertFalse(model.isGameOver());
    }

    @Test
    void testGameWinCondition() {
        GameModel winModel = new GameModel(10, 10, 1, 3);
        feedSnakeDirectly(winModel);
        assertFalse(winModel.isGameWin());

        feedSnakeDirectly(winModel);
        assertTrue(winModel.isGameWin());
        assertFalse(winModel.isGameOver());
    }
}
