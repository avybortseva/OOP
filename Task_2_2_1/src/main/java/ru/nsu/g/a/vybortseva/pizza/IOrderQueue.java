package ru.nsu.g.a.vybortseva.pizza;

/**
 * Интерфейс очереди заказов.
 * Описывает контракт взаимодействия между системой приема заказов и пекарями.
 */
public interface IOrderQueue {
    /**
     * Добавляет новый заказ в очередь.
     */
    void add(Pizza pizza) throws InterruptedException;

    /**
     * Извлекает заказ из очереди.
     * Если очередь пуста, ожидающий поток блокируется.
     */
    Pizza take() throws InterruptedException;

    /**
     * Возвращает текущее количество заказов.
     */
    int getCurrentSize();
}
