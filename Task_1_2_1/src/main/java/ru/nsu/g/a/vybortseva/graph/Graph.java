package ru.nsu.g.a.vybortseva.graph;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import java.util.List;

/**
 * Интерфейс для работы с графом
 */
public interface Graph {
    final int INVALID_INDEX = -1;

    /**
     * Добавляет вершину в граф
     * @param vertex вершина для добавления
     */
    void addVertex(Vertex vertex);

    /**
     * Удаляет вершину из графа
     * @param vertex вершина для удаления
     */
    void removeVertex(Vertex vertex);

    /**
     * Добавляет ребро между вершинами
     * @param source исходная вершина
     * @param destination целевая вершина
     */
    void addEdge(Vertex source, Vertex destination);

    /**
     * Удаляет ребро между вершинами
     * @param source исходная вершина
     * @param destination целевая вершина
     */
    void removeEdge(Vertex source, Vertex destination);

    /**
     * Проверяет наличие вершины в графе
     * @param vertex вершина для проверки
     * @return true если вершина присутствует
     */
    boolean hasVertex(Vertex vertex);

    /**
     * Проверяет наличие ребра между вершинами
     * @param source исходная вершина
     * @param destination целевая вершина
     * @return true если ребро существует
     */
    boolean hasEdge(Vertex source, Vertex destination);

    /**
     * Возвращает список соседей вершины
     * @param vertex вершина
     * @return список соседних вершин
     */
    List<Vertex> getNeighbors(Vertex vertex);

    /**
     * Возвращает список всех вершин графа
     * @return список вершин
     */
    List<Vertex> getVertices();

    /**
     * Проверяет, является ли граф ориентированным
     * @return true если граф ориентированный
     */
    boolean isDirected();

    /**
     * Читает граф из файла
     * @param filename имя файла
     */
    default void readFromFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            boolean firstLine = true;

            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

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
