package ru.nsu.g.a.vybortseva.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdjacencyMatrix implements Graph {
    private List<List<Integer>> matrix;
    private List<Vertex> vertices;
    private Map<Vertex, Integer> vertexIndexMap;
    private final boolean directed;

    public AdjacencyMatrix(boolean directed) {
        this.matrix = new ArrayList<>();
        this.vertices = new ArrayList<>();
        this.vertexIndexMap = new HashMap<>();
        this.directed = directed;
    }

    public Vertex intToVertex(int index) {
        if (index >= 0 && index < vertices.size()) {
            return vertices.get(index);
        }
        return null;
    }

    public int VertexToInt(Vertex vertex) {
        Integer index = vertexIndexMap.get(vertex);
        return index != null ? index : INVALID_INDEX;
    }

    public void setVertices(List<Vertex> vertices) {
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
                if (i != vertexIndex && matrix.get(i).get(vertexIndex) == 1 && intToVertex(i) != null) {
                    if (!neighbors.contains(intToVertex(i))) {
                        neighbors.add(intToVertex(i));
                    }
                }
            }
        }
        return neighbors;
    }

    @Override
    public List<Vertex> getVertices() {
        return new ArrayList<>(vertices);
    }

    @Override
    public boolean hasVertex(Vertex vertex) {
        return vertexIndexMap.containsKey(vertex);
    }

    @Override
    public boolean hasEdge(Vertex from, Vertex to) {
        if (hasVertex(from) && hasVertex(to)){
            int fromIndex = VertexToInt(from);
            int toIndex = VertexToInt(to);
            return matrix.get(fromIndex).get(toIndex) == 1;
        }
        return false;
    }

    @Override
    public boolean isDirected() {
        return directed;
    }


}
