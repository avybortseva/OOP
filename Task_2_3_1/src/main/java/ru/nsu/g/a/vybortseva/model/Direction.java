package ru.nsu.g.a.vybortseva.model;

/**
 * Snake movement directions.
 */
public enum Direction {
    UP(0, -1),
    DOWN(0, 1),
    LEFT(-1, 0),
    RIGHT(1, 0);

    private final int deltaX;
    private final int deltaY;

    Direction(int deltaX, int deltaY) {
        this.deltaX = deltaX;
        this.deltaY = deltaY;
    }

    /**
     * Gets Y axis delta.
     */
    public int getDy() {
        return deltaY;
    }

    /**
     * Gets X axis delta.
     */
    public int getDx() {
        return deltaX;
    }

    /**
     * Checks if direction is opposite.
     */
    public boolean isOpposite(Direction newDirection) {
        return (this.deltaX + newDirection.deltaX == 0)
                && (this.deltaY + newDirection.deltaY == 0);
    }
}
