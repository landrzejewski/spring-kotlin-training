import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

/*
    The Kotlin Coroutines library offers an important functionality that lets us decide which thread (or pool of threads)
    a coroutine should be running on. This is done using dispatchers.

    If you don’t set any dispatcher, the one chosen by default by asynchronous coroutine builders is Dispatchers.Default,
    which is designed to run CPU-intensive operations. It has a pool of threads whose size is equal to the number of cores in
    the machine your code is running on (but not less than two).

    Warning: runBlocking sets its own dispatcher if no other one is set; so, inside its scope the Dispatcher.Default is
    not the one that is chosen automatically. If we used runBlocking instead of coroutineScope in
    the above example, all coroutines would be running on “main”.
 */

suspend fun main() = coroutineScope {
    repeat(1000) {
        launch { // or launch(Dispatchers.Default) {
            val threadName = Thread.currentThread().name
            println("Running on thread: $threadName")
        }
    }
}

/*
    Let’s say that you have an expensive process and you suspect that it might use all Dispatchers.Default threads and
    starve other coroutines using the same dispatcher. In such cases, we can use limitedParallelism onDispatchers.
    Default to make a dispatcher that runs on the same threads but is limited to using not more than a certain number of
    them at the same time.

    private val dispatcher = Dispatchers.Default
        .limitedParallelism(5)
 */

/*
    To run a coroutine on the Main thread, we use Dispatchers.Main.

    Dispatchers.IO is designed to be used when we block threads with I/O operations, such as when we read/write files
    or call blocking functions. Dispatchers.IO is only needed if you have an API that blocks threads.
    If you use suspending functions, you can use any dispatcher. You do not need to use Dispatchers.IO if you want to
    use a network or database library that provides suspending functions. In many projects, this means you might not
    need to use Dispatchers.IO at all. The limit of threads Dispatchers.IO is 64.

    The most typical case in which we use Dispatchers.IO is when we need to call blocking functions from libraries.
    Dispatchers.IO has a special behavior defined for the limitedParallelism function that creates a new dispatcher
    with an independent thread limit.

    Dispatcher with a fixed pool of threads:

    private val NUMBER_OF_THREADS = 20
    val dispatcher = Executors
        .newFixedThreadPool(NUMBER_OF_THREADS)
        .asCoroutineDispatcher()

    that a dispatcher created with needs to be closed at the end of the usage.

    We can also create executor using new virtual threads from Java.

    val LoomDispatcher = Executors
        .newVirtualThreadPerTaskExecutor()
        .asCoroutineDispatcher()

    To use this dispatcher similarly to other dispatchers, we can define an extension property on the Dispatchers object.
    This should also help this dispatcher’s discoverability.

    val Dispatchers.Loom: CoroutineDispatcher
        get() = LoomDispatcher

    The last dispatcher we need to discuss is Dispatchers.Unconfined, which is different from all the other dispatchers
    as it does not change any threads. When it is started, it runs on the thread on which it was started. If it is
    resumed, it runs on the thread that resumed it. In older projects, you can find Dispatchers.Unconfined used in unit tests
    From the performance point of view, this dispatcher is cheapest as it never requires thread switching,
    therefore we might choose it if we do not care at all which thread our code runs on.

    There is a cost associated with dispatching a coroutine. When withContext is called, the coroutine needs to be
    suspended, possibly wait in a queue, and then be resumed. This is a small but unnecessary cost if we are already on this thread.

    suspend fun showUser(user: User) = withContext(Dispatchers.Main) {
        userNameElement.text = user.name
        // ...
    }

    If this function had already been called on the main dispatcher, we would have an unnecessary cost of redispatching.
    What is more, if there were a long queue for theMain thread because of withContext, the user data might be shown after some
    delay (this coroutine would need to wait for other coroutines to do their job first).
    To prevent this, there is Dispatchers.Main.immediate, which dispatches only if it is needed. So, if the function
    below is called on the Main thread, it won’t be redispatched: it will be called immediately.

    suspend fun showUser(user: User) = withContext(Dispatchers.Main.immediate) {
        userNameElement.text = user.name
        // ...
    }

    We prefer Dispatchers.Main.immediate as the withContext argument whenever this function might have already been
    called from the main dispatcher. Currently, the other dispatchers do not support immediate dispatching.

    Dispatchers determine which thread or thread pool a coroutine will run (starting and resuming) on.
    The most important options are:
        Dispatchers.Default, which we use for CPU-intensive operations;
        Dispatchers.Main, which we use to access the Main thread on Android, Swing, or JavaFX;
        Dispatchers.Main.immediate, which runs on the same thread as Dispatchers.Main but is not redispatched if it is not necessary;
        Dispatchers.IO, which we use when we need to do some blocking operations;
        Dispatchers.IO with limited parallelism or a custom dispatcher with a pool of threads, which we use when we might have many blocking calls;
        Dispatchers.Default or Dispatchers.IO with parallelism limited to 1, or a custom dispatcher with a single thread, which is used when we need to
            secure shared state modifications;
        Dispatchers.Unconfined, which does not change threads and is used in some special cases;
 */
