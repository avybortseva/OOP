package ru.nsu.g.a.vybortseva.table;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;


/**
 * A generic hash table implementation that maps keys to values using separate
 * chaining for collision resolution.
 * Supports basic operations like put, get, remove, and update with constant-time
 * performance on average.
 * Implements Iterable to allow iteration over key-value pairs.
 */
public class HashTable<K, V> implements Map<K, V>, Iterable<HashTable.Entry<K, V>> {
    private static final int DEFAULT_CAPACITY = 16;
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;

    private Entry<K, V>[] table;
    private int size;
    private int capacity;
    private final float loadFactor;
    private int modCount;

    /**
     * Represents a key-value pair entry in the hash table.
     * Stores the key, value, hash code, and reference to next entry in collision chain.
     */
    static class Entry<K, V> implements Map.Entry<K, V> {
        final K key;
        V value;
        Entry<K, V> next;
        final int hash;

        /**
         * Constructs a new entry with the given key, hash, value, and next entry reference.
         */
        public Entry(K key, int hash, V value, Entry<K, V> next) {
            this.key = key;
            this.hash = hash;
            this.value = value;
            this.next = next;
        }

        /**
         * Constructs a new entry with the given key, hash, value, and next entry reference.
         */
        public K getKey() {
            return key;
        }

        /**
         * Returns the value corresponding to this entry.
         */
        public V getValue() {
            return value;
        }

        /**
         * Replaces the value corresponding to this entry with the specified value.
         */
        @Override
        public V setValue(V value) {
            V oldValue = this.value;
            this.value = value;
            return oldValue;
        }

