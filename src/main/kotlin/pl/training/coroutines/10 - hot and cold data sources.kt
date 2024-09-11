import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow

/*
    Hot data streams are eager, produce elements independently of their consumption, and store the elements.
    Cold data streams are lazy, perform their operations on-demand, and store nothing.
    We can observe these differences when we use lists (hot) and sequences (cold).
    Builders and operations on hot data streams start immediately. On cold data
    streams, they are not started until the elements are needed
 */

/*
fun main() {
    val l = buildList {
        repeat(3) {
            add("User$it")
            println("L: Added User")
        }
    }
    val l2 = l.map {
        println("L: Processing")
        "Processed $it"
    }
    val s = sequence {
        repeat(3) {
            yield("User$it")
            println("S: Added User")
        }
    }
    val s2 = s.map {
        println("S: Processing")
        "Processed $it"
    }
}
*/

/*
    As a result, cold data streams (like Sequence, Stream or Flow):
        can be infinite;
        do a minimal number of operations;
        use less memory (no need to allocate all the intermediate collections).

    Sequence processing does fewer operations because it processes elements lazily.
    The way it works is very simple. Each intermediate operation (like map or filter)
    just decorates the previous sequence with a new operation. The terminal operation does all the work.
    This means that a list is a collection of elements, but a sequence is just a definition
    of how these elements should be calculated.

    Hot data streams:
        are always ready to be used (each operation can be a terminal operation);
        do not need to recalculate the result when used multiple times.
*/

/*
fun m(i: Int): Int {
    print("m$i ")
    return i * i
}
fun main() {
    val l = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
        .map { m(it) } // m1 m2 m3 m4 m5 m6 m7 m8 m9 m10
    println(l) // [1, 4, 9, 16, 25, 36, 49, 64, 81, 100]
    println(l.find { it > 10 }) // 16
    println(l.find { it > 10 }) // 16
    println(l.find { it > 10 }) // 16
    val s = sequenceOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
        .map { m(it) }
    println(s.toList())
// [1, 4, 9, 16, 25, 36, 49, 64, 81, 100]
    println(s.find { it > 10 }) // m1 m2 m3 m4 16
    println(s.find { it > 10 }) // m1 m2 m3 m4 16
    println(s.find { it > 10 }) // m1 m2 m3 m4 16
}
*/

/*
    Hot data sources are eager. They produce elements as soon as possible and store them. They create elements independently of their consumption.
    These are collections (List, Set) and Channel.

    Cold data sources are lazy. They process elements on-demand on the terminal operation. All intermediate functions just define what should be
    done (most often using the Decorator pattern). They generally do not store elements and create them on demand. They do the minimal number of
    operations and can be infinite. Their creation and processing of elements is typically the same process as consumption. These elements are Sequence,
    Java Stream, Flow and RxJava streams (Observable, Single, etc).
*/

