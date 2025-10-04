package ru.nsu.g.a.vybortseva.equations;

public abstract class Operations extends Expression{
    protected final Expression left;
    protected final Expression right;
    private final String operator;

    public Operations(Expression left, Expression right, String operator) {
        this.left = left;
        this.right = right;
        this.operator = operator;
    }

    public abstract int operate(int leftValue, int rightValue);
    public abstract Expression differentiate(Expression leftDeriv, Expression rightDeriv);


    @Override
    public int evaluate(String variablesStr) {
        return operate(left.evaluate(variablesStr), right.evaluate(variablesStr));
    }

    @Override
    public Expression derivative(String variable) {
        return differentiate(left.derivative(variable), right.derivative(variable));
    }

    public void print() {
        System.out.print("(");
        left.print();
        System.out.print(operator);
        right.print();
        System.out.print(")");
    }

    @Override
    public String toString() {
        return "(" + left.toString() + operator + right.toString() + ")";
    }
}
