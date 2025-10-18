package ru.nsu.g.a.vybortseva.graph;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AdjacencyListTest {

    @Test
    void addVertex() {
        AdjacencyList graph = new AdjacencyList(false);
        graph.addVertex("A");
        graph.addVertex("B");

        assertTrue(graph.hasVertex("A"));
        assertTrue(graph.hasVertex("B"));
        assertEquals(2, graph.getAllVertices().size());

        graph.addVertex("A");
        assertEquals(2, graph.getAllVertices().size());
    }

    @Test
    void removeVertex() {
        AdjacencyList graph = new AdjacencyList(false);
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("C");
        graph.addEdge("A", "B");
        graph.addEdge("B", "C");

        graph.removeVertex("B");

        assertFalse(graph.hasVertex("B"));
        assertTrue(graph.hasVertex("A"));
        assertTrue(graph.hasVertex("C"));
        assertFalse(graph.hasEdge("A", "B"));
        assertFalse(graph.hasEdge("B", "C"));

        assertThrows(IllegalArgumentException.class, () -> graph.removeVertex("D"));
    }

    @Test
    void removeVertexDirected() {
        AdjacencyList graph = new AdjacencyList(true);
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("C");
        graph.addEdge("A", "B");
        graph.addEdge("B", "C");

        graph.removeVertex("B");

        assertFalse(graph.hasVertex("B"));
        assertFalse(graph.hasEdge("A", "B"));
        assertFalse(graph.hasEdge("B", "C"));
    }

    @Test
    void addEdge() {
        AdjacencyList graph = new AdjacencyList(false);
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("C");

        graph.addEdge("A", "B");
        graph.addEdge("B", "C");

        assertTrue(graph.hasEdge("A", "B"));
        assertTrue(graph.hasEdge("B", "A"));
        assertTrue(graph.hasEdge("B", "C"));
        assertTrue(graph.hasEdge("C", "B"));
        assertFalse(graph.hasEdge("A", "C"));
    }

    @Test
    void addEdgeDirected() {
        AdjacencyList graph = new AdjacencyList(true);
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("C");

        graph.addEdge("A", "B");
        graph.addEdge("B", "C");

        assertTrue(graph.hasEdge("A", "B"));
        assertFalse(graph.hasEdge("B", "A"));
        assertTrue(graph.hasEdge("B", "C"));
        assertFalse(graph.hasEdge("C", "B"));
    }

    @Test
    void removeEdge() {
        AdjacencyList graph = new AdjacencyList(false);
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("C");
        graph.addEdge("A", "B");
        graph.addEdge("B", "C");

        graph.removeEdge("A", "B");

        assertFalse(graph.hasEdge("A", "B"));
        assertFalse(graph.hasEdge("B", "A"));
        assertTrue(graph.hasEdge("B", "C"));
        assertTrue(graph.hasEdge("C", "B"));

        assertThrows(IllegalArgumentException.class, () -> graph.removeEdge("A", "D"));
    }

    @Test
    void removeEdgeDirected() {
        AdjacencyList graph = new AdjacencyList(true);
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("C");
        graph.addEdge("A", "B");
        graph.addEdge("B", "C");

        graph.removeEdge("A", "B");

        assertFalse(graph.hasEdge("A", "B"));
        assertTrue(graph.hasEdge("B", "C"));
    }

    @Test
    void getNeighbors() {
        AdjacencyList graph = new AdjacencyList(false);
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("C");
        graph.addVertex("D");
        graph.addEdge("A", "B");
        graph.addEdge("A", "C");
        graph.addEdge("B", "D");

        List<Object> neighborsA = graph.getNeighbors("A");
        assertEquals(2, neighborsA.size());
        assertTrue(neighborsA.contains("B"));
        assertTrue(neighborsA.contains("C"));

        List<Object> neighborsB = graph.getNeighbors("B");
        assertEquals(2, neighborsB.size());
        assertTrue(neighborsB.contains("A"));
        assertTrue(neighborsB.contains("D"));

        List<Object> neighborsC = graph.getNeighbors("C");
        assertEquals(1, neighborsC.size());
        assertTrue(neighborsC.contains("A"));

        // Несуществующая вершина
        List<Object> neighborsE = graph.getNeighbors("E");
        assertTrue(neighborsE.isEmpty());
    }

    @Test
    void getNeighborsDirected() {
        AdjacencyList graph = new AdjacencyList(true);
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("C");
        graph.addEdge("A", "B");
        graph.addEdge("B", "C");

        List<Object> neighborsA = graph.getNeighbors("A");
        assertEquals(1, neighborsA.size());
        assertTrue(neighborsA.contains("B"));

        List<Object> neighborsB = graph.getNeighbors("B");
        assertEquals(1, neighborsB.size());
        assertTrue(neighborsB.contains("C"));

        List<Object> neighborsC = graph.getNeighbors("C");
        assertTrue(neighborsC.isEmpty());
    }

    @Test
    void readFromFile() {
        AdjacencyList graph = new AdjacencyList(false);

        try {
            graph.readFromFile("graph.txt");
            assertTrue(graph.getAllVertices().size() > 0);
        } catch (RuntimeException e) {
            // Если файла нет - это нормально для теста
            assertTrue(e.getMessage().contains("Error reading graph from file"));
        }
    }

    @Test
    void hasVertex() {
        AdjacencyList graph = new AdjacencyList(false);
        graph.addVertex("A");
        graph.addVertex("B");

        assertTrue(graph.hasVertex("A"));
        assertTrue(graph.hasVertex("B"));
        assertFalse(graph.hasVertex("C"));
    }

    @Test
    void hasEdge() {
        AdjacencyList graph = new AdjacencyList(false);
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("C");
        graph.addEdge("A", "B");

        assertTrue(graph.hasEdge("A", "B"));
        assertTrue(graph.hasEdge("B", "A"));
        assertFalse(graph.hasEdge("A", "C"));
        assertFalse(graph.hasEdge("B", "C"));

        assertFalse(graph.hasEdge("A", "D"));
        assertFalse(graph.hasEdge("D", "A"));
    }

    @Test
    void getAllVertices() {
        AdjacencyList graph = new AdjacencyList(false);
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("C");

        List<Object> vertices = graph.getAllVertices();
        assertEquals(3, vertices.size());
        assertTrue(vertices.contains("A"));
        assertTrue(vertices.contains("B"));
        assertTrue(vertices.contains("C"));
    }

    @Test
    void isDirected() {
        AdjacencyList undirected = new AdjacencyList(false);
        AdjacencyList directed = new AdjacencyList(true);

        assertFalse(undirected.isDirected());
        assertTrue(directed.isDirected());
    }

    @Test
    void duplicateEdges() {
        AdjacencyList graph = new AdjacencyList(false);
        graph.addVertex("A");
        graph.addVertex("B");

        graph.addEdge("A", "B");
        graph.addEdge("A", "B");

        List<Object> neighbors = graph.getNeighbors("A");
        assertEquals(1, neighbors.size());
    }
}