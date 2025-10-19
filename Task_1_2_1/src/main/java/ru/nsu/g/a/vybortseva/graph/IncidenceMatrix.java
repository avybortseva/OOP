package ru.nsu.g.a.vybortseva.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Реализация графа с использованием матрицы инцидентности.
 * Поддерживает как ориентированные, так и неориентированные графы.
 */
public class IncidenceMatrix implements Graph {
    private Map<Vertex, Integer> vertexIndices;
    private List<List<Integer>> matrix;
    private List<Vertex> vertices;
    private int edgeCount;
    private final boolean directed;

    /**
     * Конструктор для создания графа с матрицей инцидентности.
     */
    public IncidenceMatrix(boolean directed) {
        vertexIndices = new HashMap<>();
        vertices = new ArrayList<>();
        matrix = new ArrayList<>();
        edgeCount = 0;
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
    public int vertexToInt(Vertex vertex) {
        Integer index = vertexIndices.get(vertex);
        return index != null ? index : INVALID_INDEX;
    }

    /**
     * Добавляет вершину в граф.
     * Создает новую строку в матрице инцидентности для новой вершины.
     */
    @Override
    public void addVertex(Vertex vertex) {
        if (!hasVertex(vertex)) {
            vertexIndices.put(vertex, vertices.size());
            vertices.add(vertex);

            List<Integer> newRow = new ArrayList<>();
            for (int i = 0; i < edgeCount; i++) {
                newRow.add(0);
            }
            matrix.add(newRow);
        }
    }

    /**
     * Удаляет вершину из графа.
     * Удаляет соответствующую строку из матрицы инцидентности.
     */
    @Override
    public void removeVertex(Vertex vertex) {
        if (hasVertex(vertex)) {
            int vertexIndex = vertexToInt(vertex);

            vertexIndices.remove(vertex);
            vertices.remove(vertexIndex);
            matrix.remove(vertexIndex);

            vertexIndices.clear();
            for (int i = 0; i < vertices.size(); i++) {
                vertexIndices.put(vertices.get(i), i);
            }
        } else {
            throw new IllegalArgumentException("Vertex not found: " + vertex);
        }
    }

    /**
     * Добавляет ребро между двумя вершинами.
     * Создает новый столбец в матрице инцидентности.
     */
    @Override
    public void addEdge(Vertex from, Vertex to) {
        if (hasVertex(from) && hasVertex(to)) {
            int fromIdx = vertexToInt(from);
            int toIdx = vertexToInt(to);

            for (List<Integer> row : matrix) {
                row.add(0);
            }

            if (from.equals(to)) {
                if (directed) {
                    matrix.get(fromIdx).set(edgeCount, 2);
                } else {
                    matrix.get(fromIdx).set(edgeCount, 1);
                }
            } else {
                matrix.get(fromIdx).set(edgeCount, 1);
                if (directed) {
                    matrix.get(toIdx).set(edgeCount, -1);
                } else {
                    matrix.get(toIdx).set(edgeCount, 1);
                }
            }
            edgeCount++;
        } else {
            throw new IllegalArgumentException("One or both vertices not found");
        }
    }

    /**
     * Удаляет ребро между двумя вершинами.
     * Удаляет соответствующий столбец из матрицы инцидентности.
     */
    @Override
    public void removeEdge(Vertex from, Vertex to) {
        if (hasVertex(from) && hasVertex(to)) {
            int fromIdx = vertexToInt(from);
            int toIdx = vertexToInt(to);

            int edgeToRemove = INVALID_INDEX;

            for (int edge = 0; edge < edgeCount; edge++) {
                if (from.equals(to)) {
                    if (directed) {
                        if (matrix.get(fromIdx).get(edge) == 2) {
                            edgeToRemove = edge;
                            break;
                        }
                    } else {
                        if (matrix.get(fromIdx).get(edge) == 1) {
                            boolean isLoop = true;
                            for (int i = 0; i < vertices.size(); i++) {
                                if (i != fromIdx && matrix.get(i).get(edge) == 1) {
                                    isLoop = false;
                                    break;
                                }
                            }
                            if (isLoop) {
                                edgeToRemove = edge;
                                break;
                            }
                        }
                    }
                } else {
                    if (directed) {
                        if (matrix.get(fromIdx).get(edge) == 1
                                && matrix.get(toIdx).get(edge) == -1) {
                            edgeToRemove = edge;
                            break;
                        }
                    } else {
                        if (matrix.get(fromIdx).get(edge) == 1
                                && matrix.get(toIdx).get(edge) == 1) {
                            edgeToRemove = edge;
                            break;
                        }
                    }
                }
            }

            if (edgeToRemove == INVALID_INDEX) {
                throw new NullPointerException("Edge between vertices "
                        + from + " and " + to + " not found");
            }

            for (List<Integer> row : matrix) {
                row.remove(edgeToRemove);
            }
            edgeCount--;
        } else {
            throw new NullPointerException("One or both vertices not found");
        }
    }

    /**
     * Возвращает список соседей указанной вершины.
     * Для ориентированного графа возвращает только вершины, достижимые из данной.
     */
    @Override
    public List<Vertex> getNeighbors(Vertex vertex) {
        List<Vertex> neighbors = new ArrayList<>();
        int vertexIndex = vertexToInt(vertex);
        if (vertexIndex == INVALID_INDEX) return neighbors;

        List<Integer> vertexRow = matrix.get(vertexIndex);

        for (int edge = 0; edge < edgeCount; edge++) {
            int value = vertexRow.get(edge);

            if (directed) {
                if (value == 1) {
                    for (int i = 0; i < vertices.size(); i++) {
                        if (matrix.get(i).get(edge) == -1) {
                            Vertex neighbor = intToVertex(i);
                            if (!neighbors.contains(neighbor)) {
                                neighbors.add(neighbor);
                            }
                        }
                    }
                } else if (value == 2) {
                    if (!neighbors.contains(vertex)) {
                        neighbors.add(vertex);
                    }
                }
            } else {
                if (value == 1) {
                    boolean foundOther = false;
                    for (int i = 0; i < vertices.size(); i++) {
                        if (i != vertexIndex && matrix.get(i).get(edge) == 1) {
                            Vertex neighbor = intToVertex(i);
                            if (!neighbors.contains(neighbor)) {
                                neighbors.add(neighbor);
                            }
                            foundOther = true;
                        }
                    }
                    if (!foundOther && !neighbors.contains(vertex)) {
                        neighbors.add(vertex);
                    }
                }
            }
        }
        return neighbors;
    }

    /**
     * Проверяет наличие вершины в графе.
     */
    @Override
    public boolean hasVertex(Vertex vertex) {
        return vertexIndices.containsKey(vertex);
    }

    /**
     * Проверяет наличие ребра между двумя вершинами.
     */
    @Override
    public boolean hasEdge(Vertex from, Vertex to) {
        if (hasVertex(from) && hasVertex(to)) {
            int fromIdx = vertexToInt(from);
            int toIdx = vertexToInt(to);

            for (int edge = 0; edge < edgeCount; edge++) {
                if (from.equals(to)) {
                    if (directed) {
                        if (matrix.get(fromIdx).get(edge) == 2) {
                            return true;
                        }
                    } else {
                        if (matrix.get(fromIdx).get(edge) == 1) {
                            boolean isLoop = true;
                            for (int i = 0; i < vertices.size(); i++) {
                                if (i != fromIdx && matrix.get(i).get(edge) == 1) {
                                    isLoop = false;
                                    break;
                                }
                            }
                            if (isLoop) {
                                return true;
                            }
                        }
                    }
                } else {
                    if (directed) {
                        if (matrix.get(fromIdx).get(edge) == 1
                                && matrix.get(toIdx).get(edge) == -1) {
                            return true;
                        }
                    } else {
                        if (matrix.get(fromIdx).get(edge) == 1
                                && matrix.get(toIdx).get(edge) == 1) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * Возвращает список всех вершин графа.
     */
    public List<Vertex> getVertices() {
        return new ArrayList<>(vertices);
    }

    /**
     * Проверяет, является ли граф ориентированным.
     */
    @Override
    public boolean isDirected() {
        return directed;
    }
}