package ru.nsu.g.a.vybortseva.equations;

/**
 * Represents addition operation.
 */
public class Add extends Operations {

    /**
     * Constructs addition operation with left and right expressions.
     */
    public Add(Expression left, Expression right) {
        super(left, right, "+");
    }

    /**
     * Implementation of addition.
     */
    @Override
    public int operate(int leftValue, int rightValue) {
        return leftValue + rightValue;
    }

    /**
     * Calculating the derivative sum.
     */
    @Override
    public Expression differentiate(Expression leftDeriv, Expression rightDeriv) {
        return new Add(leftDeriv, rightDeriv);
    }
}
