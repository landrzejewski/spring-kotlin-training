package pl.training.coroutinesimport kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.selects.select

/*
    Channel supports any number of senders and receivers, and every value that is sent to a channel is received only once.
    Channel is an interface that implements two other interfaces:
        SendChannel, which is used to send elements (adding elements) and to close the channel;
        ReceiveChannel, which receives (or takes)the elements.
    When we try to receive and there are no elements in the channel, the coroutine is suspended until the element is available.
    On the other hand, send will be suspended when the channel reaches its capacity.

    If you need to send or receive from a non-suspending function, you can use trySend and tryReceive. Both operations are immediate and
    return ChannelResult, which contains information about the success or failure of the operation, as well as its result. Use trySend and
    tryReceive only for channels with limited capacity because they will not work for the rendezvous channel.

    A channel might have any number of senders and receivers. However, the most common situation is when there is one
    coroutine on both sides of the channel.
 */

/*
suspend fun main(): Unit = coroutineScope {
    val channel = Channel<Int>()
    launch {
        repeat(5) { index ->
            delay(1000)
            println("Producing next one")
            channel.send(index * 2)
        }
    }
    launch {
        repeat(5) {
            val received = channel.receive()
            println(received)
        }
    }
}
*/

/*
    Such an implementation is far from perfect. First, the receiver needs to know how
    many elements will be sent; however, this is rarely the case, so we would prefer
    to listen for as long as the sender is willing to send. To receive elements on the
    channel until it is closed, we could use a for-loop or consumeEach function
*/

/*
suspend fun main(): Unit = coroutineScope {
    val channel = Channel<Int>()
    launch {
        repeat(5) { index ->
            println("Producing next one")
            delay(1000)
            channel.send(index * 2)
        }
        channel.close()
    }
    launch {
        for (element in channel) {
            println(element)
        }
// or
// channel.consumeEach { element ->
// println(element)
// }
    }
}
*/

/*
    The common problem with this way of sending elements is that it is easy to forget
    to close the channel, especially in the case of exceptions. If one coroutine stops
    producing because of an exception, the other will wait for elements forever. It is
    much more convenient to use the produce function, which is a coroutine builder
    that returns ReceiveChannel.
*/

/*
    The produce function closes the channel whenever the builder coroutine ends in
    any way (finished, stopped, cancelled). Thanks to this, we will never forget to call
    close. The produce builder is a very popular way to create a channel, and for good
    reason: it offers a lot of safety and convenience.
*/

/*
suspend fun main(): Unit = coroutineScope {
    val channel = produce {
        repeat(5) { index ->
            println("Producing next one")
            delay(1000)
            send(index * 2)
        }
    }
    for (element in channel) {
        println(element)
    }
}
*/

/*
     Unlimited - channel with capacity Channel.UNLIMITED that has an unlimited capacity buffer, and send never suspends.

     Buffered - channel with concrete capacity size or Channel.BUFFERED (which is 64 by default and can be overridden by setting the
     kotlinx.coroutines.channels.defaultBuffer system property in JVM).

     Rendezvous (default) - channel with capacity 0 or Channel.RENDEZVOUS (which is equal to 0), meaning that an exchange can happen only if sender
     and receiver meet (so it is like a book exchange spot, instead of a bookshelf).

    Conflated - channel with capacity Channel.CONFLATED which has a buffer of size 1, and each new element replaces the previous one.
*/

/*
    We will make our producer fast and our receiver slow. With unlimited capacity,
    the channel should accept all the elements and then let them be received one after another.
*/

/*
suspend fun main(): Unit = coroutineScope {
    val channel = produce(capacity = Channel.UNLIMITED) {
        repeat(5) { index ->
            send(index * 2)
            delay(100)
            println("Sent")
        }
    }
    delay(1000)
    for (element in channel) {
        println(element)
        delay(1000)
    }
}
*/

/*
    With a capacity of concrete size, we will first produce until the buffer is full, after
    which the producer will need to start waiting for the receiver.
*/

/*
suspend fun main(): Unit = coroutineScope {
    val channel = produce(capacity = 3) {
        repeat(5) { index ->
            send(index * 2)
            delay(100)
            println("Sent")
        }
    }
    delay(1000)
    for (element in channel) {
        println(element)
        delay(1000)
    }
}
*/

/*
    With a channel of default (or Channel.RENDEZVOUS) capacity, the producer will always wait for a receiver.
*/

/*
suspend fun main(): Unit = coroutineScope {
    val channel = produce {
// or produce(capacity = Channel.RENDEZVOUS) {
        repeat(5) { index ->
            send(index * 2)
            delay(100)
            println("Sent")
        }
    }
    delay(1000)
    for (element in channel) {
        println(element)
        delay(1000)
    }
}
*/

