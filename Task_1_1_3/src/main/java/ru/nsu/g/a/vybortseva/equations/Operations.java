
package ru.nsu.g.a.vybortseva.equations;

import java.util.Map;

/**
 * The method for the operarions.
 */
public abstract class Operations extends Expression {
    protected final Expression left;
    protected final Expression right;
    private final String operator;

    /**
     * Constructs for the operations.
     */
    public Operations(Expression left, Expression right, String operator) {
        this.left = left;
        this.right = right;
        this.operator = operator;
    }

    public abstract int operate(int leftValue, int rightValue);

    public abstract Expression differentiate(Expression leftDeriv, Expression rightDeriv);

    /**
     * Method for evaluating of an expression.
     */
    @Override
    public int evaluate(Map<String, Integer> variables) {
        int leftValue = left.evaluate(variables);
        int rightValue = right.evaluate(variables);
        return operate(leftValue, rightValue);
    }


    /**
     * Method for derivative of an expressions.
     */
    @Override
    public Expression derivative(String variable) {
        return differentiate(left.derivative(variable), right.derivative(variable));
    }

    /**
     * Method for printing of an expression.
     */
    public void print() {
        System.out.print("(");
        left.print();
        System.out.print(operator);
        right.print();
        System.out.print(")");
    }

    /**
     * Method toString.
     */
    @Override
    public String toString() {
        return "(" + left.toString() + operator + right.toString() + ")";
    }
}
