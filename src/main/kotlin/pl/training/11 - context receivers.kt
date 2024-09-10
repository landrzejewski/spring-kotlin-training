@file:Suppress("CONTEXT_RECEIVERS_DEPRECATED")

package pl.training

data class Contact(val id: Long, val firstName: String, val lastName: String)

val CONTACTS = listOf(
    Contact(1, "John", "Smith"),
    Contact(2, "Mary", "Johnson")
)

/*
    Contact type is the receiver of the toJson function. The receiver is the object on which the extension function
    is invoked, which is available in the function body as this.
*/

/*
fun Contact.toJson(): String =
    """
        {
            "id": ${id},
            "firstName": "${firstName}",
            "lastName": "${lastName}",
        }
    """.trimIndent()

fun toJson(objs: List<Contact>) =
    objs.joinToString(separator = ", ", prefix = "[", postfix = "]") {
        it.toJson()
    }

fun main() {
    println(toJson(CONTACTS))
}
*/

/*
    Now we want to be able to serialize any list of objects as JSON. So we decide to make the toJson function generic
    but toJson is not defined on T type (compilation error). We want to execute toJson function only in a scope where
    we know a toJson function is defined on the T type.
*/

/*
fun <T> toJson(objs: List<T>) =
    objs.joinToString(separator = ", ", prefix = "[", postfix = "]") {
        it.toJson() // compilation error
    }
*/

/*
    JsonScope<T> is the dispatcher receiver of the toJson function. In this way, we limit the visibility of the
    toJson function, which allows us to call it only inside the scope. We say that the toJson function is a
    context-dependent construct. We can access the dispatcher receiver in the function body as this.
    As we might guess, Kotlin represents the this reference as a union type of the dispatcher receiver and the
    receiver of the extension function.
*/

/*
interface JsonScope<T> {    // <- dispatcher receiver
    fun T.toJson(): String  // <- extension function receiver
    // 'this' type in 'toJson' function is JsonScope<T> & T
}

/*
    Then, we define the toJson function as an extension function on the JsonScope interface.
*/

fun <T> JsonScope<T>.toJson(objs: List<T>) =
    objs.joinToString(separator = ", ", prefix = "[", postfix = "]") { it.toJson() }

/*
    The next step is to define the JsonScope implementation for the Contact type. We can implement it as an
    anonymous object.
*/

val contactJsonScope = object : JsonScope<Contact> {
    override fun Contact.toJson(): String {
        return """
        {
            "id": ${id},
            "firstName": "${firstName}",
            "lastName": "${lastName}",
        }
    """.trimIndent()
    }
}

/*
    Now we can use one of the scope functions like `with`. It is working but this approach has some limitations:
        We add the toJson function as an extension to the JsonScope interface. However, the function has nothing to do with
        the JsonScope type. We placed it there because it was the only technical possible solution.
        It’s somewhat misleading: The toJson is not a method of the JsonScope type.

        Extension functions are only available on objects, which is only sometimes what we desire.
        For example, we don’t want our developers to use the toJson in the following way
        println(contactJsonScope.toJson(CONTACTS))

        We are limited to having only one receiver using extension functions with scopes.
 */
fun main() {
    with(contactJsonScope) {
        println(toJson(CONTACTS))
    }
    println(contactJsonScope.toJson(CONTACTS))
}
*/

/*
    Context receivers are a way to add a context or a scope to a function without passing this context as an argument.
    Kotlin introduced a new keyword, context, that allows us to specify the context the function needs to execute.
    The context keyword is followed by the type of the context receiver. The context receivers are available as
    the this reference inside the function body.
    All the coroutine builders, a.k.a. launch and async, are extensions of the CoroutineScope, the dispatcher
    receiver and the safe place to call the suspend functions.
*/

/*
interface JsonScope<T> {    // <- dispatcher receiver
    fun T.toJson(): String  // <- extension function receiver
    // 'this' type in 'toJson' function is JsonScope<T> & T
}

val contactJsonScope = object : JsonScope<Contact> {
    override fun Contact.toJson(): String {
        return """
        {
            "id": ${id},
            "firstName": "${firstName}",
            "lastName": "${lastName}",
        }
    """.trimIndent()
    }
}

interface Logger {
    fun info(message: String)
}

val consoleLogger = object : Logger {
    override fun info(message: String) {
        println("[INFO] $message")
    }
}

context (JsonScope<T>, Logger)
fun <T> toJson(objs: List<T>): String {
    this@Logger.info("Serializing $objs list as JSON")
    return objs.joinToString(separator = ", ", prefix = "[", postfix = "]") {
        it.toJson()
    }
}

fun main() {
    with(contactJsonScope) {  //  takes a receiver and a lambda as arguments and executes the lambda in the receiver’s scope.
        with(consoleLogger) {
            println(toJson(CONTACTS))
        }
    }
    // println(contactJsonScope.toJson(CONTACTS)) // error
}
*/

/*
   Context properties - future alternative for context receivers
   https://www.youtube.com/watch?v=ZvnXLB4Gdig
*/
