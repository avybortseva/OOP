package ru.nsu.g.a.vybortseva.graph;

import java.util.*;

public class TopologicalSorting {

    public static List<Object> topologicalSort(Graph graph) {
        if (!graph.isDirected()) {
            throw new IllegalArgumentException("Topological sort can only be applied to directed graphs");
        }

        List<Object> result = new ArrayList<>();
        Set<Object> visited = new HashSet<>();
        Set<Object> tempMark = new HashSet<>();

        List<Object> allVertices = graph.getAllVertices();

        for (Object vertex : allVertices) {
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

    private static void visit(Object vertex, Graph graph, Set<Object> visited,
                              Set<Object> tempMark, List<Object> result) {
        if (tempMark.contains(vertex)) {
            throw new IllegalArgumentException("Cycle detected");
        }

        if (visited.contains(vertex)) {
            return;
        }

        tempMark.add(vertex);

        List<Object> neighbors = graph.getNeighbors(vertex);
        for (Object neighbor : neighbors) {
            visit(neighbor, graph, visited, tempMark, result);
        }

        tempMark.remove(vertex);
        visited.add(vertex);

        result.add(vertex);
    }
}