package ru.nsu.g.a.vybortseva.lab;

/**
 * Класс, реализующий последовательную проверку наличия составных чисел в массиве.
 */
public class Sequential implements Operation {
    /**
     * Проверяет, содержит ли массив целых чисел хотя бы одно составное число.
     */
    @Override
    public boolean hasCompositeNumber(long[] numbers) {
        for (long number : numbers) {
            if (!isPrime(number)) {
                return true;
            }
        }
        return false;
    }
}
