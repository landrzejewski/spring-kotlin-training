package pl.training.coroutinesimport kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch


/*
    A flow represents a lazy process that emits values. The Flow interface itself only
    allows the flowing elements to be collected, which means handling each element
    as it reaches the end of the flow (collect for Flow is like forEach for collections).

    Flow should be used for streams of data that need to use coroutines.

    The simplest way to create a flow is by using the flowOf function, where we just
    define what values this flow should have(similar to the listOf function for a list).
*/

/*
suspend fun getUserName(): String {
    delay(1000)
    return "UserName"
}

suspend fun main() {
    flowOf(1, 2, 3, 4, 5)
        .collect { print(it) } // 12345

    emptyFlow<Int>()
        .collect { print(it) } // (nothing)

    listOf(1, 2, 3, 4, 5)
    // or setOf(1, 2, 3, 4, 5)
    // or sequenceOf(1, 2, 3, 4, 5)
        .asFlow()
        .collect { print(it) } // 12345

    val function = suspend {
        // this is suspending lambda expression
        delay(1000)
        "UserName"
    }
    function.asFlow()
        .collect { println(it) }

    ::getUserName.asFlow()
        .collect { println(it) }
}
*/

/*
    For reactive ext

    suspend fun main() = coroutineScope {
        Flux.range(1, 5).asFlow()
            .collect { print(it) } // 12345
        Flowable.range(1, 5).asFlow()
            .collect { print(it) } // 12345
        Observable.range(1, 5).asFlow()
            .collect { print(it) } // 12345
    }

    To convert the other way around, you need more specific libraries.
    With kotlinx-coroutines-reactor, you can convert Flow to Flux. With
    kotlinx-coroutines-rx3 (or kotlinx-coroutines-rx2), you can convert Flow to Flowable or Observable.

    suspend fun main(): Unit = coroutineScope {
        val flow = flowOf(1, 2, 3, 4, 5)
        flow.asFlux()
            .doOnNext { print(it) } // 12345
            .subscribe()
        flow.asFlowable()
            .subscribe { print(it) } // 12345
        flow.asObservable()
            .subscribe { print(it) } // 12345
}

*/

// Flow builders

/*
fun makeFlow(): Flow<Int> = flow {
    repeat(3) { num ->
        delay(1000)
        emit(num)
    }
}
suspend fun main() {
    makeFlow()
        .collect { println(it) }
}
*/

/*
    Flow is a cold data stream, so it produces values on demand when they are needed.
*/

/*
    To react to each flowing value, we use the onEach function.
    The onEach lambda expression is suspending, and elements are processed one
    after another in order (sequentially). So, if we add delay in onEach, we will delay each value as it flows.
*/

/*
suspend fun main() {
    flowOf(1, 2, 3, 4)
        .onEach { print(it) }
        .collect() // 1234
}
*/

/*
suspend fun main() {
    flowOf(1, 2)
        .onEach { delay(1000) }
        .collect { println(it) }
*/

/*
    The onStart function sets a listener that should be called immediately once the flow is started.
    onStart does not wait for the first element: it is called when we request the first element.

    It is good to know that in onStart (as well as in onCompletion, onEmpty and catch)
    we can emit elements. Such elements will flow downstream from this place.
*/

/*
suspend fun main() {
    flowOf(1, 2)
        .onEach { delay(1000) }
        .onStart { println("Before") }
        .collect { println(it) }
}
*/

/*
suspend fun main() {
    flowOf(1, 2)
        .onEach { delay(1000) }
        .onStart { emit(0) }
        .collect { println(it) }
}
*/

/*
    There are a few ways in which a flow can be completed. The most common one
    is when the flow builder is done (i.e., the last element has been sent), although
    this also happens in the case of an uncaught exception or a coroutine cancellation. In all these cases, we can add a listener for flow completion by using the
    onCompletion method.
*/

/*
suspend fun main() = coroutineScope {
    flowOf(1, 2)
        .onEach { delay(1000) }
        .onCompletion { println("Completed") }
        .collect { println(it) }
}

suspend fun main() = coroutineScope {
    val job = launch {
        flowOf(1, 2)
            .onEach { delay(1000) }
            .onCompletion { println("Completed") }
            .collect { println(it) }
    }
    delay(1100)
    job.cancel()
}
*/

