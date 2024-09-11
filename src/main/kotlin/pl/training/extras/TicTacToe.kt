package pl.training.extras

import java.util.Collections.disjoint

class TicTacToe(
    private var crossFields: MutableSet<Int> = mutableSetOf<Int>(),
    private var circleFields: MutableSet<Int> = mutableSetOf<Int>(),
    startingPlayer: Player = Player.CROSS
) {

    var currentPlayer = startingPlayer
        private set
    val isGameOver: Boolean
        get() = allFieldsAreTake() || currentPlayerTookWinningSequence()
    val winner: Player?
        get() = getFinalWinner()

    init {
        if (!disjoint(crossFields, circleFields)) {
            throw IllegalArgumentException()
        }
    }

    private fun allFieldsAreTake() = BOARD_SIZE - crossFields.size - circleFields.size <= 0

    private fun currentPlayerTookWinningSequence() = WINNING_SEQUENCES.any { playerFields().containsAll(it) }

    fun makeMove(field: Int): Boolean {
        if (!isOnBoard(field) || isTaken(field)) return false
        playerFields().add(field)
        if (!isGameOver) {
            currentPlayer = currentPlayer.reverse()
        }
        return true
    }

    private fun playerFields() = if (currentPlayer == Player.CROSS) crossFields else circleFields

    private fun isOnBoard(field: Int) = field in FIRST_FILED_NUMBER..BOARD_SIZE

    private fun isTaken(field: Int) = field in crossFields.union(circleFields)

    private fun getFinalWinner() = if (isGameOver) currentPlayer else null

    companion object {

        private const val FIRST_FILED_NUMBER = 1
        private const val BOARD_SIZE = 9

        private val WINNING_SEQUENCES = setOf(
            setOf(1, 2, 3), setOf(4, 5, 6), setOf(7, 8, 9),
            setOf(1, 4, 7), setOf(2, 5, 8), setOf(3, 6, 9),
            setOf(1, 5, 9), setOf(3, 5, 7)
        )

    }

}
