package pl.training.coroutinesimport kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.supervisorScope

/*
    Suspending functions and synchronous coroutines, including coroutine scope functions and runBlocking,
    throw exceptions that end their body.

    Asynchronous coroutine builders (async and launch) propagate exceptions that end their body to their parents
    via the scope. An exception received this way is treated as if it occurred in the parent coroutine.

    An important consequence of this mechanism is that exceptions propagate from
    child to parent. Yes, if you have a process that starts a number of coroutines and
    one of them throws an exception, this will automatically lead to the cancellation
    of all the other coroutines.
 */

/*
fun main(): Unit = runBlocking {
    launch {
        launch {
            delay(1000)
            throw Error("Some error")
        }
        launch {
            delay(2000)
            println("Will not be printed")
        }
        launch {
            delay(500) // faster than the exception
            println("Will be printed")
        }
    }
    launch {
        delay(2000)
        println("Will not be printed")
    }
}
*/

/*
    SupervisorJob is a special kind of job that ignores all exceptions in its children.
    SupervisorJob is generally used as part of a scope in which we start multiple
    coroutines (more about this in the Constructing coroutine scope chapter). Thanks
    to this, an exception in one coroutine will not cancel this scope and all its children.
*/

/*
fun main(): Unit = runBlocking {
    val scope = CoroutineScope(SupervisorJob())
    scope.launch {
        delay(1000)
        throw Error("Some error")
    }
    scope.launch {
        delay(2000)
        println("Will be printed")
    }
    delay(3000)
    println(scope.isActive)
}
*/

/*
    The only simple way to start a coroutine with a SupervisorJob is to use
    supervisorScope. It is a coroutine scope function, so it behaves just like
    coroutineScope, but it uses a SupervisorJob instead of a regular Job. This
    way, exceptions from its children are ignored (they only print stacktrace).

    Beware that supervisorScope only ignores exceptions from its children. If an
    exception occurs in supervisorScope itself, it breaks this coroutine builder and
    the exception propagates to its parent. If an exception occurs in a child of a child,
    it propagates to the parent of this child, destroys it, and only then gets ignored.

    supervisorScope is often used when we need to start multiple independent processes and we don’t want an
    exception in one of them to cancel the others.

    supervisorScope does not support changing context. If you need to both change
    context and use a SupervisorJob, you need to wrap supervisorScope with withContext.
*/

/*
fun main(): Unit = runBlocking {
    supervisorScope {
        launch {
            delay(1000)
            throw Error("Some error")
        }
        launch {
            delay(2000)
            println("Will be printed")
        }
        launch {
            delay(2000)
            println("Will be printed")
        }
    }
    println("Done")
}
*/

/*
    When we call await on Deferred, it should return the result of this coroutine
    if the coroutine finished successfully, or it should throw an exception if the
    coroutine ended with an exception (i.e., CancellationException if the coroutine
    was cancelled). This is why if we want to silence exceptions from async, it is not
    enough to use supervisorScope: we also need to catch the exception when calling await.
*/

/*
class MyException : Throwable()

suspend fun main() = supervisorScope {
    val str1 = async<String> {
        delay(1000)
        throw MyException()
    }
    val str2 = async {
        delay(2000)
        "Text2"
    }
    try {
        println(str1.await())
    } catch (e: MyException) {
        println(e)
    }
    println(str2.await())
}
*/

/*
    When dealing with exceptions, sometimes it is useful to define default behavior
    for all exceptions. This is where the CoroutineExceptionHandler context comes
    in handy. It does not stop the exception propagating, but it can be used to define
    what should happen in the case of an exception
*/

/*
fun main(): Unit = runBlocking {
    val handler =
        CoroutineExceptionHandler { ctx, exception ->
            println("Caught $exception")
        }
    val scope = CoroutineScope(SupervisorJob() + handler)
    scope.launch {
        delay(1000)
        throw Error("Some error")
    }
    scope.launch {
        delay(2000)
        println("Will be printed")
    }
    delay(3000)
}
*/

/*
    Exceptions propagate from child to parent. Suspending functions (including coroutine scope functions) throw exceptions,
    and asynchronous coroutine builders propagate exceptions to their parents via the scope.

    To stop exception propagation, you can catch exceptions from suspending functions before they reach coroutine
    builders, or catch exceptions from scope functions.

    SupervisorJob is a special kind of job that ignores all exceptions in its children. It is used to prevent exceptions
    from canceling all the children of a scope.


    When calling await on Deferred, it should return the value if the coroutine finished successfully, or it should throw an exception if the coroutine ended
    with an exception.

    CoroutineExceptionHandler is a context that can be used to define default behavior for all exceptions in a coroutine.
*/