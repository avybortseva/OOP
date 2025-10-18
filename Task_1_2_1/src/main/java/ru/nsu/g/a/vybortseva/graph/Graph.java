package ru.nsu.g.a.vybortseva.graph;

import java.io.IOException;
import java.util.List;

public interface Graph {
    void addVertex(Object vertex);
    void removeVertex(Object vertex);
    void addEdge(Object from, Object to);
    void removeEdge(Object from, Object to);
    List<Object> getNeighbors(Object vertex);
    void readFromFile(String filename);
    boolean hasVertex(Object vertex);
    boolean hasEdge(Object from, Object to);
    List<Object> getAllVertices();
    boolean isDirected();
    @Override
    String toString();
}
