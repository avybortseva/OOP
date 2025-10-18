package ru.nsu.g.a.vybortseva.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdjacencyList implements Graph {
    private Map<Vertex, List<Vertex>> adjacencyList;
    private final boolean directed;

    public AdjacencyList(boolean directed) {
        this.directed = directed;
        this.adjacencyList = new HashMap<>();
    }


    @Override
    public void addVertex(Vertex vertex) {
        if (!hasVertex(vertex)) {
            adjacencyList.put(vertex, new ArrayList<>());
        }
    }

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

    @Override
    public List<Vertex> getNeighbors(Vertex vertex) {
        if (hasVertex(vertex)) {
            return new ArrayList<>(adjacencyList.get(vertex));
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public boolean hasVertex(Vertex vertex) {
        return adjacencyList.containsKey(vertex);
    }

    @Override
    public boolean hasEdge(Vertex from, Vertex to) {
        if (!hasVertex(from) || !hasVertex(to)) {
            return false;
        }
        return adjacencyList.get(from).contains(to);
    }

    public List<Vertex> getVertices() {
        return new ArrayList<>(adjacencyList.keySet());
    }

    @Override
    public boolean isDirected() {
        return directed;
    }
}
