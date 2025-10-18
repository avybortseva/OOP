package ru.nsu.g.a.vybortseva.graph;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Демонстрация трех представлений графа ===\n");

        List<String> testVertices = Arrays.asList("A", "B", "C", "D", "E");
        String[][] testEdges = {
                {"A", "B"}, {"A", "C"}, {"B", "D"}, {"C", "D"}, {"D", "E"}
        };

        System.out.println("=== НЕОРИЕНТИРОВАННЫЙ ГРАФ ===\n");

        demonstrateAdjacencyMatrix(false, testVertices, testEdges);
        demonstrateIncidenceMatrix(false, testVertices, testEdges);
        demonstrateAdjacencyList(false, testVertices, testEdges);

        System.out.println("\n=== ОРИЕНТИРОВАННЫЙ ГРАФ ===\n");

        demonstrateAdjacencyMatrix(true, testVertices, testEdges);
        demonstrateIncidenceMatrix(true, testVertices, testEdges);
        demonstrateAdjacencyList(true, testVertices, testEdges);

        System.out.println("\n=== ТОПОЛОГИЧЕСКАЯ СОРТИРОВКА ===\n");

        testTopologicalSort();

        System.out.println("\n=== ЧТЕНИЕ ИЗ ФАЙЛА ===\n");

        demonstrateGraphFromFile();
    }

    private static void demonstrateAdjacencyMatrix(boolean directed, List<String> vertices, String[][] edges) {
        System.out.println("1. МАТРИЦА СМЕЖНОСТИ:");

        AdjacencyMatrix graph = new AdjacencyMatrix(directed);
        graph.setVertices(Arrays.asList(vertices.toArray()));

        for (String[] edge : edges) {
            graph.addEdge(edge[0], edge[1]);
        }

        printGraphInfo(graph, directed);
        printAdjacencyMatrixRepresentation(graph, vertices);

        if (directed) {
            try {
                List<Object> sorted = TopologicalSorting.topologicalSort(graph);
                System.out.println("Топологическая сортировка: " + sorted);
            } catch (IllegalArgumentException e) {
                System.out.println("Топологическая сортировка невозможна: " + e.getMessage());
            }
        }
        System.out.println();
    }

    private static void demonstrateIncidenceMatrix(boolean directed, List<String> vertices, String[][] edges) {
        System.out.println("2. МАТРИЦА ИНЦИДЕНТНОСТИ:");

        IncidenceMatrix graph = new IncidenceMatrix(directed);

        for (String vertex : vertices) {
            graph.addVertex(vertex);
        }

        for (String[] edge : edges) {
            graph.addEdge(edge[0], edge[1]);
        }

        printGraphInfo(graph, directed);
        printSimpleIncidenceMatrix(graph, vertices, edges);

        if (directed) {
            try {
                List<Object> sorted = TopologicalSorting.topologicalSort(graph);
                System.out.println("Топологическая сортировка: " + sorted);
            } catch (IllegalArgumentException e) {
                System.out.println("Топологическая сортировка невозможна: " + e.getMessage());
            }
        }
        System.out.println();
    }

    private static void demonstrateAdjacencyList(boolean directed, List<String> vertices, String[][] edges) {
        System.out.println("3. СПИСОК СМЕЖНОСТИ:");

        AdjacencyList graph = new AdjacencyList(directed);

        for (String vertex : vertices) {
            graph.addVertex(vertex);
        }

        for (String[] edge : edges) {
            graph.addEdge(edge[0], edge[1]);
        }

        printGraphInfo(graph, directed);
        printAdjacencyListRepresentation(graph);

        if (directed) {
            try {
                List<Object> sorted = TopologicalSorting.topologicalSort(graph);
                System.out.println("Топологическая сортировка: " + sorted);
            } catch (IllegalArgumentException e) {
                System.out.println("Топологическая сортировка невозможна: " + e.getMessage());
            }
        }
        System.out.println();
    }

    private static void testTopologicalSort() {
        System.out.println("Тест топологической сортировки на ациклическом графе:");

        AdjacencyList dag = new AdjacencyList(true);

        String[] vertices = {"A", "B", "C", "D", "E", "F"};
        for (String vertex : vertices) {
            dag.addVertex(vertex);
        }

        dag.addEdge("A", "B");
        dag.addEdge("A", "C");
        dag.addEdge("B", "D");
        dag.addEdge("C", "D");
        dag.addEdge("D", "E");
        dag.addEdge("E", "F");

        System.out.println("Вершины: " + dag.getAllVertices());
        System.out.println("Список смежности:");
        for (Object vertex : dag.getAllVertices()) {
            System.out.println("  " + vertex + " -> " + dag.getNeighbors(vertex));
        }

        try {
            List<Object> sorted = TopologicalSorting.topologicalSort(dag);
            System.out.println("Результат топологической сортировки: " + sorted);
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }

        System.out.println("\nТест топологической сортировки на циклическом графе:");

        AdjacencyList cyclicGraph = new AdjacencyList(true);

        for (String vertex : vertices) {
            cyclicGraph.addVertex(vertex);
        }

        cyclicGraph.addEdge("A", "B");
        cyclicGraph.addEdge("B", "C");
        cyclicGraph.addEdge("C", "A");
        cyclicGraph.addEdge("C", "D");

        System.out.println("Вершины: " + cyclicGraph.getAllVertices());
        System.out.println("Список смежности:");
        for (Object vertex : cyclicGraph.getAllVertices()) {
            System.out.println("  " + vertex + " -> " + cyclicGraph.getNeighbors(vertex));
        }

        try {
            List<Object> sorted = TopologicalSorting.topologicalSort(cyclicGraph);
            System.out.println("Результат топологической сортировки: " + sorted);
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    private static void demonstrateGraphFromFile() {
        String filename = "graph.txt";

        System.out.println("Чтение графа из файла: " + filename);

        try {
            System.out.println("\n--- Матрица смежности из файла ---");
            AdjacencyMatrix matrixFromFile = new AdjacencyMatrix(false);
            matrixFromFile.readFromFile(filename);
            printGraphInfo(matrixFromFile, false);
            printAdjacencyListRepresentation(matrixFromFile);

            System.out.println("\n--- Матрица инцидентности из файла ---");
            IncidenceMatrix incidenceFromFile = new IncidenceMatrix(false);
            incidenceFromFile.readFromFile(filename);
            printGraphInfo(incidenceFromFile, false);

            System.out.println("\n--- Список смежности из файла ---");
            AdjacencyList listFromFile = new AdjacencyList(false);
            listFromFile.readFromFile(filename);
            printGraphInfo(listFromFile, false);
            printAdjacencyListRepresentation(listFromFile);

            System.out.println("\n--- Ориентированный граф из файла ---");
            AdjacencyList directedFromFile = new AdjacencyList(true);
            directedFromFile.readFromFile(filename);
            printGraphInfo(directedFromFile, true);
            printAdjacencyListRepresentation(directedFromFile);

            try {
                List<Object> sorted = TopologicalSorting.topologicalSort(directedFromFile);
                System.out.println("Топологическая сортировка: " + sorted);
            } catch (IllegalArgumentException e) {
                System.out.println("Топологическая сортировка невозможна: " + e.getMessage());
            }
        } catch (Exception e) {
            System.out.println("Ошибка при чтении файла: " + e.getMessage());
        }
    }

    private static void printGraphInfo(Graph graph, boolean directed) {
        System.out.println("Тип графа: " + (directed ? "ориентированный" : "неориентированный"));
        System.out.println("Вершины: " + graph.getAllVertices());
        System.out.println("Количество вершин: " + graph.getAllVertices().size());

        List<Object> vertices = graph.getAllVertices();

        System.out.println("Соседи вершин:");
        for (Object vertex : vertices) {
            List<Object> neighbors = graph.getNeighbors(vertex);
            System.out.println("  " + vertex + " -> " + neighbors);
        }
    }

    private static void printAdjacencyMatrixRepresentation(AdjacencyMatrix graph, List<String> vertices) {
        System.out.println("Матрица смежности:");
        System.out.print("    ");
        for (String vertex : vertices) {
            System.out.print(vertex + " ");
        }
        System.out.println();

        for (int i = 0; i < vertices.size(); i++) {
            System.out.print(vertices.get(i) + " | ");
            for (int j = 0; j < vertices.size(); j++) {
                Object from = graph.intToObject(i);
                Object to = graph.intToObject(j);
                System.out.print((graph.hasEdge(from, to) ? "1 " : "0 "));
            }
            System.out.println();
        }
    }

    private static void printSimpleIncidenceMatrix(IncidenceMatrix graph, List<String> vertices, String[][] edges) {
        System.out.println("Матрица инцидентности:");

        System.out.print("   ");
        for (int i = 0; i < edges.length; i++) {
            System.out.print(edges[i][0] + edges[i][1] + " ");
        }
        System.out.println();

        for (String vertex : vertices) {
            System.out.print(vertex + " |");

            for (String[] edge : edges) {
                String from = edge[0];
                String to = edge[1];

                if (graph.isDirected()) {
                    if (vertex.equals(from) && graph.hasEdge(from, to)) {
                        System.out.print(" 1 ");
                    } else if (vertex.equals(to) && graph.hasEdge(from, to)) {
                        System.out.print("-1 ");
                    } else {
                        System.out.print(" 0 ");
                    }
                } else {
                    if ((vertex.equals(from) || vertex.equals(to)) && graph.hasEdge(from, to)) {
                        System.out.print(" 1 ");
                    } else {
                        System.out.print(" 0 ");
                    }
                }
            }
            System.out.println();
        }
    }

    private static void printAdjacencyListRepresentation(Graph graph) {
        System.out.println("Список смежности:");
        for (Object vertex : graph.getAllVertices()) {
            List<Object> neighbors = graph.getNeighbors(vertex);
            System.out.println("  " + vertex + " -> " + neighbors);
        }
    }
}