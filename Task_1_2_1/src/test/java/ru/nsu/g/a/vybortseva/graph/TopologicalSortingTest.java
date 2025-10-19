package ru.nsu.g.a.vybortseva.graph;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import java.util.List;

class TopologicalSortingTest {

    @Test
    void topologicalSort() {
        AdjacencyList graph = new AdjacencyList(true);
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
        graph.addEdge(vertexC, vertexD);

        List<Vertex> result = TopologicalSorting.topologicalSort(graph);
        assertEquals(4, result.size());

        assertTrue(result.indexOf(vertexA) < result.indexOf(vertexB));
        assertTrue(result.indexOf(vertexA) < result.indexOf(vertexC));
        assertTrue(result.indexOf(vertexB) < result.indexOf(vertexD));
        assertTrue(result.indexOf(vertexC) < result.indexOf(vertexD));
    }

    @Test
    void topologicalSortComplexGraph() {
        AdjacencyList graph = new AdjacencyList(true);
        Vertex vertexA = new StringVertex("A");
        Vertex vertexB = new StringVertex("B");
        Vertex vertexC = new StringVertex("C");
        Vertex vertexD = new StringVertex("D");
        Vertex vertexE = new StringVertex("E");
        Vertex vertexF = new StringVertex("F");

        graph.addVertex(vertexA);
        graph.addVertex(vertexB);
        graph.addVertex(vertexC);
        graph.addVertex(vertexD);
        graph.addVertex(vertexE);
        graph.addVertex(vertexF);

        graph.addEdge(vertexA, vertexB);
        graph.addEdge(vertexA, vertexC);
        graph.addEdge(vertexB, vertexD);
        graph.addEdge(vertexC, vertexD);
        graph.addEdge(vertexD, vertexE);
        graph.addEdge(vertexE, vertexF);

        List<Vertex> result = TopologicalSorting.topologicalSort(graph);
        assertEquals(6, result.size());

        assertTrue(result.indexOf(vertexA) < result.indexOf(vertexB));
        assertTrue(result.indexOf(vertexA) < result.indexOf(vertexC));
        assertTrue(result.indexOf(vertexB) < result.indexOf(vertexD));
        assertTrue(result.indexOf(vertexC) < result.indexOf(vertexD));
        assertTrue(result.indexOf(vertexD) < result.indexOf(vertexE));
        assertTrue(result.indexOf(vertexE) < result.indexOf(vertexF));
    }

    @Test
    void topologicalSortWithMultipleStarts() {
        AdjacencyList graph = new AdjacencyList(true);
        Vertex vertexA = new StringVertex("A");
        Vertex vertexB = new StringVertex("B");
        Vertex vertexC = new StringVertex("C");
        Vertex vertexD = new StringVertex("D");

        graph.addVertex(vertexA);
        graph.addVertex(vertexB);
        graph.addVertex(vertexC);
        graph.addVertex(vertexD);

        graph.addEdge(vertexA, vertexC);
        graph.addEdge(vertexB, vertexC);
        graph.addEdge(vertexC, vertexD);

        List<Vertex> result = TopologicalSorting.topologicalSort(graph);
        assertEquals(4, result.size());

        assertTrue(result.indexOf(vertexA) < result.indexOf(vertexC));
        assertTrue(result.indexOf(vertexB) < result.indexOf(vertexC));
        assertTrue(result.indexOf(vertexC) < result.indexOf(vertexD));
    }

    @Test
    void topologicalSortSingleVertex() {
        AdjacencyList graph = new AdjacencyList(true);
        Vertex vertexA = new StringVertex("A");

        graph.addVertex(vertexA);

        List<Vertex> result = TopologicalSorting.topologicalSort(graph);
        assertEquals(1, result.size());
        assertEquals(vertexA, result.get(0));
    }

    @Test
    void topologicalSortEmptyGraph() {
        AdjacencyList graph = new AdjacencyList(true);

        List<Vertex> result = TopologicalSorting.topologicalSort(graph);
        assertTrue(result.isEmpty());
    }

    @Test
    void topologicalSortWithCycle() {
        AdjacencyList graph = new AdjacencyList(true);
        Vertex vertexA = new StringVertex("A");
        Vertex vertexB = new StringVertex("B");
        Vertex vertexC = new StringVertex("C");

        graph.addVertex(vertexA);
        graph.addVertex(vertexB);
        graph.addVertex(vertexC);

        graph.addEdge(vertexA, vertexB);
        graph.addEdge(vertexB, vertexC);
        graph.addEdge(vertexC, vertexA);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> TopologicalSorting.topologicalSort(graph)
        );

