package ru.nsu.g.a.vybortseva.graph;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

/**
 * Интерфейс для работы с графом.
 */
public interface Graph {
    final int INVALID_INDEX = -1;

    /**
     * Добавляет вершину в граф.
     */
    void addVertex(Vertex vertex);

    /**
     * Удаляет вершину из графа.
     */
    void removeVertex(Vertex vertex);

    /**
     * Добавляет ребро между вершинами.
     */
    void addEdge(Vertex source, Vertex destination);

    /**
     * Удаляет ребро между вершинами.
     */
    void removeEdge(Vertex source, Vertex destination);

    /**
     * Проверяет наличие вершины в графе.
     */
    boolean hasVertex(Vertex vertex);

    /**
     * Проверяет наличие ребра между вершинами.
     */
    boolean hasEdge(Vertex source, Vertex destination);

    /**
     * Возвращает список соседей вершины.
     */
    List<Vertex> getNeighbors(Vertex vertex);

    /**
     * Возвращает список всех вершин графа.
     */
    List<Vertex> getVertices();

    /**
     * Проверяет, является ли граф ориентированным.
     */
    boolean isDirected();

    /**
     * Читает граф из файла.
     */
    default void readFromFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            boolean firstLine = true;

            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) {
                    continue;
                }

                if (firstLine) {
                    firstLine = false;
                    continue;
                }

                String[] parts = line.split("\\s+");
                if (parts.length >= 2) {
                    Vertex from = new StringVertex(parts[0]);
                    Vertex to = new StringVertex(parts[1]);

                    if (!hasVertex(from)) {
                        addVertex(from);
                    }
                    if (!hasVertex(to)) {
                        addVertex(to);
                    }

                    addEdge(from, to);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading graph from file: " + filename, e);
        }
    }
}
