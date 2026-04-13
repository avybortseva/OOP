package ru.nsu.g.a.vybortseva.pizza;

/**
 * Интерфейс, описывающий поведение пекаря.
 */
public interface BakerInt extends Runnable {
    /**
     * Возвращает текущее состояние пекаря.
     */
    Baker.State getState();

    /**
     * Возвращает уникальный идентификатор пекаря.
     */
    int getId();
}
