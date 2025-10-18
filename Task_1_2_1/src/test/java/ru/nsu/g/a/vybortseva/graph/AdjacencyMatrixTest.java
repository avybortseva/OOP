package ru.nsu.g.a.vybortseva.graph;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AdjacencyMatrixTest {

    @Test
    void intToObject() {
        AdjacencyMatrix graph = new AdjacencyMatrix(false);
        graph.setVertices(Arrays.asList("A", "B", "C"));

        assertEquals("A", graph.intToObject(0));
        assertEquals("B", graph.intToObject(1));
        assertEquals("C", graph.intToObject(2));
        assertNull(graph.intToObject(3));
        assertNull(graph.intToObject(-1));
    }

    @Test
    void objectToInt() {
        AdjacencyMatrix graph = new AdjacencyMatrix(false);
        graph.setVertices(Arrays.asList("A", "B", "C"));

        assertEquals(0, graph.objectToInt("A"));
        assertEquals(1, graph.objectToInt("B"));
        assertEquals(2, graph.objectToInt("C"));
        assertEquals(-1, graph.objectToInt("D"));
    }

    @Test
    void setVertices() {
        AdjacencyMatrix graph = new AdjacencyMatrix(false);
        List<Object> vertices = Arrays.asList("A", "B", "C");
        graph.setVertices(vertices);

        assertEquals(3, graph.getAllVertices().size());
        assertTrue(graph.hasVertex("A"));
        assertTrue(graph.hasVertex("B"));
        assertTrue(graph.hasVertex("C"));
    }

    @Test
    void addVertex() {
        AdjacencyMatrix graph = new AdjacencyMatrix(false);
        graph.addVertex("A");
        graph.addVertex("B");

        assertEquals(2, graph.getAllVertices().size());
        assertTrue(graph.hasVertex("A"));
        assertTrue(graph.hasVertex("B"));

        graph.addVertex("A");
        assertEquals(2, graph.getAllVertices().size());
    }

    @Test
    void removeVertex() {
        AdjacencyMatrix graph = new AdjacencyMatrix(false);
        graph.setVertices(Arrays.asList("A", "B", "C"));
        graph.addEdge("A", "B");
        graph.addEdge("B", "C");

        graph.removeVertex("B");

        assertEquals(2, graph.getAllVertices().size());
        assertFalse(graph.hasVertex("B"));
        assertFalse(graph.hasEdge("A", "B"));
        assertFalse(graph.hasEdge("B", "C"));

        assertThrows(IllegalArgumentException.class, () -> graph.removeVertex("D"));
    }

    @Test
    void addEdge() {
        AdjacencyMatrix graph = new AdjacencyMatrix(false);
        graph.setVertices(Arrays.asList("A", "B", "C"));

        graph.addEdge("A", "B");
        graph.addEdge("B", "C");

        assertTrue(graph.hasEdge("A", "B"));
        assertTrue(graph.hasEdge("B", "A"));
        assertTrue(graph.hasEdge("B", "C"));
        assertTrue(graph.hasEdge("C", "B"));
        assertFalse(graph.hasEdge("A", "C"));

        assertThrows(IllegalArgumentException.class, () -> graph.addEdge("A", "D"));

        AdjacencyMatrix graphDir = new AdjacencyMatrix(true);
        graphDir.setVertices(Arrays.asList("A", "B", "C"));

        graphDir.addEdge("A", "B");
        graphDir.addEdge("B", "C");

        assertTrue(graphDir.hasEdge("A", "B"));
        assertFalse(graphDir.hasEdge("B", "A"));
        assertTrue(graphDir.hasEdge("B", "C"));
        assertFalse(graphDir.hasEdge("C", "B"));
    }

    @Test
    void removeEdge() {
        AdjacencyMatrix graph = new AdjacencyMatrix(false);
        graph.setVertices(Arrays.asList("A", "B", "C"));
        graph.addEdge("A", "B");
        graph.addEdge("B", "C");

        graph.removeEdge("A", "B");

        assertFalse(graph.hasEdge("A", "B"));
        assertFalse(graph.hasEdge("B", "A"));
        assertTrue(graph.hasEdge("B", "C"));

        assertThrows(IllegalArgumentException.class, () -> graph.removeEdge("A", "D"));
    }

    @Test
    void getNeighbors() {
        AdjacencyMatrix graph = new AdjacencyMatrix(false);
        graph.setVertices(Arrays.asList("A", "B", "C", "D"));
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

        AdjacencyMatrix graphDir = new AdjacencyMatrix(true);
        graphDir.setVertices(Arrays.asList("A", "B", "C"));
        graphDir.addEdge("A", "B");
        graphDir.addEdge("B", "C");

        List<Object> neighborsADir = graphDir.getNeighbors("A");
        assertEquals(1, neighborsADir.size());
        assertTrue(neighborsA.contains("B"));

        List<Object> neighborsBDir = graphDir.getNeighbors("B");
        assertEquals(1, neighborsBDir.size());
        assertTrue(neighborsBDir.contains("C"));

        List<Object> neighborsCDir = graphDir.getNeighbors("C");
        assertEquals(0, neighborsCDir.size());
    }

    @Test
    void hasVertex() {
        AdjacencyMatrix graph = new AdjacencyMatrix(false);
        graph.setVertices(Arrays.asList("A", "B"));

        assertTrue(graph.hasVertex("A"));
        assertTrue(graph.hasVertex("B"));
        assertFalse(graph.hasVertex("C"));
    }

    @Test
    void hasEdge() {
        AdjacencyMatrix graph = new AdjacencyMatrix(false);
        graph.setVertices(Arrays.asList("A", "B", "C"));
        graph.addEdge("A", "B");

        assertTrue(graph.hasEdge("A", "B"));
        assertTrue(graph.hasEdge("B", "A"));
        assertFalse(graph.hasEdge("A", "C"));
        assertFalse(graph.hasEdge("B", "C"));
    }

    @Test
    void getAllVertices() {
        AdjacencyMatrix graph = new AdjacencyMatrix(false);
        graph.setVertices(Arrays.asList("A", "B", "C"));

        List<Object> vertices = graph.getAllVertices();
        assertEquals(3, vertices.size());
        assertTrue(vertices.contains("A"));
        assertTrue(vertices.contains("B"));
        assertTrue(vertices.contains("C"));
    }

    @Test
    void isDirected() {
        AdjacencyMatrix undirected = new AdjacencyMatrix(false);
        AdjacencyMatrix directed = new AdjacencyMatrix(true);

        assertFalse(undirected.isDirected());
        assertTrue(directed.isDirected());
    }
}