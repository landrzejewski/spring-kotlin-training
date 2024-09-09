package pl.training

fun main() {
    // `if` expression

    var number = 5
    if (number % 2 == 0) {
        println("$number is even")
    }

    val letter: Char = if (5 < 3) 'a' else 'b'

    /*
        `when` expression
        If we use a when-condition as an expression, its conditions must be exhaustive: it should cover
        all possible branch conditions or provide an else branch.

    */

    val value: Any? = null
    when (value) {
        1 -> println("One")
        2 -> println("Two")
        3, 4, 5 -> println("Three, four or five")
        in 6..10 -> println("between 6 and 10")
        // value > 0 -> println("between 6 and 10")
        // is String ->  println("Is text")
        // test() -> println("Is null")
        null -> println("Is null")
        else -> {
            println("Other")
        }
    }

    val probability = 70
    val result = when {
        probability < 40 -> "Na-ha"
        probability <= 80 -> "Likely"
        probability < 100 -> "Yes"
        else -> "Who knows"
    }

    /*
        Inside the `when` parentheses, we can also define a variable, and its value will be accessible in each condition
    */

    when (val response = 5) {
        is Number -> println(response)
        is String -> println(response.length)
    }

    // Loops

    val numbers = arrayOf(1, 2, 3, 5)

    for (currentNumber in numbers) {
        println("Current number: $currentNumber")
    }

    for (index in numbers.indices) {
        println("Current index: $index")
    }

    for ((index, currentNumber) in numbers.withIndex()) {
        println("$index: Current number: $currentNumber")
    }

    for (currentNumber in 1..10) {
        println("Current number: $currentNumber")
    }

    for (currentNumber in 1..< 10) {
        println("Current number: $currentNumber")
    }

    for (currentNumber in 1 until 10) {
        println("Current number: $currentNumber")
    }

    for (currentNumber in 10 downTo 0 step 2) {
        println("Current number: $currentNumber")
    }

    while (number > 0) {
        println("Current number: $number")
        number--
    }

    do {
        println("Current number: $number")
        number--
    } while (number > 0)

    /*
        Controlling the loop execution:
            break - terminates the nearest enclosing loop
            continue - proceeds to the next step of the nearest enclosing loop

        For nested loops, one can use labels to apply break or continue on the selected level.
    */

    firstLoop@ while (number > 0) {
        println("Current number: $number")
        number--
        for (currentNumber in numbers) {
            println("Current number: $currentNumber")
            if (currentNumber == 2) {
                break@firstLoop // breaks external loop
            }
        }
    }

}