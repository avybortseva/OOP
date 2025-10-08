package ru.nsu.g.a.vybortseva.equations;

import java.util.Map;

/**
 * The class for expressions.
 */
public abstract class Expression {
    public abstract int evaluate(Map<String, Integer> variables);


    public abstract void print();

    public abstract Expression derivative(String variableStr);
}
