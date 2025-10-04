package ru.nsu.g.a.vybortseva.equations;

public class Add extends Operations{

    /**
     * adding two numbers
     */
    public Add(Expression left, Expression right) {
        super(left, right, "+");
    }

    /**
     * implementation of addition
     */
    @Override
    public int operate(int leftValue, int rightValue) {
        return leftValue + rightValue;
    }

    /**
     * calculating the derivative sum
     */
    @Override
    public Expression differentiate(Expression leftDeriv, Expression rightDeriv) {
        return new Add(leftDeriv, rightDeriv);
    }
}
