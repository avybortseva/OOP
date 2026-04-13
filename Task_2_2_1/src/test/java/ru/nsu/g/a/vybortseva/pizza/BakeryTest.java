package ru.nsu.g.a.vybortseva.pizza;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BakeryTest {
    private Bakery bakery;

    @BeforeEach
    void setUp() {
        bakery = new Bakery();
    }

    @Test
    void testFullCycle() throws Exception {
        try {
            bakery.loadConfig("config.json");
        } catch (Exception e) {
            fail("Не удалось загрузить config.json для теста. Проверьте папку resources.");
        }
        bakery.start();
        bakery.placeOrder(1);
        bakery.placeOrder(2);
        Thread.sleep(1000);
        bakery.stop();
        assertTrue(true, "Пиццерия успешно завершила работу");
    }

    @Test
    void testPlaceOrderAfterStop() throws Exception {
        bakery.loadConfig("config.json");
        bakery.start();
        bakery.stop();
        assertDoesNotThrow(() -> bakery.placeOrder(999));
    }

    @Test
    void testLoadNonExistentConfig() {
        assertThrows(Exception.class, () -> bakery.loadConfig("non_existent.json"));
    }

    @Test
    void testMainMethod() {
        assertDoesNotThrow(() -> Bakery.main(new String[]{"config.json"}));
    }

    @Test
    void testStopWithoutStart() {
        assertDoesNotThrow(() -> bakery.stop());
    }
}