/*
    A flow might complete without emitting any value, which might be an indication
    of an unexpected event. For such cases, there is the onEmpty function, which in-
    vokes the given action when this flow completes without emitting any elements.
    onEmpty might then be used to emit some default value.
*/

/*
suspend fun main() = coroutineScope {
    flow<List<Int>> { delay(1000) }
        .onEmpty { emit(emptyList()) }
        .collect { println(it) }
}
*/

/*
    At any point of flow building or processing, an exception might occur. Such an
    exception will flow down, closing each processing step on the way; however, it
    can be caught and managed. To do so, we can use the catch method. This listener
    receives the exception as an argument and allows you to perform recovering operations.

    The catch will only react to the exceptions thrown in the function defined
    upstream (you can imagine that the exception needs to be caught as it flows down).
*/

/*
class MyError : Throwable("My error")
val flow = flow {
    emit(1)
    emit(2)
    throw MyError()
}
suspend fun main(): Unit {
    flow.onEach { println("Got $it") }
        .catch { println("Caught $it") }
        .collect { println("Collected $it") }
}
*/

/*
    Uncaught exceptions in a flow immediately cancel this flow, and collect
    rethrows this exception. This behavior is typical of suspending functions, and
    coroutineScope behaves the same way. Exceptions can be caught outside flow
    using the classic try-catch block.

    Notice that using catch does not protect us from an exception in the terminal
    operation (because catch cannot be placed after the last operation). So, if there
    is an exception in the collect, it won’t be caught, and an error will be thrown.

    Therefore, it is common practice to move the operation from collect to onEach
    and place it before the catch. This is specifically useful if we suspect that collect
    might raise an exception. If we move the operation from collect, we can be sure
    that catch will catch all exceptions.
*/

/*
val flow = flow {
    emit("Message1")
    throw MyError()
}
suspend fun main(): Unit {
    try {
        flow.collect { println("Collected $it") }
    } catch (e: MyError) {
        println("Caught")
    }
}
// Collected Message1
// Caught
*/

/*
    An exception flows through a flow, closing each step one by one. These steps
    become inactive, so it is not possible to send messages after an exception, but each
    step gives you a reference to the previous ones, and this reference can be used
    to start this flow again. Based on this idea, Kotlin offers the retry and retryWhen
    functions. Here is a simplified implementation of retryWhen:
 */

/*
suspend fun main() {
    flow {
        emit(1)
        emit(2)
        error("E")
        emit(3)
    }.retry(3) {
        print(it.message)
        true
    }.collect { print(it) } // 12E12E12E12(exception thrown)
}
*/

/*
    Lambda expressions used as arguments for flow operations (like onEach, onStart,
    onCompletion, etc.) and its builders (like flow or channelFlow) are all suspending
    in nature. Suspending functions need to have a context and should be in relation
    to their parent (for structured concurrency). So, you might be wondering where
    these functions take their context from. The answer is: from the context where
    collect is called.
*/

/*
fun usersFlow(): Flow<String> = flow {
    repeat(2) {
        val ctx = currentCoroutineContext()
        val name = ctx[CoroutineName]?.name
        emit("User$it in $name")
    }
}
suspend fun main() {
    val users = usersFlow()
    withContext(CoroutineName("Name1")) {
        users.collect { println(it) }
    }
    withContext(CoroutineName("Name2")) {
        users.collect { println(it) }
    }
}
*/

/*
    How does this code work? The terminal operation call requests elements from
    upstream, thereby propagating the coroutine context. However, it can also be
    modified by the flowOn function.
*/

// Flow processing

/*
suspend fun main() {
    flowOf(1, 2, 3) // [1, 2, 3]
        .map { it * it } // [1, 4, 9]
        .collect { print(it) } // 149
}
*/

/*
suspend fun main() {
    (1..10).asFlow() // [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]
        .filter { it <= 5 } // [1, 2, 3, 4, 5]
        .filter { isEven(it) } // [2, 4]
        .collect { print(it) } // 24
}
fun isEven(num: Int): Boolean = num % 2 == 0
*/

/*
suspend fun main() {
    ('A'..'Z').asFlow()
        .take(5) // [A, B, C, D, E]
        .collect { print(it) } // ABCDE
}
*/

/*
suspend fun main() {
    ('A'..'Z').asFlow()
        .drop(20) // [U, V, W, X, Y, Z]
        .collect { print(it) } // UVWXYZ
}
*/

