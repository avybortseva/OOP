package ru.nsu.g.a.vybortseva.equations;

import java.util.Map;

public class Add extends Operations{

    public Add(Expression left, Expression right) {
        super(left, right, "+");
    }

    @Override
    public int operate(int leftValue, int rightValue) {
        return leftValue + rightValue;
    }

    @Override
    public Expression differentiate(Expression leftDeriv, Expression rightDeriv) {
        return new Add(leftDeriv, rightDeriv);
    }
}
