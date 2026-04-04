package ru.nsu.g.a.vybortseva.controller;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import ru.nsu.g.a.vybortseva.model.Direction;
import ru.nsu.g.a.vybortseva.model.GameModel;
import ru.nsu.g.a.vybortseva.model.Point;

/**
 * Game UI controller.
 */
public class GameController {
    @FXML
    private Canvas gameCanvas;
    @FXML
    private Pane canvasContainer;
    @FXML
    private Label scoreLabel;

    @FXML
    private Label bestScoreLabel;
    @FXML
    private Label targetLabel;

    private int bestScore = 0;
    private AnimationTimer gameTimer;

    private GameModel model;
    private double tileSize;

    /**
     * Initializes controller and listeners.
     */
    @FXML
    public void initialize() {
        setupGame();

        canvasContainer.widthProperty().addListener((obs, old, val) -> layoutCanvas());
        canvasContainer.heightProperty().addListener((obs, old, val) -> layoutCanvas());

        Platform.runLater(this::layoutCanvas);
    }

    /**
     * Aligns canvas within container.
     */
    private void layoutCanvas() {
        double w = canvasContainer.getWidth();
        double h = canvasContainer.getHeight();

        double size = Math.min(w, h);

        if (size > 0) {
            gameCanvas.setWidth(size);
            gameCanvas.setHeight(size);

            gameCanvas.setLayoutX((w - size) / 2);
            gameCanvas.setLayoutY((h - size) / 2);

            this.tileSize = size / 16;
            draw();
        }
    }

    /**
     * Resets game state.
     */
    private void setupGame() {
        model = new GameModel(16, 16, 10, 50);
        targetLabel.setText("Target: ");
        gameCanvas.setFocusTraversable(true);
        gameCanvas.setOnKeyPressed(this::handleKeyPress);

        if (gameTimer != null) {
            gameTimer.stop();
        }
        startGameLoop();
    }

    /**
     * Handles restart button.
     */
    @FXML
    private void handleRestart() {
        setupGame();
        gameCanvas.requestFocus();
    }

    /**
     * Processes keyboard input.
     */
    private void handleKeyPress(KeyEvent event) {
        switch (event.getCode()) {
            case W, UP -> model.getSnake().setDirection(Direction.UP);
            case S, DOWN -> model.getSnake().setDirection(Direction.DOWN);
            case A, LEFT -> model.getSnake().setDirection(Direction.LEFT);
            case D, RIGHT -> model.getSnake().setDirection(Direction.RIGHT);
        }
    }

    /**
     * Runs game animation timer.
     */
    private void startGameLoop() {
        AnimationTimer timer = new AnimationTimer() {
            private long lastUpdate = 0;

            @Override
            public void handle(long now) {
                if (now - lastUpdate >= 150000000) {  // 150 мс
                    model.update();
                    draw();
                    updateUi();
                    lastUpdate = now;

                    if (model.isGameOver()) {
                        stop();
                        showGameOver();
                    }
                    if (model.isGameWin()) {
                        stop();
                        showWin();
                    }
                }
            }
        };
        timer.start();
    }

    /**
     * Renders game objects.
     */
    private void draw() {
        GraphicsContext gc = gameCanvas.getGraphicsContext2D();
        gc.clearRect(0, 0, gameCanvas.getWidth(), gameCanvas.getHeight());

        for (int x = 0; x < 16; x++) {
            for (int y = 0; y < 16; y++) {
                if ((x + y) % 2 == 0) {
                    gc.setFill(Color.web("#2c3e50"));
                } else {
                    gc.setFill(Color.web("#34495e"));
                }
                gc.fillRect(x * tileSize, y * tileSize, tileSize, tileSize);
            }
        }

        gc.setFill(Color.PINK);
        for (Point p : model.getFoods()) {
            gc.fillOval(p.getX() * tileSize + (tileSize * 0.1),
                    p.getY() * tileSize + (tileSize * 0.1),
                    tileSize * 0.8, tileSize * 0.8);
        }

        gc.setFill(Color.GREEN);
        for (Point p : model.getSnake().getBody()) {
            gc.fillRect(p.getX() * tileSize + 1, p.getY() * tileSize + 1,
                    tileSize - 2, tileSize - 2);
        }
    }

    /**
     * Updates labels.
     */
    private void updateUi() {
        int currentScore = model.getSnake().getBody().size();
        scoreLabel.setText("Current Length: " + currentScore);

        if (currentScore > bestScore) {
            bestScore = currentScore;
            bestScoreLabel.setText("Best Length: " + bestScore);
        }
    }

    /**
     * Displays game over text.
     */
    private void showGameOver() {
        GraphicsContext gc = gameCanvas.getGraphicsContext2D();
        gc.setFill(Color.RED);
        gc.setFont(new javafx.scene.text.Font("Arial", 40));
        gc.fillText("GAME OVER", gameCanvas.getWidth() / 4, gameCanvas.getHeight() / 2);
    }

    /**
     * Displays win text.
     */
    private void showWin() {
        GraphicsContext gc = gameCanvas.getGraphicsContext2D();
        gc.setFill(Color.GOLD);
        gc.setFont(new javafx.scene.text.Font("Arial", 40));
        gc.fillText("YOU WIN!", gameCanvas.getWidth() / 3, gameCanvas.getHeight() / 2);
    }
}
