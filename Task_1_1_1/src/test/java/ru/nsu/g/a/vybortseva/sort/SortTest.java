package ru.nsu.g.a.vybortseva.sort;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

class SortTest {

    @Test
    void emptyArray() {
        int[] array = new int[]{};
        var result = Sort.heapsort(array);
        assertArrayEquals(new int[]{}, result);
    }

    @Test
    void singleElement() {
        int[] array = new int[]{1};
        var result = Sort.heapsort(array);
        assertArrayEquals(new int[]{1}, result);
    }

    @Test
    void twoElementsSorted() {
        int[] array = new int[]{1, 2};
        var result = Sort.heapsort(array);
        assertArrayEquals(new int[]{1, 2}, result);
    }

    @Test
    void twoElementsReversed() {
        int[] array = new int[]{2, 1};
        var result = Sort.heapsort(array);
        assertArrayEquals(new int[]{1, 2}, result);
    }

    @Test
    void sortedArray() {
        int[] array = new int[]{1, 2, 3, 4, 5, 6, 7};
        var result = Sort.heapsort(array);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5, 6, 7}, result);
    }

    @Test
    void reversedArray() {
        int[] array = new int[]{7, 6, 5, 4, 3, 2, 1};
        var result = Sort.heapsort(array);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5, 6, 7}, result);
    }

    @Test
    void arrayWithDuplicates() {
        int[] array = new int[]{3, 1, 4, 1, 5, 9, 2, 6, 6, 28, 39, 10};
        var result = Sort.heapsort(array);
        assertArrayEquals(new int[]{1, 1, 2, 3, 4, 5, 6, 6, 9, 10, 28, 39}, result);
    }

    @Test
    void uniformArray() {
        int[] array = new int[]{7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7};
        var result = Sort.heapsort(array);
        assertArrayEquals(new int[]{7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7}, result);
    }

    @Test
    void negativeNumbers() {
        int[] array = new int[]{-4, -3, -7, -1, -20};
        var result = Sort.heapsort(array);
        assertArrayEquals(new int[]{-20, -7, -4, -3, -1}, result);
    }

    @Test
    void mixedSignNumbers() {
        int[] array = new int[]{-1, 6, -3, -9, 3, -4, 3, 4, 5, -3};
        var result = Sort.heapsort(array);
        assertArrayEquals(new int[]{-9, -4, -3, -3, -1, 3, 3, 4, 5, 6}, result);
    }

    @Test
    void largeArray() {
        int[] array = new int[1000];
        int[] expected = new int[1000];
        for (int i = 0; i < 1000; i++) {
            array[i] = 1000 - i;
            expected[i] = i + 1;
        }
        var result = Sort.heapsort(array);
        assertArrayEquals(expected, result);
    }

}
