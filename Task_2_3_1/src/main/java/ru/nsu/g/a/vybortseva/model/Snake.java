package ru.nsu.g.a.vybortseva.model;

import java.util.LinkedList;

public class Snake {
    private final LinkedList<Point> body;
    private Direction direction;

    public Snake(Point start) {
        this.body = new LinkedList<>();
        body.add(start);
        this.direction = Direction.UP;
    }

    public void setDirection(Direction newDirection) {
        if (direction.isOpposite(newDirection)) {
            return;
        }
        this.direction = newDirection;
    }

    public void move() {
        Point head = body.getFirst();
        Point newHead = new Point(
                head.getX() + direction.getDx(),
                head.getY() + direction.getDy()
        );
        body.addFirst(newHead);
        body.removeLast();
    }

    public void grow() {
        Point head = body.getFirst();
        Point newHead = new Point(
                head.getX() + direction.getDx(),
                head.getY() + direction.getDy()
        );
        body.addFirst(newHead);
    }

    public LinkedList<Point> getBody() {
        return body;
    }

    public Point nextHead() {
        int x = body.getFirst().getX();
        int y = body.getFirst().getY();

        return new Point(x + direction.getDx(), y + direction.getDy());
    }
}
