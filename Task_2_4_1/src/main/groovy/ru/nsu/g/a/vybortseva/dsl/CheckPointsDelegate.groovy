package ru.nsu.g.a.vybortseva.dsl

import ru.nsu.g.a.vybortseva.model.CheckPoint
import ru.nsu.g.a.vybortseva.model.Config

class CheckPointsDelegate {
    Config config

    CheckPointsDelegate(Config config) {
        this.config = config
    }

    void point(Map params) {
        CheckPoint cp = new CheckPoint(
                params.name as String,
                params.date as String
        )
        config.getPoints().add(cp)
    }
}
