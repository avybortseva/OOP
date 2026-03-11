package ru.nsu.g.a.vybortseva.pizza;

import java.util.List;

/**
 * Класс конфигурации пиццерии.
 * Служит моделью для десериализации данных из JSON-файла.
 */
public class PizzeriaConfig {
    private List<BakerConfig> bakers;
    private List<CourierConfig> couriers;
    private int warehouseCapacity;

    /**
     * Список конфигураций пекарей.
     */
    public List<BakerConfig> getBakers() {
        return bakers;
    }

    /**
     * Список конфигураций пекарей.
     */
    public void setBakers(List<BakerConfig> bakers) {
        this.bakers = bakers;
    }

    /**
     * Список конфигураций курьеров.
     */
    public List<CourierConfig> getCouriers() {
        return couriers;
    }

    /**
     * Список конфигураций курьеров.
     */
    public void setCouriers(List<CourierConfig> couriers) {
        this.couriers = couriers;
    }

    /**
     * Вместимость склада.
     */
    public int getWarehouseCapacity() {
        return warehouseCapacity;
    }

    /**
     * Вместимость склада.
     */
    public void setWarehouseCapacity(int warehouseCapacity) {
        this.warehouseCapacity = warehouseCapacity;
    }

    /**
     * Вложенный класс для хранения параметров отдельного пекаря.
     */
    public static class BakerConfig {
        public int id;
        public int speed;

        /** id пекаря. */
        public int getId() {
            return id;
        }

        /** id пекаря. */
        public void setId(int id) {
            this.id = id;
        }

        /** Скорость пекаря. */
        public int getSpeed() {
            return speed;
        }

        /** Скорость пекаря. */
        public void setSpeed(int speed) {
            this.speed = speed;
        }
    }

    /**
     * Конфигурация отдельного курьера.
     */
    public static class CourierConfig {
        public int id;
        public int speed;
        public int capacity;

        /** id курьера. */
        public int getId() {
            return id;
        }

        /** id курьера. */
        public void setId(int id) {
            this.id = id;
        }

        /** Скорость курьера. */
        public int getSpeed() {
            return speed;
        }

        /** Скорость курьера. */
        public void setSpeed(int speed) {
            this.speed = speed;
        }

        /** Вместимость багажника. */
        public int getCapacity() {
            return capacity;
        }

        /** Вместимость багажника. */
        public void setCapacity(int capacity) {
            this.capacity = capacity;
        }
    }
}