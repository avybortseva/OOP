package ru.nsu.g.a.vybortseva.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Реализация графа с использованием матрицы смежности.
 * Поддерживает как ориентированные, так и неориентированные графы.
 * Вершины хранятся в списке, а ребра представлены в виде матрицы.
 */
public class AdjacencyMatrix implements Graph {
    private List<List<Integer>> matrix;
    private List<Vertex> vertices;
    private Map<Vertex, Integer> vertexIndexMap;
    private final boolean directed;

    /**
     * Конструктор для создания графа с матрицей смежности.
     */
    public AdjacencyMatrix(boolean directed) {
        this.matrix = new ArrayList<>();
        this.vertices = new ArrayList<>();
        this.vertexIndexMap = new HashMap<>();
        this.directed = directed;
    }

    /**
     * Преобразует индекс в вершину.
     */
    public Vertex intToVertex(int index) {
        if (index >= 0 && index < vertices.size()) {
            return vertices.get(index);
        }
        return null;
    }

    /**
     * Преобразует вершину в индекс.
     */
    public int VertexToInt(Vertex vertex) {
        Integer index = vertexIndexMap.get(vertex);
        return index != null ? index : INVALID_INDEX;
    }

    /**
     * Добавляет вершину в граф.
     * Матрица смежности автоматически расширяется для accommodate новой вершины.
     */
    @Override
    public void addVertex(Vertex vertex) {
        // хз может если есть менять значение просто
        if (!hasVertex(vertex)) {
            vertices.add(vertex);

            int newIndex = vertices.size() - 1;
            vertexIndexMap.put(vertex, newIndex);

            int newSize = vertices.size();

            for (int i = 0; i < matrix.size(); i++) {
                matrix.get(i).add(0);
            }

            List<Integer> newRow = new ArrayList<>();
            for (int i = 0; i < newSize; i++) {
                newRow.add(0);
            }
            matrix.add(newRow);
        }
    }

    /**
     * Удаляет вершину из графа.
     * Удаляет соответствующую строку и столбец из матрицы смежности.
     */
    @Override
    public void removeVertex(Vertex vertex) {
        if (hasVertex(vertex)){
            int removedIndex = VertexToInt(vertex);

            vertices.remove(removedIndex);
            vertexIndexMap.remove(vertex);

            matrix.remove(removedIndex);
            for (List<Integer> row : matrix) {
                row.remove(removedIndex);
            }

            for (int i = removedIndex; i < vertices.size(); i++) {
                Vertex currentVertex = vertices.get(i);
                vertexIndexMap.put(currentVertex, i);
            }
        } else {
            throw new IllegalArgumentException("Vertex not found: " + vertex);
        }
    }

    /**
     * Добавляет ребро между двумя вершинами.
     */
    @Override
    public void addEdge(Vertex from, Vertex to) {
        if (hasVertex(from) && hasVertex(to)){
            int fromIndex = VertexToInt(from);
            int toIndex = VertexToInt(to);
            matrix.get(fromIndex).set(toIndex, 1);
            if (!directed) {
                matrix.get(toIndex).set(fromIndex, 1);
            }
        } else {
            throw new IllegalArgumentException("One or both vertices not found");
        }
    }

    /**
     * Удаляет ребро между двумя вершинами.
     */
    @Override
    public void removeEdge(Vertex from, Vertex to) {
        if (hasVertex(from) && hasVertex(to)){
            int fromIndex = VertexToInt(from);
            int toIndex = VertexToInt(to);
            matrix.get(fromIndex).set(toIndex, 0);
            if (!directed) {
                matrix.get(toIndex).set(fromIndex, 0);
            }
        } else {
            throw new IllegalArgumentException("One or both vertices not found");
        }
    }

    /**
     * Возвращает список соседей указанной вершины.
     * Для неориентированного графа включает все смежные вершины.
     */
    @Override
    public List<Vertex> getNeighbors(Vertex vertex) {
        List<Vertex> neighbors = new ArrayList<>();
        int vertexIndex = VertexToInt(vertex);

        if (vertexIndex == INVALID_INDEX) return neighbors;

        List<Integer> row = matrix.get(vertexIndex);
        for (int i = 0; i < row.size(); i++) {
            if (row.get(i) == 1 && intToVertex(i) != null) {
                neighbors.add(intToVertex(i));
            }
        }

        if (!directed) {
            for (int i = 0; i < matrix.size(); i++) {
                if (i != vertexIndex && matrix.get(i).get(vertexIndex) == 1
                        && intToVertex(i) != null) {
                    if (!neighbors.contains(intToVertex(i))) {
                        neighbors.add(intToVertex(i));
                    }
                }
            }
        }
        return neighbors;
    }

    /**
     * Возвращает список всех вершин графа.
     */
    @Override
    public List<Vertex> getVertices() {
        return new ArrayList<>(vertices);
    }

    /**
     * Проверяет наличие вершины в графе.
     */
    @Override
    public boolean hasVertex(Vertex vertex) {
        return vertexIndexMap.containsKey(vertex);
    }

    /**
     * Проверяет наличие ребра между двумя вершинами.
     */
    @Override
    public boolean hasEdge(Vertex from, Vertex to) {
        if (hasVertex(from) && hasVertex(to)){
            int fromIndex = VertexToInt(from);
            int toIndex = VertexToInt(to);
            return matrix.get(fromIndex).get(toIndex) == 1;
        }
        return false;
    }

    /**
     * Проверяет, является ли граф ориентированным.
     */
    @Override
    public boolean isDirected() {
        return directed;
    }
}
