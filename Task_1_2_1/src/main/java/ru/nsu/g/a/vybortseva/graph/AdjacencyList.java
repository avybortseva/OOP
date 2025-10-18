package ru.nsu.g.a.vybortseva.graph;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdjacencyList implements Graph {
    private Map<Object, List<Object>> adjacencyList;
    private final boolean directed;

    public AdjacencyList(boolean directed) {
        this.directed = directed;
        this.adjacencyList = new HashMap<>();
    }


    @Override
    public void addVertex(Object vertex) {
        if (!hasVertex(vertex)) {
            adjacencyList.put(vertex, new ArrayList<>());
        }
    }

    @Override
    public void removeVertex(Object vertex) {
        if (hasVertex(vertex)) {
            if (directed) {
                for (List<Object> neighbors : adjacencyList.values()) {
                    neighbors.remove(vertex);
                }
            } else {
                List<Object> neighbors = adjacencyList.get(vertex);
                for (Object neighbor : neighbors) {
                    List<Object> neighborList = adjacencyList.get(neighbor);
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

    @Override
    public void addEdge(Object from, Object to) {
        if (!hasVertex(from)) {
            addVertex(from);
        }
        if (!hasVertex(to)) {
            addVertex(to);
        }

        List<Object> fromNeighbors = adjacencyList.get(from);
        if (!fromNeighbors.contains(to)) {
            fromNeighbors.add(to);
        }

        if (!directed) {
            List<Object> toNeighbors = adjacencyList.get(to);
            if (!toNeighbors.contains(from)) {
                toNeighbors.add(from);
            }
        }
    }

    @Override
    public void removeEdge(Object from, Object to) {
        if (!hasVertex(from) || !hasVertex(to)) {
            throw new IllegalArgumentException("One or both vertices not found");
        }

        List<Object> fromNeighbors = adjacencyList.get(from);
        fromNeighbors.remove(to);

        if (!directed) {
            List<Object> toNeighbors = adjacencyList.get(to);
            toNeighbors.remove(from);
        }
    }

    @Override
    public List<Object> getNeighbors(Object vertex) {
        if (hasVertex(vertex)) {
            return new ArrayList<>(adjacencyList.get(vertex));
        } else {
            return new ArrayList<>();
        }
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
        return adjacencyList.containsKey(vertex);
    }

    @Override
    public boolean hasEdge(Object from, Object to) {
        if (!hasVertex(from) || !hasVertex(to)) {
            return false;
        }
        return adjacencyList.get(from).contains(to);
    }

    @Override
    public List<Object> getAllVertices() {
        return new ArrayList<>(adjacencyList.keySet());
    }

    @Override
    public boolean isDirected() {
        return directed;
    }
}
