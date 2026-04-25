tasks {
    task(id: '2_1_1', title: 'DSL', maxPoints: 20, soft: '2026-05-01', hard: '2026-05-10')
}

check {
    group(12345) {
        task '2_1_1'
    }
}

groups {
    group(12345) {
        student(name: 'Иванов Иван', gitName: 'ivanov-git', repoUrl: 'https://github.com/avybortseva/OOP/')
//        student(name: 'Петров Петр', gitName: 'petrov-git', repoUrl: 'https://github.com/petrov/repo')
    }
}

checkPoints {
    point(name: 'Первый месяц', date: '2026-03-01')
    point(name: 'Середина семестра', date: '2026-04-15')
    point(name: 'Финальный зачет', date: '2026-05-25')
}
