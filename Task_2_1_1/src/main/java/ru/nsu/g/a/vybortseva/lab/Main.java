package ru.nsu.g.a.vybortseva.lab;

import java.util.Arrays;

/**
 * Класс с проверкой производительности вычислений.
 */
public class Main {
    /**
     * Главный метод.
     */
    public static void main(String[] args) {
        long[] numbers = new long[1000000];
        int prime = 1000000007;
        Arrays.fill(numbers, prime);

        long start;
        boolean result;
        long time;

        // последовательно
        start = System.currentTimeMillis();
        result = new Sequential().hasCompositeNumber(numbers);
        time = System.currentTimeMillis() - start;
        System.out.println("Последовательная: " + time + " мс");

        // 1, 2, 5, 10, 15, 20 потоков
        int[] threads = {1, 2, 5, 8, 10, 12, 15, 20};

        for (int t : threads) {
            start = System.currentTimeMillis();
            result = new Threaded(t).hasCompositeNumber(numbers);
            time = System.currentTimeMillis() - start;

            System.out.printf("%d потоков: %d мс", t, time);
            System.out.println();
        }

        // ParallelStream
        start = System.currentTimeMillis();
        result = new ParallelStream().hasCompositeNumber(numbers);
        time = System.currentTimeMillis() - start;

        System.out.printf("ParallelStream: %d мс", time);

    }
}