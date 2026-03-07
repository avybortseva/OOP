package ru.nsu.g.a.vybortseva.pizza;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Главный класс управления пиццерией.
 * Отвечает за загрузку конфигурации, инициализацию потоков пекарей и курьеров,
 * прием заказов и корректное завершение работы.
 */
public class Bakery {
    private PizzeriaConfig config;
    private final List<Thread> bakerThreads = new ArrayList<>();
    private final List<Thread> courierThreads = new ArrayList<>();
    private OrderQueue orderQueue;
    private Warehouse warehouse;
    private volatile boolean isOpened = true;

    /**
     * Загружает конфигурацию пиццерии из файла в ресурсах.
     */
    public void loadConfig(String fileName) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        this.config = mapper.readValue(
                getClass().getClassLoader().getResourceAsStream(fileName),
                PizzeriaConfig.class
        );
    }

    /**
     * Инициализирует склад, очередь и запускает все рабочие потоки.
     */
    public void start() {
        this.warehouse = new Warehouse(config.getWarehouseCapacity());
        this.orderQueue = new OrderQueue();

        for (PizzeriaConfig.BakerConfig bc : config.getBakers()) {
            Thread t = new Thread(new Baker(bc.id, bc.speed, orderQueue, warehouse), "Baker-" + bc.id);
            bakerThreads.add(t);
            t.start();
        }

        for (PizzeriaConfig.CourierConfig cc : config.getCouriers()) {
            Thread t = new Thread(new Courier(cc.id, cc.speed, cc.capacity, warehouse), "Courier-" + cc.id);
            courierThreads.add(t);
            t.start();
        }
        System.out.println("Пиццерия открыта!");
    }

    /**
     * Добавляет новый заказ в систему, если пиццерия открыта.
     */
    public void placeOrder(int orderId) throws InterruptedException {
        if (isOpened) {
            orderQueue.add(new Pizza(orderId));
            System.out.println("[" + orderId + "] [ordered]");
        }
    }

    /**
     * Останавливает прием заказов и дожидается выполнения всех текущих задач.
     * После очистки очереди и склада завершает работу всех потоков.
     */
    public void stop() {
        System.out.println("--- Закрываем прием заказов. Доделываем остатки... ---");
        isOpened = false;

        try {
            while (orderQueue.getCurrentSize() > 0 || warehouse.getCurrentSize() > 0) {
                Thread.sleep(500);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("--- Все заказы выполнены. Останавливаем потоки. ---");
        for (Thread t : bakerThreads) t.interrupt();
        for (Thread t : courierThreads) t.interrupt();
    }

    /**
     * Точка входа в программу.
     */
    public static void main(String[] args) {
        Bakery bakery = new Bakery();
        try {
            bakery.loadConfig("config.json");
            bakery.start();

            long startTime = System.currentTimeMillis();
            int orderCounter = 1;

            while (System.currentTimeMillis() - startTime < 10000) {
                bakery.placeOrder(orderCounter++);
                Thread.sleep(1000);
            }

            bakery.stop();

        } catch (IOException | InterruptedException e) {
            System.err.println("Ошибка работы пиццерии: " + e.getMessage());
        }
    }
}
