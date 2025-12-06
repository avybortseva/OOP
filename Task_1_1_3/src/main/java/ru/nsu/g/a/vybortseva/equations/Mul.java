package ru.nsu.g.a.vybortseva.equations;

/**
 * Represents Multiplication operation.
 */
public class Mul extends Operations {
    public Mul(Expression left, Expression right) {
        super(left, right, "*");
    }

    /**
     * Implementation of multiplication.
     */
    @Override
    public int operate(int leftValue, int rightValue) {
        return leftValue * rightValue;
    }

    /**
     * Calculating the derivative of multiplication.
     */
    @Override
    public Expression differentiate(Expression leftDeriv, Expression rightDeriv) {
        return new Add(new Mul(leftDeriv, right), new Mul(left, rightDeriv));
    }

    /**
     * Упрощение умножения
     */
    @Override
    public Expression simplify() {
        Expression simpleLeft = left.simplify();
        Expression simpleRight = right.simplify();

        if (simpleLeft instanceof Number && simpleRight instanceof Number) {
            return new Number(((Number) simpleLeft).getValue() * ((Number) simpleRight).getValue());
        }

        if (isZero(simpleLeft) || isZero(simpleRight)) {
            return new Number(0);
        }

        if (isOne(simpleLeft)) {
            return simpleRight;
        }

        if (isOne(simpleRight)) {
            return simpleLeft;
        }

        if (simpleLeft != left || simpleRight != right) {
            return new Mul(simpleLeft, simpleRight);
        }

        return this;
    }
}
