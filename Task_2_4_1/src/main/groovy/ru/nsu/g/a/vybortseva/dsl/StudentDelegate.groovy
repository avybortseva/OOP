package ru.nsu.g.a.vybortseva.dsl

import ru.nsu.g.a.vybortseva.model.Group
import ru.nsu.g.a.vybortseva.model.Student

class StudentDelegate {
    Group group

    StudentDelegate(Group group) {
        this.group = group
    }

    void student(Map<String, String> params) {
        Student newStudent = new Student(
                params.name,
                params.gitName,
                params.repoUrl
        )
        group.addStudent(newStudent)
    }
}
