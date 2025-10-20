package ru.nsu.g.a.vybortseva.graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Класс для выполнения топологической сортировки ориентированного графа.
 */
public class TopologicalSorting {

    /**
     * Выполняет топологическую сортировку ориентированного ациклического графа (DAG).
     * Использует алгоритм Depth-First Search (DFS) с обнаружением циклов.
     */
    public static List<Vertex> topologicalSort(Graph graph) {
        if (!graph.isDirected()) {
            throw new IllegalArgumentException(
                    "Topological sort can only be applied to directed graphs");
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
                    throw new IllegalArgumentException(
                            "Graph contains a cycle, topological sort is impossible");
                }
            }
        }

        Collections.reverse(result);
        return result;
    }

    /**
     * Вспомогательный метод для обхода графа в глубину (DFS).
     * Помечает вершины и обнаруживает циклы.
     */
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