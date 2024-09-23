package pl.training.extras

enum class Player {

    CIRCLE, CROSS;

    fun reverse() = if (this == CROSS) CIRCLE else CROSS

}
