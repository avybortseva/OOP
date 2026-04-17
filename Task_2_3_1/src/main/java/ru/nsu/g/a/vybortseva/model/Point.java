package ru.nsu.g.a.vybortseva.model;

import java.util.Objects;

/**
 * Coordinate point.
 */
public class Point {
    private final int coordX;
    private final int coordY;

    /**
     * Creates new point.
     */
    public Point(int coordX, int coordY) {
        this.coordX = coordX;
        this.coordY = coordY;
    }

    /**
     * Gets X coordinate.
     */
    public int getX() {
        return coordX;
    }

    /**
     * Gets Y coordinate.
     */
    public int getY() {
        return coordY;
    }

    /**
     * Compares two points.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Point point = (Point) o;
        return coordX == point.coordX && coordY == point.coordY;
    }

    /**
     * Generates hash code.
     */
    @Override
    public int hashCode() {
        return Objects.hash(coordX, coordY);
    }

    /**
     * String representation.
     */
    @Override
    public String toString() {
        return "Point{" + "coordX=" + coordX + ", coordY=" + coordY + '}';
    }
}
