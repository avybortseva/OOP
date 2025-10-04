package ru.nsu.g.a.vybortseva.equations;

public class Number extends Expression {
    private final int number;

    public Number(int number) {
        this.number = number;
    }

    @Override
    public int evaluate(String variablesStr) {
        return number;
    }

    @Override
    public void print() {
        System.out.print(number);
    }

    @Override
    public Expression derivative(String variableStr) {
        return new Number(0);
    }

    @Override
    public String toString() {
        return Integer.toString(number);
    }
}
