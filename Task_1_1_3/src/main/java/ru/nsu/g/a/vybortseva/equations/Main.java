package ru.nsu.g.a.vybortseva.equations;

import ru.nsu.g.a.vybortseva.equations.exceptions.MathExpressionException;

/**
 * The main class.
 */
public class Main {
    /**
     * Main method of the program.
     */
    public static void main(String[] args) {

        try {
            Expression e = new Add(new Number(3), new Mul(new Number(2), new Variable("x")));

            System.out.print("Expression e: ");
            e.print(); // (3+(2*x))
            System.out.println();

            Expression de = e.derivative("x");
            System.out.print("Derivative de: ");
            de.print(); // (0+((0*x)+(2*1)))
            System.out.println();

            Expression e2 = new Add(new Number(3), new Mul(new Number(2),
                    new Variable("x")));
            int result = e2.evaluate("x = 10; y = 13");
            System.out.print("Result: ");
            System.out.println(result);
            System.out.println();

            System.out.println("PARSER");
            Parser parser = new Parser();
            Expression parsedExpr = parser.parse("(3+(2*x))");
            System.out.print("Parsed expression: ");
            parsedExpr.print(); // (3+(2*x))
            System.out.println();
            System.out.println();
            int parsedResult = parsedExpr.evaluate("x = 10");
            System.out.println("Parsed result: " + parsedResult); // 23

            System.out.println("=== Парсинг выражений без скобок ===");

            System.out.println("Пример 1: 3 + 2 * x");
            Expression expr1 = Expression.parseNoParentheses("3 + 2 * x");
            System.out.print("Дерево: ");
            expr1.print();
            System.out.println();
            System.out.println("Результат при x=10: " + expr1.evaluate("x = 10"));

            System.out.println("\nПример 2: (3 + 2) * x");
            Expression expr2 = Expression.parseNoParentheses("(3 + 2) * x");
            System.out.print("Дерево: ");
            expr2.print(); // ((3+2)*x)
            System.out.println();
            System.out.println("Результат при x=10: " + expr2.evaluate("x = 10"));

            System.out.println("\nПример 3: a + b * c - d / e");
            Expression expr3 = Expression.parseNoParentheses("a + b * c - d / e");
            System.out.print("Дерево: ");
            expr3.print();
            System.out.println();

            System.out.println("\nПример 4: var1 * 2 + var2");
            Expression expr4 = Expression.parseNoParentheses("var1 * 2 + var2");
            System.out.print("Дерево: ");
            expr4.print();
            System.out.println();
            System.out.println("Результат: " + expr4.evaluate("var1 = 5; var2 = 3"));

            System.out.println("\nПример 5: Производная выражения 3 + 2 * x");
            Expression deriv = expr1.derivative("x");
            System.out.print("Производная: ");
            deriv.print();
            System.out.println();

            System.out.println("\nПример 6: -(x + 5)");
            Expression unary7 = Expression.parseNoParentheses("-(-x + 5)");
            System.out.print("Дерево: ");
            unary7.print(); // ((-1)*(-x+5))
            System.out.println();
            System.out.println("Результат при x=2: " + unary7.evaluate("x = 2"));

            System.out.println("\nПример 7: 3 * 1 + 0 * x");
            Expression complex3 = new Add(
                    new Mul(new Number(3), new Number(1)),
                    new Mul(new Number(0), new Variable("x"))
            );
            System.out.print("Исходное: ");
            complex3.print();
            System.out.print(" -> Упрощенное: ");
            Expression simplifiedComplex3 = complex3.simplify();
            simplifiedComplex3.print();
            System.out.println(" (ожидается: 3)");

        } catch (MathExpressionException e) {
            System.err.println("Ошибка в математическом выражении: " + e.getMessage());
        }
    }
}