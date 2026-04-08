package ru.nsu.g.a.vybortseva.controller;

import java.util.Map;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import ru.nsu.g.a.vybortseva.config.GameConfig;
import ru.nsu.g.a.vybortseva.model.Direction;
import ru.nsu.g.a.vybortseva.model.Food;
import ru.nsu.g.a.vybortseva.model.GameModel;
import ru.nsu.g.a.vybortseva.view.GameRenderer;

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
    private Label targetLabel;

    private AnimationTimer gameTimer;
    private GameModel model;
    private GameRenderer renderer;
    private double tileSize;
    private final IntegerProperty currentScore = new SimpleIntegerProperty(0);

    /**
     * Initializes controller and listeners.
     */
    @FXML
    public void initialize() {
        this.renderer = new GameRenderer(gameCanvas);
        scoreLabel.textProperty().bind(currentScore.asString("TOTAL LENGTH: %d"));

        setupGame();

        canvasContainer.widthProperty().addListener((obs, old, val) -> layoutCanvas());
        canvasContainer.heightProperty().addListener((obs, old, val) -> layoutCanvas());

        Platform.runLater(() -> {
            layoutCanvas();
            gameCanvas.requestFocus();
        });
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

            this.tileSize = size / model.getConfig().getWidth();
            draw();
        }
    }

    /**
     * Resets game state.
     */
    private void setupGame() {
        GameConfig config = GameConfig.createEasyLevel();
        model = new GameModel(config);

        currentScore.set(model.getSnake().getBody().size());

        updateUi();

        gameCanvas.setFocusTraversable(true);
        gameCanvas.setOnKeyPressed(this::handleKeyPress);
        gameCanvas.requestFocus();

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
            case W, UP -> model.handleDirectionChange(Direction.UP);
            case S, DOWN -> model.handleDirectionChange(Direction.DOWN);
            case A, LEFT -> model.handleDirectionChange(Direction.LEFT);
            case D, RIGHT -> model.handleDirectionChange(Direction.RIGHT);
            default -> {
                // ничего не делаем
            }
        }
    }

    /**
     * Runs game animation timer.
     */
    private void startGameLoop() {
        this.gameTimer = new AnimationTimer() {
            private long lastUpdate = 0;

            @Override
            public void handle(long now) {
                if (now - lastUpdate >= model.getConfig().getTickDelayNanos()) {
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
        this.gameTimer.start();
    }

    /**
     * Renders game objects.
     */
    private void draw() {
        if (model != null && renderer != null) {
            renderer.render(model, tileSize);
        }
    }

    /**
     * Updates labels.
     */
    private void updateUi() {
        targetLabel.setTextFill(Color.WHITE);
        scoreLabel.setTextFill(Color.WHITE);
        currentScore.set(model.getSnake().getBody().size());

        StringBuilder targetsText = new StringBuilder("NECESSARY FOOD:\n");
        Map<Food.FoodType, Integer> targets = model.getConfig().getTargetFoodCounts();
        Map<Food.FoodType, Integer> eaten = model.getEatenCount();

        targets.forEach((type, target) -> {
            targetsText.append(String.format("%s: %d / %d\n", type, eaten.get(type), target));
        });

        targetLabel.setText(targetsText.toString());
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
