package ru.nsu.g.a.vybortseva.pizza;

import java.util.List;

/**
 * Класс, представляющий курьера.
 * Курьер забирает одну или несколько пицц со склада (не более объема багажника)
 * и осуществляет их доставку заказчикам.
 */
public class Courier implements Runnable {
    private final int id;
    private final int speed;
    private final int capacity;
    private List<Pizza> pizzas;
    private final Warehouse warehouse;

    /**
     * Создает экземпляр курьера.
     */
    public Courier(int id, int speed, int capacity, Warehouse warehouse) {
        if (speed <= 0) {
            throw new IllegalArgumentException("Speed can't be negative");
        }
        this.id = id;
        this.speed = speed;
        this.capacity = capacity;
        this.warehouse = warehouse;
    }

    /**
     * Основной цикл работы курьера.
     * Курьер ожидает появления пицц на складе, забирает их и имитирует доставку.
     */
    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                pizzas = warehouse.take(capacity);

                for (Pizza p : pizzas) {
                    System.out.println("[" + p.getId() + "] [is delivering]");
                }
                Thread.sleep(speed);

                for (Pizza p : pizzas) {
                    System.out.println("[" + p.getId() + "] [delivered]");
                }
                pizzas = null;
            }
        } catch (InterruptedException e) {
            System.out.println("Курьер " + id + " закончил смену.");
            Thread.currentThread().interrupt();
        }
    }
}
