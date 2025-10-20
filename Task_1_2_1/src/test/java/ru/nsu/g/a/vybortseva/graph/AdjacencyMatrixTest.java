package ru.nsu.g.a.vybortseva.graph;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.Test;

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
    void vertexToInt() {
        AdjacencyMatrix graph = new AdjacencyMatrix(false);
        Vertex vertexA = new StringVertex("A");
        Vertex vertexB = new StringVertex("B");
        Vertex vertexC = new StringVertex("C");

        graph.addVertex(vertexA);
        graph.addVertex(vertexB);
        graph.addVertex(vertexC);

        assertEquals(0, graph.vertexToInt(vertexA));
        assertEquals(1, graph.vertexToInt(vertexB));
        assertEquals(2, graph.vertexToInt(vertexC));

        Vertex vertexD = new StringVertex("D");
        assertEquals(AdjacencyMatrix.INVALID_INDEX, graph.vertexToInt(vertexD));
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
        Vertex vertex1Dir = new StringVertex("A");
        Vertex vertex2Dir = new StringVertex("B");
        Vertex vertex3Dir = new StringVertex("C");

        graphDir.addVertex(vertex1Dir);
        graphDir.addVertex(vertex2Dir);
        graphDir.addVertex(vertex3Dir);

        graphDir.addEdge(vertex1Dir, vertex2Dir);
        graphDir.addEdge(vertex2Dir, vertex3Dir);

        assertTrue(graphDir.hasEdge(vertex1Dir, vertex2Dir));
        assertFalse(graphDir.hasEdge(vertex2Dir, vertex1Dir));
        assertTrue(graphDir.hasEdge(vertex2Dir, vertex3Dir));
        assertFalse(graphDir.hasEdge(vertex3Dir, vertex2Dir));
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
        Vertex vertex1Dir = new StringVertex("A");
        Vertex vertex2Dir = new StringVertex("B");
        Vertex vertex3Dir = new StringVertex("C");

        graphDir.addVertex(vertex1Dir);
        graphDir.addVertex(vertex2Dir);
        graphDir.addVertex(vertex3Dir);
        graphDir.addEdge(vertex1Dir, vertex2Dir);
        graphDir.addEdge(vertex2Dir, vertex3Dir);

        List<Vertex> neighbors1Dir = graphDir.getNeighbors(vertex1Dir);
        assertEquals(1, neighbors1Dir.size());
        assertTrue(neighbors1Dir.contains(vertex2Dir));

        List<Vertex> neighbors2Dir = graphDir.getNeighbors(vertex2Dir);
        assertEquals(1, neighbors2Dir.size());
        assertTrue(neighbors2Dir.contains(vertex3Dir));

        List<Vertex> neighbors3Dir = graphDir.getNeighbors(vertex3Dir);
        assertEquals(0, neighbors3Dir.size());
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