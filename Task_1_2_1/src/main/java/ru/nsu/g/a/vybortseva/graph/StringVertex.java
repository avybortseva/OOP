package ru.nsu.g.a.vybortseva.graph;

import java.util.Objects;

public class StringVertex implements Vertex {
    private String name;

    public StringVertex(String name) {
        this.name = name;
    }

    @Override
    public String getId() { return name; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StringVertex vertex = (StringVertex) o;
        return name.equals(vertex.name);
    }

    @Override
    public int hashCode() { return name.hashCode(); }

    @Override
    public String toString() { return "Vertex" + ": " + name; }
}
