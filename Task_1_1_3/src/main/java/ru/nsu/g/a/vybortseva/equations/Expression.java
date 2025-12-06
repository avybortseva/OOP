package ru.nsu.g.a.vybortseva.equations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

/**
 * The class for expressions.
 */
public abstract class Expression {
    private static final Map<String, Map<String, Integer>> CACHE = new HashMap<>();

    public abstract int evaluate(String variables);

    public abstract void print();

    public abstract Expression derivative(String variableStr);

    public abstract Expression simplify();

    /**
     * Статический метод для парсинга выражения из строки (без скобок).
     */
    public static Expression parseNoParentheses(String expression) {
        expression = expression.trim();
        if (expression.isEmpty()) {
            throw new IllegalArgumentException("Expression cannot be empty");
        }

        List<String> tokens = tokenize(expression);
        List<String> postfix = postfixConverting(tokens);

        return evaluatePostfix(postfix);
    }

    /**
     * Токенизация выражения.
     */
    private static List<String> tokenize(String expression) {
        List<String> tokens = new ArrayList<>();
        StringBuilder currentToken = new StringBuilder();

        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);

            if (c == '+' || c == '-' || c == '*' || c == '/' || c == '(' || c == ')') {
                if (c == '-') {
                    boolean isUnaryMinus = false;
                    if (i == 0) {
                        isUnaryMinus = true;
                    } else {
                        char prevChar = expression.charAt(i - 1);
                        if (prevChar == '+' || prevChar == '-' || prevChar == '*'
                                || prevChar == '/' || prevChar == '(' || Character.isWhitespace(prevChar)) {

                            if (!tokens.isEmpty()) {
                                String lastToken = tokens.get(tokens.size() - 1);
                                if (lastToken.equals("+") || lastToken.equals("-")
                                        || lastToken.equals("*") || lastToken.equals("/")
                                        || lastToken.equals("(")) {
                                    isUnaryMinus = true;
                                }
                            } else {
                                isUnaryMinus = true;
                            }
                        }
                    }
                    if (isUnaryMinus) {
                        if (!currentToken.isEmpty()) {
                            tokens.add(currentToken.toString());
                            currentToken = new StringBuilder();
                        }
                        tokens.add("~");
                        continue;
                    }
                }

                if (!currentToken.isEmpty()) {
                    tokens.add(currentToken.toString());
                    currentToken = new StringBuilder();
                }
                tokens.add(String.valueOf(c));
            } else if (Character.isWhitespace(c)) {
                if (!currentToken.isEmpty()) {
                    tokens.add(currentToken.toString());
                    currentToken = new StringBuilder();
                }
            } else {
                currentToken.append(c);
            }
        }
        if (!currentToken.isEmpty()) {
            tokens.add(currentToken.toString());
        }
        return tokens;
    }

    /**
     * Преобразование инфиксной записи в постфиксную (обратную польскую нотацию).
     */
    private static List<String> postfixConverting(List<String> tokens) {
        List<String> output = new ArrayList<>();
        Stack<String> operators = new Stack<>();

        Map<String, Integer> precedence = new HashMap<>();
        precedence.put("+", 1);
        precedence.put("-", 1);
        precedence.put("*", 2);
        precedence.put("/", 2);
        precedence.put("~", 3);

        for (String token : tokens) {
            if (token.matches("-?\\d+") || token.matches("[a-zA-Z_][a-zA-Z_0-9]*")) {
                output.add(token);
            }
            else if (precedence.containsKey(token)) {
                if (token.equals("~")) {
                    operators.push(token);
                } else {

                    while (!operators.isEmpty() && !operators.peek().equals("(")
                            && precedence.getOrDefault(operators.peek(), 0)
                            >= precedence.get(token)) {
                        output.add(operators.pop());
                    }
                    operators.push(token);
                }
            }
            else if (token.equals("(")) {
                operators.push(token);
            }
            else if (token.equals(")")) {
                while (!operators.isEmpty() && !operators.peek().equals("(")) {
                    output.add(operators.pop());
                }
                if (!operators.isEmpty() && operators.peek().equals("(")) {
                    operators.pop();
                } else {
                    throw new IllegalArgumentException("Mismatched parentheses");
                }
            }
        }
        while (!operators.isEmpty()) {
            if (operators.peek().equals("(")) {
                throw new IllegalArgumentException("Mismatched parentheses");
            }
            output.add(operators.pop());
        }

        return output;
    }

    /**
     * Построение дерева выражений из постфиксной записи.
     */
    private static Expression evaluatePostfix(List<String> postfix) {
        Stack<Expression> stack = new Stack<>();

        for (String token : postfix) {
            if (token.matches("-?\\d+")) {
                stack.push(new Number(Integer.parseInt(token)));
            } else if (token.matches("[a-zA-Z_][a-zA-Z_0-9]*")) {
                stack.push(new Variable(token));
            } else if (token.equals("~")) {
                if (stack.isEmpty()) {
                    throw new IllegalArgumentException("Not enough operands for unary minus");
                }
                Expression operand = stack.pop();
                stack.push(new Mul(new Number(-1), operand));
            } else {
                if (stack.size() < 2) {
                    throw new IllegalArgumentException("Not enough operands for operator");
                }
                Expression right = stack.pop();
                Expression left = stack.pop();

                switch (token) {
                    case "+":
                        stack.push(new Add(left, right));
                        break;
                    case "-":
                        stack.push(new Sub(left, right));
                        break;
                    case "*":
                        stack.push(new Mul(left, right));
                        break;
                    case "/":
                        stack.push(new Div(left, right));
                        break;
                    default:
                        throw new IllegalArgumentException("Unknown operator");
                }
            }
        }
        if (stack.size() != 1) {
            throw new IllegalArgumentException("Invalid expression");
        }

        return stack.pop();
    }

    /**
     * Статический метод для парсинга выражения из строки (со скобками).
     */
    public static Expression parseWithParentheses(String expression) {
        Parser parser = new Parser();
        return parser.parse(expression);
    }

    /**
     * Getting of the parse variables.
     */
    protected Map<String, Integer> getCachedVariables(String variablesStr) {
        return CACHE.computeIfAbsent(variablesStr, this::parseVariables);
    }

    /**
     * Method for the parsing.
     */
    private Map<String, Integer> parseVariables(String variablesStr) {
        Map<String, Integer> variables = new HashMap<>();
        if (variablesStr != null && !variablesStr.trim().isEmpty()) {
            String[] assignments = variablesStr.split(";");
            for (String assignment : assignments) {
                String[] parts = assignment.split("=");
                if (parts.length == 2) {
                    String nameVar = parts[0].trim();
                    int value = Integer.parseInt(parts[1].trim());
                    variables.put(nameVar, value);
                }
            }
        }
        return variables;
    }

    /**
     * Clean the cache.
     */
    public static void clearCache() {
        CACHE.clear();
    }
}
