package pl.training.commons.aop

interface Cache<K, V> {

    fun put(key: K, value: V)

    fun get(key: K): V?

}
