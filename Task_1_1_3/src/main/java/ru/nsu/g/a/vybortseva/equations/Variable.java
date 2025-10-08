package ru.nsu.g.a.vybortseva.equations;

import java.util.HashMap;
import java.util.Map;
import ru.nsu.g.a.vybortseva.equations.exceptions.InvalidExpressionException;
import ru.nsu.g.a.vybortseva.equations.exceptions.UndefinedVariableException;

/**
 * The class for variables.
 */
public class Variable extends Expression {
    private final String name;

    /**
     * Constructs for a variable.
     */
    public Variable(String name) {
        this.name = name;
    }

    /**
     * Method for evaluating of a variable is the variable itself.
     */
    @Override
    public int evaluate(Map<String, Integer> variables) {
        if (!variables.containsKey(name)) {
            throw new UndefinedVariableException(name);
        }
        return variables.get(name);
    }

    /**
     * Method for printing of a variable.
     */
    @Override
    public void print() {
        System.out.print(name);
    }

    /**
     * Method for derivative of a variable is the variable itself.
     */
    @Override
    public Expression derivative(String variable) {
        if (this.name.equals(variable)) {
            return new Number(1);
        } else {
            return new Number(0);
        }
    }

    /**
     * Method toString.
     */
    @Override
    public String toString() {
        return name;
    }
}
