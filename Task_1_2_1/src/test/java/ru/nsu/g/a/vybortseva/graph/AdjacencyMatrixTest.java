package ru.nsu.g.a.vybortseva.graph;

import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Тесты для класса AdjacencyMatrix
 */
class AdjacencyMatrixTest {

    @Test
    void intToVertex() {
        AdjacencyMatrix graph = new AdjacencyMatrix(false);
        Vertex vertexA = new StringVertex("A");
        Vertex vertexB = new StringVertex("B");
        Vertex vertexC = new StringVertex("C");

        graph.addVertex(vertexA);
        graph.addVertex(vertexB);
        graph.addVertex(vertexC);

        assertEquals(vertexA, graph.intToVertex(0));
        assertEquals(vertexB, graph.intToVertex(1));
        assertEquals(vertexC, graph.intToVertex(2));
        assertNull(graph.intToVertex(3));
        assertNull(graph.intToVertex(-1));
    }

    @Test
    void VertexToInt() {
        AdjacencyMatrix graph = new AdjacencyMatrix(false);
        Vertex vertexA = new StringVertex("A");
        Vertex vertexB = new StringVertex("B");
        Vertex vertexC = new StringVertex("C");

        graph.addVertex(vertexA);
        graph.addVertex(vertexB);
        graph.addVertex(vertexC);

        assertEquals(0, graph.VertexToInt(vertexA));
        assertEquals(1, graph.VertexToInt(vertexB));
        assertEquals(2, graph.VertexToInt(vertexC));

        Vertex vertexD = new StringVertex("D");
        assertEquals(AdjacencyMatrix.INVALID_INDEX, graph.VertexToInt(vertexD));
    }

    @Test
    void addVertex() {
        AdjacencyMatrix graph = new AdjacencyMatrix(false);
        Vertex vertexA = new StringVertex("A");
        Vertex vertexB = new StringVertex("B");

        graph.addVertex(vertexA);
        graph.addVertex(vertexB);

        assertEquals(2, graph.getVertices().size());
        assertTrue(graph.hasVertex(vertexA));
        assertTrue(graph.hasVertex(vertexB));

        graph.addVertex(vertexA);
        assertEquals(2, graph.getVertices().size());
    }

    @Test
    void removeVertex() {
        AdjacencyMatrix graph = new AdjacencyMatrix(false);
        Vertex vertexA = new StringVertex("A");
        Vertex vertexB = new StringVertex("B");
        Vertex vertexC = new StringVertex("C");

        graph.addVertex(vertexA);
        graph.addVertex(vertexB);
        graph.addVertex(vertexC);
        graph.addEdge(vertexA, vertexB);
        graph.addEdge(vertexB, vertexC);

        graph.removeVertex(vertexB);

        assertEquals(2, graph.getVertices().size());
        assertFalse(graph.hasVertex(vertexB));
        assertFalse(graph.hasEdge(vertexA, vertexB));
        assertFalse(graph.hasEdge(vertexB, vertexC));

        Vertex vertexD = new StringVertex("D");
        assertThrows(IllegalArgumentException.class, () -> graph.removeVertex(vertexD));
    }

    @Test
    void addEdge() {
        AdjacencyMatrix graph = new AdjacencyMatrix(false);
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

        Vertex vertexD = new StringVertex("D");
        assertThrows(IllegalArgumentException.class, () -> graph.addEdge(vertexA, vertexD));

        AdjacencyMatrix graphDir = new AdjacencyMatrix(true);
        Vertex vertexADir = new StringVertex("A");
        Vertex vertexBDir = new StringVertex("B");
        Vertex vertexCDir = new StringVertex("C");

        graphDir.addVertex(vertexADir);
        graphDir.addVertex(vertexBDir);
        graphDir.addVertex(vertexCDir);

        graphDir.addEdge(vertexADir, vertexBDir);
        graphDir.addEdge(vertexBDir, vertexCDir);

        assertTrue(graphDir.hasEdge(vertexADir, vertexBDir));
        assertFalse(graphDir.hasEdge(vertexBDir, vertexADir));
        assertTrue(graphDir.hasEdge(vertexBDir, vertexCDir));
        assertFalse(graphDir.hasEdge(vertexCDir, vertexBDir));
    }

    @Test
    void removeEdge() {
        AdjacencyMatrix graph = new AdjacencyMatrix(false);
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

        Vertex vertexD = new StringVertex("D");
        assertThrows(IllegalArgumentException.class, () -> graph.removeEdge(vertexA, vertexD));
    }

    @Test
    void getNeighbors() {
        AdjacencyMatrix graph = new AdjacencyMatrix(false);
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

        AdjacencyMatrix graphDir = new AdjacencyMatrix(true);
        Vertex vertexADir = new StringVertex("A");
        Vertex vertexBDir = new StringVertex("B");
        Vertex vertexCDir = new StringVertex("C");

        graphDir.addVertex(vertexADir);
        graphDir.addVertex(vertexBDir);
        graphDir.addVertex(vertexCDir);
        graphDir.addEdge(vertexADir, vertexBDir);
        graphDir.addEdge(vertexBDir, vertexCDir);

        List<Vertex> neighborsADir = graphDir.getNeighbors(vertexADir);
        assertEquals(1, neighborsADir.size());
        assertTrue(neighborsADir.contains(vertexBDir));

        List<Vertex> neighborsBDir = graphDir.getNeighbors(vertexBDir);
        assertEquals(1, neighborsBDir.size());
        assertTrue(neighborsBDir.contains(vertexCDir));

        List<Vertex> neighborsCDir = graphDir.getNeighbors(vertexCDir);
        assertEquals(0, neighborsCDir.size());
    }

    @Test
    void hasVertex() {
        AdjacencyMatrix graph = new AdjacencyMatrix(false);
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
        AdjacencyMatrix graph = new AdjacencyMatrix(false);
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
    }

    @Test
    void getVertices() {
        AdjacencyMatrix graph = new AdjacencyMatrix(false);
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
        AdjacencyMatrix undirected = new AdjacencyMatrix(false);
        AdjacencyMatrix directed = new AdjacencyMatrix(true);

        assertFalse(undirected.isDirected());
        assertTrue(directed.isDirected());
    }
}