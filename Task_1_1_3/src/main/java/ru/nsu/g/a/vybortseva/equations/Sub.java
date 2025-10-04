package ru.nsu.g.a.vybortseva.equations;

import java.util.Map;

public class Sub extends Operations{
    public Sub(Expression left, Expression right) {
        super(left, right, "-");
    }

    @Override
    public int operate(int leftValue, int rightValue) {
        return leftValue - rightValue;
    }

    @Override
    public Expression differentiate(Expression leftDeriv, Expression rightDeriv) {
        return new Sub(leftDeriv, rightDeriv);
    }
}
