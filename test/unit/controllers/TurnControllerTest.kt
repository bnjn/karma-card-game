package unit.controllers

import board.Board
import controllers.TurnController
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class TurnControllerTest {
    @Test
    fun `when TurnController is initialised, getTurnNumber returns 0`() {
        val board = Board()
        val turnController = TurnController(board)
        val turnNumber = turnController.getTurnNumber()
        assertEquals(0, turnNumber)
    }

    @Test
    fun `when TurnController is initialised, getActivePlayer returns null`() {
        val board = Board()
        val turnController = TurnController(board)
        val activePlayer = turnController.getActivePlayer()
        assertEquals(null, activePlayer)
    }

    @Test
    fun `findStartingPlayer sets activePlayer to the Player with the lowest face up card`() {
        // seed = 1 results in player 2 having the lowest card
        val board = Board(seed = 1)
        val turnController = TurnController(board)
        val startingPlayer = board.getPlayerById(board.getPlayerIds().first())
        turnController.findStartingPlayer()
        assertEquals(startingPlayer, turnController.getActivePlayer())
    }

    @Test
    fun `findStartingPlayer sets activePlayer to the Player with the lowest face up card with a 3 player board`() {
        // seed = 5 results in player 2 having the lowest card
        val board = Board(numberOfPlayers = 3, seed = 5)
        val turnController = TurnController(board)
        val startingPlayer = board.getPlayerById(board.getPlayerIds()[1])
        turnController.findStartingPlayer()
        assertEquals(startingPlayer, turnController.getActivePlayer())
    }

    @Test
    fun `when 2 out of 3 players have the same rank lowest cards, findStartingPlayer selects one at random`() {
        // seed = 12825 results in player 1 and 2 having the lowest card
        val board = Board(numberOfPlayers = 3, seed = 12825)
        val turnController = TurnController(board)
        val startingPlayer = board.getPlayerById(board.getPlayerIds()[1])
        turnController.findStartingPlayer()
        assertEquals(startingPlayer, turnController.getActivePlayer())
    }

    @Test
    fun `playTurn throws an exception if a starting player hasn't been chosen`() {
        val board = Board()
        val turnController = TurnController(board)
        val player = board.getPlayerById(board.getPlayerIds().first())
        val cardToPlay = player.getHandCards().first()
        assertFailsWith<IllegalStateException> { turnController.playTurn(player.id, cardToPlay) }
    }

    // initialTurn(cardsToSwap: Set<Pair<UUID, List<Card>>>) -
}
