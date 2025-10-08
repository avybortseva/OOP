package ru.nsu.g.a.vybortseva.equations;

import ru.nsu.g.a.vybortseva.equations.exceptions.InvalidExpressionException;
import java.util.HashMap;
import java.util.Map;

/**
 * Utility class for parsing variables string into Map
 */
public class ParseVariables {

    /**
     * Parses variables string into Map
     */
    public static Map<String, Integer> parseVariables(String variablesStr) {
        Map<String, Integer> variables = new HashMap<>();

        if (variablesStr != null && !variablesStr.trim().isEmpty()) {
            String[] assignments = variablesStr.split(";");
            for (String assignment : assignments) {
                String[] parts = assignment.split("=");
                if (parts.length != 2) {
                    throw new InvalidExpressionException("Invalid variable assignment: " + assignment);
                }

                String name = parts[0].trim();
                String valueStr = parts[1].trim();

                try {
                    int value = Integer.parseInt(valueStr);
                    variables.put(name, value);
                } catch (NumberFormatException e) {
                    throw new InvalidExpressionException("Invalid variable value: " + valueStr);
                }
            }
        }

        return variables;
    }
}