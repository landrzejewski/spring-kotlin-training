package pl.training

import kotlin.random.Random

/*
    A generic type is a generic element that is parameterized over types.
    In Kotlin, we have three kinds of generic elements: generic functions, classes and interfaces.
*/

fun <T> oneOf(a: T, b: T): T = if (Random.nextBoolean()) a else b

class Wrapper<V>(val value: V)

interface Converter<S, T> {

    fun convert(source: S): T

}

class DoubleToInt : Converter<Double, Int> {

    override fun convert(source: Double) = source.toInt()

}

class IntToDouble : Converter<Int, Double> {

    override fun convert(source: Int) = source.toDouble()

}

class StringToBoolean : Converter<String, Boolean> {

    override fun convert(source: String) = source.toBoolean()

}

val converters = mapOf(
    "doubleToInt" to DoubleToInt(),
    "intToDouble" to IntToDouble(),
    "StringToBoolean" to StringToBoolean()
)

/*
open class Vehicle
open class Car() : Vehicle()
class RaceCar : Car()

class Garage<out T : Vehicle> {

    private val vehicles = mutableListOf<T>()

    // for T - invarinat - can take and return T
    // for `out` produces T, covariant - can return T but can't take T
    // for `in` consumes T, contravariant - can take T but can't return T

    fun getLast(): T = vehicles.last()

    fun add(vehicle: T) = vehicles.add(vehicle)

}

fun drive(garage: Garage<Car>) {
    val vehicle = garage.getLast()
}
*/

/*
fun main() {
    Wrapper(2)
    Wrapper("Test")

    val converter = converters["doubleToInt"] as? Converter<Double, Int>
    converter?.let {
        it.convert(2.0)
    }

    // for T - can pass exact type, can't pass subtype and super type
    // for `out` - can pass exact type, subtype, can't pass supertype
    // for `in` - can pass exact type, supertype, can't pass subtype

    /*drive(Garage<RaceCar>())
    drive(Garage<Car>())
    drive(Garage<Vehicle>())*/

    // In some cases, we don’t want to specify a concrete type argument for a type. In
    // these scenarios, we can use a star projection *, which accepts any type

    val list = listOf("A", "B")
    println(list is List<*>) // true
    // println(list is List<Int>) // Compilation error
}
*/
/*
    Let’s say that Child is a subtype of Parent, and you have a generic Box class to
    enclose them both. The question is: what is the relation between the Box<Child>
    and Box<Parent> types? In other words, can we use Box<Child> where Box<Parent> is
    expected, or vice versa?
*/

/*
    When a type parameter has no variance modifier (no `out` or `in` modifier), we say
    it is invariant and thus expects an exact type. So, if we have class Box<T>, then
    there is no relation between Box<Child> and Box<Parent>
 */

/*
class Box<T: Parent>
open class Parent
class Child : Parent()

fun main() {
    val p: Parent = Child() // Child is a subtype of Parent
    val bp: Box<Parent> = Box<Child>() // Error: Type mismatch
    val bc: Box<Child> = Box<Parent>() // Error: Type mismatch
    val bn: Box<Number> = Box<Int>() // Error: Type mismatch
    val bi: Box<Int> = Box<Number>() // Error: Type mismatch
}
*/

/*
    Variance modifiers determine what the relationship should be between
    Box<Child> and Box<Parent>. When we use the `out` modifier, we make a covariant
    type parameter. When A is a subtype of B, the Box type parameter is covariant
    and the Box<A> type is a subtype of Box<B>. So, in our example, for
    class Box<out T>, the Box<Child> type is a subtype of Box<Parent>.
*/

/*
class Box<out T>
open class Parent
class Child : Parent()

fun main() {
    val d: Parent = Child() // Child is a subtype of Parent
    val bp: Box<Parent> = Box<Child>() // OK
    val bc: Box<Child> = Box<Parent>() // Error: Type mismatch
    val bn: Box<Number> = Box<Int>() // OK
    val bi: Box<Int> = Box<Number>() // Error: Type mismatch
}
*/

/*
    When we use the `in` modifier, we make a contravariant type parameter. When A
    is a subtype of B and the Box type parameter is contravariant, then
    type Box<B> is a subtype of Box<A>. So, in our example, for class Box<in T> the
    Box<Parent> type is a subtype of Box<Child>.
*/

/*
class Box<in T>
open class Parent
class Child : Parent()

fun main() {
    val d: Parent = Child() // Child is a subtype of Parent
    val bp: Box<Parent> = Box<Child>() // Error: Type mismatch
    val bc: Box<Child> = Box<Parent>() // OK
    val bn: Box<Number> = Box<Int>() // Error: Type mismatch
    val bi: Box<Int> = Box<Number>() // OK
}
*/
