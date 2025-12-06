package ru.nsu.g.a.vybortseva.equations;

import ru.nsu.g.a.vybortseva.equations.exceptions.MathCalculationException;

/**
 * The method for the operations.
 */
public abstract class Operations extends Expression {
    protected final Expression left;
    protected final Expression right;
    private final String operator;

    /**
     * Constructs for the operations.
     */
    public Operations(Expression left, Expression right, String operator) {
        this.left = left;
        this.right = right;
        this.operator = operator;
    }

    public abstract int operate(int leftValue, int rightValue);

    public abstract Expression differentiate(Expression leftDeriv, Expression rightDeriv);

    /**
     * Method for evaluating of an expression.
     */
    @Override
    public int evaluate(String variablesStr) {
        try {
            int leftValue = left.evaluate(variablesStr);
            int rightValue = right.evaluate(variablesStr);
            return operate(leftValue, rightValue);
        } catch (ArithmeticException e) {
            throw new MathCalculationException("math error: " + e.getMessage());
        }
    }

    /**
     * Method for derivative of an expressions.
     */
    @Override
    public Expression derivative(String variable) {
        return differentiate(left.derivative(variable), right.derivative(variable));
    }

    /**
     * Method for printing of an expression.
     */
    public void print() {
        System.out.print("(");
        left.print();
        System.out.print(operator);
        right.print();
        System.out.print(")");
    }

    /**
     * Method toString.
     */
    @Override
    public String toString() {
        return "(" + left.toString() + operator + right.toString() + ")";
    }

    /**
     * Общий метод упрощения для операций
     */
    @Override
    public abstract Expression simplify();

    protected boolean isNumber(Expression expr, int value) {
        return expr instanceof Number && ((Number) expr).getValue() == value;
    }

    protected boolean isZero(Expression expr) {
        return isNumber(expr, 0);
    }

    protected boolean isOne(Expression expr) {
        return isNumber(expr, 1);
    }

    protected boolean areExpressionsEqual(Expression expr1, Expression expr2) {
        if (expr1 == expr2) return true;
        if (expr1 == null || expr2 == null) return false;
        return expr1.toString().equals(expr2.toString());
    }
}
