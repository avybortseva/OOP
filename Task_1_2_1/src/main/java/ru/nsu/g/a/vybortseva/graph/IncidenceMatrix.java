package ru.nsu.g.a.vybortseva.graph;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IncidenceMatrix implements Graph{
    private Map<Object, Integer> vertexIndices;
    private List<List<Integer>> matrix;
    private List<Object> vertices;
    private int edgeCount;
    private final boolean directed;

    public IncidenceMatrix(boolean directed) {
        vertexIndices = new HashMap<>();
        vertices = new ArrayList<>();
        matrix = new ArrayList<>();
        edgeCount = 0;
        this.directed = directed;
    }

    public Object intToObject(int index) {
        if (index >= 0 && index < vertices.size()) {
            return vertices.get(index);
        }
        return null;
    }

    public int objectToInt(Object vertex) {
        Integer index = vertexIndices.get(vertex);
        return index != null ? index : -1;
    }

    @Override
    public void addVertex(Object vertex) {
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

    @Override
    public void removeVertex(Object vertex) {
        if (hasVertex(vertex)) {

            vertexIndices.remove(vertex);
            vertices.remove(objectToInt(vertex));
            matrix.remove(objectToInt(vertex));

            vertexIndices.clear();
            for (int i = 0; i < vertices.size(); i++) {
                vertexIndices.put(vertices.get(i), i);
            }
        } else {
            throw new IllegalArgumentException("Vertex not found: " + vertex);
        }

    }

    @Override
    public void addEdge(Object from, Object to) {
        if (hasVertex(from) || hasVertex(to)) {
            int fromIdx = vertexIndices.get(from);
            int toIdx = vertexIndices.get(to);

            for (List<Integer> row : matrix) {
                row.add(0);
            }

            matrix.get(fromIdx).set(edgeCount, 1);
            if (directed) {
                matrix.get(toIdx).set(edgeCount, -1);
            } else {
                matrix.get(toIdx).set(edgeCount, 1);
            }
            edgeCount++;

        } else {
            throw new IllegalArgumentException("One or both vertices not found");
        }
    }

    @Override
    public void removeEdge(Object from, Object to) {
        if (hasVertex(from) || hasVertex(to)) {
            int fromIdx = vertexIndices.get(from);
            int toIdx = vertexIndices.get(to);

            int edgeToRemove = -1;

            for (int edge = 0; edge < edgeCount; edge++) {
                if (directed) {
                    if (matrix.get(fromIdx).get(edge) == 1 &&
                            matrix.get(toIdx).get(edge) == -1) {
                        edgeToRemove = edge;
                        break;
                    }
                } else {
                    if (matrix.get(fromIdx).get(edge) == 1 &&
                            matrix.get(toIdx).get(edge) == 1) {
                        edgeToRemove = edge;
                        break;
                    }
                }
            }

            if (edgeToRemove == -1) {
                throw new IllegalArgumentException("Ребро между вершинами " + from + " и " + to + " не найдено");
            }

            for (List<Integer> row : matrix) {
                row.remove(edgeToRemove);
            }
            edgeCount--;
        } else {
            throw new IllegalArgumentException("One or both vertices not found");
        }
    }

    @Override
    public List<Object> getNeighbors(Object vertex) {
        List<Object> neighbors = new ArrayList<>();
        int vertexIndex = objectToInt(vertex);
        if (vertexIndex == -1) return neighbors;

        List<Integer> vertexRow = matrix.get(vertexIndex);

        for (int edge = 0; edge < edgeCount; edge++) {
            int value = vertexRow.get(edge);

            if (directed) {
                if (value == 1) {
                    for (int i = 0; i < vertices.size(); i++) {
                        if (i != vertexIndex && matrix.get(i).get(edge) == -1) {
                            Object neighbor = intToObject(i);
                            if (!neighbors.contains(neighbor)) {
                                neighbors.add(neighbor);
                            }
                        }
                    }
                }
            } else {
                if (value == 1) {
                    for (int i = 0; i < vertices.size(); i++) {
                        if (i != vertexIndex && matrix.get(i).get(edge) == 1) {
                            Object neighbor = intToObject(i);
                            if (!neighbors.contains(neighbor)) {
                                neighbors.add(neighbor);
                            }
                        }
                    }
                }
            }
        }
        return neighbors;
    }

    @Override
    public void readFromFile(String filename) {
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
                    String from = parts[0];
                    String to = parts[1];

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

    @Override
    public boolean hasVertex(Object vertex) {
        return vertexIndices.containsKey(vertex);
    }

    @Override
    public boolean hasEdge(Object from, Object to) {
        if (hasVertex(from) && hasVertex(to)){
            int fromIdx = objectToInt(from);
            int toIdx = objectToInt(to);
            for (int edge = 0; edge < edgeCount; edge++) {
                if (directed) {
                    if (matrix.get(fromIdx).get(edge) == 1 &&
                            matrix.get(toIdx).get(edge) == -1) {
                        return true;
                    }
                } else {
                    if (matrix.get(fromIdx).get(edge) == 1 &&
                            matrix.get(toIdx).get(edge) == 1) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public List<Object> getAllVertices() {
        return new ArrayList<>(vertices);
    }

    @Override
    public boolean isDirected() {
        return directed;
    }
}
