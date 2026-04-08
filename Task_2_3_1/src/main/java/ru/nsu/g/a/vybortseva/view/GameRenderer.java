package ru.nsu.g.a.vybortseva.view;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import ru.nsu.g.a.vybortseva.model.Food;
import java.util.List;

import ru.nsu.g.a.vybortseva.model.GameModel;
import ru.nsu.g.a.vybortseva.model.Obstacle;
import ru.nsu.g.a.vybortseva.model.Point;

/**
 * Handles graphical rendering of game components.
 */
public class GameRenderer {
    private final Canvas gameCanvas;

    /**
     * Creates renderer for specific canvas.
     */
    public GameRenderer(Canvas gameCanvas) {
        this.gameCanvas = gameCanvas;
    }

    /**
     * Renders full game frame.
     */
    public void render(GameModel model, double tileSize) {
        GraphicsContext gc = gameCanvas.getGraphicsContext2D();

        gc.clearRect(0, 0, gameCanvas.getWidth(), gameCanvas.getHeight());
        int cols = model.getConfig().getWidth();
        int rows = model.getConfig().getHeight();
        drawBackground(gc, cols, rows, tileSize);

        drawObstacles(gc, model.getObstacles(), tileSize);

        drawFood(gc, model.getFoods(), tileSize);

        drawSnake(gc, model, tileSize);
    }

    /**
     * Draws eyes on snake head.
     */
    private void drawEyes(GraphicsContext gc, Point head, double tileSize) {
        gc.setFill(Color.WHITE);
        double s = tileSize * 0.15;
        gc.fillOval(head.getX() * tileSize + tileSize / 4,
                head.getY() * tileSize + tileSize / 4, s, s);
        gc.fillOval(head.getX() * tileSize + tileSize*0.6,
                head.getY() * tileSize + tileSize / 4, s, s);
    }

    /**
     * Draws checkered game field.
     */
    private void drawBackground(GraphicsContext gc, int cols, int rows, double tileSize) {
        for (int x = 0; x < cols; x++) {
            for (int y = 0; y < rows; y++) {
                if ((x + y) % 2 == 0) {
                    gc.setFill(Color.web("#aad751"));
                } else {
                    gc.setFill(Color.web("#a2d149"));
                }
                gc.fillRect(x * tileSize, y * tileSize, tileSize, tileSize);
            }
        }
    }

    /**
     * Draws static map obstacles.
     */
    private void drawObstacles(GraphicsContext gc, List<Obstacle> obstacles, double tileSize) {
        gc.setFill(Color.web("#4a5a4a"));
        for (Obstacle obs : obstacles) {
            for (Point p : obs.getPoints()) {
                gc.fillRect(p.getX() * tileSize, p.getY() * tileSize, tileSize, tileSize);

                gc.setStroke(Color.BLACK);
                gc.setLineWidth(0.5);
                gc.strokeRect(p.getX() * tileSize + 0.5,
                        p.getY() * tileSize + 0.5, tileSize - 1, tileSize - 1);
            }
        }
    }

    /**
     * Draws all active food items.
     */
    private void drawFood(GraphicsContext gc, List<Food> foods, double tileSize) {
        for (Food food : foods) {
            Point p = food.getPosition();
            gc.setFill(Color.web(food.getType().getColorHex()));
            gc.fillOval(p.getX() * tileSize + (tileSize * 0.1),
                    p.getY() * tileSize + (tileSize * 0.1),
                    tileSize * 0.8, tileSize * 0.8);
        }
    }

    /**
     * Draws snake with head and body gradient.
     */
    private void drawSnake(GraphicsContext gc, GameModel model, double tileSize) {
        List<Point> body = model.getSnake().getBody();

        for (int i = 0; i < body.size(); i++) {
            Point p = body.get(i);

            if (i == 0) {
                Color headColor;
                if (model.isGameOver()) {
                    headColor = Color.web("#FF6B6B");
                } else {
                    headColor = Color.web("#4EA8DE");
                }
                gc.setFill(headColor);
                gc.fillRoundRect(p.getX() * tileSize, p.getY() * tileSize,
                        tileSize, tileSize, 12, 12);
                drawEyes(gc, p, tileSize);
            } else {
                Color startColor = Color.web("#4895EF");
                Color endColor = Color.WHITE;

                double fraction = Math.min((double) i / (body.size() + 5), 0.9);
                Color segmentColor = startColor.interpolate(endColor, fraction);

                gc.setFill(segmentColor);
                gc.fillRoundRect(p.getX() * tileSize + 1.5, p.getY() * tileSize + 1.5,
                        tileSize - 3, tileSize - 3, 8, 8);
            }
        }
    }
}
