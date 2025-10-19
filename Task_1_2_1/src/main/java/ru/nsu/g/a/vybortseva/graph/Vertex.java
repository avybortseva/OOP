package ru.nsu.g.a.vybortseva.graph;

/**
 * Интерфейс, который должны реализовывать все вершины графа
 */
public interface Vertex {
    /**
     * Возвращает уникальный идентификатор вершины
     */
    String getId();

    /**
     * Возвращает строковое представление вершины
     */
    String toString();

    /**
     * Проверяет эквивалентность вершин
     */
    boolean equals(Object obj);

    /**
     * Возвращает хэш-код вершины
     */
    int hashCode();
}