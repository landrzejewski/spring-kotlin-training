package pl.training.commons.aop

import java.util.concurrent.locks.ReadWriteLock
import java.util.concurrent.locks.ReentrantReadWriteLock

class LinkedHashMapCache<K, V>(private val capacity: Int) : Cache<K, V> {

    private val cache: MutableMap<K, V> = object : LinkedHashMap<K, V>(capacity, 1f, true) {
        override fun removeEldestEntry(eldest: MutableMap.MutableEntry<K, V>?): Boolean {
            return size > capacity
        }
    }

    private val lock: ReadWriteLock = ReentrantReadWriteLock()

    override fun put(key: K, value: V) {
        lock.writeLock().lock()
        try {
            cache[key] = value
        } finally {
            lock.writeLock().unlock()
        }
    }

    override fun get(key: K): V? {
        lock.readLock().lock()
        return try {
            cache[key]
        } finally {
            lock.readLock().unlock()
        }
    }

}
