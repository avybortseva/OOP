package ru.nsu.g.a.vybortseva.graph;

import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Тесты для класса AdjacencyList
 */
class AdjacencyListTest {

    @Test
    void addVertex() {
        AdjacencyList graph = new AdjacencyList(false);
        Vertex vertexA = new StringVertex("A");
        Vertex vertexB = new StringVertex("B");

        graph.addVertex(vertexA);
        graph.addVertex(vertexB);

        assertTrue(graph.hasVertex(vertexA));
        assertTrue(graph.hasVertex(vertexB));
        assertEquals(2, graph.getVertices().size());

        graph.addVertex(vertexA);
        assertEquals(2, graph.getVertices().size());
    }

    @Test
    void removeVertex() {
        AdjacencyList graph = new AdjacencyList(false);
        Vertex vertexA = new StringVertex("A");
        Vertex vertexB = new StringVertex("B");
        Vertex vertexC = new StringVertex("C");

        graph.addVertex(vertexA);
        graph.addVertex(vertexB);
        graph.addVertex(vertexC);
        graph.addEdge(vertexA, vertexB);
        graph.addEdge(vertexB, vertexC);

        graph.removeVertex(vertexB);

        assertFalse(graph.hasVertex(vertexB));
        assertTrue(graph.hasVertex(vertexA));
        assertTrue(graph.hasVertex(vertexC));
        assertFalse(graph.hasEdge(vertexA, vertexB));
        assertFalse(graph.hasEdge(vertexB, vertexC));

        Vertex vertexD = new StringVertex("D");
        assertThrows(IllegalArgumentException.class, () -> graph.removeVertex(vertexD));
    }

    @Test
    void removeVertexDirected() {
        AdjacencyList graph = new AdjacencyList(true);
        Vertex vertexA = new StringVertex("A");
        Vertex vertexB = new StringVertex("B");
        Vertex vertexC = new StringVertex("C");

        graph.addVertex(vertexA);
        graph.addVertex(vertexB);
        graph.addVertex(vertexC);
        graph.addEdge(vertexA, vertexB);
        graph.addEdge(vertexB, vertexC);

        graph.removeVertex(vertexB);

        assertFalse(graph.hasVertex(vertexB));
        assertFalse(graph.hasEdge(vertexA, vertexB));
        assertFalse(graph.hasEdge(vertexB, vertexC));
    }

    @Test
    void addEdge() {
        AdjacencyList graph = new AdjacencyList(false);
        Vertex vertexA = new StringVertex("A");
        Vertex vertexB = new StringVertex("B");
        Vertex vertexC = new StringVertex("C");

        graph.addVertex(vertexA);
        graph.addVertex(vertexB);
        graph.addVertex(vertexC);

        graph.addEdge(vertexA, vertexB);
        graph.addEdge(vertexB, vertexC);

        assertTrue(graph.hasEdge(vertexA, vertexB));
        assertTrue(graph.hasEdge(vertexB, vertexA));
        assertTrue(graph.hasEdge(vertexB, vertexC));
        assertTrue(graph.hasEdge(vertexC, vertexB));
        assertFalse(graph.hasEdge(vertexA, vertexC));
    }

    @Test
    void addEdgeDirected() {
        AdjacencyList graph = new AdjacencyList(true);
        Vertex vertexA = new StringVertex("A");
        Vertex vertexB = new StringVertex("B");
        Vertex vertexC = new StringVertex("C");

        graph.addVertex(vertexA);
        graph.addVertex(vertexB);
        graph.addVertex(vertexC);

        graph.addEdge(vertexA, vertexB);
        graph.addEdge(vertexB, vertexC);

        assertTrue(graph.hasEdge(vertexA, vertexB));
        assertFalse(graph.hasEdge(vertexB, vertexA));
        assertTrue(graph.hasEdge(vertexB, vertexC));
        assertFalse(graph.hasEdge(vertexC, vertexB));
    }

    @Test
    void removeEdge() {
        AdjacencyList graph = new AdjacencyList(false);
        Vertex vertexA = new StringVertex("A");
        Vertex vertexB = new StringVertex("B");
        Vertex vertexC = new StringVertex("C");

        graph.addVertex(vertexA);
        graph.addVertex(vertexB);
        graph.addVertex(vertexC);
        graph.addEdge(vertexA, vertexB);
        graph.addEdge(vertexB, vertexC);

        graph.removeEdge(vertexA, vertexB);

        assertFalse(graph.hasEdge(vertexA, vertexB));
        assertFalse(graph.hasEdge(vertexB, vertexA));
        assertTrue(graph.hasEdge(vertexB, vertexC));
        assertTrue(graph.hasEdge(vertexC, vertexB));

        Vertex vertexD = new StringVertex("D");
        assertThrows(IllegalArgumentException.class, () -> graph.removeEdge(vertexA, vertexD));
    }

    @Test
    void removeEdgeDirected() {
        AdjacencyList graph = new AdjacencyList(true);
        Vertex vertexA = new StringVertex("A");
        Vertex vertexB = new StringVertex("B");
        Vertex vertexC = new StringVertex("C");

        graph.addVertex(vertexA);
        graph.addVertex(vertexB);
        graph.addVertex(vertexC);
        graph.addEdge(vertexA, vertexB);
        graph.addEdge(vertexB, vertexC);

        graph.removeEdge(vertexA, vertexB);

        assertFalse(graph.hasEdge(vertexA, vertexB));
        assertTrue(graph.hasEdge(vertexB, vertexC));
    }

