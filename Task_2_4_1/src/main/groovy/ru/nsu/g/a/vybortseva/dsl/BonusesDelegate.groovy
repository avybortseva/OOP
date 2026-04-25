package ru.nsu.g.a.vybortseva.dsl

import ru.nsu.g.a.vybortseva.model.Bonus
import ru.nsu.g.a.vybortseva.model.Config

class BonusesDelegate {
    Config config

    BonusesDelegate(Config config) {
        this.config = config
    }

    void bonus(Map params) {
        Bonus newBonus = new Bonus(
                params.student as String,
                params.task as String,
                params.points as int
        )
        config.getBonuses().add(newBonus)
    }
}
