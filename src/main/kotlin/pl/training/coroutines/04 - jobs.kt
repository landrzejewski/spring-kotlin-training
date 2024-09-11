package pl.training.coroutinesimport kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/*
    When a coroutine is suspended, the only thing that remains is its continuation.
    This continuation includes references to local variables, labels marking where
    each suspending function has stopped, and this coroutine context.
    However, coroutines need to keep more information than that: they also need
    to know their state, their relationships (parent and children), and more, so they
    keep this in a special context called Job.

    Job is every coroutine’s most important context. Every coroutine has its own job,
    and this is the only context not inherited from the parent. It cannot be inherited:
    every coroutine has its own state and its own relationships, so Job cannot be
    shared. Job cannot be set from the outside as every coroutine builder must create
    and control its own job.

    Job is a context that implements the Job interface.
    It is cancellable and has a lifecycle. Job also has a state, which can be used to cancel
    a coroutine, await coroutine completion, and much more.

    Every coroutine has its own job that can be accessed from its context using the Job key.
 */

/*
fun main(): Unit = runBlocking {
    print(coroutineContext[Job]?.isActive) // true
}
*/

// There is also an extension property, job, which lets us access the job more easily.

/*
// extension
val CoroutineContext.job: Job
    get() = get(Job) ?: error("Current context doesn't...")

// usage
fun main(): Unit = runBlocking {
    print(coroutineContext.job.isActive) // true
}
*/

/*
    Asynchronous coroutine builders return their jobs so they can be used elsewhere.
    This is clearly visible for launch, where Job is an explicit result type.
*/

/*
fun main(): Unit = runBlocking {
    val job: Job = launch {
        delay(1000)
        println("Test")
    }
}
*/

/*
    The type returned by the async function is Deferred<T>, which also implements the Job interface.
*/

/*
fun main(): Unit = runBlocking {
    val deferred: Deferred<String> = async {
        delay(1000)
        "Test"
    }
    val job: Job = deferred
}
*/

/*
    Job is the only coroutine context that is not inherited by a coroutine from another
    coroutine. Every coroutine creates its own Job, and the job from an argument or
    parent coroutine is used as a parent of this new job
*/

/*
fun main(): Unit = runBlocking {
    val name = CoroutineName("Some name")
    val job = Job()
    launch(name + job) {
        val childName = coroutineContext[CoroutineName]
        println(childName == name) // true
        val childJob = coroutineContext[Job]
        println(childJob == job) // false
        println(childJob == job.children.first()) // true
    }
}
*/

/*
    The parent can reference all its children, and the children can refer to the parent. This parent-child relationship
    enables the implementation of cancellation and exception handling inside a coroutine’s scope. In most cases, Job
    is passed implicitly in the scope of a coroutine builder, as in the example below. runBlocking is a parent of
    launch because launch can find its job in the scope provided by runBlocking.
*/

/*
fun main(): Unit = runBlocking {
    val job: Job = launch {
        delay(1000)
    }
    val parentJob: Job = coroutineContext.job
    println(job == parentJob) // false
    val parentChildren: Sequence<Job> = parentJob.children
    println(parentChildren.first() == job) // true
}
*/

/*
    Structured concurrency mechanisms will not work if a new Job context replaces the one from the parent.
    In the example below, runBlocking does not wait for launch because it has no relation with it. This is because
    launch uses the job from the argument as a parent.
    When a coroutine has its own (independent) job, it has nearly no relation to its parent. It inherits other contexts,
    but other consequences of the parent-child relationship do not apply. This causes us to lose structured concurrency, which
    is a problematic situation that should be avoided.
*/

/*
fun main(): Unit = runBlocking {
    launch(Job()) { // the new job replaces one from parent
        delay(1000)
        println("Will not be printed")
    }
}
*/

/*
    Every coroutine has its own state that is managed by its job. State lifecycle is essential for the basic mechanisms
    of coroutines, like cancellation and synchronization.

    In the “Active” state, a job is running. If the job was created with a coroutine builder, this is the state where
    the body of this coroutine will be executed. In this state, we can start child coroutines. Most coroutines will start
    in the “Active” state. Only those that are started lazily will start in the “New” state, so these need to be explicitly
    started (using start method) in order for them to move to the “Active” state. When a coroutine is executing its body,
    it is definitely in the “Active” state. When body execution is finished, its state changes to “Completing”, where
    this coroutine waits for its children’s completion. Once all its children have completed, the job (coroutine) changes
    its state to “Completed”, which is a terminal state. Alternatively, if a job is cancelled or fails during the
    “Active” or “Completing” state, its state will change to “Cancelling”. In this state, we have the last chance to do
    some clean-up, like closing connections or freeing resources (we will see how to do this in the next chapter).
    Once this is done, the job will move to the “Cancelled” state.
*/

/*
suspend fun main() = coroutineScope {
// Job created with a builder is active
    val job = Job()
    println(job) // JobImpl{Active}@ADD
// until we complete it with a method
    job.complete()
    println(job) // JobImpl{Completed}@ADD
// launch is initially active by default
    val activeJob = launch {
        delay(1000)
    }
    println(activeJob) // StandaloneCoroutine{Active}@ADD
// here we wait until this job is done
    activeJob.join() // (1 sec)
    println(activeJob) // StandaloneCoroutine{Completed}@ADD
// launch started lazily is in New state
    val lazyJob = launch(start = CoroutineStart.LAZY) {
        delay(1000)
    }
    println(lazyJob) // LazyStandaloneCoroutine{New}@ADD
// we need to start it, to make it active
    lazyJob.start()
    println(lazyJob)

    lazyJob.join() // (1 sec)
    println(lazyJob) //LazyStandaloneCoroutine{Completed}@ADD
}
*/

/*
    A coroutine’s job can be used to wait until it completes. To do this, we use the join method, which suspends
    until a concrete job reaches a final state (either “Cancelled” or “Completed”).
*/

/*
fun main(): Unit = runBlocking {
    val job1 = launch {
        delay(1000)
        println("Test1")
    }
    val job2 = launch {
        delay(2000)
        println("Test2")
    }
    job1.join()
    job2.join()
    println("All tests are done")
}
*/

/*
    The Job interface also exposes a children property that lets us reference all its
    children. We might as well use it to wait until all children are in a final state
*/

/*
fun main(): Unit = runBlocking {
    launch {
        delay(1000)
        println("Test1")
    }
    launch {
        delay(2000)
        println("Test2")
    }
    val children = coroutineContext[Job]
        ?.children
    val childrenNum = children?.count()
    println("Number of children: $childrenNum")
    children?.forEach { it.join() }
    println("All tests are done")
}
*/

/*
    Job is the most important context for every coroutine. It is cancellable and has a lifecycle. It also has a state,
    and it can be used to cancel coroutines, track their state, and much more.

    Every coroutine has its own job, which is the only context not inherited from the parent. A job from an
    argument or parent coroutine is used as a   parent of this new job.

    Coroutines can be in one of the following states: “New”, “Active”, “Completing”, “Completed”, “Cancelling”, and
    “Cancelled”. Regular coroutines start   in the “Active” state; when they finish their body execution, they move to
    the “Completing” state; then, once their children are completed, they move to the “Completed” state.

    You should avoid using Job() as an explicit parent of coroutines as this can lead to unexpected behavior.

    Job can be used to synchronize coroutines. We can use join to wait for a coroutine to complete, or we can use
    CompletableDeferred to wait for a value produced by another coroutine.
*/