    @Test
    void getNeighbors() {
        AdjacencyList graph = new AdjacencyList(false);
        Vertex vertexA = new StringVertex("A");
        Vertex vertexB = new StringVertex("B");
        Vertex vertexC = new StringVertex("C");
        Vertex vertexD = new StringVertex("D");

        graph.addVertex(vertexA);
        graph.addVertex(vertexB);
        graph.addVertex(vertexC);
        graph.addVertex(vertexD);
        graph.addEdge(vertexA, vertexB);
        graph.addEdge(vertexA, vertexC);
        graph.addEdge(vertexB, vertexD);

        List<Vertex> neighborsA = graph.getNeighbors(vertexA);
        assertEquals(2, neighborsA.size());
        assertTrue(neighborsA.contains(vertexB));
        assertTrue(neighborsA.contains(vertexC));

        List<Vertex> neighborsB = graph.getNeighbors(vertexB);
        assertEquals(2, neighborsB.size());
        assertTrue(neighborsB.contains(vertexA));
        assertTrue(neighborsB.contains(vertexD));

        List<Vertex> neighborsC = graph.getNeighbors(vertexC);
        assertEquals(1, neighborsC.size());
        assertTrue(neighborsC.contains(vertexA));

        Vertex vertexE = new StringVertex("E");
        List<Vertex> neighborsE = graph.getNeighbors(vertexE);
        assertTrue(neighborsE.isEmpty());
    }

    @Test
    void getNeighborsDirected() {
        AdjacencyList graph = new AdjacencyList(true);
        Vertex vertexA = new StringVertex("A");
        Vertex vertexB = new StringVertex("B");
        Vertex vertexC = new StringVertex("C");

        graph.addVertex(vertexA);
        graph.addVertex(vertexB);
        graph.addVertex(vertexC);
        graph.addEdge(vertexA, vertexB);
        graph.addEdge(vertexB, vertexC);

        List<Vertex> neighborsA = graph.getNeighbors(vertexA);
        assertEquals(1, neighborsA.size());
        assertTrue(neighborsA.contains(vertexB));

        List<Vertex> neighborsB = graph.getNeighbors(vertexB);
        assertEquals(1, neighborsB.size());
        assertTrue(neighborsB.contains(vertexC));

        List<Vertex> neighborsC = graph.getNeighbors(vertexC);
        assertTrue(neighborsC.isEmpty());
    }

    @Test
    void readFromFile() {
        AdjacencyList graph = new AdjacencyList(false);

        try {
            graph.readFromFile("graph.txt");
            assertTrue(graph.getVertices().size() > 0);
        } catch (RuntimeException e) {
            assertTrue(e.getMessage().contains("Error reading graph from file"));
        }
    }

    @Test
    void hasVertex() {
        AdjacencyList graph = new AdjacencyList(false);
        Vertex vertexA = new StringVertex("A");
        Vertex vertexB = new StringVertex("B");

        graph.addVertex(vertexA);
        graph.addVertex(vertexB);

        assertTrue(graph.hasVertex(vertexA));
        assertTrue(graph.hasVertex(vertexB));

        Vertex vertexC = new StringVertex("C");
        assertFalse(graph.hasVertex(vertexC));
    }

    @Test
    void hasEdge() {
        AdjacencyList graph = new AdjacencyList(false);
        Vertex vertexA = new StringVertex("A");
        Vertex vertexB = new StringVertex("B");
        Vertex vertexC = new StringVertex("C");

        graph.addVertex(vertexA);
        graph.addVertex(vertexB);
        graph.addVertex(vertexC);
        graph.addEdge(vertexA, vertexB);

        assertTrue(graph.hasEdge(vertexA, vertexB));
        assertTrue(graph.hasEdge(vertexB, vertexA));
        assertFalse(graph.hasEdge(vertexA, vertexC));
        assertFalse(graph.hasEdge(vertexB, vertexC));

        Vertex vertexD = new StringVertex("D");
        assertFalse(graph.hasEdge(vertexA, vertexD));
        assertFalse(graph.hasEdge(vertexD, vertexA));
    }

    @Test
    void getVertices() {
        AdjacencyList graph = new AdjacencyList(false);
        Vertex vertexA = new StringVertex("A");
        Vertex vertexB = new StringVertex("B");
        Vertex vertexC = new StringVertex("C");

        graph.addVertex(vertexA);
        graph.addVertex(vertexB);
        graph.addVertex(vertexC);

        List<Vertex> vertices = graph.getVertices();
        assertEquals(3, vertices.size());
        assertTrue(vertices.contains(vertexA));
        assertTrue(vertices.contains(vertexB));
        assertTrue(vertices.contains(vertexC));
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
        Vertex vertexA = new StringVertex("A");
        Vertex vertexB = new StringVertex("B");

        graph.addVertex(vertexA);
        graph.addVertex(vertexB);

        graph.addEdge(vertexA, vertexB);
        graph.addEdge(vertexA, vertexB);

        List<Vertex> neighbors = graph.getNeighbors(vertexA);
        assertEquals(1, neighbors.size());
    }
}