package ru.nsu.g.a.vybortseva.equations;

public class Sub extends Operations{
    public Sub(Expression left, Expression right) {
        super(left, right, "-");
    }

    /**
     * calculating of a subtraction
     */
    @Override
    public int operate(int leftValue, int rightValue) {
        return leftValue - rightValue;
    }

    /**
     * calculating the derivative of a subtraction
     */
    @Override
    public Expression differentiate(Expression leftDeriv, Expression rightDeriv) {
        return new Sub(leftDeriv, rightDeriv);
    }
}
