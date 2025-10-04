package ru.nsu.g.a.vybortseva.equations;

public class Mul extends Operations{
    public Mul(Expression left, Expression right) {
        super(left, right, "*");
    }

    /**
     * implementation of multiplication
     */
    @Override
    public int operate(int leftValue, int rightValue) {
        return leftValue * rightValue;
    }

    /**
     * calculating the derivative of multiplication
     */
    @Override
    public Expression differentiate(Expression leftDeriv, Expression rightDeriv) {
        return new Add(new Mul(leftDeriv, right), new Mul(left, rightDeriv));
    }
}
