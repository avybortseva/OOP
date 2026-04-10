package ru.nsu.g.a.vybortseva.model;

import ru.nsu.g.a.vybortseva.config.GameConfig;

import java.util.*;

public class ObstaclesGenerator {

    /**
     * Generate all obstacles.
     */
    public static List<Obstacle> generateAll(GameConfig config) {
        List<Obstacle> obstacles = new ArrayList<>();
        Map<Integer, Integer> setup = config.getObstacleDistribution();

        setup.forEach((length, count) -> {
            for (int i = 0; i < count; i++) {
                createAndAddObstacle(length, config, obstacles);
            }
        });

        return obstacles;
    }

    /**
     * Tries to create and add a single obstacle of specific length to the game.
     * Uses a while loop to keep trying until a valid obstacle shape is formed.
     */
    private static void createAndAddObstacle(int targetLen, GameConfig config, List<Obstacle> obstacles) {
        Random random = new Random();
        boolean obstacleCreated = false;

        while (!obstacleCreated) {
            Obstacle newObs = new Obstacle();
            Point start = null;
            int startAttempts = 0;

            do {
                Point p = new Point(random.nextInt(config.getWidth()),
                        random.nextInt(config.getHeight()));
                if (Obstacle.isValidPoint(p, config.getWidth(),
                        config.getHeight(), obstacles, newObs.getPoints())) {
                    start = p;
                }
                startAttempts++;
            } while (start == null && startAttempts < 100);

            if (start == null) {
                continue;
            }

            newObs.addPoint(start);
            for (int i = 1; i < targetLen; i++) {
                Point lastPoint = newObs.getPoints().getLast();
                List<Point> neighbors = getNeighbors(lastPoint);
                Collections.shuffle(neighbors);

                boolean pointAdded = false;
                for (Point neighbor : neighbors) {
                    if (Obstacle.isValidPoint(neighbor, config.getWidth(), config.getHeight(),
                            obstacles, newObs.getPoints())) {
                        newObs.addPoint(neighbor);
                        pointAdded = true;
                        break;
                    }
                }
                if (!pointAdded) {
                    break;
                }
            }
            if (newObs.getPoints().size() == targetLen) {
                obstacles.add(newObs);
                obstacleCreated = true;
            }
        }
    }

    private static List<Point> getNeighbors(Point p) {
        List<Point> neighbors = new ArrayList<>();
        neighbors.add(new Point(p.getX() + 1, p.getY()));
        neighbors.add(new Point(p.getX() - 1, p.getY()));
        neighbors.add(new Point(p.getX(), p.getY() + 1));
        neighbors.add(new Point(p.getX(), p.getY() - 1));
        return neighbors;
    }
}
