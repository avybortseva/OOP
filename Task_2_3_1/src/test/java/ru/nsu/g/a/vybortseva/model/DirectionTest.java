package ru.nsu.g.a.vybortseva.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class DirectionTest {
    @Test
    void testDeltas() {
        assertEquals(-1, Direction.UP.getDy());
        assertEquals(1, Direction.RIGHT.getDx());
    }

    @Test
    void testIsOpposite() {
        assertTrue(Direction.UP.isOpposite(Direction.DOWN));
        assertTrue(Direction.LEFT.isOpposite(Direction.RIGHT));

        assertFalse(Direction.UP.isOpposite(Direction.LEFT));
        assertFalse(Direction.UP.isOpposite(Direction.UP));
    }
}
