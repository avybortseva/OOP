package ru.nsu.g.a.vybortseva.lab;

/**
 * Интерфейс для операций с простыми числами.
 */
public interface Operation {

    /**
     * Проверяет, является ли число простым.
     */
    static boolean isPrime(long number) {
        if (number <= 1) {
            return false;
        }
        if (number % 2 == 0 && number != 2) {
            return false;
        }

        for (long i = 3; i * i <= number; i += 2) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }


    /**
     * Проверяет, есть ли в массиве хотя бы одно составное число.
     */
    boolean hasCompositeNumber(long[] numbers);
}
