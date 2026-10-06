package retailiq.structures;

/**
 * Custom Generic Hash Table using separate chaining, implemented without java.util.*
 *
 * @param <K> Key type
 * @param <V> Value type
 */
public class MyHashTable<K, V> {

    public static class Entry<K, V> {
        public K key;
        public V value;
        public Entry<K, V> next;

        public Entry(K key, V value, Entry<K, V> next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }

    private static final int DEFAULT_CAPACITY = 16;
    private static final float LOAD_FACTOR = 0.75f;

    private Entry<K, V>[] table;
    private int size;
    private int capacity;

    @SuppressWarnings("unchecked")
    public MyHashTable(int capacity) {
        this.capacity = capacity;
        this.table = new Entry[capacity];
        this.size = 0;
    }

    public MyHashTable() {
        this(DEFAULT_CAPACITY);
    }

    private int hash(K key) {
        if (key == null) return 0;
        int h = key.hashCode();
        return (h & 0x7fffffff) % capacity;
    }

    public void put(K key, V value) {
        int index = hash(key);
        Entry<K, V> head = table[index];
        Entry<K, V> curr = head;

        while (curr != null) {
            if ((key == null && curr.key == null) || (key != null && key.equals(curr.key))) {
                curr.value = value;
                return;
            }
            curr = curr.next;
        }

        Entry<K, V> newEntry = new Entry<>(key, value, head);
        table[index] = newEntry;
        size++;

        if ((float) size / capacity >= LOAD_FACTOR) {
            resize();
        }
    }

    public V get(K key) {
        int index = hash(key);
        Entry<K, V> curr = table[index];
        while (curr != null) {
            if ((key == null && curr.key == null) || (key != null && key.equals(curr.key))) {
                return curr.value;
            }
            curr = curr.next;
        }
        return null;
    }

    public boolean containsKey(K key) {
        return get(key) != null;
    }

    public V remove(K key) {
        int index = hash(key);
        Entry<K, V> curr = table[index];
        Entry<K, V> prev = null;

        while (curr != null) {
            if ((key == null && curr.key == null) || (key != null && key.equals(curr.key))) {
                if (prev == null) {
                    table[index] = curr.next;
                } else {
                    prev.next = curr.next;
                }
                size--;
                return curr.value;
            }
            prev = curr;
            curr = curr.next;
        }
        return null;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    @SuppressWarnings("unchecked")
    private void resize() {
        int newCapacity = capacity * 2;
        Entry<K, V>[] oldTable = table;
        table = new Entry[newCapacity];
        capacity = newCapacity;
        size = 0;

        for (int i = 0; i < oldTable.length; i++) {
            Entry<K, V> curr = oldTable[i];
            while (curr != null) {
                put(curr.key, curr.value);
                curr = curr.next;
            }
        }
    }
}
