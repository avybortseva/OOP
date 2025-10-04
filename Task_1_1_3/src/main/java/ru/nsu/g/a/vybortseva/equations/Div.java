package ru.nsu.g.a.vybortseva.equations;

public class Div extends Operations{
    public Div(Expression left, Expression right) {
        super(left, right, "/");
    }

    @Override
    public int operate(int leftValue, int rightValue) {
        return leftValue / rightValue;
    }

    @Override
    public Expression differentiate(Expression leftDeriv, Expression rightDeriv) {
        Expression top1 = new Mul(leftDeriv, right);
        Expression top2 = new Mul(left, rightDeriv);
        Expression bot = new Mul(right, right);
        return new Div(new Sub(top1, top2), bot);
    }
}
