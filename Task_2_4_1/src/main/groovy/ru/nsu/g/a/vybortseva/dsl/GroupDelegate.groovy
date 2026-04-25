package ru.nsu.g.a.vybortseva.dsl

import ru.nsu.g.a.vybortseva.model.Config
import ru.nsu.g.a.vybortseva.model.Group

class GroupDelegate {
    Config config

    GroupDelegate(Config config) {
        this.config = config
    }

    void group(int number, Closure cl) {
        Group newGroup = new Group(number)
        config.getGroups().add(newGroup)

        def studentDelegate = new StudentDelegate(newGroup)
        cl.delegate = studentDelegate
        cl.resolveStrategy = Closure.DELEGATE_FIRST
        cl()
    }
}
