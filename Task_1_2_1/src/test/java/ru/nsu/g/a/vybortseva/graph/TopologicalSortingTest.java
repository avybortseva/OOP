package ru.nsu.g.a.vybortseva.graph;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TopologicalSortingTest {

    @Test
    void topologicalSort() {
        AdjacencyList graph1 = new AdjacencyList(true);
        graph1.addVertex("A");
        graph1.addVertex("B");
        graph1.addVertex("C");
        graph1.addVertex("D");

        graph1.addEdge("A", "B");
        graph1.addEdge("A", "C");
        graph1.addEdge("B", "D");
        graph1.addEdge("C", "D");

        List<Object> result1 = TopologicalSorting.topologicalSort(graph1);
        assertEquals(4, result1.size());

        assertTrue(result1.indexOf("A") < result1.indexOf("B"));
        assertTrue(result1.indexOf("A") < result1.indexOf("C"));
        assertTrue(result1.indexOf("B") < result1.indexOf("D"));
        assertTrue(result1.indexOf("C") < result1.indexOf("D"));
    }

    @Test
    void topologicalSortComplexGraph() {
        AdjacencyList graph = new AdjacencyList(true);
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("C");
        graph.addVertex("D");
        graph.addVertex("E");
        graph.addVertex("F");

        graph.addEdge("A", "B");
        graph.addEdge("A", "C");
        graph.addEdge("B", "D");
        graph.addEdge("C", "D");
        graph.addEdge("D", "E");
        graph.addEdge("E", "F");

        List<Object> result = TopologicalSorting.topologicalSort(graph);
        assertEquals(6, result.size());

        assertTrue(result.indexOf("A") < result.indexOf("B"));
        assertTrue(result.indexOf("A") < result.indexOf("C"));
        assertTrue(result.indexOf("B") < result.indexOf("D"));
        assertTrue(result.indexOf("C") < result.indexOf("D"));
        assertTrue(result.indexOf("D") < result.indexOf("E"));
        assertTrue(result.indexOf("E") < result.indexOf("F"));
    }

    @Test
    void topologicalSortWithMultipleStarts() {
        AdjacencyList graph = new AdjacencyList(true);
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("C");
        graph.addVertex("D");

        graph.addEdge("A", "C");
        graph.addEdge("B", "C");
        graph.addEdge("C", "D");

        List<Object> result = TopologicalSorting.topologicalSort(graph);
        assertEquals(4, result.size());

        assertTrue(result.indexOf("A") < result.indexOf("C"));
        assertTrue(result.indexOf("B") < result.indexOf("C"));
        assertTrue(result.indexOf("C") < result.indexOf("D"));
    }

    @Test
    void topologicalSortSingleVertex() {
        AdjacencyList graph = new AdjacencyList(true);
        graph.addVertex("A");

        List<Object> result = TopologicalSorting.topologicalSort(graph);
        assertEquals(1, result.size());
        assertEquals("A", result.get(0));
    }

    @Test
    void topologicalSortEmptyGraph() {
        AdjacencyList graph = new AdjacencyList(true);

        List<Object> result = TopologicalSorting.topologicalSort(graph);
        assertTrue(result.isEmpty());
    }

    @Test
    void topologicalSortWithCycle() {
        AdjacencyList graph = new AdjacencyList(true);
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("C");

        graph.addEdge("A", "B");
        graph.addEdge("B", "C");
        graph.addEdge("C", "A");

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> TopologicalSorting.topologicalSort(graph)
        );

        assertTrue(exception.getMessage().contains("cycle"));
    }

    @Test
    void topologicalSortSelfLoop() {
        AdjacencyList graph = new AdjacencyList(true);
        graph.addVertex("A");
        graph.addVertex("B");

        graph.addEdge("A", "A");
        graph.addEdge("A", "B");

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> TopologicalSorting.topologicalSort(graph)
        );

        assertTrue(exception.getMessage().contains("cycle"));
    }

    @Test
    void topologicalSortUndirectedGraph() {
        AdjacencyList graph = new AdjacencyList(false);
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addEdge("A", "B");

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> TopologicalSorting.topologicalSort(graph)
        );

        assertTrue(exception.getMessage().contains("directed"));
    }

    @Test
    void topologicalSortWithIsolatedVertices() {
        AdjacencyList graph = new AdjacencyList(true);
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("C");
        graph.addVertex("D");

        graph.addEdge("A", "B");

        List<Object> result = TopologicalSorting.topologicalSort(graph);
        assertEquals(4, result.size());

        assertTrue(result.indexOf("A") < result.indexOf("B"));
    }

    @Test
    void topologicalSortDifferentGraphRepresentations() {

        AdjacencyMatrix matrixGraph = new AdjacencyMatrix(true);
        matrixGraph.setVertices(Arrays.asList("A", "B", "C"));
        matrixGraph.addEdge("A", "B");
        matrixGraph.addEdge("B", "C");

        List<Object> matrixResult = TopologicalSorting.topologicalSort(matrixGraph);
        assertEquals(3, matrixResult.size());
        assertTrue(matrixResult.indexOf("A") < matrixResult.indexOf("B"));
        assertTrue(matrixResult.indexOf("B") < matrixResult.indexOf("C"));

        IncidenceMatrix incidenceGraph = new IncidenceMatrix(true);
        incidenceGraph.addVertex("A");
        incidenceGraph.addVertex("B");
        incidenceGraph.addVertex("C");
        incidenceGraph.addEdge("A", "B");
        incidenceGraph.addEdge("B", "C");

        List<Object> incidenceResult = TopologicalSorting.topologicalSort(incidenceGraph);
        assertEquals(3, incidenceResult.size());
        assertTrue(incidenceResult.indexOf("A") < incidenceResult.indexOf("B"));
        assertTrue(incidenceResult.indexOf("B") < incidenceResult.indexOf("C"));
    }

    @Test
    void topologicalSortLargeGraph() {
        AdjacencyList graph = new AdjacencyList(true);

        for (int i = 0; i < 10; i++) {
            graph.addVertex("V" + i);
        }

        for (int i = 0; i < 9; i++) {
            graph.addEdge("V" + i, "V" + (i + 1));
        }

        List<Object> result = TopologicalSorting.topologicalSort(graph);
        assertEquals(10, result.size());

        for (int i = 0; i < 9; i++) {
            assertTrue(result.indexOf("V" + i) < result.indexOf("V" + (i + 1)));
        }
    }
}