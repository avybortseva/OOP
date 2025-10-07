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
     * Constructs for the parser.
     */
    public Expression parse(String expression) {
        this.input = expression.replaceAll("\\s", "");
        this.pos = 0;
        return parseExpression();
    }

    /**
     * Method for parsing the expression.
     */
    private Expression parseExpression() {
        if (pos >= input.length()) {
            throw new UnexpectedTokenException("Неожиданный конец выражения", pos);
        }

        if (input.charAt(pos) == '(') {
            pos++;
            Expression left = parseExpression();

            if (pos >= input.length()) {
                throw new UnexpectedTokenException("Ожидался оператор", pos);
            }

            char operator = input.charAt(pos++);

            Expression right = parseExpression();
            if (pos >= input.length() || input.charAt(pos) != ')') {
                throw new UnexpectedTokenException("Ожидалась закрывающая скобка", pos);
            }
            pos++;

            return switch (operator) {
                case '+' -> new Add(left, right);
                case '-' -> new Sub(left, right);
                case '*' -> new Mul(left, right);
                case '/' -> new Div(left, right);
                default ->
                    throw new UnexpectedTokenException("Неизвестный оператор: " + operator, pos - 1);
            };
        } else {
            return parseSimpleExpression();
        }
    }

    /**
     * Method for parsing a simple expression.
     */
    private Expression parseSimpleExpression() {
        String token = "";

        while (pos < input.length()
                && (Character.isDigit(input.charAt(pos))
                || Character.isLetter(input.charAt(pos)))) {
            token += input.charAt(pos++);
        }

        if (token.isEmpty()) {
            throw new UnexpectedTokenException("Ожидалось число или переменная", pos);
        }

        if (Character.isDigit(token.charAt(0))) {
            try {
                return new Number(Integer.parseInt(token));
            } catch (NumberFormatException e) {
                throw new InvalidExpressionException("Некорректное число: " + token);
            }
        } else {
            return new Variable(token);
        }
    }
}
