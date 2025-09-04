package ru.nsu.g.a.vybortseva.sort;

/**
 * Heapsort
 * The Sort class contains two methods for
 * pyramid sorting of an array of integers.
 * accepts: an array of integers
 * returns: sorted array
 */
public class Sort {
    /**
     * Sorting the created heap from the array.
     */
    public static void heapsort(int[] array) {
        int len = array.length;

        if (len <= 1) {
            return;
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
    }

    /**
     * Converting an array to a heap, the root
     * of which is the maximum element.
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
