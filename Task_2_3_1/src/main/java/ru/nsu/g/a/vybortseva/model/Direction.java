package ru.nsu.g.a.vybortseva.model;

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

    public int getDy() {
        return dy;
    }

    public int getDx() {
        return dx;
    }

    public boolean isOpposite(Direction newDirection) {
        return (this.dx + newDirection.dx == 0)
                && (this.dy + newDirection.dy == 0);
    }
}
