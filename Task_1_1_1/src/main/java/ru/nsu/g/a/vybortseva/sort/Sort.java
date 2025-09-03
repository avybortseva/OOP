package ru.nsu.g.a.vybortseva.sort;

/**
 * Описание класса.
 */
public class Sort {
    /**
     * Описание.
     */
    public static int[] heapsort(int[] array) {
        int len = array.length;

        if (len <= 1) {
            return array;
        }

        for (int i = len / 2 - 1; i >= 0; i--) {
            heapify(array, i, len);
        }

        for (int i = len - 1; i > 0; i--) {
            int temp = array[0];
            array[0] = array[i];
            array[i] = temp;

            heapify(array, 0, i);
        }

        return array;
    }

    /**
     * Описание.
     */
    private static void heapify(int[] array, int root, int len) {
        int maxim = root;
        int left = root * 2 + 1;
        int right = root * 2 + 2;

        if (left < len && array[left] > array[maxim]) {
            maxim = left;
        }
        if (right < len && array[right] > array[maxim]) {
            maxim = right;
        }
        if (maxim != root) {
            int temp = array[root];
            array[root] = array[maxim];
            array[maxim] = temp;

            heapify(array, maxim, len);
        }
    }
}
