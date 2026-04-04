package ru.nsu.g.a.vybortseva.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class PointTest {

    @Test
    void testConstructorAndGetters() {
        Point point = new Point(5, 10);

        assertEquals(5, point.getX());
        assertEquals(10, point.getY());
    }

    @Test
    void testEqualsSameObject() {
        Point p = new Point(3, 4);
        assertTrue(p.equals(p));
    }

    @Test
    void testEqualsEqualObjects() {
        Point p1 = new Point(7, 8);
        Point p2 = new Point(7, 8);
        assertEquals(p1, p2);
        assertEquals(p2, p1);
    }

    @Test
    void testEqualsDifferentObjects() {
        Point basePoint = new Point(2, 2);

        Point differentX = new Point(9, 2);
        Point differentY = new Point(2, 9);
        Point differentBoth = new Point(9, 9);

        assertNotEquals(basePoint, differentX);
        assertNotEquals(basePoint, differentY);
        assertNotEquals(basePoint, differentBoth);
    }

    @Test
    void testEqualsWithNullAndOtherClasses() {
        Point p = new Point(1, 1);
        assertNotEquals(null, p);
        assertNotEquals("Просто строка", p);
    }

    @Test
    void testHashCode() {
        Point p1 = new Point(5, 5);
        Point p2 = new Point(5, 5);
        Point p3 = new Point(10, 10);
        assertEquals(p1.hashCode(), p2.hashCode());
        assertNotEquals(p1.hashCode(), p3.hashCode());
    }

    @Test
    void testToString() {
        Point p = new Point(-3, 15);
        String expectedString = "Point{x=-3, y=15}";
        assertEquals(expectedString, p.toString());
    }
}
