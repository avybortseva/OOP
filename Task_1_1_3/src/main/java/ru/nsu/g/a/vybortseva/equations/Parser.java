package ru.nsu.g.a.vybortseva.equations;

import ru.nsu.g.a.vybortseva.equations.exceptions.InvalidExpressionException;
import ru.nsu.g.a.vybortseva.equations.exceptions.UnexpectedTokenException;

/**
 * The class for the parser.
 */
public class Parser {
    private String input;
    private int pos;

    /**
     * Constructor for the parser.
     */
    public Expression parse(String expression) {
        this.input = expression.replaceAll("\\s", "");
        this.pos = 0;
        return parseExpression();
    }

    private Expression parseExpression() {
        if (pos >= input.length()) {
            throw new UnexpectedTokenException("Unexpected end of expression", pos);
        }

        if (input.charAt(pos) == '(') {
            pos++;

            if (pos >= input.length()) {
                throw new UnexpectedTokenException("Expected operator", pos);
            }

            Expression left = parseExpression();
            char operator = input.charAt(pos++);
            Expression right = parseExpression();

            if (pos >= input.length() || input.charAt(pos) != ')') {
                throw new UnexpectedTokenException("Expected closing parenthesis", pos);
            }
            pos++;

            return switch (operator) {
                case '+' -> new Add(left, right);
                case '-' -> new Sub(left, right);
                case '*' -> new Mul(left, right);
                case '/' -> new Div(left, right);
                default -> throw new UnexpectedTokenException("Unknown operator: "
                        + operator, pos - 1);
            };
        } else {
            return parseSimpleExpression();
        }
    }

    private Expression parseSimpleExpression() {
        if (pos >= input.length()) {
            throw new UnexpectedTokenException("Expected number or variable", pos);
        }

        String token = "";
        while (pos < input.length()
                && (Character.isDigit(input.charAt(pos))
                || Character.isLetter(input.charAt(pos)))) {
            token += input.charAt(pos++);
        }

        if (token.isEmpty()) {
            throw new UnexpectedTokenException("Expected number or variable", pos);
        }

        if (Character.isDigit(token.charAt(0))) {
            try {
                return new Number(Integer.parseInt(token));
            } catch (NumberFormatException e) {
                throw new InvalidExpressionException("Invalid number format: " + token);
            }
        } else {
            return new Variable(token);
        }
    }
}