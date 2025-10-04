package ru.nsu.g.a.vybortseva.equations;

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
            throw new IllegalArgumentException("Unexpected end of expression");
        }

        if (input.charAt(pos) == '(') {
            pos++;
            Expression left = parseExpression();
            char operator = input.charAt(pos++);
            Expression right = parseExpression();
            pos++;

            return switch (operator) {
                case '+' -> new Add(left, right);
                case '-' -> new Sub(left, right);
                case '*' -> new Mul(left, right);
                case '/' -> new Div(left, right);
                default -> throw new IllegalArgumentException("Unknown operator: " + operator);
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
            throw new IllegalArgumentException("Expected number or variable at pos " + pos);
        }

        boolean isNumber = true;
        for (int i = 0; i < token.length(); i++) {
            if (!Character.isDigit(token.charAt(i))) {
                isNumber = false;
                break;
            }
        }

        if (isNumber) {
            return new Number(Integer.parseInt(token));
        } else {
            return new Variable(token);
        }
    }
}
