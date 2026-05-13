package ru.nsu.g.a.vybortseva.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ObstacleTest {
    private List<Obstacle> existingObstacles;
    private List<Point> currentPoints;
    private final int width = 20;
    private final int height = 20;

    @BeforeEach
    void setUp() {
        existingObstacles = new ArrayList<>();
        currentPoints = new ArrayList<>();
    }

    @Test
    void testAddPoint() {
        Obstacle obstacle = new Obstacle();
        Point p = new Point(5, 5);
        obstacle.addPoint(p);

        assertEquals(1, obstacle.getPoints().size());
        assertTrue(obstacle.getPoints().contains(p));
    }

    @Test
    void testOutOfBounds() {
        Point p = new Point(-1, 5);
        assertFalse(Obstacle.isValidPoint(p, width, height, existingObstacles, currentPoints));

        Point p2 = new Point(width, 5);
        assertFalse(Obstacle.isValidPoint(p2, width, height, existingObstacles, currentPoints));
    }

    @Test
    void testZeroRestriction() {
        Point p = new Point(0, 10);
        assertFalse(Obstacle.isValidPoint(p, width, height, existingObstacles, currentPoints));
    }

    @Test
    void testDuplicateInCurrent() {
        Point p = new Point(5, 5);
        currentPoints.add(p);

        assertFalse(Obstacle.isValidPoint(p, width, height, existingObstacles, currentPoints));
    }

    @Test
    void testProximityToOthers() {
        Obstacle existing = new Obstacle();
        existing.addPoint(new Point(10, 10));
        existingObstacles.add(existing);
        Point nearPoint = new Point(10, 11);
        assertFalse(Obstacle.isValidPoint(nearPoint, width, height,
                existingObstacles, currentPoints));
    }
}
