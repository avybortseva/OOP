package ru.nsu.g.a.vybortseva.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a single wall obstacle.
 */
public class Obstacle {
    private final List<Point> points;

    /**
     * Creates obstacle with given points.
     */
    public Obstacle() {
        this.points = new ArrayList<>();
    }

    /**
     * Adds a point to the obstacle.
     */
    public void addPoint(Point point) {
        points.add(point);
    }

    /**
     * Checks if a point can be part of a wall.
     */
    public static boolean isValidPoint(Point p, int width, int height,
                                       List<Obstacle> existingObstacles,
                                       List<Point> currentPoints) {
        if (p.getX() < 0 || p.getX() >= width || p.getY() < 0 || p.getY() >= height) {
            return false;
        }
        if (p.getX() == 0) {
            return false;
        }
        if (currentPoints.contains(p)) {
            return false;
        }

        for (Obstacle obs : existingObstacles) {
            for (Point op : obs.getPoints()) {
                if (Math.abs(p.getX() - p.getY()) <= 1 && Math.abs(op.getY() - p.getY()) <= 1) {
                    return false;
                }
            }
        }
        return !currentPoints.contains(p);
    }

    /**
     * Gets all points of this obstacle.
     */
    public List<Point> getPoints() {
        return points;
    }
}
