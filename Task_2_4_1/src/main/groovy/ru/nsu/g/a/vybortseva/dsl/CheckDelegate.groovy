package ru.nsu.g.a.vybortseva.dsl

import ru.nsu.g.a.vybortseva.model.Assignment
import ru.nsu.g.a.vybortseva.model.Config

class CheckDelegate {
    Config config

    CheckDelegate(Config config) {
        this.config = config
    }

    void group(int number, Closure cl) {
        Assignment assignment = new Assignment(number)
        config.getAssignments().add(assignment)

        def taskRefDelegate = new TaskReferenceDelegate(assignment)
        cl.delegate = taskRefDelegate
        cl.resolveStrategy = Closure.DELEGATE_FIRST
        cl()
    }
}
