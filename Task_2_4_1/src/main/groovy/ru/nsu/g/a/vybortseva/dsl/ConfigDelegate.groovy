package ru.nsu.g.a.vybortseva.dsl

import ru.nsu.g.a.vybortseva.model.Config

class ConfigDelegate {
    Config config;

    ConfigDelegate(Config config) {
        this.config = config
    }

    void tasks(Closure cl) {
        def delegate = new TasksDelegate(config)
        cl.delegate = delegate
        cl.resolveStrategy = Closure.DELEGATE_FIRST
        cl()
    }

    void groups(Closure cl) {
        def delegate = new GroupDelegate(config)
        cl.delegate = delegate
        cl.resolveStrategy = Closure.DELEGATE_FIRST
        cl()
    }

    void check(Closure cl) {
        def delegate = new CheckDelegate(config)
        cl.delegate = delegate
        cl.resolveStrategy = Closure.DELEGATE_FIRST
        cl()
    }

    void checkPoints(Closure cl) {
        def delegate = new CheckPointsDelegate(config)
        cl.delegate = delegate
        cl.resolveStrategy = Closure.DELEGATE_FIRST
        cl()
    }

    void bonuses(Closure cl) {
        def delegate = new BonusesDelegate(config)
        cl.delegate = delegate
        cl.resolveStrategy = Closure.DELEGATE_FIRST
        cl()
    }
}