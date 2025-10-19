package ru.nsu.g.a.vybortseva.graph;

import java.util.Arrays;
import java.util.List;

/**
 * Main класс для демонстрации работы с графами
 */
public class Main {

    /**
     * Main метод для демонстрации работы с графами
     */
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

    /**
     * Демонстрирует работу с матрицей смежности.
     */
    private static void demonstrateAdjacencyMatrix(boolean directed,
                                                   List<String> vertices, String[][] edges) {
        System.out.println("1. МАТРИЦА СМЕЖНОСТИ:");

        AdjacencyMatrix graph = new AdjacencyMatrix(directed);

        for (String vertex : vertices) {
            graph.addVertex(new StringVertex(vertex));
        }

        for (String[] edge : edges) {
            Vertex from = new StringVertex(edge[0]);
            Vertex to = new StringVertex(edge[1]);
            graph.addEdge(from, to);
        }

        printGraphInfo(graph, directed);
        printAdjacencyMatrixRepresentation(graph, vertices);

        if (directed) {
            try {
                List<Vertex> sorted = TopologicalSorting.topologicalSort(graph);
                System.out.println("Топологическая сортировка: " + sorted);
            } catch (IllegalArgumentException e) {
                System.out.println("Топологическая сортировка невозможна: " + e.getMessage());
            }
        }
        System.out.println();
    }

    /**
     * Демонстрирует работу с матрицей инцидентности.
     */
    private static void demonstrateIncidenceMatrix(boolean directed,
                                                   List<String> vertices, String[][] edges) {
        System.out.println("2. МАТРИЦА ИНЦИДЕНТНОСТИ:");

        IncidenceMatrix graph = new IncidenceMatrix(directed);

        for (String vertex : vertices) {
            graph.addVertex(new StringVertex(vertex));
        }

        for (String[] edge : edges) {
            Vertex from = new StringVertex(edge[0]);
            Vertex to = new StringVertex(edge[1]);
            graph.addEdge(from, to);
        }

        printGraphInfo(graph, directed);
        printSimpleIncidenceMatrix(graph, vertices, edges);

        if (directed) {
            try {
                List<Vertex> sorted = TopologicalSorting.topologicalSort(graph);
                System.out.println("Топологическая сортировка: " + sorted);
            } catch (IllegalArgumentException e) {
                System.out.println("Топологическая сортировка невозможна: " + e.getMessage());
            }
        }
        System.out.println();
    }

    /**
     * Демонстрирует работу со списком смежности.
     */
    private static void demonstrateAdjacencyList(boolean directed,
                                                 List<String> vertices, String[][] edges) {
        System.out.println("3. СПИСОК СМЕЖНОСТИ:");

        AdjacencyList graph = new AdjacencyList(directed);

        for (String vertex : vertices) {
            graph.addVertex(new StringVertex(vertex));
        }

        for (String[] edge : edges) {
            Vertex from = new StringVertex(edge[0]);
            Vertex to = new StringVertex(edge[1]);
            graph.addEdge(from, to);
        }

        printGraphInfo(graph, directed);
        printAdjacencyListRepresentation(graph);

        if (directed) {
            try {
                List<Vertex> sorted = TopologicalSorting.topologicalSort(graph);
                System.out.println("Топологическая сортировка: " + sorted);
            } catch (IllegalArgumentException e) {
                System.out.println("Топологическая сортировка невозможна: " + e.getMessage());
            }
        }
        System.out.println();
    }

