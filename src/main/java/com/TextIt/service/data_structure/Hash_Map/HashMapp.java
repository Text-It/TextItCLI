package com.TextIt.service.data_structure.Hash_Map;

public class HashMapp<K,V> {
    private final int CAPACITY = 16;
    public Entry[] entries = new Entry[CAPACITY];

    public void clear() {
        for (int i = 0; i < CAPACITY; i++) {
            entries[i] = null;
        }
    }

    static class Entry<K,V>{
        private K key;
        private V value;
        Entry<K,V> next;
        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    // Hash Function
    public int getIndex(K key){
        return Math.abs(key.hashCode() % CAPACITY);
    }

    public void put(K key, V value){
        int index = getIndex(key);

        if (entries[index] == null) {
            entries[index] = new Entry<>(key, value);
            return;
        }

        Entry<K, V> current = entries[index];
        Entry<K, V> prev = null;

        while (current != null) {
            if (current.key.equals(key)) {
                // update existing key
                current.value = value;
                return;
            }
            prev = current;
            current = current.next;
        }
        prev.next = new Entry<>(key, value); // append new node
    }

    public V get(K key){
        int index = getIndex(key);
        Entry<K, V> current = entries[index];
        while (current != null) {
            if (current.key.equals(key)) {
                return current.value;
            }
            current = current.next;
        }
        System.out.println("Key not found");
        return null;
    }

    public void remove(K key){
        int index = getIndex(key);
        Entry<K, V> current = entries[index];
        Entry<K, V> prev = null;
        while (current != null) {
            if (current.key.equals(key)) {
                if (prev == null) {
                    entries[index] = current.next; // first node
                } else {
                    prev.next = current.next; // middle/end
                }
                return;
            }
            prev = current;
            current = current.next;
        }
    }
}
