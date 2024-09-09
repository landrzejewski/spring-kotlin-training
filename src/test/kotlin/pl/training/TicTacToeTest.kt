package pl.training

import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class TicTacToeTest {

    @Test
    fun should_allow_only_free_fields_to_be_taken() {
    }

    @ValueSource(ints = [0, 10])
    @ParameterizedTest
    fun should_allow_only_on_board_fields_to_be_taken(field: Int) {
    }

    @Test
    fun should_change_player_after_field_is_taken()  {
    }

    @Test
    fun should_not_change_player_after_field_is_not_taken()  {
    }

    @Test
    fun should_end_game_when_all_fields_are_taken() {
    }

    @Test
    fun should_throw_exception_when_initial_game_state_is_invalid() {
    }

    @Test
    fun should_end_game_when_player_took_winning_sequence() {
    }

    @Test
    fun should_return_player_which_took_winning_sequence_as_winner() {
    }

}