    /**
     * Тестирует топологическую сортировку на ациклическом и циклическом графах.
     */
    private static void testTopologicalSort() {
        System.out.println("Тест топологической сортировки на ациклическом графе:");

        AdjacencyList dag = new AdjacencyList(true);

        String[] vertices = {"A", "B", "C", "D", "E", "F"};
        for (String vertex : vertices) {
            dag.addVertex(new StringVertex(vertex));
        }

        dag.addEdge(new StringVertex("A"), new StringVertex("B"));
        dag.addEdge(new StringVertex("A"), new StringVertex("C"));
        dag.addEdge(new StringVertex("B"), new StringVertex("D"));
        dag.addEdge(new StringVertex("C"), new StringVertex("D"));
        dag.addEdge(new StringVertex("D"), new StringVertex("E"));
        dag.addEdge(new StringVertex("E"), new StringVertex("F"));

        System.out.println("Вершины: " + dag.getVertices());
        System.out.println("Список смежности:");
        for (Vertex vertex : dag.getVertices()) {
            System.out.println("  " + vertex + " -> " + dag.getNeighbors(vertex));
        }

        try {
            List<Vertex> sorted = TopologicalSorting.topologicalSort(dag);
            System.out.println("Результат топологической сортировки: " + sorted);
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }

        System.out.println("\nТест топологической сортировки на циклическом графе:");

        AdjacencyList cyclicGraph = new AdjacencyList(true);

        for (String vertex : vertices) {
            cyclicGraph.addVertex(new StringVertex(vertex));
        }

        cyclicGraph.addEdge(new StringVertex("A"), new StringVertex("B"));
        cyclicGraph.addEdge(new StringVertex("B"), new StringVertex("C"));
        cyclicGraph.addEdge(new StringVertex("C"), new StringVertex("A"));
        cyclicGraph.addEdge(new StringVertex("C"), new StringVertex("D"));

        System.out.println("Вершины: " + cyclicGraph.getVertices());
        System.out.println("Список смежности:");
        for (Vertex vertex : cyclicGraph.getVertices()) {
            System.out.println("  " + vertex + " -> " + cyclicGraph.getNeighbors(vertex));
        }

        try {
            List<Vertex> sorted = TopologicalSorting.topologicalSort(cyclicGraph);
            System.out.println("Результат топологической сортировки: " + sorted);
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    /**
     * Демонстрирует чтение графа из файла для всех трех представлений.
     */
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
                List<Vertex> sorted = TopologicalSorting.topologicalSort(directedFromFile);
                System.out.println("Топологическая сортировка: " + sorted);
            } catch (IllegalArgumentException e) {
                System.out.println("Топологическая сортировка невозможна: " + e.getMessage());
            }
        } catch (Exception e) {
            System.out.println("Ошибка при чтении файла: " + e.getMessage());
        }
    }

    /**
     * Выводит основную информацию о графе.
     */
    private static void printGraphInfo(Graph graph, boolean directed) {
        System.out.println("Тип графа: " + (directed ? "ориентированный" : "неориентированный"));
        System.out.println("Вершины: " + graph.getVertices());
        System.out.println("Количество вершин: " + graph.getVertices().size());

        List<Vertex> vertices = graph.getVertices();

        System.out.println("Соседи вершин:");
        for (Vertex vertex : vertices) {
            List<Vertex> neighbors = graph.getNeighbors(vertex);
            System.out.println("  " + vertex + " -> " + neighbors);
        }
    }

    /**
     * Выводит матрицу смежности в читаемом формате.
     */
    private static void printAdjacencyMatrixRepresentation(
            AdjacencyMatrix graph, List<String> vertices) {
        System.out.println("Матрица смежности:");
        System.out.print("    ");
        for (String vertex : vertices) {
            System.out.print(vertex + " ");
        }
        System.out.println();

        for (int i = 0; i < vertices.size(); i++) {
            System.out.print(vertices.get(i) + " | ");
            for (int j = 0; j < vertices.size(); j++) {
                Vertex from = graph.intToVertex(i);
                Vertex to = graph.intToVertex(j);
                System.out.print((graph.hasEdge(from, to) ? "1 " : "0 "));
            }
            System.out.println();
        }
    }

    /**
     * Выводит упрощенное представление матрицы инцидентности.
     */
    private static void printSimpleIncidenceMatrix(IncidenceMatrix graph,
                                                   List<String> vertices, String[][] edges) {
        System.out.println("Матрица инцидентности:");

        System.out.print("   ");
        for (String[] strings : edges) {
            System.out.print(strings[0] + strings[1] + " ");
        }
        System.out.println();

        for (String vertex : vertices) {
            System.out.print(vertex + " |");

            for (String[] edge : edges) {
                Vertex from = new StringVertex(edge[0]);
                Vertex to = new StringVertex(edge[1]);

                if (graph.isDirected()) {
                    if (vertex.equals(from.getId()) && graph.hasEdge(from, to)) {
                        System.out.print(" 1 ");
                    } else if (vertex.equals(to.getId()) && graph.hasEdge(from, to)) {
                        System.out.print("-1 ");
                    } else {
                        System.out.print(" 0 ");
                    }
                } else {
                    if ((vertex.equals(from.getId()) || vertex.equals(to.getId()))
                            && graph.hasEdge(from, to)) {
                        System.out.print(" 1 ");
                    } else {
                        System.out.print(" 0 ");
                    }
                }
            }
            System.out.println();
        }
    }

    /**
     * Выводит список смежности графа.
     */
    private static void printAdjacencyListRepresentation(Graph graph) {
        System.out.println("Список смежности:");
        for (Vertex vertex : graph.getVertices()) {
            List<Vertex> neighbors = graph.getNeighbors(vertex);
            System.out.println("  " + vertex + " -> " + neighbors);
        }
    }
}