package ru.nsu.g.a.vybortseva.dsl

import ru.nsu.g.a.vybortseva.model.Config
import ru.nsu.g.a.vybortseva.model.Task

class TasksDelegate {
    Config config

    TasksDelegate(Config config) {
        this.config = config
    }

    void task(Map params) {
        Task newTask = new Task(
                params.id as String,
                params.title as String,
                params.maxPoints as Integer,
                params.soft as String,
                params.hard as String
        )
        config.getTasks().add(newTask)
    }
}
