package ru.nsu.g.a.vybortseva.pizza;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
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
    private static final long WORKING_TIME_MS = 10000;
    private static final long ORDER_DELAY_MS = 1000;

    /**
     * Загружает конфигурацию пиццерии из файла в ресурсах.
     */
    public void loadConfig(String fileName) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        File configFile = new File(fileName);
        if (!configFile.exists()) {
            throw new IOException("Файл конфигурации не найден: " + configFile.getAbsolutePath());
        }
        this.config = mapper.readValue(configFile, PizzeriaConfig.class);
    }

    /**
     * Инициализирует склад, очередь и запускает все рабочие потоки.
     */
    public void start() {
        this.warehouse = new Warehouse(config.getWarehouseCapacity());
        this.orderQueue = new OrderQueue();

        for (PizzeriaConfig.BakerConfig bc : config.getBakers()) {
            Thread t = new Thread(new Baker(bc.id, bc.speed, orderQueue, warehouse),
                    "Baker-" + bc.id);
            bakerThreads.add(t);
            t.start();
        }

        for (PizzeriaConfig.CourierConfig cc : config.getCouriers()) {
            Thread t = new Thread(new Courier(cc.id, cc.speed, cc.capacity, warehouse),
                    "Courier-" + cc.id);
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

        List<Thread> allWorkers = new ArrayList<>();
        allWorkers.addAll(bakerThreads);
        allWorkers.addAll(courierThreads);

        for (Thread t : allWorkers) {
            t.interrupt();
        }

        try {
            for (Thread t : allWorkers) {
                t.join();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("--- Пиццерия закрыта. Все работали параллельно до конца. ---");
    }

    /**
     * Точка входа в программу.
     */
    public static void main(String[] args) {
        Bakery bakery = new Bakery();

        String configPath = (args.length > 0) ? args[0] : "config.json";
        try {
            bakery.loadConfig(configPath);
            bakery.start();

            long startTime = System.currentTimeMillis();
            int orderCounter = 1;

            while (System.currentTimeMillis() - startTime < WORKING_TIME_MS) {
                bakery.placeOrder(orderCounter++);
                Thread.sleep(ORDER_DELAY_MS);
            }

            bakery.stop();

        } catch (IOException | InterruptedException e) {
            System.err.println("Ошибка работы пиццерии: " + e.getMessage());
        }
    }
}
