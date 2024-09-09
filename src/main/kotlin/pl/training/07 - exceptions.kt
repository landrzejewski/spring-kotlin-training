package pl.training

/*
    An exception is an unwanted event that interrupts the standard flow of a program.
    Exceptions are represented by objects inheriting from Throwable family types.
    Exceptions can be thrown using `throw` keyword and catch using a try-catch structure.
    Important subtypes of Throwable are Error and Exception.
    Error type represents exceptions that are impossible to recover from and should not be caught.
    Exception type represents exceptions we can recover from using a try-catch block.
 */

class DivisionByZeroException : Exception("Division by zero")

fun divide(value: Double, by: Double): Double {
    if (by == 0.0) {
        throw DivisionByZeroException()
    }
    return value / by
}

fun main() {
    val result = try {
        divide(10.0, 0.0)
    } catch (_: DivisionByZeroException) {
        0.0
    } catch (_: Throwable) {
        0.0
    } finally {
        println("Finally")
    }
    println("Result: $result")
}