/*
suspend fun main() {
    val ints: Flow<Int> = flowOf(1, 2, 3)
    val doubles: Flow<Double> = flowOf(0.1, 0.2, 0.3)
    val together: Flow<Number> = merge(ints, doubles)
    print(together.toList())
// [1, 0.1, 0.2, 0.3, 2, 3]
// or [1, 0.1, 0.2, 0.3, 2, 3]
// or [0.1, 1, 2, 3, 0.2, 0.3]
// or any other combination
}
*/

/*
suspend fun main() {
    val ints: Flow<Int> = flowOf(1, 2, 3)
        .onEach { delay(1000) }
    val doubles: Flow<Double> = flowOf(0.1, 0.2, 0.3)
    val together: Flow<Number> = merge(ints, doubles)
    together.collect { println(it) }
}
*/

/*
suspend fun main() {
    val flow1 = flowOf("A", "B", "C")
        .onEach { delay(400) }
    val flow2 = flowOf(1, 2, 3, 4)
        .onEach { delay(1000) }
    flow1.zip(flow2) { f1, f2 -> "${f1}_${f2}" }
        .collect { println(it) }
}
*/

/*
suspend fun main() {
    val flow1 = flowOf("A", "B", "C")
        .onEach { delay(400) }
    val flow2 = flowOf(1, 2, 3, 4)
        .onEach { delay(1000) }
    flow1.combine(flow2) { f1, f2 -> "${f1}_${f2}" }
        .collect { println(it) }
}
*/

/*
fun main() {
    val list = listOf(1, 2, 3, 4)
    val res = list.fold(0) { acc, i -> acc + i }
    println(res) // 10
    val res2 = list.fold(1) { acc, i -> acc * i }
    println(res2) // 24
}
*/

/*
suspend fun main() {
    val list = flowOf(1, 2, 3, 4)
        .onEach { delay(1000) }
    val res = list.fold(0) { acc, i -> acc + i }
    println(res)
}
*/

/*
fun main() {
    val list = listOf(1, 2, 3, 4)
    val res = list.scan(0) { acc, i -> acc + i }
    println(res) // [0, 1, 3, 6, 10]
}
*/

/*
fun flowFrom(elem: String) = flowOf(1, 2, 3)
    .onEach { delay(1000) }
    .map { "${it}_${elem} " }
suspend fun main() {
    flowOf("A", "B", "C")
        .flatMapConcat { flowFrom(it) }
        .collect { println(it) }
}
*/

/*
fun flowFrom(elem: String) = flowOf(1, 2, 3)
    .onEach { delay(1000) }
    .map { "${it}_${elem} " }
suspend fun main() {
    flowOf("A", "B", "C")
        .flatMapMerge { flowFrom(it) }
        .collect { println(it) }

 */

/*
suspend fun main() {
    flowOf("A", "B", "C")
        .flatMapMerge(concurrency = 2) { flowFrom(it) }
        .collect { println(it) }
}
*/

/*
fun flowFrom(elem: String) = flowOf(1, 2, 3)
    .onEach { delay(1000) }
    .map { "${it}_${elem} " }
suspend fun main() {
    flowOf("A", "B", "C")
        .flatMapLatest { flowFrom(it) }
        .collect { println(it) }
}
*/

/*
suspend fun main() {
    flowOf("A", "B", "C")
        .onEach { delay(1200) }
        .flatMapLatest { flowFrom(it) }
        .collect { println(it) }
}
*/

/*
suspend fun main() {
    flowOf(1, 2, 2, 3, 2, 1, 1, 3)
        .distinctUntilChanged()
        .collect { print(it) } // 123213
}
*/

// terminal operations

/*
suspend fun main() {
    val flow = flowOf(1, 2, 3, 4) // [1, 2, 3, 4]
        .map { it * it } // [1, 4, 9, 16]
    println(flow.first()) // 1
    println(flow.count()) // 4
    println(flow.reduce { acc, value -> acc * value }) // 576
    println(flow.fold(0) { acc, value -> acc + value }) // 30
}
*/

/*
    Flow is typically cold, so its values are calculated on demand. However, there
    are cases in which we want multiple receivers to be subscribed to one source of
    changes. This is where we use SharedFlow, which is conceptually similar to a
    mailing list.We also have StateFlow, which is similar to an observable value. Let’s
    explain them both step by step.

    Let’s start with MutableSharedFlow, which is like a broadcast channel: everyone
    can send (emit) messages which will be received by every coroutine that is
    listening (collecting).
*/