/*
    Finally, we will not be storing past elements when using the Channel.CONFLATED
    capacity. New elementswill replace the previous ones, so we will be able to receive
    only the last one, therefore we lose elements that were sent earlier.
*/

/*
suspend fun main(): Unit = coroutineScope {
    val channel = produce(capacity = Channel.CONFLATED) {
        repeat(5) { index ->
            send(index * 2)
            delay(100)
            println("Sent")
        }
    }
    delay(1000)
    for (element in channel) {
        println(element)
        delay(1000)
    }
}
*/

/*
    To customize channels further, we can control what happens when the buffer is
    full (onBufferOverflow parameter). There are the following options:

    SUSPEND (default) - when the buffer is full, suspend on the send method.
    DROP_OLDEST - when the buffer is full, drop the oldest element.
    DROP_LATEST - when the buffer is full, drop the latest element.
*/

/*
suspend fun main(): Unit = coroutineScope {
    val channel = Channel<Int>(
        capacity = 2,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    launch {
        repeat(5) { index ->
            channel.send(index * 2)
            delay(100)
            println("Sent")
        }
        channel.close()
    }
    delay(1000)
    for (element in channel) {
        println(element)
        delay(1000)
    }
}
*/

/*
    One more Channel function parameter which we should know about is
    onUndeliveredElement. It is called when an element couldn’t be handled for
    some reason. Most often this means that a channel was closed or cancelled, but
    it might also happen when send, receive, receiveOrNull, or hasNext throw an
    error. We generally use it to close resources that are sent by this channel.
*/

/*
val channel = Channel<Resource>(capacity) { resource ->
    resource.close()
}
// or
// val channel = Channel<Resource>(
// capacity,
// onUndeliveredElement = { resource ->
// resource.close()
// }
// )
// Producer code
val resourceToSend = openResource()
channel.send(resourceToSend)
// Consumer code
val resourceReceived = channel.receive()
try {
// work with received resource
} finally {
    resourceReceived.close()
}
*/

/*
    Multiple coroutines can receive from a single channel; however, to receive them
    properly we should use a for-loop (consumeEach should not be used by multiple coroutines).

    The elements are distributed fairly. The channel has a FIFO (first-in-first-out)
    queue of coroutines waiting for an element. This is why in the above example you
    can see that the elements are received by the next coroutines (0, 1, 2, 0, 1, 2, etc)
*/

/*
fun CoroutineScope.produceNumbers() = produce {
    repeat(10) {
        delay(100)
        send(it)
    }
}
fun CoroutineScope.launchProcessor(
    id: Int,
    channel: ReceiveChannel<Int>
) = launch {
    for (msg in channel) {
        println("#$id received $msg")
    }
}
suspend fun main(): Unit = coroutineScope {
    val channel = produceNumbers()
    repeat(3) { id ->
        delay(10)
        launchProcessor(id, channel)
    }
}
*/

/*
    Multiple coroutines can send to a single channel. In the below example, you can
    see two coroutines sending elements to the same channel.
*/

/*
suspend fun sendString(
    channel: SendChannel<String>,
    text: String,
    time: Long
) {
    while (true) {
        delay(time)
        channel.send(text)
    }
}
fun main() = runBlocking {
    val channel = Channel<String>()
    launch { sendString(channel, "foo", 200L) }
    launch { sendString(channel, "BAR!", 500L) }
    repeat(50) {
        println(channel.receive())
    }
    coroutineContext.cancelChildren()
}
*/

/*
    Sometimes, we need to merge multiple channels into one. For that, you might
    find the following function useful as it merges multiple channels using the
    produce function
*/

/*
fun <T> CoroutineScope.fanIn(
    channels: List<ReceiveChannel<T>>
): ReceiveChannel<T> = produce {
    for (channel in channels) {
        launch {
            for (elem in channel) {
                send(elem)
            }
        }
    }
}
*/

/*
    Sometimes we set two channels such that one produces elements based on those
    received from another. In such cases, we call it a pipeline.
*/

/*
// A channel of number from 1 to 3
fun CoroutineScope.numbers(): ReceiveChannel<Int> =
    produce {
        repeat(3) { num ->
            send(num + 1)
        }
    }
fun CoroutineScope.square(numbers: ReceiveChannel<Int>) =
    produce {
        for (num in numbers) {
            send(num * num)
        }
    }
suspend fun main() = coroutineScope {
    val numbers = numbers()
    val squared = square(numbers)
    for (num in squared) {
        println(num)
    }
}
*/

/*
    Channels are useful when different coroutines need to communicate with each
    other. They guarantee no conflicts (i.e., no problem with the shared state) and
    fairness. A typical case in which we use channels is when values are produced
    on one side, and we want to process them on the other side. Channels are used
    to separate producers and consumers of data.
*/

