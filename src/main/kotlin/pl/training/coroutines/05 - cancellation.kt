package pl.training.coroutinesimport kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.cancel
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import kotlinx.coroutines.yield
import kotlin.coroutines.cancellation.CancellationException

/*
    Calling cancel from a Job object changes its state to “Cancelling”. Additionally:
        All the children of this job are also cancelled.

        The job cannot be used as a parent for any new coroutines.

        At the first suspension point, a CancellationException is thrown. If this coroutine is currently suspended,
        it will be resumed immediately with CancellationException. CancellationException is ignored by the coroutine
        builder, so it is not necessary to catch it, but it is used to complete this coroutine body as soon as possible.

        Once the coroutine body is completed and all its children are completed too, it changes its state to “Cancelled”.
*/

/*
    launch starts a process that prints a number every 200ms. However, after 1100ms,
    we cancel this process, therefore the coroutine changes state to “Cancelling”, and
    a CancellationException is thrown at the first suspension point. This exception
    ends our coroutine body, so the coroutine changes state to “Cancelled”, join
    resumes, and we see “Cancelled successfully”.

    We might cancel with a different exception (by passing an exception as an argument to the cancel function) in order
    to specify the cause. This cause needs to be a subtype of CancellationException because only an exception of
    this type can be used to cancel a coroutine.
*/

/*
suspend fun main(): Unit = coroutineScope {
    val job = launch {
        repeat(1_000) { i ->
            delay(200)
            println("Printing $i")
        }
    }
    delay(1100)
    job.cancel()
    job.join()
    println("Cancelled successfully")
}
*/

/*
    The cancellation mechanism is simple but very powerful. It guarantees that if
    we have a finally block, it will be executed, and all the resources will be freed.
    It also allows us to make an action only in case of cancellation by catching
    CancellationException. It is not necessary to rethrow this exception because it
    is ignored by the coroutine builder, but it is considered good practice to do so in
    case there is some outer scope that should know about the cancellation.

    Notice that join in the example above is used to wait for the cancellation to finish
    before we can proceed. Without this, we would have a race condition, and we
    would (most likely) see “Cancelled successfully” before “Cancelled…” and “Finally”.

    Since this is a common pattern, the kotlinx.coroutines library offers a convenient extension function with a
    self-descriptive name, cancelAndJoin
*/

/*
suspend fun main(): Unit = coroutineScope {
    val job = launch {
        try {
            repeat(1_000) { i ->
                delay(200)
                println("Printing $i")
            }
        } catch (e: CancellationException) {
            println("Cancelled with $e")
            throw e
        } finally {
            println("Finally")
        }
    }
    delay(700)
    job.cancel()
    job.join()
    println("Cancelled successfully")
    delay(1000)
}
*/

/*
    public suspend fun Job.cancelAndJoin() {
        cancel()
        return join()
    }
*/

/*
    Because cancellation happens at suspension points, it won’t happen until a suspension.
    The example below presents a situation in which a coroutine cannot be cancelled
    because there is no suspension point inside it (we use Thread.sleep instead of delay). The execution needs over
    3 minutes, even though it should be cancelled after 1 second.
*/

/*
suspend fun main(): Unit = coroutineScope {
    val job = Job()
    launch(job) {
        repeat(1_000) { i ->
            Thread.sleep(200) // We might have some
// complex operations or reading files here
            println("Printing $i")
        }
    }
    delay(1000)
    job.cancelAndJoin()
    println("Cancelled successfully")
    delay(1000)
}
*/

/*
    There are a few ways to deal with such situations. The first one is to use the
    yield() function from time to time. This function suspends and immediately
    resumes a coroutine. This gives an opportunity to do whatever is needed during
    suspension (or resuming), including cancellation (or changing a thread using dispatcher).
 */

/*
suspend fun main(): Unit = coroutineScope {
    val job = Job()
    launch(job) {
        repeat(1_000) { i ->
            Thread.sleep(200)
            yield()
            println("Printing $i")
        }
    }
    delay(1100)
    job.cancelAndJoin()
    println("Cancelled successfully")
    delay(1000)
}
*/

/*
    It is good practice to use yield in suspend functions between blocks of non-suspended CPU-intensive or time-intensive operations.

    Another option is to track the state of the job. Inside a coroutine builder, this
    (the receiver) references the scope of this builder. CoroutineScope has a context
    we can reference using the coroutineContext property. Thus, we can access the
    coroutine job (using coroutineContext[Job] or coroutineContext.job) and check
    what its current state is.
*/

/*
suspend fun main(): Unit = coroutineScope {
    val job = Job()
    launch(job) {
        do {
            Thread.sleep(200)
            println("Printing")
        } while (isActive)
    }
    delay(1100)
    job.cancelAndJoin()
    println("Cancelled successfully")
}
*/

