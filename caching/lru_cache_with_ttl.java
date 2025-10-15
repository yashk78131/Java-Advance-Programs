/*
===============================================================
Problem Statement:
Implement an LRU (Least Recently Used) cache supporting:
 - O(1) average get and put operations
 - Optional per-entry TTL (time-to-live) in milliseconds
 - Thread-safety for concurrent access
 - Automatic eviction of expired entries via a background cleaner

This program demonstrates an efficient and practical cache component,
useful in many production systems (web caching, session stores, etc).

Input / Output:
No stdin required. The `main` method demonstrates the cache:
 - Put keys with/without TTL
 - Get keys to show LRU behaviour & expiry
 - Print cache state to the console

Example (observed console output):
 - Shows insertion, access, eviction due to capacity,
   and expiry after TTL passes.

Complexity:
 - get / put : Average O(1)
 - Space: O(capacity)
===============================================================
*/

import java.util.*;
import java.util.concurrent.*;
public class lru_cache_with_ttl {

    static final class CacheEntry<V> {
        final V value;
        final long expiresAt; // epoch millis; Long.MAX_VALUE => no expiry

        CacheEntry(V value, long expiresAt) {
            this.value = value;
            this.expiresAt = expiresAt;
        }

        boolean isExpired() {
            return System.currentTimeMillis() > expiresAt;
        }
    }

    public static class LRUCache<K, V> implements AutoCloseable {
        private final int capacity;
        private final long cleanerIntervalMillis;
        private final Map<K, CacheEntry<V>> map;
        private final ScheduledExecutorService cleaner;

        /**
         * Create a cache with capacity. Cleaner runs every 1s by default.
         *
         * @param capacity max entries before eviction
         */
        public LRUCache(int capacity) {
            this(capacity, 1000L); // default cleaner interval 1000 ms
        }

        /**
         * Full constructor.
         *
         * @param capacity max entries
         * @param cleanerIntervalMillis how often to remove expired entries
         */
        public LRUCache(int capacity, long cleanerIntervalMillis) {
            if (capacity <= 0) throw new IllegalArgumentException("Capacity must be > 0");
            this.capacity = capacity;
            this.cleanerIntervalMillis = Math.max(200L, cleanerIntervalMillis);

            // LinkedHashMap with accessOrder = true to maintain LRU order
            this.map = new LinkedHashMap<K, CacheEntry<V>>(capacity + 1, 0.75f, true) {
                @Override
                protected boolean removeEldestEntry(Map.Entry<K, CacheEntry<V>> eldest) {
                    // Remove eldest when size exceeds capacity
                    return size() > LRUCache.this.capacity;
                }
            };

            // Scheduled cleaner to evict expired entries periodically
            this.cleaner = Executors.newSingleThreadScheduledExecutor(r -> {
                Thread t = new Thread(r, "LRUCache-Cleaner");
                t.setDaemon(true);
                return t;
            });
            this.cleaner.scheduleAtFixedRate(this::cleanupExpired, this.cleanerIntervalMillis,
                    this.cleanerIntervalMillis, TimeUnit.MILLISECONDS);
        }

        /** Put a key-value pair with TTL in milliseconds (>=1). TTL <= 0 => no expiry. */
        public synchronized void put(K key, V value, long ttlMillis) {
            long expiresAt = (ttlMillis > 0) ? (System.currentTimeMillis() + ttlMillis) : Long.MAX_VALUE;
            map.put(key, new CacheEntry<>(value, expiresAt));
        }

        /** Convenience put with no TTL (persistent until eviction). */
        public void put(K key, V value) {
            put(key, value, 0);
        }

        /** Get value; returns null if absent or expired. Access updates recency. */
        public synchronized V get(K key) {
            CacheEntry<V> entry = map.get(key);
            if (entry == null) return null;
            if (entry.isExpired()) {
                map.remove(key);
                return null;
            }
            return entry.value;
        }

        /** Remove a key explicitly. */
        public synchronized void remove(K key) {
            map.remove(key);
        }

        /** Current cache size (non-expired entries only might be slightly off until cleanup runs). */
        public synchronized int size() {
            return map.size();
        }

        /** Dump current snapshot (for debugging / demo). */
        public synchronized List<K> keysInLRUOrder() {
            // Return keys from most-recently-used to least (LinkedHashMap iteration gives least->most by access-order)
            List<K> list = new ArrayList<>(map.keySet());
            Collections.reverse(list);
            return list;
        }

        /** Cleanup expired entries (runs from cleaner thread). */
        private synchronized void cleanupExpired() {
            Iterator<Map.Entry<K, CacheEntry<V>>> it = map.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<K, CacheEntry<V>> e = it.next();
                if (e.getValue().isExpired()) {
                    it.remove();
                }
            }
        }

        /** Close and stop background cleaner. */
        @Override
        public void close() {
            cleaner.shutdownNow();
        }
    }

    // ------------------- Demo Main -------------------
    public static void main(String[] args) throws InterruptedException {
        System.out.println("LRU Cache with TTL demo\n");

        // Capacity 3, cleaner every 500ms
        try (LRUCache<String, String> cache = new LRUCache<>(3, 500L)) {
            System.out.println("Putting A -> apple (no TTL)");
            cache.put("A", "apple"); // no expiry

            System.out.println("Putting B -> banana (TTL 1500ms)");
            cache.put("B", "banana", 1500);

            System.out.println("Putting C -> cherry (TTL 3000ms)");
            cache.put("C", "cherry", 3000);

            System.out.println("\nCache keys (MRU -> LRU): " + cache.keysInLRUOrder());

            // Access A to make it most-recently-used
            System.out.println("\nAccessing A: " + cache.get("A"));
            System.out.println("Cache keys after accessing A (MRU -> LRU): " + cache.keysInLRUOrder());

            // Insert D to exceed capacity => oldest (LRU) evicted
            System.out.println("\nPut D -> durian (no TTL) - will evict LRU");
            cache.put("D", "durian");

            System.out.println("Cache keys now (MRU -> LRU): " + cache.keysInLRUOrder());

            // Wait for ttl of B to expire
            System.out.println("\nWaiting 2 seconds for B to expire...");
            Thread.sleep(2000);

            System.out.println("Get B (should be null): " + cache.get("B"));
            System.out.println("Cache keys after expiry cleanup (MRU -> LRU): " + cache.keysInLRUOrder());

            // Show adding after expiry and capacity handling
            System.out.println("\nPut E -> elderberry (no TTL)");
            cache.put("E", "elderberry");
            System.out.println("Cache keys final (MRU -> LRU): " + cache.keysInLRUOrder());

            System.out.printf("\nFinal cache size: %d\n", cache.size());

            // Sleep a bit to let cleaner run (demo only)
            Thread.sleep(700);
        } // cache.close() called automatically
        System.out.println("\nDemo complete. Cleaner stopped.");
    }
}
