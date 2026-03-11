package ru.nsu.g.a.vybortseva.pizza;

import java.util.List;

/**
 * Класс, представляющий курьера.
 * Курьер забирает одну или несколько пицц со склада (не более объема багажника)
 * и осуществляет их доставку заказчикам.
 */
public class Courier implements CourierInt {
    private final int id;
    private final int speed;
    private final int capacity;
    private List<Pizza> pizzas;
    private final WarehouseInt warehouse;

    /**
     * Создает экземпляр курьера.
     */
    public Courier(int id, int speed, int capacity, WarehouseInt warehouse) {
        if (speed <= 0) {
            throw new IllegalArgumentException("Speed can't be negative");
        }
        this.id = id;
        this.speed = speed;
        this.capacity = capacity;
        this.warehouse = warehouse;
    }

    /**
     * Возвращает уникальный идентификатор курьера.
     */
    public int getId() {
        return id;
    }

    /**
     * Возвращает вместимость багажника курьера.
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Основной цикл работы курьера.
     * Курьер ожидает появления пицц на складе, забирает их и имитирует доставку.
     */
    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted() || warehouse.getCurrentSize() > 0) {
                try {
                    pizzas = warehouse.take(capacity);
                } catch (InterruptedException e) {
                    break;
                }

                for (Pizza p : pizzas) {
                    System.out.println("[" + p.getId() + "] [is delivering]");
                }

                try {
                    Thread.sleep(speed);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }

                for (Pizza p : pizzas) {
                    System.out.println("[" + p.getId() + "] [delivered]");
                }
                pizzas = null;
            }
        } finally {
            System.out.println("Курьер " + id + " закончил смену.");
        }
    }
}
