package ru.nsu.g.a.vybortseva.equations;

/**
 * The main class.
 */
public class Main {
    /**
     * Main method of the program.
     */
    public static void main(String[] args) {

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
    }
}