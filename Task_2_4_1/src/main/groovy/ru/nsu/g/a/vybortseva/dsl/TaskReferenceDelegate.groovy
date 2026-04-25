package ru.nsu.g.a.vybortseva.dsl

import ru.nsu.g.a.vybortseva.model.Assignment

class TaskReferenceDelegate {
    Assignment assignment

    TaskReferenceDelegate(Assignment assignment) {
        this.assignment = assignment
    }

    void task(String id) {
        assignment.addTask(id)
    }
}
