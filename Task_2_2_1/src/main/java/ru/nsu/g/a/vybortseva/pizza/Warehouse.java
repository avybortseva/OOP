package ru.nsu.g.a.vybortseva.pizza;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Склад готовой продукции.
 * Служит буфером между пекарями и курьерами с ограниченной вместимостью.
 */
public class Warehouse implements IWarehouse {
    private final LinkedList<Pizza> queue;
    private final int capacity;

    /**
     * Создает склад с заданной вместимостью.
     */
    public Warehouse(int capacity) {
        this.queue = new LinkedList<Pizza>();
        this.capacity = capacity;
    }

    /**
     * Возвращает текущее количество пицц на складе.
     */
    public synchronized int getCurrentSize() {
        return queue.size();
    }

    /**
     * Добавляет готовую пиццу на склад.
     * Если склад заполнен, вызывающий поток переходит в состояние ожидания.
     */
    public synchronized void add(Pizza pizza) throws InterruptedException {
        while (queue.size() >= capacity) {
            wait();
        }
        queue.add(pizza);
        notifyAll();
    }

    /**
     * Забирает со склада несколько пицц (не более указанного количества).
     * Если склад пуст, поток ожидает появления хотя бы одной пиццы.
     */
    public synchronized List<Pizza> take(int courierCapacity) throws InterruptedException {
        while (queue.isEmpty()) {
            wait();
        }
        ArrayList<Pizza> pizzas = new ArrayList<>();
        while (!queue.isEmpty() && pizzas.size() < courierCapacity) {
            pizzas.add(queue.removeFirst());
        }
        notifyAll();
        return pizzas;
    }
}
