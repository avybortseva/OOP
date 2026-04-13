package ru.nsu.g.a.vybortseva.pizza;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class PizzeriaConfigTest {
    @Test
    void testGettersAndSetters() {
        PizzeriaConfig config = new PizzeriaConfig();
        config.setWarehouseCapacity(10);
        assertEquals(10, config.getWarehouseCapacity());

        List<PizzeriaConfig.BakerConfig> bakers = new ArrayList<>();
        config.setBakers(bakers);
        assertEquals(bakers, config.getBakers());

        List<PizzeriaConfig.CourierConfig> couriers = new ArrayList<>();
        config.setCouriers(couriers);
        assertEquals(couriers, config.getCouriers());
    }

    @Test
    void testBakerConfigGettersSetters() {
        PizzeriaConfig.BakerConfig baker = new PizzeriaConfig.BakerConfig();
        baker.setId(1);
        baker.setSpeed(100);

        assertEquals(1, baker.getId());
        assertEquals(100, baker.getSpeed());
    }

    @Test
    void testCourierConfigGettersSetters() {
        PizzeriaConfig.CourierConfig courier = new PizzeriaConfig.CourierConfig();
        courier.setId(1);
        courier.setSpeed(200);
        courier.setCapacity(5);

        assertEquals(1, courier.getId());
        assertEquals(200, courier.getSpeed());
        assertEquals(5, courier.getCapacity());
    }
}
