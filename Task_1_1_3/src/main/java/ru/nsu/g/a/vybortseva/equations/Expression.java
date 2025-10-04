package ru.nsu.g.a.vybortseva.equations;

public abstract class Expression {
    public abstract int evaluate(String variables);

    public abstract void print();

    public abstract Expression derivative(String variableStr);
}
