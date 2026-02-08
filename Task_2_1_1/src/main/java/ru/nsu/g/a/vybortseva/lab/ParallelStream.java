package ru.nsu.g.a.vybortseva.lab;

import java.util.Arrays;

/**
 * Класс, реализующий параллельную проверку наличия составных чисел в массиве.
 */
public class ParallelStream implements Operation {
    /**
     * Проверяет, содержит ли массив целых чисел хотя бы одно составное число.
     */
    @Override
    public boolean hasCompositeNumber(long[] numbers) {
        return Arrays.stream(numbers)
                .parallel()
                .anyMatch(x -> !isPrime(x));
    }
}
