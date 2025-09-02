package ru.nsu.g.a.vybortseva.sort;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SortTest {

    @Test
    void sort() {
        int[] array = new int[]{1, 3, 2};
        var result = Sort.sort(array);
        assertArrayEquals(new int[]{1, 2, 3}, result);
    }
}