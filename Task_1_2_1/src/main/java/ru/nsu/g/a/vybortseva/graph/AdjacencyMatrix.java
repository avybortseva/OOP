package ru.nsu.g.a.vybortseva.graph;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdjacencyMatrix implements Graph {
    private List<List<Integer>> matrix;
    private List<Object> vertices;
    private Map<Object, Integer> vertexIndexMap;
    private final boolean directed;

    public AdjacencyMatrix(boolean directed) {
        this.matrix = new ArrayList<>();
        this.vertices = new ArrayList<>();
        this.vertexIndexMap = new HashMap<>();
        this.directed = directed;
    }

    public Object intToObject(int index) {
        if (index >= 0 && index < vertices.size()) {
            return vertices.get(index);
        }
        return null;
    }

    public int objectToInt(Object vertex) {
        Integer index = vertexIndexMap.get(vertex);
        return index != null ? index : -1;
    }

    public void setVertices(List<Object> vertices) {
        this.vertices = new ArrayList<>(vertices);
        this.vertexIndexMap.clear();

        for (int i = 0; i < vertices.size(); i++) {
            vertexIndexMap.put(vertices.get(i), i);
        }

        this.matrix = new ArrayList<>();
        for (int i = 0; i < vertices.size(); i++) {
            List<Integer> row = new ArrayList<>();
            for (int j = 0; j < vertices.size(); j++) {
                row.add(0);
            }
            matrix.add(row);
        }
    }

    @Override
    public void addVertex(Object vertex) {
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

    @Override
    public void removeVertex(Object vertex) {
        if (hasVertex(vertex)){
            int removedIndex = objectToInt(vertex);

            vertices.remove(vertex);
            vertexIndexMap.remove(vertex);

            vertexIndexMap.clear();
            for (int i = 0; i < vertices.size(); i++) {
                vertexIndexMap.put(vertices.get(i), i);
            }

            matrix.remove(removedIndex);
            for (List<Integer> row : matrix) {
                row.remove(removedIndex);
            }
        } else {
            throw new IllegalArgumentException("Vertex not found: " + vertex);
        }
    }

    @Override
    public void addEdge(Object from, Object to) {
        if (hasVertex(from) && hasVertex(to)){
            int fromIndex = objectToInt(from);
            int toIndex = objectToInt(to);
            matrix.get(fromIndex).set(toIndex, 1);
            if (!directed) {
                matrix.get(toIndex).set(fromIndex, 1);
            }
        } else {
            throw new IllegalArgumentException("One or both vertices not found");
        }
    }

    @Override
    public void removeEdge(Object from, Object to) {
        if (hasVertex(from) && hasVertex(to)){
            int fromIndex = objectToInt(from);
            int toIndex = objectToInt(to);
            matrix.get(fromIndex).set(toIndex, 0);
            if (!directed) {
                matrix.get(toIndex).set(fromIndex, 0);
            }
        } else {
            throw new IllegalArgumentException("One or both vertices not found");
        }
    }

    @Override
    public List<Object> getNeighbors(Object vertex) {
        List<Object> neighbors = new ArrayList<>();
        int vertexIndex = objectToInt(vertex);

        if (vertexIndex == -1) return neighbors;

        List<Integer> row = matrix.get(vertexIndex);
        for (int i = 0; i < row.size(); i++) {
            if (row.get(i) == 1 && intToObject(i) != null) {
                neighbors.add(intToObject(i));
            }
        }

        if (!directed) {
            for (int i = 0; i < matrix.size(); i++) {
                if (i != vertexIndex && matrix.get(i).get(vertexIndex) == 1 && intToObject(i) != null) {
                    if (!neighbors.contains(intToObject(i))) {
                        neighbors.add(intToObject(i));
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
        return vertexIndexMap.containsKey(vertex);
    }

    @Override
    public boolean hasEdge(Object from, Object to) {
        if (hasVertex(from) && hasVertex(to)){
            int fromIndex = objectToInt(from);
            int toIndex = objectToInt(to);
            return matrix.get(fromIndex).get(toIndex) == 1;
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
