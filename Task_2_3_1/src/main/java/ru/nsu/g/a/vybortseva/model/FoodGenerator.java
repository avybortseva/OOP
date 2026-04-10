package ru.nsu.g.a.vybortseva.model;

import ru.nsu.g.a.vybortseva.config.GameConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FoodGenerator {
    /**
     * Generate all foods.
     */
    public static boolean generateSpecificFood(GameConfig config,
                                               Snake snake,
                                               List<Obstacle> obstacles,
                                               List<Food> foods,
                                               Food.FoodType type) {
        List<Point> freePoints = new ArrayList<>();

        for (int x = 0; x < config.getWidth(); x++) {
            for (int y = 0; y < config.getHeight(); y++) {
                Point p = new Point(x, y);
                if (!isPointOccupied(p, snake, foods, obstacles)) {
                    freePoints.add(p);
                }
            }
        }

        if (!freePoints.isEmpty()) {
            Random random = new Random();
            Point foodPoint = freePoints.get(random.nextInt(freePoints.size()));
            foods.add(new Food(foodPoint, type));
            return true;
        }
        return false;
    }

    private static boolean isPointOccupied(Point p, Snake snake, List<Food> foods, List<Obstacle> obstacles) {
        if (snake != null && snake.getBody().contains(p)) {
            return true;
        }
        for (Food f : foods) {
            if (f.getPosition().equals(p)) {
                return true;
            }
        }
        for (Obstacle obs : obstacles) {
            if (obs.getPoints().contains(p)) {
                return true;
            }
        }
        return false;
    }
}
