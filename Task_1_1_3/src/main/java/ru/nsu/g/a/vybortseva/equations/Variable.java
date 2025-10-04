package ru.nsu.g.a.vybortseva.equations;

import java.util.HashMap;
import java.util.Map;

public class Variable extends Expression {
    private final String name;

    public Variable(String name) {
        this.name = name;
    }

    @Override
    public int evaluate(String variablesStr) {
        Map<String, Integer> variables = parseVariables(variablesStr);
        if (!variables.containsKey(name)){
            throw new IllegalArgumentException("Variable '" + name + "' is not defined.");
        }
        return variables.get(name);
    }

    private Map<String, Integer> parseVariables(String variablesStr) {
        Map<String, Integer> variables = new HashMap<>();
        if (variablesStr != null && !variablesStr.trim().isEmpty()){
            String[] assignments = variablesStr.split(";");
            for (String assignment : assignments){
                String[] parts = assignment.split("=");
                if (parts.length == 2){
                    String nameVar = parts[0].trim();
                    int value = Integer.parseInt(parts[1].trim());
                    variables.put(nameVar, value);
                }
            }
        }
        return variables;
    }

    @Override
    public void print() {
        System.out.print(name);
    }

    @Override
    public Expression derivative(String variable) {
        if (this.name.equals(variable)) {
            return new Number(1);
        } else {
            return new Number(0);
        }
    }

    @Override
    public String toString() {
        return name;
    }
}
