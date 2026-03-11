package ru.nsu.g.a.vybortseva.pizza;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BakerTest {
    private OrderQueue orderQueue;
    private Warehouse warehouse;
    private final int BakerId = 1;
    private final int Speed = 100;

    @BeforeEach
    void setUp() {
        orderQueue = new OrderQueue();
        warehouse = new Warehouse(5);
    }

    @Test
    void testInvalidBakerSpeed() {
        assertThrows(IllegalArgumentException.class, () ->
                        new Baker(BakerId, 0, orderQueue, warehouse));
    }

    @Test
    void testBakerWorkCycle() throws InterruptedException {
        Pizza pizza = new Pizza(777);
        orderQueue.add(pizza);

        Baker baker = new Baker(BakerId, Speed, orderQueue, warehouse);
        Thread bakerThread = new Thread(baker);

        bakerThread.start();

        Thread.sleep(Speed + 200);
        assertEquals(0, orderQueue.getCurrentSize());
        assertEquals(1, warehouse.getCurrentSize());

        bakerThread.interrupt();
        bakerThread.join(500);
        assertFalse(bakerThread.isAlive());
    }

    @Test
    void testBakerWaitingForOrder() throws InterruptedException {
        Baker baker = new Baker(BakerId, Speed, orderQueue, warehouse);
        Thread bakerThread = new Thread(baker);

        bakerThread.start();
        Thread.sleep(200);

        assertEquals(Thread.State.WAITING, bakerThread.getState());

        bakerThread.interrupt();
        bakerThread.join(500);
    }

    @Test
    void testBakerFinishesPizzaAfterInterrupt() throws InterruptedException {
        OrderQueue queue = new OrderQueue();
        Warehouse warehouse = new Warehouse(1);
        Baker baker = new Baker(1, 100000, queue, warehouse);

        queue.add(new Pizza(1));
        Thread bakerThread = new Thread(baker);
        bakerThread.start();

        Thread.sleep(2000);
        bakerThread.interrupt();
        bakerThread.join(2000);

        assertFalse(bakerThread.isAlive());
        assertEquals(0, queue.getCurrentSize());
        assertEquals(1, warehouse.getCurrentSize());
    }
}
