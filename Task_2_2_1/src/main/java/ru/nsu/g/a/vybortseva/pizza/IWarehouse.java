package ru.nsu.g.a.vybortseva.pizza;

import java.util.List;

/**
 * Интерфейс склада готовой продукции.
 */
public interface IWarehouse {
    /**
     * Добавляет готовую пиццу на склад.
     */
    void add(Pizza pizza) throws InterruptedException;

    /**
     * Забирает со склада пиццы в соответствии с вместимостью курьера.
     */
    List<Pizza> take(int courierCapacity) throws InterruptedException;

    /**
     * Возвращает текущее количество пицц на складе.
     */
    int getCurrentSize();
}
