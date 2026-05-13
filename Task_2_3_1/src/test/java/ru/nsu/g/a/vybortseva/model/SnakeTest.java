package ru.nsu.g.a.vybortseva.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SnakeTest {
    private Snake snake;

    @BeforeEach
    void setUp() {
        Point startPoint = new Point(5, 5);
        snake = new Snake(startPoint);
    }

    @Test
    void testInitialization() {
        assertEquals(1, snake.getBody().size());

        Point head = snake.getBody().getFirst();
        assertEquals(5, head.getX());
        assertEquals(5, head.getY());

        Point next = snake.nextHead();
        assertEquals(5, next.getX());
        assertEquals(4, next.getY());
    }

    @Test
    void testMove() {
        snake.move();
        assertEquals(1, snake.getBody().size());

        Point head = snake.getBody().getFirst();
        assertEquals(5, head.getX());
        assertEquals(4, head.getY());
    }

    @Test
    void testGrow() {
        snake.grow();
        assertEquals(2, snake.getBody().size());
        Point newHead = snake.getBody().getFirst();
        Point tail = snake.getBody().getLast();

        assertEquals(5, newHead.getX());
        assertEquals(4, newHead.getY());
        assertEquals(5, tail.getX());
        assertEquals(5, tail.getY());
    }

    @Test
    void testSetValidDirection() {
        snake.setDirection(Direction.LEFT);
        snake.move();

        Point head = snake.getBody().getFirst();
        assertEquals(4, head.getX());
        assertEquals(5, head.getY());
    }

    @Test
    void testSetInvalidDirectionOpposite() {
        snake.setDirection(Direction.DOWN);
        snake.move();

        Point head = snake.getBody().getFirst();
        assertEquals(5, head.getX());
        assertEquals(4, head.getY());
    }

    @Test
    void testNextHeadDoesNotChangeState() {
        Point expectedNext = new Point(5, 4);
        Point actualNext = snake.nextHead();

        assertEquals(expectedNext.getX(), actualNext.getX());
        assertEquals(expectedNext.getY(), actualNext.getY());

        assertEquals(1, snake.getBody().size());
        assertEquals(5, snake.getBody().getFirst().getX());
        assertEquals(5, snake.getBody().getFirst().getY());
    }
}