        assertTrue(exception.getMessage().contains("cycle"));
    }

    @Test
    void topologicalSortSelfLoop() {
        AdjacencyList graph = new AdjacencyList(true);
        Vertex vertexA = new StringVertex("A");
        Vertex vertexB = new StringVertex("B");

        graph.addVertex(vertexA);
        graph.addVertex(vertexB);

        graph.addEdge(vertexA, vertexA);
        graph.addEdge(vertexA, vertexB);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> TopologicalSorting.topologicalSort(graph)
        );

        assertTrue(exception.getMessage().contains("cycle"));
    }

    @Test
    void topologicalSortUndirectedGraph() {
        AdjacencyList graph = new AdjacencyList(false);
        Vertex vertexA = new StringVertex("A");
        Vertex vertexB = new StringVertex("B");

        graph.addVertex(vertexA);
        graph.addVertex(vertexB);
        graph.addEdge(vertexA, vertexB);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> TopologicalSorting.topologicalSort(graph)
        );

        assertTrue(exception.getMessage().contains("directed"));
    }

    @Test
    void topologicalSortWithIsolatedVertices() {
        AdjacencyList graph = new AdjacencyList(true);
        Vertex vertexA = new StringVertex("A");
        Vertex vertexB = new StringVertex("B");
        Vertex vertexC = new StringVertex("C");
        Vertex vertexD = new StringVertex("D");

        graph.addVertex(vertexA);
        graph.addVertex(vertexB);
        graph.addVertex(vertexC);
        graph.addVertex(vertexD);

        graph.addEdge(vertexA, vertexB);

        List<Vertex> result = TopologicalSorting.topologicalSort(graph);
        assertEquals(4, result.size());

        assertTrue(result.indexOf(vertexA) < result.indexOf(vertexB));
    }

    @Test
    void topologicalSortDifferentGraphRepresentations() {
        // Тест с AdjacencyMatrix
        AdjacencyMatrix matrixGraph = new AdjacencyMatrix(true);
        Vertex vertexA = new StringVertex("A");
        Vertex vertexB = new StringVertex("B");
        Vertex vertexC = new StringVertex("C");

        matrixGraph.addVertex(vertexA);
        matrixGraph.addVertex(vertexB);
        matrixGraph.addVertex(vertexC);
        matrixGraph.addEdge(vertexA, vertexB);
        matrixGraph.addEdge(vertexB, vertexC);

        List<Vertex> matrixResult = TopologicalSorting.topologicalSort(matrixGraph);
        assertEquals(3, matrixResult.size());
        assertTrue(matrixResult.indexOf(vertexA) < matrixResult.indexOf(vertexB));
        assertTrue(matrixResult.indexOf(vertexB) < matrixResult.indexOf(vertexC));

        // Тест с IncidenceMatrix
        IncidenceMatrix incidenceGraph = new IncidenceMatrix(true);
        Vertex vertexA2 = new StringVertex("A");
        Vertex vertexB2 = new StringVertex("B");
        Vertex vertexC2 = new StringVertex("C");

        incidenceGraph.addVertex(vertexA2);
        incidenceGraph.addVertex(vertexB2);
        incidenceGraph.addVertex(vertexC2);
        incidenceGraph.addEdge(vertexA2, vertexB2);
        incidenceGraph.addEdge(vertexB2, vertexC2);

        List<Vertex> incidenceResult = TopologicalSorting.topologicalSort(incidenceGraph);
        assertEquals(3, incidenceResult.size());
        assertTrue(incidenceResult.indexOf(vertexA2) < incidenceResult.indexOf(vertexB2));
        assertTrue(incidenceResult.indexOf(vertexB2) < incidenceResult.indexOf(vertexC2));
    }

    @Test
    void topologicalSortLargeGraph() {
        AdjacencyList graph = new AdjacencyList(true);

        for (int i = 0; i < 10; i++) {
            graph.addVertex(new StringVertex("V" + i));
        }

        for (int i = 0; i < 9; i++) {
            Vertex from = new StringVertex("V" + i);
            Vertex to = new StringVertex("V" + (i + 1));
            graph.addEdge(from, to);
        }

        List<Vertex> result = TopologicalSorting.topologicalSort(graph);
        assertEquals(10, result.size());

        for (int i = 0; i < 9; i++) {
            Vertex from = new StringVertex("V" + i);
            Vertex to = new StringVertex("V" + (i + 1));
            assertTrue(result.indexOf(from) < result.indexOf(to));
        }
    }
}