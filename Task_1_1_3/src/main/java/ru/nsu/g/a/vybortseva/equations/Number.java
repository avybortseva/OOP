package ru.nsu.g.a.vybortseva.equations;

public class Number extends Expression {
    private final int number;

    public Number(int number) {
        this.number = number;
    }

    /**
     * method for evaluating of a number is the number itself
     */
    @Override
    public int evaluate(String variablesStr) {
        return number;
    }

    /**
     * method for printing of a number
     */
    @Override
    public void print() {
        System.out.print(number);
    }

    /**
     * method for derivative of a number is the number itself
     */
    @Override
    public Expression derivative(String variableStr) {
        return new Number(0);
    }

    /**
     * method toString
     */
    @Override
    public String toString() {
        return Integer.toString(number);
    }
}
