package ru.nsu.g.a.vybortseva.model;

import java.util.LinkedList;

/**
 * Snake entity logic.
 */
public class Snake {
    private final LinkedList<Point> body;
    private Direction direction;

    /**
     * Creates snake at start point.
     */
    public Snake(Point start) {
        this.body = new LinkedList<>();
        body.add(start);
        this.direction = Direction.UP;
    }

    /**
     * Sets new movement direction.
     */
    public void setDirection(Direction newDirection) {
        if (direction.isOpposite(newDirection)) {
            return;
        }
        this.direction = newDirection;
    }

    /**
     * Moves snake forward.
     */
    public void move() {
        Point head = body.getFirst();
        Point newHead = new Point(
                head.getX() + direction.getDx(),
                head.getY() + direction.getDy()
        );
        body.addFirst(newHead);
        body.removeLast();
    }

    /**
     * Increases snake length.
     */
    public void grow() {
        Point head = body.getFirst();
        Point newHead = new Point(
                head.getX() + direction.getDx(),
                head.getY() + direction.getDy()
        );
        body.addFirst(newHead);
    }

    /**
     * Gets snake body points.
     */
    public LinkedList<Point> getBody() {
        return body;
    }

    /**
     * Predicts next head position.
     */
    public Point nextHead() {
        int x = body.getFirst().getX();
        int y = body.getFirst().getY();

        return new Point(x + direction.getDx(), y + direction.getDy());
    }
}
