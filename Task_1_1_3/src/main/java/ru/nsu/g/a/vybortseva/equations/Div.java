package ru.nsu.g.a.vybortseva.equations;

import ru.nsu.g.a.vybortseva.equations.exceptions.DivisionByZeroException;
import ru.nsu.g.a.vybortseva.equations.exceptions.MathCalculationException;

/**
 * Represents division operation.
 */
public class Div extends Operations {
    public Div(Expression left, Expression right) {
        super(left, right, "/");
    }

    /**
     * Implementation of division.
     */
    @Override
    public int operate(int leftValue, int rightValue) {
        if (rightValue == 0) {
            throw new DivisionByZeroException();
        }
        return leftValue / rightValue;
    }

    /**
     * Calculating the division derivative.
     */
    @Override
    public Expression differentiate(Expression leftDeriv, Expression rightDeriv) {
        Expression top1 = new Mul(leftDeriv, right);
        Expression top2 = new Mul(left, rightDeriv);
        Expression bot = new Mul(right, right);
        return new Div(new Sub(top1, top2), bot);
    }

    /**
     * Упрощение деления.
     */
    @Override
    public Expression simplify() {
        Expression simpleLeft = left.simplify();
        Expression simpleRight = right.simplify();

        if (simpleLeft instanceof Number && simpleRight instanceof Number) {
            int divisor = ((Number) simpleRight).getValue();
            if (divisor == 0) {
                throw new DivisionByZeroException();
            }
            return new Number(((Number) simpleLeft).getValue() / divisor);
        }

        if (isZero(simpleLeft)) {
            return new Number(0);
        }

        if (isOne(simpleRight)) {
            return simpleLeft;
        }

        if (simpleLeft != left || simpleRight != right) {
            return new Div(simpleLeft, simpleRight);
        }

        return this;
    }
}
