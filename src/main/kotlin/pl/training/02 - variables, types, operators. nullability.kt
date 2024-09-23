package pl.training

/*
    Read-only variables are declared using the `val` keyword. Variables whose state can change are declared using the
    `var` keyword. Kotlin is a statically typed language, and the type of a variable can be specified by the programmer
    or inferred by the compiler. In Kotlin, all values are considered objects (there are no primitive types).
    All basic types that represent numbers are a subtype of the Number type. Explicit type conversion is done using
    dedicated methods, e.g. toByte(), toShort() or toChar().

    Build-in types:

    Type	Size (bits)    Min value	Max value
    Byte	8	           -128	        127
    Short	16	           -32768	    32767
    Int	    32	           -2^31	    2^31-1    // default
    Long	64	           -2^63	    2^63-1

    Type	Size (bits)	   Min value	Max value
    UByte	8	           0	        255
    UShort	16	           0	        65535
    UInt	32	           0	        2^32-1
    ULong	64	           0	        2^64-1

    Type	Size (bits)	   Significant bits	     Exponent bits 	    Decimal digits
    Float	32	           24	                 8	                6-7
    Double	64	           53	                 11	                15-16             // default

 */

fun main() {
    val number: Int = 1
    //  number = 5 // error, variable is immutable

    var otherNumber = 20
    otherNumber = 30 // mutable variable, value can be changed

    val size: Byte = 100 // Byte
    val smallValue = 1 // Int
    val bigValue = 5000000000 // Long
    val creditCardNumber = 1234_5678_9012_3456L // Long
    val hexBytes = 0xFF_EC_DE_5E // Long
    val bytes = 0b11010010_01101001_10010100_10010010 // Long
    val b: UByte = 1u  // UByte
    val s: UShort = 1u // UShort
    val l: ULong = 1u  // ULong
    val a1 = 42u // UInt
    val a2 = 0xFFFF_FFFF_FFFFu // ULong
    val a3 = 1uL // ULong
    val result = 4.5 // Double
    val totalValue = 1.4F // Float

    /*
        Kotlin offers a standard set of arithmetic operators, i.e. +, -, *, /, %. Dividing integers always returns
        an integer (the fractional part is discarded). The type of the returned result is promoted to the
        largest of the types used. It is possible to override standard operators.

        Kotlin also supports operations that modify a mutable variables:
            +=, where a += b is the equivalent of a = a + b
            -=, where a -= b is the equivalent of a = a - b
            *=, where a *= b is the equivalent of a = a * b
            /=, where a /= b is the equivalent of a = a / b
            %=, where a %= b is the equivalent of a = a % b
            post-incrementation and pre-incrementation ++, which increment variables value by 1
            post-decrementation and pre-decrementation --, which decrement variables value by 1
    */

    val x1 = 5L / 3 // 1 Long
    val x2 = 5 / 2.toFloat() // 2.5 Float
    val x3 = 5.toDouble() / 2.toFloat() // 2.5 Double
    val x4 = 5.0 / 2.toFloat() // 2.5 Double

    /*
        Kotlin supports operations on bits using the following methods:
            and - keeps only bits that have 1 in the same binary positions in both numbers
            or - keeps only bits that have 1 in the same binary positions in one or both numbers
            xor - keeps only bits that have exactly one 1 in the same binary positions in both numbers
            shl - shifts the left value left by the right number of bits
            shr - shifts the left value right by the right number of bits, filling the leftmost bits with copies of the sign bit
            ushr - shifts the left value right by the right number of bits, filling the leftmost bits with zeros
    */

    println(0b0101 and 0b0001) // 1, that is 0b0001
    println(0b0101 or 0b0001) // 5, that is 0b0101

    /*
        Boolean represents a type that can store one of two allowed values (true  or false). When combined with the
        logical operators ||, &&, ! (or, and, negation) it allows you to create and resolve logical expressions.
        Logical expressions are expanded lazily. For safety reasons Kotlin does not support automatic conversion to Boolean.
     */

    val timeIsNotExceeded = false
    val resultIsReady = true
    val isSuccess = timeIsNotExceeded && resultIsReady

    // The Char type represents a single character, each character is represented as a Unicode number.

    val letter: Char = 'a'
    val newLine = '\n'
    val char = '\uFF00'
    println('A'.code) // 65

    /*
        In the Kotlin, text is represented by a String type, which is actually a sequence of characters.
        Any modification of the String instance creates a new object. Literals can contain special expressions,
        whose result is included in the resulting string.
     */

    val text = "Hello Kotlin"
    val firstLetter = text[0]
    val name = "Jan"
    val helloMessage = "Hello $name \n"
    println("$helloMessage length is ${helloMessage.length}")
    val otherText = """
       |Programming Kotlin
       |is fun!
       |$helloMessage\n Programmer earns a lot of ${'$'}
       """.trimMargin()
    println(otherText)

    /*
        Arrays are represented by the Array type. Each array has a length, and its elements are accessed using the []
        operator and an index varying from 0 to n - 1 (where n is the size of the array).
        There are also types representing arrays of primitive types such as IntArray or ByteArray.
    */

    val numbers = arrayOf(1, 2, 3, 4, 5)
    print(numbers.size) // 5
    val secondValue = numbers[1] // 2
    Array(5) { it + 1 }.forEach { println(it) }

    /*
        We use the `is` operator to check the type membership. If the type is confirmed, there is no
        need for explicit casting (smart casts). Due to type obfuscation, in the case of generic types, e.g. for Lists,
        it is not possible to check the exact type of an object (an exception may be when the type results
        from the context, e.g. function input arguments checked at compile time).
        Explicit casting can be performed using the `as` operator. If the cast is not possible, the operation will
        throw an exception.
    */

    val response: Any = "Some text"

    if (response is String) {
        print(response.length)
    }

    when (response) {
        is String -> print(response.length + 1)
        is Int -> print(response)
    }

    // val responseText = response as String // explicit casting
    // val responseText = response as? String

    /*
        Equality
        In Kotlin, we compare two objects for equality using the double equality sign ==. To check if two objects are
        not equal, we use the non-equality sign !=. Numbers and all objects that are comparable (i.e., they
        implement the Comparable interface) can also be compared with >, <, >=, and <=.
    */

    /*
        Nullability
        In Kotlin every property/variable needs to have an explicit value. There is no such thing as an
        implicit null value. Nullable values cannot be used directly.
    */

    class Contact {
        val name = "John"
    }

    // val contact: Contact = null // compilation error, it's impossible to assign null to regular type
     val contact: Contact? = null // declaration of nullable type (regular type name with a question mark)
    //  val contactName = contact.name // compilation error, name cannot be accessed without checking if contact is null or use of safe call
    val nameLength = contact?.name?.length // safe call, nameLength type is Int?
    val length = nameLength ?: 0 // Elvis operator, length type is Int

    // When we don’t expect a null value, and we want to throw an exception if one occurs, we can use the not-null assertion !!
    contact!!.name // throws an exception if contact is null

    if (contact != null) {
        println(contact.name.length) // smart casting
    }

    /*
        There are situations where we want to keep a property type not nullable, but we cannot specify its value during object creation.
        In such situations, lateinit variables can be used.
    */

    class View {

        lateinit var viewModel: Any

    }

    /*
      Kotlin allows to override the predefined set of operators on types.
      To implement an operator, one has to create a member function or an extension function with a specific name for the corresponding type.
    */

    class Money(val value: Double, val currency: String) {

        operator fun plus(money: Money) = Money(value + money.value, money.currency)

    }

    Money(1.0, "EUR") + Money(1.0, "EUR")

}
