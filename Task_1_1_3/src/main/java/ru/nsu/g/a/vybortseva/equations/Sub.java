package ru.nsu.g.a.vybortseva.equations;

/**
 * Represents substraction operation.
 */
public class Sub extends Operations {
    /**
     * Constructs subtraction operation with left and right expressions.
     */
    public Sub(Expression left, Expression right) {
        super(left, right, "-");
    }

    /**
     * Calculating of a subtraction.
     */
    @Override
    public int operate(int leftValue, int rightValue) {
        return leftValue - rightValue;
    }

    /**
     * Calculating the derivative of a subtraction.
     */
    @Override
    public Expression differentiate(Expression leftDeriv, Expression rightDeriv) {
        return new Sub(leftDeriv, rightDeriv);
    }
}
