package ru.nsu.g.a.vybortseva.graph;

/**
 * Реализация вершины графа со строковым идентификатором.
 * Вершина идентифицируется по своему имени (строке).
 */
public class StringVertex implements Vertex {
    private String name;

    /**
     * Конструктор для создания вершины с заданным именем.
     */
    public StringVertex(String name) {
        this.name = name;
    }

    /**
     * Возвращает идентификатор вершины.
     */
    @Override
    public String getId() {
        return name;
    }

    /**
     * Сравнивает данную вершину с другим объектом на равенство.
     * Две вершины считаются равными, если они имеют одинаковые имена.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        StringVertex vertex = (StringVertex) o;
        return name.equals(vertex.name);
    }

    /**
     * Возвращает хэш-код вершины.
     * Хэш-код основан на имени вершины.
     */
    @Override
    public int hashCode() {
        return name.hashCode();
    }

    /**
     * Возвращает строковое представление вершины.
     */
    @Override
    public String toString() {
        return "Vertex" + ": " + name;
    }
}
