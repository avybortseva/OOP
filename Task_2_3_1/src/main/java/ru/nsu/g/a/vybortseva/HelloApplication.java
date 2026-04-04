package ru.nsu.g.a.vybortseva;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(
                HelloApplication.class.getResource("game-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        stage.setMinWidth(600);
        stage.setMinHeight(400);

        stage.setScene(scene);
        stage.setWidth(900);
        stage.setHeight(680);
        stage.show();
    }
}
