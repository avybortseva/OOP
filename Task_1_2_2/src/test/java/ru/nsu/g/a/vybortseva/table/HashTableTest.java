package ru.nsu.g.a.vybortseva.table;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ConcurrentModificationException;
import java.util.Iterator;

class HashTableTest {

    private HashTable<String, Integer> hashTable;

    @BeforeEach
    void setUp() {
        hashTable = new HashTable<>();
    }

    @Test
    void testPutAndGet() {
        hashTable.put("one", 1);
        hashTable.put("two", 2);
        hashTable.put("three", 3);

        assertEquals(1, hashTable.get("one"));
        assertEquals(2, hashTable.get("two"));
        assertEquals(3, hashTable.get("three"));
        assertNull(hashTable.get("nonexistent"));
    }

    @Test
    void testPutOverwrite() {
        hashTable.put("key", 1);
        assertEquals(1, hashTable.get("key"));

        hashTable.put("key", 2);
        assertEquals(2, hashTable.get("key"));
    }

    @Test
    void testRemove() {
        hashTable.put("one", 1);
        hashTable.put("two", 2);
        hashTable.put("three", 3);

        hashTable.remove("two");
        assertNull(hashTable.get("two"));
        assertEquals(1, hashTable.get("one"));
        assertEquals(3, hashTable.get("three"));
    }

    @Test
    void testRemoveNonexistent() {
        hashTable.put("one", 1);
        hashTable.remove("nonexistent");
        assertEquals(1, hashTable.get("one"));
    }

    @Test
    void testUpdate() {
        hashTable.put("one", 1);
        hashTable.update("one", 100);
        assertEquals(100, hashTable.get("one"));

        // Test update on nonexistent key (should do nothing)
        hashTable.update("nonexistent", 999);
        assertNull(hashTable.get("nonexistent"));
    }

    @Test
    void testContainsKey() {
        assertFalse(hashTable.containsKey("one"));

        hashTable.put("one", 1);
        assertTrue(hashTable.containsKey("one"));
        assertFalse(hashTable.containsKey("two"));

        hashTable.put(null, 0);
        assertTrue(hashTable.containsKey(null));
    }

    @Test
    void testIsEmpty() {
        assertTrue(hashTable.isEmpty());

        hashTable.put("one", 1);
        assertFalse(hashTable.isEmpty());

        hashTable.remove("one");
        assertTrue(hashTable.isEmpty());
    }

    @Test
    void testIterator() {
        hashTable.put("one", 1);
        hashTable.put("two", 2);
        hashTable.put("three", 3);

        int count = 0;
        for (HashTable.Entry<String, Integer> entry : hashTable) {
            assertNotNull(entry.getKey());
            assertNotNull(entry.getValue());
            count++;
        }
        assertEquals(3, count);
    }

    @Test
    void testIteratorOnEmptyTable() {
        Iterator<HashTable.Entry<String, Integer>> iterator = hashTable.iterator();
        assertFalse(iterator.hasNext());
    }

    @Test
    void testIteratorConcurrentModification() {
        hashTable.put("one", 1);
        hashTable.put("two", 2);

        Iterator<HashTable.Entry<String, Integer>> iterator = hashTable.iterator();
        assertTrue(iterator.hasNext());

        hashTable.put("three", 3);

        assertThrows(ConcurrentModificationException.class, iterator::hasNext);
        assertThrows(ConcurrentModificationException.class, iterator::next);
    }

    @Test
    void testEquals() {
        HashTable<String, Integer> table1 = new HashTable<>();
        HashTable<String, Integer> table2 = new HashTable<>();

        assertTrue(table1.equals(table2));

        table1.put("one", 1);
        table1.put("two", 2);
        table2.put("one", 1);
        table2.put("two", 2);
        assertTrue(table1.equals(table2));

        table2.update("two", 3);
        assertFalse(table1.equals(table2));

        table2.remove("two");
        table2.put("three", 2);
        assertFalse(table1.equals(table2));

        table2.remove("three");
        assertFalse(table1.equals(table2));

        assertFalse(table1.equals(null));

        assertFalse(table1.equals("not a hashtable"));
    }

    @Test
    void testEqualsWithDifferentOrder() {
        HashTable<String, Integer> table1 = new HashTable<>();
        HashTable<String, Integer> table2 = new HashTable<>();

        table1.put("a", 1);
        table1.put("b", 2);

        table2.put("b", 2);
        table2.put("a", 1);

        assertTrue(table1.equals(table2));
    }

    @Test
    void testHashCode() {
        HashTable<String, Integer> table1 = new HashTable<>();
        HashTable<String, Integer> table2 = new HashTable<>();

        assertEquals(table1.hashCode(), table2.hashCode());

        table1.put("one", 1);
        table1.put("two", 2);
        table2.put("one", 1);
        table2.put("two", 2);
        assertEquals(table1.hashCode(), table2.hashCode());

        int hashCode1 = table1.hashCode();
        int hashCode2 = table1.hashCode();
        assertEquals(hashCode1, hashCode2);
    }

    @Test
    void testHashCodeWithNullValues() {
        HashTable<String, Integer> table = new HashTable<>();
        table.put(null, 1);
        table.put("key", null);

        assertDoesNotThrow(table::hashCode);
    }

    @Test
    void testToString() {
        HashTable<String, Integer> table = new HashTable<>();

        assertEquals("{}", table.toString());

        table.put("one", 1);
        String result = table.toString();
        assertTrue(result.equals("{one=1}") || result.contains("one=1"));

        table.put("two", 2);
        result = table.toString();
        assertTrue(result.contains("one=1") && result.contains("two=2"));
    }

    @Test
    void testResize() {
        HashTable<Integer, String> table = new HashTable<>(4, 0.5f);

        table.put(1, "one");
        table.put(2, "two");
        table.put(3, "three");

        assertEquals("one", table.get(1));
        assertEquals("two", table.get(2));
        assertEquals("three", table.get(3));
    }

    @Test
    void testConstructorWithInvalidParameters() {
        assertThrows(IllegalArgumentException.class, () -> new HashTable<>(10, -1.0f));
    }

    @Test
    void testEntryEqualsAndHashCode() {
        HashTable.Entry<String, Integer> entry1 = new HashTable.Entry<>("key", 123, 1, null);
        HashTable.Entry<String, Integer> entry2 = new HashTable.Entry<>("key", 123, 1, null);
        HashTable.Entry<String, Integer> entry3 = new HashTable.Entry<>("different", 456, 1, null);

        assertTrue(entry1.equals(entry2));
        assertFalse(entry1.equals(entry3));
        assertFalse(entry1.equals(null));
        assertFalse(entry1.equals("not an entry"));

        assertEquals(entry1.hashCode(), entry2.hashCode());
    }

    @Test
    void testEntryToString() {
        HashTable.Entry<String, Integer> entry = new HashTable.Entry<>("test", 123, 42, null);
        assertEquals("test = 42", entry.toString());
    }

    @Test
    void testMainMethod() {
        assertDoesNotThrow(() -> HashTable.main(new String[]{}));
    }
}