package ru.nsu.g.a.vybortseva.pizza;

import java.util.LinkedList;

/**
 * Общая очередь заказов на пиццу.
 * Обеспечивает передачу заказов от системы к свободным пекарям.
 */
public class OrderQueue {
    private final LinkedList<Pizza> queue;

    /**
     * Создает пустую очередь заказов.
     */
    public OrderQueue() {
        this.queue = new LinkedList<>();
    }

    /**
     * Возвращает текущее количество заказов в очереди.
     */
    public synchronized int getCurrentSize() {
        return queue.size();
    }

    /**
     * Добавляет новый заказ в очередь.
     */
    public synchronized void add(Pizza pizza) throws InterruptedException {
        queue.add(pizza);
        notifyAll();
    }

    /**
     * Извлекает заказ из очереди для исполнения пекарем.
     * Если заказов нет, поток переходит в состояние ожидания.
     */
    public synchronized Pizza take() throws InterruptedException {
        while (queue.isEmpty()) {
            wait();
        }
        return queue.removeFirst();
    }
}
