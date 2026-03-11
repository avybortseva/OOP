package ru.nsu.g.a.vybortseva.pizza;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CourierTest {
    private Warehouse warehouse;
    private final int COURIER_ID = 1;
    private final int SPEED = 100;
    private final int CAPACITY = 2;

    @BeforeEach
    void setUp() {
        warehouse = new Warehouse(10);
    }

    @Test
    void testInvalidCourierCreation() {
        assertThrows(IllegalArgumentException.class, () ->
                        new Courier(1, -100, 2, warehouse));
    }

    @Test
    void testCourierDeliveryCycle() throws InterruptedException {
        warehouse.add(new Pizza(101));
        warehouse.add(new Pizza(102));
        Courier courier = new Courier(COURIER_ID, SPEED, CAPACITY, warehouse);
        Thread courierThread = new Thread(courier);

        courierThread.start();
        Thread.sleep(SPEED + 200);

        assertEquals(0, warehouse.getCurrentSize());

        courierThread.interrupt();
        courierThread.join(500);
        assertFalse(courierThread.isAlive());
    }

    @Test
    void testCourierCapacityLimit() throws InterruptedException {
        warehouse.add(new Pizza(1));
        warehouse.add(new Pizza(2));
        warehouse.add(new Pizza(3));

        Courier courier = new Courier(COURIER_ID, SPEED, 2, warehouse);
        Thread courierThread = new Thread(courier);

        courierThread.start();
        Thread.sleep(SPEED / 2);
        assertEquals(1, warehouse.getCurrentSize(), "На складе должна остаться одна пицца из трех");

        courierThread.interrupt();
        courierThread.join(500);
    }

    @Test
    void testCourierFinishesDeliveryAfterInterrupt() throws InterruptedException {
        Warehouse warehouse = new Warehouse(5);
        warehouse.add(new Pizza(1));
        warehouse.add(new Pizza(2));

        Courier courier = new Courier(1, 10000, 2, warehouse); // Медленный курьер
        Thread courierThread = new Thread(courier);
        courierThread.start();

        Thread.sleep(2000);
        courierThread.interrupt();
        courierThread.join(2000);

        assertFalse(courierThread.isAlive());
        assertEquals(0, warehouse.getCurrentSize());
    }
}
