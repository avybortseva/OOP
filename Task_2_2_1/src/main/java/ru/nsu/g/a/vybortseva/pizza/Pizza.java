package ru.nsu.g.a.vybortseva.pizza;

/**
 * Класс, представляющий объект пиццы в системе симулятора.
 * Содержит уникальный идентификатор заказа.
 */
public class Pizza {
    private final int id;

    /**
     * Создает новый экземпляр пиццы с заданным идентификатором.
     */
    public Pizza(int id) {
        this.id = id;
    }

    /**
     * Возвращает уникальный идентификатор данной пиццы.
     */
    public int getId() {
        return id;
    }
}