/*
    Alternatively, we might use the ensureActive() function, which throws CancellationException if Job is not active.
    The result of ensureActive() and yield() seem similar but are actually very
    different. The ensureActive() function needs to be called on a CoroutineScope
    (or CoroutineContext, or Job). All it does is throw an exception if the job is no
    longer active. ensureActive() is lighter than yield(). The yield function is a
    regular top-level suspension function that does not need any scope, so it can be
    used in regular suspending functions. Since it does suspension and resuming, it
    causes redispatching, which means that if there is a queue to the dispatcher, this
    coroutine will return the thread and wait in the queue. This is considered positive
    when our operations are demanding threads as it prevents other coroutines being
    starved. yield should be used in suspending functions that make multiple CPU intensive or blocking operations.
*/

/*
    Since CancellationException is thrown when a coroutine is cancelled, it has a
    special meaning for coroutines. That is why we should not catch it with other
    exceptions, as a rule of thumb we should always rethrow it, even if all other
    exceptions are caught.
    We can explicitly catch CancellationException if we want to do something particular in the case of cancellation,
    but we should always rethrow it to inform the outer scope about the cancellation.

    All exceptions that extend CancellationException are treated in a special way:
    they only cause cancellation of the current coroutine. CancellationException is
    an open class, so it can be extended by our own classes or objects.
*/

/*
class MyNonPropagatingException : CancellationException()

suspend fun main(): Unit = coroutineScope {
    launch { // 1
        launch { // 2
            delay(2000)
            println("Will not be printed")
        }
        delay(1000)
        throw MyNonPropagatingException() // 3
    }
    launch { // 4
        delay(2000)
        println("Will be printed")
    }
}
*/

/*
    In the above snippet, we start two coroutines with builders at 1 and 4. After 1
    second, we throw a MyNonPropagatingException exception at 3, which is a subtype
    of CancellationException. This exception is caught by launch (started at 1). This
    builder cancels itself, then it also cancels its children, namely the builder defined
    at 2. However, the exception is not propagated to the parent (because it is of
    type CancellationException), so coroutineScope and its children (the coroutine
    started at 4) are not affected. This is why the coroutine that starts at 4 prints “Will
    be printed” after 2 seconds.
*/

/*
    If you want to start a certain operation with timeout, you can use the withTimeout
    function, which behaves just like coroutineScope until the timeout is exceeded.
    Then, it cancels its children and throws TimeoutCancellationException (a subtype of CancellationException).
*/

/*
suspend fun test(): Int = withTimeout(1500) {
    delay(1000)
    println("Still thinking")
    delay(1000)
    println("Done!")
    42
}
suspend fun main(): Unit = coroutineScope {
    try {
        test()
    } catch (e: TimeoutCancellationException) {
        println("Cancelled")
    }
    delay(1000) // Extra timeout does not help,
    // `test` body was cancelled
}
*/

/*
    Beware that withTimeout throws TimeoutCancellationException, which is a subtype of CancellationException
     (the same exception that is thrown when a coroutine is cancelled). So, when this exception is thrown in a coroutine builder, it only
    cancels it and does not affect its parent.
*/

/*
suspend fun main(): Unit = coroutineScope {
    launch { // 1
        launch { // 2, cancelled by its parent
            delay(2000)
            println("Will not be printed")
        }
        withTimeout(1000) { // we cancel launch
            delay(1500)
        }
    }
    launch { // 3
        delay(2000)
        println("Done")
    }
}
*/

/*
    In the above example, delay(1500) takes longer than withTimeout(1000) expects,
    so withTimeout cancels its coroutine and throws TimeoutCancellationException.
    The exception is caught by launch at 1, and it cancels itself and its children (launch
    starts at 2). The launch that starts at 3 is not affected, so it prints “Done” after 2 seconds.

    A less aggressive variant of withTimeout is withTimeoutOrNull, which does not
    throw an exception. If the timeout is exceeded, it just cancels its body and returns null.
*/

/*
    When we cancel a coroutine, it changes its state to “Cancelling”, and cancels all its children.

    A coroutine in the “Cancelling” state does not start child coroutines and throws CancellationException when we try
    to suspend it or if it is suspended.

    It is guaranteed that the body of the finally block will be executed.

    We can invoke an operation specifically in the case of cancellation by catching CancellationException, but we should
    rethrow it to inform the outer scope about the cancellation.

    To allow cancellation between non-suspending operations, we can use yield or ensureActive.

    CancellationException does not propagate to its parent.

    We can use withTimeout or withTimeoutOrNull to start a coroutine with a timeout.
*/
