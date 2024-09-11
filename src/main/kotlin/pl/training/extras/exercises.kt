package pl.training.extras

// Write a function that counts the number of occurrences of a given letter in a word/sentence

fun letters() {
    val letter = 'a'
    val text = "Ala ma kota"

    // var result = 0

    /*for (char in text) {
        if (char == letter) {
            result++
        }
    }*/

    // text.forEach { if (it == letter) result++ }

    // val result = text.filter { it == 'a' }.length

    val result = text.asSequence()
        .filter { it == letter }
        .count()

    // val result = text.count { it == letter }

    println("Number of occurrences of letter $letter in \"$text\" is equal $result")
}

// Write a function that verifies whether a given year is leap year
// https://docs.microsoft.com/en-us/office/troubleshoot/excel/determine-a-leap-year

fun isLeap(year: Int) = when (year % 4) {
    0 -> if (year % 100 == 0) year % 400 == 0 else true
    else -> false
}

// Write a function that checks whether a character is a letter (uppercase or lowercase ASCII) or a number using range

fun isAlphaNumeric(char: Char) = char in 'a'..'z' || char in 'A'..'Z' || char in '0'..'9'

// Write a function that calculates the value of the power for a given integer using loops

fun factorial(value: Long): Long {
    var factorial = 1L
    for (number in 1..value) {
        factorial *= number
    }
    return factorial
}
