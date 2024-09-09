package pl.training

import java.io.Serializable

// Minimalistic class definition
class Money

open class Account /*constructor*/(private val number: String) { // primary constructor with one private, immutable argument/property definition

    var balance = 0.0 // property

    var owner: String = ""
        get() = field.uppercase() // custom getter, `field` is a reference to backing field, must always have the same visibility and result type as the property
        //      get() {
        //          return field.uppercase()
        //      }
        set(value) {              // custom setter
            if (value.isNotBlank()) {
                field = value
            }
        }

    // If a property’s custom accessors do not use the field keyword, then the backing field will not be generated.
    val accountInfo: String
        get() = number + owner + balance

    // https://stackoverflow.com/a/60199782
    // default constructor body
    init {
        println("First init")
    }

    // another default constructor body
    init {
        println("Second init")
    }

    // additional constructor, it needs to call the primary constructor
    constructor(balance: Double, number: String) : this(number) {
        this.balance = balance
    }

    // member function
    fun printBalance() {
        println("Balance is $balance")
    }

    // companion object, similar to Java static elements, it can extend classes or implement interfaces
    companion object {

        const val DEFAULT_CURRENCY = "PLN" // compile time constant

    }

}

// Inheritance declaration (Any is default super type in Kotlin)
class PremiumAccount(number: String) : Account(number)

// Abstract classes and interfaces
abstract class User {

    abstract fun getInfo(): String

    fun generalInfo() = "User: "

}

class Admin : User() {

    override fun getInfo() = super.generalInfo() + " Admin"

}

fun interface Printable { // functional/SAM interface

    fun print(value: String)

}

fun generateReport(printable: Printable) {
    printable.print("summary")
}

class Document : Printable, Serializable {
    override fun print(value: String) {
    }
}

/*
    In Kotlin, we can define inner classes. They are static by default, which
    means that they do not have access to outer classes and can be created without a reference to an outer class.

    If you want your inner class to have a reference to its outer class, you need to use inner modifier.
*/

class Outer(val name: String) {
    class Inner {
        fun run() {
            println("Running from inner")
        }
    }
}

class OtherOuter(val name: String) {
    inner class Inner {
        fun run() {
            println("Running from inner with access to outer ($name)")
        }
    }
}

/*
    Data classes are primarily used to hold data. For each data class, the compiler automatically generates
    additional member functions:
        equals()/hashCode()
        toString() of the form "User(name=John, age=42)".
        componentN() functions corresponding to the properties in their order of declaration.
        copy() function
    To ensure consistency and meaningful behavior of the generated code, data classes have to fulfill the following requirements:
        the primary constructor must have at least one parameter
        all primary constructor parameters must be marked as `val` or `var`
        data classes can't be abstract, open, sealed, or inner
        inside the body, we should only keep redundant immutable properties, usually calculated from main properties
*/
data class Order(val id: String, val products: Array<String>) {
    val owner = "Jan" // property excluded from generated method implementation
}

// Singeleton object
/*data*/ object Origin {
    var x = 0
    var y = 0
}

/*
    Enums
    Enum represents a fixed set of values. Each enum class is a subclass of the abstract class Enum. This class guarantees
    the name and ordinal properties. Enum classes implements toString, equals, hashCode and compareTo (natural order).
*/
interface Json {

    fun toJson(): String

}

enum class Planet(val mass: Double, val radius: Double) : Json {

    EARTH(5.9, 6.3),
    MARS(3.2, 11.1) {

        override fun gravity(): Double = (G * mass / (radius * radius)) * 0.91

    };

    val G = 6.6

    open fun gravity(): Double = G * mass / (radius * radius)

    override fun toJson() = """{"name:${name}"}"""

}

/*
    Sealed classes/interfaces represent restricted hierarchies - a set of subtypes that can be made with
    classes or object declarations.
    Restrictions:
        they need to be defined in the same package and module where the sealed class or interface is
        they can’t be local or defined using object expression
*/
sealed interface Result
class Success(val data: String) : Result
class Failure(val exception: Throwable) : Result

fun handle(response: Result) = when (response) {
    is Success -> "Success with ${response.data}"
    is Failure -> "Error: ${response.exception}"
}

/*
  Annotation classes - represent meta-data, additional information.
  Annotations that are used to annotate other annotations are called as meta-annotations
*/
@MustBeDocumented
@Target(AnnotationTarget.CLASS)
// @Retention(AnnotationRetention.RUNTIME)
annotation class Factory

/*
    Extension functions are defined like regular functions, but they additionally have an extra
    type (and dot) before the function name. Extension functions can be defined on types we don’t control.
    This gives us the power to extend external APIs with our own functions. When you define an extension function,
    you do not really add anything to a class. It is just syntactic sugar.
    We can define extension properties if they do not need a backing field and are defined by accessors.
    Extensions need to be imported separately. For this reason, they can be located in a different package.
    (we can have many extensions with the same name for the same type). Extensions are not virtual, meaning that
    they cannot be redefined in derived classes.
*/
fun String.removeQuotes() = replace("\"", "").trim()
// fun removeQuotes(text: String) = text.removeQuotes().trim()

val <T> List<T>.lastIndex: Int
    get() = size - 1

class Report {

    companion object {
    }

}

fun Report.Companion.printVersion() {
    println("Version: 1.0")
}

/*
    Inline classes - a value class in Kotlin holds a single immutable value which can be inlined
    on compilation removing the wrapper type and using the underlying value itself.
    When we place the inline modifier before a function, its body will replace its usages (calls) during
    compilation.

    The simplest example is the print function from Kotlin stdlib. In JVM, it calls System.out.print.
    Since print is an inline function, all its usages during compilation are replaced with its body,
    so the print call is replaced with a System.out.print call.

    inline fun print(message: Any?) {
        System.out.print(message)
    }
 */

@JvmInline
value class Username(private val text: String)
@JvmInline
value class Password(private val text: String)

class Profile(val userName: Username, val password: Password)

fun main() {
    val account = Account("123456")
    account.printBalance()
    account.balance = 100.0 // setter call, possible because of var declaration
    println(account.balance) // getter call
    println(account.accountInfo)

    generateReport(object : Printable {  // object expression
        override fun print(value: String) {
            println(value)
        }
    })

    generateReport { println(it) }

    val inner = Outer.Inner()
    inner.run()

    OtherOuter("secondary").Inner().run()

    val orderCopy = Order("1", emptyArray())
        .copy(id = "3")

    val (id, products) = orderCopy // Position-based destructuring
    println("Order id $id")

    // orderCopy.component1()

    val planets = Planet.entries.forEach {
        println(it)
    }
    val earth = Planet.valueOf("EARTH")
    println(earth.name)
    println(earth.ordinal)
}
