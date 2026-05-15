tasks {
    task(id: '1_1_1', title: '1_1_1', soft: '2025-05-01', hard: '2026-02-10')
    task(id: '2_1_1', title: '2_1_1', soft: '2026-05-01', hard: '2026-05-10')
}

bonuses {
    bonus(student: 'vybortseva-git', task: '1_1_1', points: 2.0)
    bonus(student: 'markidonov-git', task: '2_1_1', points: 2.0)
}

check {
    group(24213) {
        task '1_1_1'
        task '2_1_1'
    }
    group(24214) {
        task '2_1_1'
    }
}

groups {
    group(24213) {
        student(id: '001', name: 'Выборцева Анастасия', gitName: 'vybortseva-git', repoUrl: 'https://github.com/avybortseva/OOP')
        student(id: '002', name: 'Изместьев Денис', gitName: 'izmestev-git', repoUrl: 'https://github.com/s2kach/OOP')
    }
    group(24214) {
        student(id: '003', name: 'Маркидонов Владимир', gitName: 'markidonov-git', repoUrl: 'https://github.com/PytoByte/OOP')
    }
}

checkPoints {
    point(name: 'Первый месяц', date: '2026-03-01')
    point(name: 'Середина семестра', date: '2026-04-15')
    point(name: 'Финальный зачет', date: '2026-05-25')
}
