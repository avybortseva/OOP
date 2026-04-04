module ru.nsu.g.a.vybortseva {
    requires javafx.controls;
    requires javafx.fxml;

    opens ru.nsu.g.a.vybortseva to javafx.fxml;
    opens ru.nsu.g.a.vybortseva.controller to javafx.fxml;

    exports ru.nsu.g.a.vybortseva;
    opens ru.nsu.g.a.vybortseva.model to javafx.fxml;
    exports ru.nsu.g.a.vybortseva.controller;
}
