package ru.nsu.g.a.vybortseva.equations;

import java.util.HashMap;
import java.util.Map;

/**
 * The class for expressions.
 */
public abstract class Expression {
    private static final Map<String, Map<String, Integer>> CACHE = new HashMap<>();

    public abstract int evaluate(String variables);

    public abstract void print();

    public abstract Expression derivative(String variableStr);

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
