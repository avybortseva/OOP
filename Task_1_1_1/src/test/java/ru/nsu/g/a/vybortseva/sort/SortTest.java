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

}
