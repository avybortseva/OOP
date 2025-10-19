package ru.nsu.g.a.vybortseva.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Реализация графа с использованием списка смежности.
 * Поддерживает как ориентированные, так и неориентированные графы.
 */
public class AdjacencyList implements Graph {
    private Map<Vertex, List<Vertex>> adjacencyList;
    private final boolean directed;

    /**
     * Конструктор для создания графа.
     */
    public AdjacencyList(boolean directed) {
        this.directed = directed;
        this.adjacencyList = new HashMap<>();
    }

    /**
     * Добавляет вершину в граф.
     * Если вершина уже существует, метод не выполняет никаких действий.
     */
    @Override
    public void addVertex(Vertex vertex) {
        if (!hasVertex(vertex)) {
            adjacencyList.put(vertex, new ArrayList<>());
        }
    }

    /**
     * Удаляет вершину из графа.
     * Также удаляет все инцидентные ребра.
     */
    @Override
    public void removeVertex(Vertex vertex) {
        if (hasVertex(vertex)) {
            if (directed) {
                for (List<Vertex> neighbors : adjacencyList.values()) {
                    neighbors.remove(vertex);
                }
            } else {
                List<Vertex> neighbors = adjacencyList.get(vertex);
                for (Vertex neighbor : neighbors) {
                    List<Vertex> neighborList = adjacencyList.get(neighbor);
                    if (neighborList != null) {
                        neighborList.remove(vertex);
                    }
                }
            }
            adjacencyList.remove(vertex);
        } else {
            throw new IllegalArgumentException("Vertex not found: " + vertex);
        }
    }

    /**
     * Добавляет ребро между двумя вершинами.
     * Если вершины не существуют, они будут автоматически созданы.
     */
    @Override
    public void addEdge(Vertex from, Vertex to) {
        if (!hasVertex(from)) {
            addVertex(from);
        }
        if (!hasVertex(to)) {
            addVertex(to);
        }

        List<Vertex> fromNeighbors = adjacencyList.get(from);
        if (!fromNeighbors.contains(to)) {
            fromNeighbors.add(to);
        }

        if (!directed) {
            List<Vertex> toNeighbors = adjacencyList.get(to);
            if (!toNeighbors.contains(from)) {
                toNeighbors.add(from);
            }
        }
    }

    /**
     * Удаляет ребро между двумя вершинами.
     */
    @Override
    public void removeEdge(Vertex from, Vertex to) {
        if (!hasVertex(from) || !hasVertex(to)) {
            throw new IllegalArgumentException("One or both vertices not found");
        }

        List<Vertex> fromNeighbors = adjacencyList.get(from);
        fromNeighbors.remove(to);

        if (!directed) {
            List<Vertex> toNeighbors = adjacencyList.get(to);
            toNeighbors.remove(from);
        }
    }

    /**
     * Возвращает список соседей указанной вершины.
     */
    @Override
    public List<Vertex> getNeighbors(Vertex vertex) {
        if (hasVertex(vertex)) {
            return new ArrayList<>(adjacencyList.get(vertex));
        } else {
            return new ArrayList<>();
        }
    }

    /**
     * Проверяет наличие вершины в графе.
     */
    @Override
    public boolean hasVertex(Vertex vertex) {
        return adjacencyList.containsKey(vertex);
    }

    /**
     * Проверяет наличие ребра между двумя вершинами.
     */
    @Override
    public boolean hasEdge(Vertex from, Vertex to) {
        if (!hasVertex(from) || !hasVertex(to)) {
            return false;
        }
        return adjacencyList.get(from).contains(to);
    }

    /**
     * Возвращает список всех вершин графа.
     */
    public List<Vertex> getVertices() {
        return new ArrayList<>(adjacencyList.keySet());
    }

    /**
     * Проверяет, является ли граф ориентированным.
     */
    @Override
    public boolean isDirected() {
        return directed;
    }
}
