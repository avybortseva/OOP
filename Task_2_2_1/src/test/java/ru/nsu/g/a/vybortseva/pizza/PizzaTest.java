package ru.nsu.g.a.vybortseva.pizza;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PizzaTest {
    @Test
    void testPizzaIdInitialization() {
        int expectedId = 42;
        Pizza pizza = new Pizza(expectedId);
        int actualId = pizza.getId();
        assertEquals(expectedId, actualId);
    }

    @Test
    void testPizzaWithDifferentIds() {
        Pizza zeroIdPizza = new Pizza(0);
        Pizza negativeIdPizza = new Pizza(-1);
        assertEquals(0, zeroIdPizza.getId());
        assertEquals(-1, negativeIdPizza.getId());
    }
}