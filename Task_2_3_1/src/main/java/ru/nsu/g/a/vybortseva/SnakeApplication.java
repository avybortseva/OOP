package ru.nsu.g.a.vybortseva;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main application class.
 */
public class SnakeApplication extends Application {
    /**
     * Sets up game window.
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(
                SnakeApplication.class.getResource("game-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        stage.setMinWidth(600);
        stage.setMinHeight(400);

        stage.setScene(scene);
        stage.setWidth(900);
        stage.setHeight(680);
        stage.show();
    }
}
