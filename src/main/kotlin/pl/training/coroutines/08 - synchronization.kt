package pl.training.coroutinesimport kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/*
suspend fun main() {
    var num = 0
    coroutineScope {
        repeat(10_000) {
            launch { // uses Dispatchers.Default
                delay(10)
                num++
            }
        }
    }
    print(num)
}
// The result very unlikely to be 10000, should be much smaller
*/

/*
    How can we deal with those problems? Popular solutions involve:
        Using atomic values
        Using concurrent collections
        Using synchronized block
        Using a dispatcher limited to a single thread
        Using mutex
*/

suspend fun main() {
    var num = 0
    val lock = Any()
    coroutineScope {
        repeat(10_000) {
            launch {
                delay(10)
                synchronized(lock) {
                    num++
                }
            }
        }
    }
    print(num) // 10000
}