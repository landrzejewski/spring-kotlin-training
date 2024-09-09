package pl.training

/*
    The function declaration is done with the `fun` keyword.
    Functions can be defined:
        in files outside any classes, called top-level functions
        inside classes or objects, called member functions (they are also called methods)
        inside functions, called local functions or nested functions, they can directly access or modify local variables

    Function parameters are read-only (immutable). All functions have a result type (Unit by default).
*/

// Typical function in Kotlin
fun square(x: Double): Double {
    return x * x
}

// Single expression function (in this case return type is inferred by the compiler but can be also provided by programmer)
fun isEven(value: Int) = value % 2 == 0

// Vararg parameters (the type of `ints` is IntArray)
fun sum(vararg ints: Int): Int {
    var accumulator = 0
    for (i in ints) accumulator += i
    return accumulator
}

// Named parameter and default values
fun sayHello(how: String = "Hello,", who: String = "there") {
    println("$how $who")
}

/*
    We can define functions with the same name in the same scope as long as they have different parameter
    types or a different number of parameters (overloading).
*/

fun asText(value: Int) = "Int $value"
fun asText(value: Long) = "Long $value"

/*
    Methods with a single parameter can use the infix modifier, which allows a special kind of function call
    (without the dot and parentheses)
*/

infix fun Int.mod(value: Int) = this % value

// Returning a result from a nested function
fun compute() {
    listOf(1, 2, 3).forEach {
        if (it == 3) return // return from compute
        print(it)
    }
    println("this point is unreachable")
}

fun compute2() {
    listOf(1, 2, 3).forEach inner@{
        if (it == 3) return@inner // return inner lambda
        print(it)
    }
}

fun compute3() {
    listOf(1, 2, 3).forEach {
        if (it == 3) return@forEach // return inner lambda
        print(it)
    }
}

fun main() {
    sayHello()
    sayHello("Hi")
    sayHello(who = "John")
    println(asText(2))
    println(20 mod 3)
}
