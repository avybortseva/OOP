package ru.nsu.g.a.vybortseva.pizza;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BakerTest {
    private OrderQueue orderQueue;
    private Warehouse warehouse;
    private final int BAKER_ID = 1;
    private final int SPEED = 100;

    @BeforeEach
    void setUp() {
        orderQueue = new OrderQueue();
        warehouse = new Warehouse(5);
    }

    @Test
    void testInvalidBakerSpeed() {
        assertThrows(IllegalArgumentException.class, () ->
                        new Baker(BAKER_ID, 0, orderQueue, warehouse));
    }

    @Test
    void testBakerWorkCycle() throws InterruptedException {
        Pizza pizza = new Pizza(777);
        orderQueue.add(pizza);

        Baker baker = new Baker(BAKER_ID, SPEED, orderQueue, warehouse);
        Thread bakerThread = new Thread(baker);

        bakerThread.start();

        Thread.sleep(SPEED + 200);
        assertEquals(0, orderQueue.getCurrentSize());
        assertEquals(1, warehouse.getCurrentSize());

        bakerThread.interrupt();
        bakerThread.join(500);
        assertFalse(bakerThread.isAlive());
    }

    @Test
    void testBakerWaitingForOrder() throws InterruptedException {
        Baker baker = new Baker(BAKER_ID, SPEED, orderQueue, warehouse);
        Thread bakerThread = new Thread(baker);

        bakerThread.start();
        Thread.sleep(200);

        assertEquals(Thread.State.WAITING, bakerThread.getState());

        bakerThread.interrupt();
        bakerThread.join(500);
    }
}
