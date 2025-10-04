package ru.nsu.g.a.vybortseva.equations;

import java.util.Map;

public abstract class Expression {
    public abstract int evaluate(String variables);

    public abstract void print();

    public abstract Expression derivative(String variableStr);
}
