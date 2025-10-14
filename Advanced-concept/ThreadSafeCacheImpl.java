import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Function;

/**
 * A thread-safe cache implementation with read-write lock mechanism.
 * This implementation ensures thread safety while maintaining good performance
 * by using ReadWriteLock for concurrent access.
 * 
 * @param <K> the type of keys maintained by this cache
 * @param <V> the type of mapped values
 */
public class ThreadSafeCacheImpl<K, V> {
    private final ConcurrentHashMap<K, V> cache;
    private final ReadWriteLock lock;
    
    public ThreadSafeCacheImpl() {
        this.cache = new ConcurrentHashMap<>();
        this.lock = new ReentrantReadWriteLock();
    }
    
    /**
     * Gets a value from cache. If not present, computes it using the provided function.
     * 
     * @param key the key whose associated value is to be returned
     * @param computeFunction the function to compute the value if not present
     * @return the value associated with the key
     */
    public V get(K key, Function<K, V> computeFunction) {
        // First try reading without lock
        V value = cache.get(key);
        if (value != null) {
            return value;
        }

        // If value not found, acquire write lock and compute
        lock.writeLock().lock();
        try {
            // Double-check after acquiring lock
            value = cache.get(key);
            if (value == null) {
                value = computeFunction.apply(key);
                cache.put(key, value);
            }
            return value;
        } finally {
            lock.writeLock().unlock();
        }
    }
    
    /**
     * Removes an entry from the cache.
     * 
     * @param key the key whose mapping is to be removed from the cache
     * @return the previous value associated with the key, or null if there was no mapping
     */
    public V remove(K key) {
        lock.writeLock().lock();
        try {
            return cache.remove(key);
        } finally {
            lock.writeLock().unlock();
        }
    }
    
    /**
     * Clears all entries from the cache.
     */
    public void clear() {
        lock.writeLock().lock();
        try {
            cache.clear();
        } finally {
            lock.writeLock().unlock();
        }
    }
    
    /**
     * Returns the current size of the cache.
     * 
     * @return the number of key-value mappings in this cache
     */
    public int size() {
        lock.readLock().lock();
        try {
            return cache.size();
        } finally {
            lock.readLock().unlock();
        }
    }
}
