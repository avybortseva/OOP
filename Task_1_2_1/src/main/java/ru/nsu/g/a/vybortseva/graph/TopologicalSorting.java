package ru.nsu.g.a.vybortseva.graph;

import java.util.*;

public class TopologicalSorting {

    public static List<Vertex> topologicalSort(Graph graph) {
        if (!graph.isDirected()) {
            throw new IllegalArgumentException("Topological sort can only be applied to directed graphs");
        }

        List<Vertex> result = new ArrayList<>();
        Set<Vertex> visited = new HashSet<>();
        Set<Vertex> tempMark = new HashSet<>();

        List<Vertex> allVertices = graph.getVertices();

        for (Vertex vertex : allVertices) {
            if (!visited.contains(vertex)) {
                try {
                    visit(vertex, graph, visited, tempMark, result);
                } catch (IllegalArgumentException e) {
                    throw new IllegalArgumentException("Graph contains a cycle, topological sort is impossible");
                }
            }
        }

        Collections.reverse(result);
        return result;
    }

    private static void visit(Vertex vertex, Graph graph, Set<Vertex> visited,
                              Set<Vertex> tempMark, List<Vertex> result) {
        if (tempMark.contains(vertex)) {
            throw new IllegalArgumentException("Cycle detected");
        }

        if (visited.contains(vertex)) {
            return;
        }

        tempMark.add(vertex);

        List<Vertex> neighbors = graph.getNeighbors(vertex);
        for (Vertex neighbor : neighbors) {
            visit(neighbor, graph, visited, tempMark, result);
        }

        tempMark.remove(vertex);
        visited.add(vertex);

        result.add(vertex);
    }
}