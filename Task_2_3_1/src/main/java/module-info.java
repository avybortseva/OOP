module ru.nsu.g.a.vybortseva {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    opens ru.nsu.g.a.vybortseva to javafx.graphics, javafx.fxml;
    opens ru.nsu.g.a.vybortseva.controller to javafx.fxml;

    exports ru.nsu.g.a.vybortseva;
    exports ru.nsu.g.a.vybortseva.config;
    opens ru.nsu.g.a.vybortseva.config to javafx.fxml, javafx.graphics;
}