/*
    Channel is a powerful inter-coroutine communication primitive. It supports any
    number of senders and receivers, and every value that is sent to a channel is
    received once.We often create a channel using the produce builder, and observe a
    channel using for-loop. Channels can be used to set up a pipelinewherewe control
    the number of coroutines working on some tasks. Nowadays, we most often use
    channels in connection with Flow, which will be presented later in the book.
*/

/*
    Coroutines provide the select function, which lets us await the result of the
    first coroutine that completes. It also offers the possibility of sending to the first
    channel that has space in the buffer or receiving from the first channel that has
    an available element. This lets us make races between coroutines or join results
    from multiple sources. Let’s see it in practice.
 */

/*
    Let’s say that we want to request data from multiple sources, but we’re only
    interested in the fastest response. The easiest way to achieve this is to start these
    requests in async processes; then, use the select function as an expression, and
    await different values inside it. Inside select, we can call onAwait on Deferred
    value, which specifies a possible select expression result. Inside its lambda expression, you can transform
    the value. In the example below, we just return an
    async result, so the select expression will complete once the first async task is
    completed, then it will return its result.

    Notice that after the select expression, we call cancelChildren on coroutineContext to cancel all the coroutines
    that were started inside the select expression. This is important because without it, coroutineScope would not
    complete until all the coroutines started inside its scope are completed.
 */

/*
suspend fun requestData1(): String {
    delay(100_000)
    return "Data1"
}
suspend fun requestData2(): String {
    delay(1000)
    return "Data2"
}
suspend fun askMultipleForData(): String = coroutineScope {
    select<String> {
        async { requestData1() }.onAwait { it }
        async { requestData2() }.onAwait { it }
    }.also { coroutineContext.cancelChildren() }
}
suspend fun main(): Unit = coroutineScope {
    println(askMultipleForData())
}
*/

/*
    The solution above is a bit complex, which is why many developers define a
    helper function raceOf or use an external library (like Splitties by Louis CAD) that
    includes such a function. The raceOf function is a simple helper function that
    takes a lambda expression with multiple async tasks and returns the result of the
    first one that completes.
*/

/*
// Implementation using raceOf from Splitties library
suspend fun askMultipleForData(): String = raceOf({
    requestData1()
}, {
    requestData2()
})
suspend fun main(): Unit = coroutineScope {
    println(askMultipleForData())
}
*/

/*
    The select function can also be used with channels. These are the main functions
    that can be used inside it:

    onReceive - selected when this channel has a value. It receives this value (like the receive method)
    and uses it as an argument for its lambda expression. When onReceive is selected, select returns the result of its lambda expression.

    onReceiveCatching - selected when this channel has a value or is closed. It receives ChannelResult, which either
    represents a value or signals that this channel is closed, and it uses this value as an argument for its lambda
    expression. When onReceiveCatching is selected, select returns the result of its lambda expression

    onSend - selected when this channel has space in the buffer. It sends a value
    to this channel (like the send method) and invokes its lambda expression
    with a reference to the channel. When onSend is selected, select returns Unit.

    The select expression can be used with onReceive or onReceiveCatching to receive
    from multiple channels.
*/

/*
suspend fun CoroutineScope.produceString(s: String, time: Long) =
    produce {
        while (true) {
            delay(time)
            send(s)
        }
    }
fun main() = runBlocking {
    val fooChannel = produceString("foo", 210L)
    val barChannel = produceString("BAR", 500L)
    repeat(7) {
        select {
            fooChannel.onReceive {
                println("From fooChannel: $it")
            }
            barChannel.onReceive {
                println("From barChannel: $it")
            }
        }
    }
    coroutineContext.cancelChildren()
}
*/

/*
    The select function can be used with onSend to send to the first channel that has space in the buffer.
*/

/*
fun main(): Unit = runBlocking {
    val c1 = Channel<Char>(capacity = 2)
    val c2 = Channel<Char>(capacity = 2)
// Send values
    launch {
        for (c in 'A'..'H') {
            delay(400)
            select<Unit> {
                c1.onSend(c) { println("Sent $c to 1") }
                c2.onSend(c) { println("Sent $c to 2") }
            }
        }
    }
// Receive values
    launch {
        while (true) {
            delay(1000)
            val c = select<String> {
                c1.onReceive { "$it from 1" }
                c2.onReceive { "$it from 2" }
            }
            println("Received $c")
        }
    }
}
*/

/*
    select is a useful function that lets us await the result of the first coroutine that
    completes, or send to or receive from the first from multiple channels. It is mainly
    used to implement different patterns for operating on channels, but it can also be
    used to implement coroutine races.
*/