        /**
         * Compares the specified object with this entry for equality.
         */
        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof Entry)) {
                return false;
            }

            Entry<K, V> entry = (Entry<K, V>) o;
            return Objects.equals(key, entry.key)
                    && Objects.equals(value, entry.value);
        }

        /**
         * Returns the hash code value for this entry.
         */
        @Override
        public int hashCode() {
            return Objects.hash(key, value);
        }

        /**
         * Returns a string representation of this entry in key-value format.
         */
        @Override
        public String toString() {
            return  key + " = " + value;
        }
    }

    /**
     * Constructs an empty hash table with default initial capacity and load factor.
     */
    public HashTable() {
        this.loadFactor = DEFAULT_LOAD_FACTOR;
        this.capacity = DEFAULT_CAPACITY;
        this.table = (Entry<K, V>[]) new Entry[capacity];
        this.size = 0;
        this.modCount = 0;
    }

    /**
     * Constructs an empty hash table with the specified initial capacity and default load factor.
     */
    public HashTable(int initialCapacity) {
        this.loadFactor = DEFAULT_LOAD_FACTOR;
        this.capacity = initialCapacity;
    }

    /**
     * Constructs an empty hash table with the specified initial capacity and load factor.
     */
    public HashTable(int initialCapacity, float loadFactor) {
        if (initialCapacity < 0) {
            throw new IllegalArgumentException("Illegal initial capacity: " + initialCapacity);
        }
        if (loadFactor <= 0 || Float.isNaN(loadFactor)) {
            throw new IllegalArgumentException("Illegal load factor: " + loadFactor);
        }

        this.loadFactor = loadFactor;
        this.capacity = initialCapacity;
        this.table = (Entry<K, V>[]) new Entry[capacity];
        this.size = 0;
        this.modCount = 0;
    }

    private int hash(Object key) {
        if (key == null) {
            return 0;
        }
        return Math.abs(key.hashCode()) % capacity;
    }

    private void resize() {
        int newCapacity = capacity * 2;
        Entry<K, V>[] newTable = (Entry<K, V>[]) new Entry[newCapacity];

        for (int i = 0; i < capacity; i++) {
            Entry<K, V> entry = table[i];
            while (entry != null) {
                Entry<K, V> next = entry.next;
                int newIndex = Math.abs(entry.hash) % newCapacity;
                entry.next = newTable[newIndex];
                newTable[newIndex] = entry;
                entry = next;
            }
        }
        table = newTable;
        capacity = newCapacity;
        modCount++;
    }

    /**
     * Associates the specified value with the specified key in this hash table.
     * If the key already exists, the existing value is replaced.
     */
    @Override
    public V put(K key, V value) {
        if (size >= capacity * loadFactor) {
            resize();
        }
        int hash = hash(key);

        Entry<K, V> current = table[hash];
        Entry<K, V> prev = null;
        while (current != null) {
            if (Objects.equals(current.key, key)) {
                current.value = value;
                modCount++;
                return null;
            }
            prev = current;
            current = current.next;
        }

        Entry<K, V> entry = new Entry<>(key, hash, value, null);
        if (prev == null) {
            table[hash] = entry;
        } else {
            prev.next = entry;
        }

        size++;
        modCount++;
        return null;
    }

    /**
     * Copies all the mappings from the specified map to this hash table.
     * These mappings will replace any mappings that this hash table had for any of the keys
     * currently in the specified map.
     */
    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        for (Map.Entry<? extends K, ? extends V> entry : m.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    /**
     * Removes all the mappings from this hash table.
     * The hash table will be empty after this call returns.
     */
    @Override
    public void clear() {
        Arrays.fill(table, null);
        size = 0;
        modCount++;
    }

    /**
     * Returns a Set view of the keys contained in this hash table.
     * The set is backed by the hash table, so changes to the hash table
     */
    @Override
    public Set<K> keySet() {
        Set<K> keys = new HashSet<>();
        for (Entry<K, V> entry : this) {
            keys.add(entry.getKey());
        }
        return keys;
    }

    /**
     * Returns a Collection view of the values contained in this hash table.
     * The collection is backed by the hash table, so changes to the hash
     * table are reflected in the collection,
     */
    @Override
    public Collection<V> values() {
        List<V> values = new ArrayList<>();
        for (Entry<K, V> entry : this) {
            values.add(entry.getValue());
        }
        return values;
    }

    /**
     * Returns a Set view of the mappings contained in this hash table.
     * The set is backed by the hash table, so changes to the hash table are reflected in the set,
     */
    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        Set<Map.Entry<K, V>> entrySet = new HashSet<>();
        for (Entry<K, V> entry : this) {
            entrySet.add(entry);
        }
        return entrySet;
    }

    /**
     * Removes the mapping for the specified key from this hash table if present.
     */
    @Override
    public V remove(Object key) {
        V oldValue = get((K) key);
        int hash = hash(key);
        Entry<K, V> current = table[hash];
        Entry<K, V> prev = null;

        while (current != null) {
            if (Objects.equals(current.key, key)) {
                if (prev == null) {
                    table[hash] = current.next;
                } else {
                    prev.next = current.next;
                }
                size--;
                modCount++;
                return oldValue;
            }
            prev = current;
            current = current.next;
        }
        return oldValue;
    }

    /**
     * Returns the value to which the specified key is mapped, or null if no mapping exists.
     */
    @Override
    public V get(Object key) {
        int hash = hash(key);
        Entry<K, V> current = table[hash];

        while (current != null) {
            if (Objects.equals(current.key, key)) {
                return current.value;
            }
            current = current.next;
        }
        return null;
    }

    /**
     * Updates the value associated with the specified key if the key exists in the hash table.
     * If the key does not exist, no action is performed.
     */
    public void update(K key, V value) {
        int hash = hash(key);
        Entry<K, V> current = table[hash];

        while (current != null) {
            if (Objects.equals(current.key, key)) {
                current.value = value;
                modCount++;
                return;
            }
            current = current.next;
        }
    }

    /**
     * Returns the number of key-value mappings in this hash table.
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Returns true if this hash table contains no key-value mappings.
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns true if this hash table contains a mapping for the specified key.
     */
    @Override
    public boolean containsKey(Object key) {
        return get(key) != null;
    }

    /**
     * Returns true if this hash table maps one or more keys to the specified value.
     */
    @Override
    public boolean containsValue(Object value) {
        for (Entry<K, V> entry : this) {
            if (Objects.equals(entry.value, value)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns an iterator over the entries in this hash table.
     */
    @Override
    public Iterator<Entry<K, V>> iterator() {
        return new HashTableIterator();
    }

    private class HashTableIterator implements Iterator<Entry<K, V>> {
        private int curIndex = 0;
        private Entry<K, V> curEntry = null;
        private Entry<K, V> nextEntry = null;
        private final int expectedModCount;

        /**
         * Constructs a new iterator over the entries in the hash table.
         * The iterator supports fail-fast behavior and will throw ConcurrentModificationException
         * if the hash table is structurally modified during iteration.
         */
        public HashTableIterator() {
            this.expectedModCount = modCount;
            findNextEntry();
        }

        private void findNextEntry() {
            if (curEntry != null && curEntry.next != null) {
                nextEntry = curEntry.next;
                return;
            }

            while (curIndex < capacity) {
                if (table[curIndex] != null) {
                    nextEntry = table[curIndex];
                    curIndex++;
                    return;
                }
                curIndex++;
            }
            nextEntry = null;
        }

        /**
         * Returns true if the iteration has more elements.
         */
        @Override
        public boolean hasNext() {
            checkModCount();
            return nextEntry != null;
        }

        /**
         * Returns the next element in the iteration.
         */
        @Override
        public Entry<K, V> next() {
            checkModCount();
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            curEntry = nextEntry;
            findNextEntry();
            return curEntry;
        }

        private void checkModCount() {
            if (modCount != expectedModCount) {
                throw new ConcurrentModificationException();
            }
        }
    }

    /**
     * Compares the specified object with this hash table for equality.
     * Returns true if the given object is also a hash table and contains the same
     * key-value mappings.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof HashTable<?, ?> other)) {
            return false;
        }

        if (this.size != other.size) {
            return false;
        }

        for (Entry<K, V> entry : this) {
            if (!containsEntry(other, entry)) {
                return false;
            }
        }
        return true;
    }

    private boolean containsEntry(HashTable<?, ?> other, Entry<K, V> entry) {
        for (Entry<?, ?> otherEntry : other) {
            if (Objects.equals(entry.getKey(), otherEntry.getKey())
                    && Objects.equals(entry.getValue(), otherEntry.getValue())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the hash code value for this hash table.
     * The hash code is computed as the sum of hash codes of all entries.
     */
    @Override
    public int hashCode() {
        int result = 0;

        for (Entry<K, V> entry : this) {
            int entryHash = 0;

            int keyHash;
            if (entry.key == null) {
                keyHash = 0;
            } else {
                keyHash = entry.key.hashCode();
            }

            int valueHash;
            if (entry.value == null) {
                valueHash = 0;
            } else {
                valueHash = entry.value.hashCode();
            }

            entryHash = keyHash ^ valueHash;

            result += entryHash;
        }
        return result;
    }

    /**
     * Returns a string representation of this hash table in key-value pair format.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");

        boolean first = true;
        for (Entry<K, V> entry : this) {
            if (!first) {
                sb.append(", ");
            }
            sb.append(entry.key).append("=").append(entry.value);
            first = false;
        }

        sb.append("}");
        return sb.toString();
    }

    /**
     * Main method demonstrating basic usage of the HashTable class.
     */
    public static void main(String[] args) {
        HashTable<String, Number> hashTable = new HashTable<>();

        hashTable.put("one", 1);
        hashTable.update("one", 1.0);
        System.out.println(hashTable.get("one")); // Выведет: 1.0

        hashTable.put("two", 2);
        hashTable.put("three", 3);

        System.out.println("Все элементы:");
        for (Entry<String, Number> entry : hashTable) {
            System.out.println(entry);
        }

        HashTable<String, Number> hashTable2 = new HashTable<>();
        hashTable2.put("one", 1.0);
        hashTable2.put("two", 2);
        hashTable2.put("three", 3);

        System.out.println("Таблицы равны: " + hashTable.equals(hashTable2));

        hashTable.remove("two");
        System.out.println("После удаления 'two': " + hashTable);
    }
}
