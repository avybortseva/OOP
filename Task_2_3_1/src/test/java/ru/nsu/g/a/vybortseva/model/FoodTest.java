package ru.nsu.g.a.vybortseva.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FoodTest {

    @Test
    void testFoodCreation() {
        Point p = new Point(5, 5);
        Food food = new Food(p, Food.FoodType.RED);

        assertEquals(p, food.getPosition());
        assertEquals(Food.FoodType.RED, food.getType());
    }

    @Test
    void testEqualsSamePosition() {
        Point p1 = new Point(10, 20);
        Point p2 = new Point(10, 20);

        Food food1 = new Food(p1, Food.FoodType.RED);
        Food food2 = new Food(p2, Food.FoodType.BLUE);

        assertEquals(food1, food2);
    }

    @Test
    void testEqualsWithPoint() {
        Point p = new Point(3, 4);
        Food food = new Food(p, Food.FoodType.ORANGE);
        assertTrue(food.equals(p));
    }

    @Test
    void testEqualsInvalid() {
        Food food = new Food(new Point(1, 1), Food.FoodType.RED);

        assertNotEquals(null, food);
        assertNotEquals("Some String", food);
    }

    @Test
    void testHashCode() {
        Point p = new Point(7, 8);
        Food food = new Food(p, Food.FoodType.RED);

        assertEquals(p.hashCode(), food.hashCode());
    }

    @Test
    void testFoodTypeColors() {
        assertEquals("#e74c3c", Food.FoodType.RED.getColorHex());
        assertEquals("#3498db", Food.FoodType.BLUE.getColorHex());
        assertEquals("#f39c12", Food.FoodType.ORANGE.getColorHex());
    }
}
