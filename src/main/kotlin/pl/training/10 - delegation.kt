package pl.training

import kotlin.properties.Delegates
import kotlin.properties.Delegates.observable
import kotlin.reflect.KProperty
import kotlin.reflect.KProperty1

/*
    Delegation pattern on class level
    The by-clause in the supertype list for Derived indicates that b will be stored internally in objects
    of Derived and the compiler will generate all the methods of Base that forward to b.
    Overrides work as you expect: the compiler will use your override implementations instead of those in the delegate object.
    Note, however, that members overridden in this way do not get called from the members of the delegate object,
    which can only access its own implementations of the interface members
*/

interface Base {
    fun print()
}

class BaseImpl(val x: Int) : Base {
    override fun print() { println(x) }
}

class Derived(b: Base) : Base by b {

    override fun print() { println("Derived") }

}

/*
    Delegation pattern on properties level
    The Kotlin standard library provides factory methods for several useful kinds of delegates:
        Lazy properties: the value is computed only on first access.
        Observable properties: listeners are notified about changes to this property.
        Storing properties in a map instead of a separate field for each property.
 */

val lazyValue: String by lazy {
    println("Calculating value")
    "Some value"
}

class Counter {
    var value: Int by observable(0) { prop, oldValue, newValue ->
        println("$oldValue => $newValue")
    }
}

class Participant(map: Map<String, Any?>) {
    val name: String by map
    val age: Int     by map

    override fun toString() = "$name: $age"
}

class LoggerDelegate<T> {

    private var value: T? = null

    operator fun getValue(owner: Any, property: KProperty<*>): T {
        println("$owner.${property.name} read")
        return value ?: throw IllegalStateException()
    }


    operator fun setValue(owner: Any, property: KProperty<*>, value: T) {
        println("$owner.${property.name} write")
        this.value = value
    }

}


class Person {

    var name: String by LoggerDelegate()

    override fun toString(): String {
        return "Person"
    }

}

fun main() {
    val base = BaseImpl(10)
    Derived(base).print()

    println(lazyValue)
    println(lazyValue)

    Counter().value++

    val user = Participant(mapOf(
        "name" to "John Doe",
        "age"  to 25
    ))

    println(user)

    val person = Person()
    person.name = "Jan"
    println(person.name)
}
