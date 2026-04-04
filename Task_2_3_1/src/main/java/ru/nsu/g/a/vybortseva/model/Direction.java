package ru.nsu.g.a.vybortseva.model;

/**
 * Snake movement directions.
 */
public enum Direction {
    UP(0, -1),
    DOWN(0, 1),
    LEFT(-1, 0),
    RIGHT(1, 0);

    private final int dx, dy;

    Direction(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    /**
     * Gets Y axis delta.
     */
    public int getDy() {
        return dy;
    }

    /**
     * Gets X axis delta.
     */
    public int getDx() {
        return dx;
    }

    /**
     * Checks if direction is opposite.
     */
    public boolean isOpposite(Direction newDirection) {
        return (this.dx + newDirection.dx == 0)
                && (this.dy + newDirection.dy == 0);
    }
}
