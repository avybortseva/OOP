package ru.nsu.g.a.vybortseva.pizza;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WarehouseTest {
    private Warehouse warehouse;
    private final int capacity = 2;

    @BeforeEach
    void setUp() {
        warehouse = new Warehouse(capacity);
    }

    @Test
    void testAddAndTakeBatch() throws InterruptedException {
        warehouse.add(new Pizza(1));
        warehouse.add(new Pizza(2));

        assertEquals(2, warehouse.getCurrentSize());

        List<Pizza> delivery = warehouse.take(5);

        assertEquals(2, delivery.size());
        assertEquals(0, warehouse.getCurrentSize());
    }

    @Test
    void testWaitWhenFull() throws InterruptedException {
        warehouse.add(new Pizza(1));
        warehouse.add(new Pizza(2));

        Thread bakerThread = new Thread(() -> {
            try {
                warehouse.add(new Pizza(3));
            } catch (InterruptedException ignored) {
                Thread.currentThread().interrupt();
            }
        });

        bakerThread.start();
        Thread.sleep(300);

        assertEquals(Thread.State.WAITING, bakerThread.getState());

        warehouse.take(1);

        bakerThread.join(1000);
        assertEquals(2, warehouse.getCurrentSize());
    }

    @Test
    void testWaitWhenEmpty() throws InterruptedException {
        Thread courierThread = new Thread(() -> {
            try {
                warehouse.take(1);
            } catch (InterruptedException ignored) {
                Thread.currentThread().interrupt();
            }
        });

        courierThread.start();
        Thread.sleep(300);

        assertEquals(Thread.State.WAITING, courierThread.getState());

        warehouse.add(new Pizza(10));
        courierThread.join(1000);

        assertEquals(0, warehouse.getCurrentSize());
    }
}
