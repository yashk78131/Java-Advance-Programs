// File: ThreadSafeLRUCache.java
import java.util.*;

public class ThreadSafeLRUCache<K,V> {
    private final Map<K,V> map;
    private final int capacity;
    private long hits = 0, misses = 0;

    public ThreadSafeLRUCache(int capacity) {
        this.capacity = capacity;
        this.map = Collections.synchronizedMap(new LinkedHashMap<>() {
            protected boolean removeEldestEntry(Map.Entry<K,V> eldest) {
                return size() > ThreadSafeLRUCache.this.capacity;
            }
        });
    }

    public V get(K key) {
        synchronized (map) {
            V val = map.remove(key);
            if (val != null) { map.put(key, val); hits++; return val; }
            misses++;
            return null;
        }
    }

    public void put(K key, V value) {
        synchronized (map) { map.put(key, value); }
    }

    public synchronized String stats() { return "hits=" + hits + " misses=" + misses; }

    public static void main(String[] args) throws InterruptedException {
        ThreadSafeLRUCache<Integer,String> cache = new ThreadSafeLRUCache<>(3);
        cache.put(1,"one"); cache.put(2,"two"); cache.put(3,"three");
        System.out.println(cache.get(2)); // two
        cache.put(4,"four"); // evicts 1
        System.out.println(cache.get(1)); // null
        System.out.println(cache.stats());
    }
}
