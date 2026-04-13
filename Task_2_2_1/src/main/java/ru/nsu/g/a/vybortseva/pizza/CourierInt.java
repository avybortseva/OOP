package ru.nsu.g.a.vybortseva.pizza;

/**
 * Интерфейс, описывающий поведение курьера.
 */
public interface CourierInt extends Runnable {
    /**
     * Возвращает уникальный идентификатор курьера.
     */
    int getId();

    /**
     * Возвращает вместимость багажника курьера.
     */
    int getCapacity();
}