/*
suspend fun main(): Unit = coroutineScope {
    val mutableSharedFlow =
        MutableSharedFlow<String>(replay = 0)
// or MutableSharedFlow<String>()
    launch {
        mutableSharedFlow.collect {
            println("#1 received $it")
        }
    }
    launch {
        mutableSharedFlow.collect {
            println("#2 received $it")
        }
    }
    delay(1000)
    mutableSharedFlow.emit("Message1")
    mutableSharedFlow.emit("Message2")
}
*/

/*
    The above program never ends because the coroutineScope is waiting
    for the coroutines that were started with launch and which keep
    listening on MutableSharedFlow. Apparently, MutableSharedFlow is
    not closable, so the only way to fix this problem is to cancel the whole scope.

    MutableSharedFlow can also keep sending messages. If we set the replay parameter (it defaults to 0),
    the defined number of last values will be kept. If a coroutine
    now starts observing, it will receive these values first. This cache can also be reset with resetReplayCache.
*/

/*
suspend fun main(): Unit = coroutineScope {
    val mutableSharedFlow = MutableSharedFlow<String>(
        replay = 2,
    )
    mutableSharedFlow.emit("Message1")
    mutableSharedFlow.emit("Message2")
    mutableSharedFlow.emit("Message3")
    println(mutableSharedFlow.replayCache)
// [Message2, Message3]
    launch {
        mutableSharedFlow.collect {
            println("#1 received $it")
        }
// #1 received Message2
// #1 received Message3
    }
    delay(100)
    mutableSharedFlow.resetReplayCache()
    println(mutableSharedFlow.replayCache) // []
}
*/

/*
    MutableSharedFlow is conceptually similar to RxJava Subjects. When
    the replay parameter is set to 0, it is similar to a PublishSubject.
    When replay is 1, it is similar to a BehaviorSubject. When replay is
    Int.MAX_VALUE, it is similar to ReplaySubject.

    In Kotlin, we like to have a distinction between interfaces that are used to only listen and those that are used to
    modify. For instance, we’ve already seen the distinction between SendChannel, ReceiveChannel and just Channel. The same
    rule applies here. MutableSharedFlow inherits from both SharedFlow and FlowCollector.
    The former inherits fromFlow and is used to observe, while FlowCollector is used to emit values.
*/

/*
suspend fun main(): Unit = coroutineScope {
    val mutableSharedFlow = MutableSharedFlow<String>()
    val sharedFlow: SharedFlow<String> = mutableSharedFlow
    val collector: FlowCollector<String> = mutableSharedFlow
    launch {
        mutableSharedFlow.collect {
            println("#1 received $it")
        }
    }
    launch {
        sharedFlow.collect {
            println("#2 received $it")
        }
    }
    delay(1000)
    mutableSharedFlow.emit("Message1")
    collector.emit("Message2")
}
*/

/*
    Flow is often used to observe changes, like user actions, database modifications,
    or new messages. We already know the different ways in which these events can
    be processed and handled. We’ve learned how to merge multiple flows into one.
    But what if multiple classes are interested in these changes and we would like to
    turn one flow into multiple flows? The solution is SharedFlow, and the easiest way
    to turn a Flow into a SharedFlow is by using the shareIn function.
*/

/*
suspend fun main(): Unit = coroutineScope {
    val flow = flowOf("A", "B", "C")
        .onEach { delay(1000) }
    val sharedFlow: SharedFlow<String> = flow.shareIn(
        scope = this,
        started = SharingStarted.Eagerly,
// replay = 0 (default)
    )
    delay(500)
    launch {
        sharedFlow.collect { println("#1 $it") }
    }
    delay(1000)
    launch {
        sharedFlow.collect { println("#2 $it") }
    }
    delay(1000)
    launch {
        sharedFlow.collect { println("#3 $it") }
    }
}
*/

/*
    The shareIn function creates a SharedFlow and sends elements from its Flow.
    Since we need to start a coroutine to collect elements on flow, shareIn expects a
    coroutine scope as the first argument. The third argument is replay, which is 0 by
    default. The second argument is interesting: started determines when listening
    for values should start, depending on the number of listeners. The following
    options are supported:

    SharingStarted.Eagerly - immediately starts listening for values and sending them to a flow.
    Notice that if you have a limited replay value and your
    values appear before you start subscribing, you might lose some values (if your replay is 0, you will lose all such values).
*/

