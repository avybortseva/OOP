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

    /**
     * Упрощение сложения.
     */
    @Override
    public Expression simplify() {
        Expression simpleLeft = left.simplify();
        Expression simpleRight = right.simplify();

        if (simpleLeft instanceof Number && simpleRight instanceof Number) {
            return new Number(((Number) simpleLeft).getValue() + ((Number) simpleRight).getValue());
        }

        if (isZero(simpleLeft)) {
            return simpleRight;
        }

        if (isZero(simpleRight)) {
            return simpleLeft;
        }

        if (simpleLeft != left || simpleRight != right) {
            return new Add(simpleLeft, simpleRight);
        }

        return this;
    }
}
