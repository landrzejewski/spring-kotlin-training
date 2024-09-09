/*
    Packages are a way to group files together and avoid name conflicts. If we don’t specify a package,
    the file belongs to the default package. The package membership declaration should be the first significant
    line of the source file. The package name does not have to correspond to the actual directory structure on disk,
    but it is a good practice.

    To use a function or class from another package, we need to import it. Imports are specified after the package
    declaration and before other elements.

    Default imports include: kotlin.*, kotlin.annotation.*, kotlin.collections.*, java.lang.
*/

package pl.training

import java.time.LocalDateTime as DateTime

/*
    The program is started by executing the main function, the input parameters are optional.
    Other examples of valid main function:

    fun main(args: Array<String>) {
        println("Hello World")
    }

    fun main(vararg args: String) {
        println("Hello World")
    }

    suspend fun main() {
        println("Hello World")
    }

    class Main {
        companion object {
            @JvmStatic
            fun main(args: Array<String>) {
                println("Hello World")
            }
        }
    }

    To view the equivalent of Kotlin code in Java, you can choose Tools->Show Kotlin Bytecode->Decompile
*/

fun main() {
    println("Hello World ${DateTime.now()}")
}

/*
    Access modifiers for global elements:

    public - element is visible everywhere, default
    internal - element is visible everywhere in the same module (maven, gradle etc.)
    private - element is visible inside the file that contains the declaration

    Access modifiers for class elements:

    public - element is visible everywhere, default
    internal - element is visible everywhere in the same module (maven, gradle etc.)
    private - element is visible inside the class that contains the declaration
    protected - element is visible inside the class that contains the declaration and its subclasses

 */