/*
suspend fun main(): Unit = coroutineScope {
    val flow = flowOf("A", "B", "C")
    val sharedFlow: SharedFlow<String> = flow.shareIn(
        scope = this,
        started = SharingStarted.Eagerly,
    )
    delay(100)
    launch {
        sharedFlow.collect { println("#1 $it") }
    }
    print("Done")
}
*/

/*
    SharingStarted.Lazily - starts listening when the first subscriber appears.
    This guarantees that this first subscriber gets all the emitted values, while
    subsequent subscribers are only guaranteed to get the most recent replay
    values. The upstream flow continues to be active even when all subscribers
    disappear, but only the most recent replay values are cached without subscribers.
*/

/*
suspend fun main(): Unit = coroutineScope {
    val flow1 = flowOf("A", "B", "C")
    val flow2 = flowOf("D")
        .onEach { delay(1000) }
    val sharedFlow = merge(flow1, flow2).shareIn(
        scope = this,
        started = SharingStarted.Lazily,
    )
    delay(100)
    launch {
        sharedFlow.collect { println("#1 $it") }
    }
    delay(1000)
    launch {
        sharedFlow.collect { println("#2 $it") }
    }
}
*/

/*
    WhileSubscribed() - starts listening on the flow when the first
    subscriber appears; it stops when the last subscriber disappears. If a
    new subscriber appears when our SharedFlow is stopped, it will start
    again. WhileSubscribed has additional optional configuration parameters:
    stopTimeoutMillis (how long to listen after the last subscriber disappears,
    0 by default) and replayExpirationMillis (how long to keep replay after
    stopping, Long.MAX_VALUE by default).
*/

/*
suspend fun main(): Unit = coroutineScope {
    val flow = flowOf("A", "B", "C", "D")
        .onStart { println("Started") }
        .onCompletion { println("Finished") }
        .onEach { delay(1000) }
    val sharedFlow = flow.shareIn(
        scope = this,
        started = SharingStarted.WhileSubscribed(),
    )
    delay(3000)
    launch {
        println("#1 ${sharedFlow.first()}")
    }
    launch {
        println("#2 ${sharedFlow.take(2).toList()}")
    }
    delay(3000)
    launch {
        println("#3 ${sharedFlow.first()}")
    }
}
*/

/*
    StateFlow is an extension of the SharedFlow concept. It works similarly to SharedFlow when the replay parameter
    is set to 1. It always stores one value, which can be accessed using the value property.
*/

/*
suspend fun main() = coroutineScope {
    val state = MutableStateFlow("A")
    println(state.value) // A
    launch {
        state.collect { println("Value changed to $it") }
// Value changed to A
    }
    delay(1000)
    state.value = "B" // Value changed to B
    delay(1000)
    launch {
        state.collect { println("and now it is $it") }
// and now it is B
    }
    delay(1000)
    state.value = "C" // Value changed to C and now it is C
}
*/

/*
    stateIn is a function that transforms Flow<T> into StateFlow<T>. It can only be
    called with a scope, but it is a suspending function. Remember that StateFlow
    needs to always have a value; so, if you don’t specify it, then you need to wait until
    the first value is calculated.
*/

/*
suspend fun main() = coroutineScope {
    val flow = flowOf("A", "B", "C")
        .onEach { delay(1000) }
        .onEach { println("Produced $it") }
    val stateFlow: StateFlow<String> = flow.stateIn(this)
    println("Listening")
    println(stateFlow.value)
    stateFlow.collect { println("Received $it") }
}
*/

/*
    The second variant of stateIn is not suspending but it requires an initial value
    and a started mode. This mode has the same options as shareIn (as previously explained).
 */

/*
suspend fun main() = coroutineScope {
    val flow = flowOf("A", "B")
        .onEach { delay(1000) }
        .onEach { println("Produced $it") }
    val stateFlow: StateFlow<String> = flow.stateIn(
        scope = this,
        started = SharingStarted.Lazily,
        initialValue = "Empty"
    )
    println(stateFlow.value)
    delay(2000)
    stateFlow.collect { println("Received $it") }
}
*/