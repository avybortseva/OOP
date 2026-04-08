package ru.nsu.g.a.vybortseva.model;

/**
 * Represents a food item on the board.
 */
public class Food {
    private final Point position;
    private final FoodType type;

    /**
     * Possible types of food with different visual representations.
     */
    public enum FoodType {
        RED("#e74c3c"),
        BLUE("#3498db"),
        ORANGE("#f39c12");

        private final String colorHex;

        FoodType(String colorHex) {
            this.colorHex = colorHex;
        }

        /**
         * Returns hex color code.
         */
        public String getColorHex() {
            return colorHex;
        }
    }

    /**
     * Constructs a new Food item.
     */
    public Food(Point position, FoodType type) {
        this.position = position;
        this.type = type;
    }

    /**
     * Gets the current position of the food.
     */
    public Point getPosition() {
        return position;
    }

    /**
     * Gets the type of the food.
     */
    public FoodType getType() {
        return type;
    }

    /**
     * Generates a hash code based on the food's position.
     */
    @Override
    public int hashCode() {
        return position.hashCode();
    }

    /**
     * Compares this food with another object.
     * Supports comparison with other Food objects or direct Point objects.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof Food food) {
            return position.equals(food.position);
        }
        if (o instanceof Point p) {
            return position.equals(p);
        }
        return false;
    }
}
