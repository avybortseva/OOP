package ru.nsu.g.a.vybortseva.lab;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Класс, реализующий многопоточную проверку наличия составных чисел в массиве.
 */
public class Threaded implements Operation {
    final int threadCount;

    /**
     * Конструктор для определения threadCount.
     */
    public Threaded(int threadCount) {
        this.threadCount = threadCount;
    }

    /**
     * Проверяет, содержит ли массив целых чисел хотя бы одно составное число.
     */
    @Override
    public boolean hasCompositeNumber(long[] numbers) {
        Thread[] threads = new Thread[threadCount];
        AtomicBoolean compositeIs = new AtomicBoolean(false);
        int dataSize = (int) Math.ceil((double) numbers.length / threadCount);

        for (int i = 0; i < threadCount; i++) {
            int start = i * dataSize;
            int end = Math.min(start + dataSize, numbers.length);

            if (start > numbers.length) {
                break;
            }

            threads[i] = new Thread(() -> {
                for (int j = start; j < end && !compositeIs.get(); j++) {
                    if (!isPrime(numbers[j])) {
                        compositeIs.set(true);
                        break;
                    }
                }
            });
            threads[i].start();
        }

        for (Thread thread : threads) {
            if (thread != null) {
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
        return compositeIs.get();
    }
}
