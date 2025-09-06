package ru.nsu.g.a.vybortseva.sort;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

class SortTest {

    @Test
    void emptyArray() {
        int[] array = new int[]{};
        Sort.heapsort(array);
        assertArrayEquals(new int[]{}, array);
    }

    @Test
    void singleElement() {
        int[] array = new int[]{1};
        Sort.heapsort(array);
        assertArrayEquals(new int[]{1}, array);
    }

    @Test
    void twoElementsSorted() {
        int[] array = new int[]{1, 2};
        Sort.heapsort(array);
        assertArrayEquals(new int[]{1, 2}, array);
    }

    @Test
    void twoElementsReversed() {
        int[] array = new int[]{2, 1};
        Sort.heapsort(array);
        assertArrayEquals(new int[]{1, 2}, array);
    }

    @Test
    void sortedArray() {
        int[] array = new int[]{1, 2, 3, 4, 5, 6, 7};
        Sort.heapsort(array);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5, 6, 7}, array);
    }

    @Test
    void reversedArray() {
        int[] array = new int[]{7, 6, 5, 4, 3, 2, 1};
        Sort.heapsort(array);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5, 6, 7}, array);
    }

    @Test
    void arrayWithDuplicates() {
        int[] array = new int[]{3, 1, 4, 1, 5, 9, 2, 6, 6, 28, 39, 10};
        Sort.heapsort(array);
        assertArrayEquals(new int[]{1, 1, 2, 3, 4, 5, 6, 6, 9, 10, 28, 39}, array);
    }

    @Test
    void uniformArray() {
        int[] array = new int[]{7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7};
        Sort.heapsort(array);
        assertArrayEquals(new int[]{7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7}, array);
    }

    @Test
    void negativeNumbers() {
        int[] array = new int[]{-4, -3, -7, -1, -20};
        Sort.heapsort(array);
        assertArrayEquals(new int[]{-20, -7, -4, -3, -1}, array);
    }

    @Test
    void mixedSignNumbers() {
        int[] array = new int[]{-1, 6, -3, -9, 3, -4, 3, 4, 5, -3};
        Sort.heapsort(array);
        assertArrayEquals(new int[]{-9, -4, -3, -3, -1, 3, 3, 4, 5, 6}, array);
    }

    @Test
    void largeArray() {
        int[] array = new int[1000];
        int[] expected = new int[1000];
        for (int i = 0; i < 1000; i++) {
            array[i] = 1000 - i;
            expected[i] = i + 1;
        }
        Sort.heapsort(array);
        assertArrayEquals(expected, array);
    }

    @Test
    void complexityAnalysis() {
        System.out.println("\n=== Анализ сложности алгоритма ===");
        System.out.println("Теоретическая сложность: O(n log n)");
        System.out.println("Размер массива | Время (нс) | n log n | Отношение (Время / n log n)");
        System.out.println("---------------------------------------------------------------");

        int[] sizes = {100, 500, 1000, 5000, 10000, 50000, 100000};

        for (int size : sizes) {
            int[] array = generateRandomArray(size);

            long startTime = System.nanoTime();
            Sort.heapsort(array);
            long endTime = System.nanoTime();
            long duration = endTime - startTime;

            double nLogN = size * Math.log(size);
            double ratio = duration / nLogN;

            System.out.printf("%12d | %10d | %8.2f | %8.4f%n",
                    size, duration, nLogN, ratio);
        }

        System.out.println("---------------------------------------------------------------");
    }

    @Test
    void complexityAnalysisBestCase() {
        System.out.println("\n=== Анализ сложности ===");
        System.out.println("Размер массива | Время (нс) | n log n | Отношение");
        System.out.println("---------------------------------------------------------------");

        int[] sizes = {100, 500, 1000, 5000, 10000, 50000, 100000};

        for (int size : sizes) {
            int[] array = generateSortedArray(size);

            long startTime = System.nanoTime();
            Sort.heapsort(array);
            long endTime = System.nanoTime();
            long duration = endTime - startTime;


            double nLogN = size * Math.log(size);
            double ratio = duration / nLogN;

            System.out.printf("%12d | %10d | %8.2f | %8.4f%n",
                    size, duration, nLogN, ratio);
        }
    }

    @Test
    void complexityAnalysisWorstCase() {
        System.out.println("\n=== Анализ сложности ===");
        System.out.println("Размер массива | Время (нс) | n log n | Отношение");
        System.out.println("---------------------------------------------------------------");

        int[] sizes = {100, 500, 1000, 5000, 10000, 50000, 100000};

        for (int size : sizes) {
            int[] array = generateReverseSortedArray(size);

            long startTime = System.nanoTime();
            Sort.heapsort(array);
            long endTime = System.nanoTime();
            long duration = endTime - startTime;

            double nLogN = size * Math.log(size);
            double ratio = duration / nLogN;

            System.out.printf("%12d | %10d | %8.2f | %8.4f%n",
                    size, duration, nLogN, ratio);
        }
    }

    private int[] generateRandomArray(int size) {
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = (int) (Math.random() * size * 10);
        }
        return array;
    }

    private int[] generateSortedArray(int size) {
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = i;
        }
        return array;
    }

    private int[] generateReverseSortedArray(int size) {
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = size - i;
        }
        return array;
    }


}
