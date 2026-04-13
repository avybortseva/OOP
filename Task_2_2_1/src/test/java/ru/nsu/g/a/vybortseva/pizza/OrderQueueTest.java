package ru.nsu.g.a.vybortseva.pizza;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OrderQueueTest {
    private OrderQueue orderQueue;

    @BeforeEach
    void setUp() {
        orderQueue = new OrderQueue();
    }

    @Test
    void testAddAndTake() throws InterruptedException {
        Pizza pizza = new Pizza(1);
        orderQueue.add(pizza);
        assertEquals(1, orderQueue.getCurrentSize());
        Pizza takenPizza = orderQueue.take();
        assertEquals(pizza, takenPizza);
        assertEquals(0, orderQueue.getCurrentSize());
    }

    @Test
    void testWaitWhenEmpty() throws InterruptedException {
        Thread consumerThread = new Thread(() -> {
            try {
                orderQueue.take();
            } catch (InterruptedException ignored) {
                Thread.currentThread().interrupt();
            }
        });
        consumerThread.start();
        Thread.sleep(200);
        assertEquals(Thread.State.WAITING, consumerThread.getState());
        consumerThread.interrupt();
    }

    @Test
    void testNotifyOnAdd() throws InterruptedException {
        final Pizza[] result = new Pizza[1];
        Thread consumerThread = new Thread(() -> {
            try {
                result[0] = orderQueue.take();
            } catch (InterruptedException ignored) {
                Thread.currentThread().interrupt();
            }
        });
        consumerThread.start();
        Pizza newPizza = new Pizza(100);
        orderQueue.add(newPizza);
        consumerThread.join(1000);
        assertEquals(newPizza, result[0], "Поток должен проснуться и забрать добавленную пиццу");
    }
}
