package ru.nsu.g.a.vybortseva.model;

import java.util.Objects;

/**
 * Coordinate point.
 */
public class Point {
    private final int x;
    private final int y;

    /**
     * Creates new point.
     */
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Gets X coordinate.
     */
    public int getX() {
        return x;
    }

    /**
     * Gets Y coordinate.
     */
    public int getY() {
        return y;
    }

    /**
     * Compares two points.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return x == point.x && y == point.y;
    }

    /**
     * Generates hash code.
     */
    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    /**
     * String representation.
     */
    @Override
    public String toString() {
        return "Point{" + "x=" + x + ", y=" + y + '}';
    }
}
