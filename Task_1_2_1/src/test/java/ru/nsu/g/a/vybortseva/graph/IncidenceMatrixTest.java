package ru.nsu.g.a.vybortseva.graph;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class IncidenceMatrixTest {

    @Test
    void intToObject() {
        IncidenceMatrix graph = new IncidenceMatrix(false);
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("C");

        assertEquals("A", graph.intToObject(0));
        assertEquals("B", graph.intToObject(1));
        assertEquals("C", graph.intToObject(2));
        assertNull(graph.intToObject(3));
        assertNull(graph.intToObject(-1));
    }

    @Test
    void objectToInt() {
        IncidenceMatrix graph = new IncidenceMatrix(false);
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("C");

        assertEquals(0, graph.objectToInt("A"));
        assertEquals(1, graph.objectToInt("B"));
        assertEquals(2, graph.objectToInt("C"));
        assertEquals(-1, graph.objectToInt("D"));
    }

    @Test
    void addVertex() {
        IncidenceMatrix graph = new IncidenceMatrix(false);
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
        IncidenceMatrix graph = new IncidenceMatrix(false);
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
        IncidenceMatrix graph = new IncidenceMatrix(true);
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
        IncidenceMatrix graph = new IncidenceMatrix(false);
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
        IncidenceMatrix graph = new IncidenceMatrix(true);
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
        IncidenceMatrix graph = new IncidenceMatrix(false);
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("C");
        graph.addEdge("A", "B");
        graph.addEdge("B", "C");

        assertTrue(graph.hasEdge("A", "B"));
        assertTrue(graph.hasEdge("B", "A"));

        graph.removeEdge("A", "B");

        assertFalse(graph.hasEdge("A", "B"));
        assertFalse(graph.hasEdge("B", "A"));

        assertTrue(graph.hasEdge("B", "C"));
        assertTrue(graph.hasEdge("C", "B"));

        assertThrows(NullPointerException.class, () -> graph.removeEdge("A", "D"));
    }

    @Test
    void removeEdgeDirected() {
        IncidenceMatrix graph = new IncidenceMatrix(true);
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
        IncidenceMatrix graph = new IncidenceMatrix(false);
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

        List<Object> neighborsE = graph.getNeighbors("E");
        assertTrue(neighborsE.isEmpty());
    }

    @Test
    void getNeighborsDirected() {
        IncidenceMatrix graph = new IncidenceMatrix(true);
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
        IncidenceMatrix graph = new IncidenceMatrix(false);

        try {
            graph.readFromFile("graph.txt");
            assertTrue(graph.getAllVertices().size() > 0);
        } catch (RuntimeException e) {
            assertTrue(e.getMessage().contains("Error reading graph from file"));
        }
    }

    @Test
    void hasVertex() {
        IncidenceMatrix graph = new IncidenceMatrix(false);
        graph.addVertex("A");
        graph.addVertex("B");

        assertTrue(graph.hasVertex("A"));
        assertTrue(graph.hasVertex("B"));
        assertFalse(graph.hasVertex("C"));
    }

    @Test
    void hasEdge() {
        IncidenceMatrix graph = new IncidenceMatrix(false);
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
    void hasEdgeDirected() {
        IncidenceMatrix graph = new IncidenceMatrix(true);
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
    void getAllVertices() {
        IncidenceMatrix graph = new IncidenceMatrix(false);
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
        IncidenceMatrix undirected = new IncidenceMatrix(false);
        IncidenceMatrix directed = new IncidenceMatrix(true);

        assertFalse(undirected.isDirected());
        assertTrue(directed.isDirected());
    }

    @Test
    void multipleEdges() {
        IncidenceMatrix graph = new IncidenceMatrix(false);
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("C");

        graph.addEdge("A", "B");
        graph.addEdge("A", "C");
        graph.addEdge("B", "C");

        assertEquals(2, graph.getNeighbors("A").size());
        assertEquals(2, graph.getNeighbors("B").size());
        assertEquals(2, graph.getNeighbors("C").size()); // A и B
    }

    @Test
    void emptyGraph() {
        IncidenceMatrix graph = new IncidenceMatrix(false);

        assertTrue(graph.getAllVertices().isEmpty());
        assertFalse(graph.hasVertex("A"));
        assertFalse(graph.hasEdge("A", "B"));
        assertTrue(graph.getNeighbors("A").isEmpty());
    }

    @Test
    void singleVertex() {
        IncidenceMatrix graph = new IncidenceMatrix(false);
        graph.addVertex("A");

        assertEquals(1, graph.getAllVertices().size());
        assertTrue(graph.hasVertex("A"));
        assertTrue(graph.getNeighbors("A").isEmpty());
    }

    @Test
    void selfLoopDirected() {
        IncidenceMatrix graph = new IncidenceMatrix(true);
        graph.addVertex("A");

        graph.addEdge("A", "A");

        assertTrue(graph.hasEdge("A", "A"));
        List<Object> neighbors = graph.getNeighbors("A");
        assertEquals(1, neighbors.size());
        assertTrue(neighbors.contains("A"));
    }

    @Test
    void selfLoopUndirected() {
        IncidenceMatrix graph = new IncidenceMatrix(false);
        graph.addVertex("A");

        graph.addEdge("A", "A");

        assertTrue(graph.hasEdge("A", "A"));
        List<Object> neighbors = graph.getNeighbors("A");
        assertEquals(1, neighbors.size());
        assertTrue(neighbors.contains("A"));
    }
}