package unit.controllers

import board.Board
import cards.Card
import cards.data.CardRank
import cards.data.CardSuit
import controllers.TurnController
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class TurnControllerTest {
    @Test
    fun `when TurnController is initialised, getActivePlayer returns null`() {
        val board = Board()
        val turnController = TurnController(board)
        val activePlayerId = turnController.getActivePlayerId()
        assertEquals(null, activePlayerId)
    }

    @Test
    fun `findStartingPlayer sets activePlayerId to the Player id with the lowest face up card`() {
        // seed = 1 results in player 2 having the lowest card
        val board = Board(seed = 1)
        val turnController = TurnController(board)
        val startingPlayerId = board.getPlayerIds().first()
        turnController.findStartingPlayer()
        assertEquals(startingPlayerId, turnController.getActivePlayerId())
    }

    @Test
    fun `findStartingPlayer sets activePlayerId to the Player id with the lowest face up card with a 3 player board`() {
        // seed = 5 results in player 2 having the lowest card
        val board = Board(numberOfPlayers = 3, seed = 5)
        val turnController = TurnController(board)
        val startingPlayerId = board.getPlayerIds()[1]
        turnController.findStartingPlayer()
        assertEquals(startingPlayerId, turnController.getActivePlayerId())
    }

    @Test
    fun `when 2 out of 3 players have the same rank lowest cards, findStartingPlayer selects one at random`() {
        // seed = 12825 results in player 1 and 2 having the lowest card
        val board = Board(numberOfPlayers = 3, seed = 12825)
        val turnController = TurnController(board)
        val startingPlayerId = board.getPlayerIds()[1]
        // seed = 2 results in player 2 being selected
        turnController.findStartingPlayer(seed = 2)
        assertEquals(startingPlayerId, turnController.getActivePlayerId())
    }

    @Test
    fun `when a starting player has already been selected, findStartingPlayer throws an exception`() {
        val board = Board()
        val turnController = TurnController(board)
        turnController.findStartingPlayer()
        assertFailsWith<IllegalStateException> { turnController.findStartingPlayer() }
    }

    @Test
    fun `when no starting player has been selected, getPlayerOrder returns a list of Player ids in the same order as the board`() {
        val board = Board(numberOfPlayers = 4)
        val turnController = TurnController(board)
        assertEquals(board.getPlayerIds(), turnController.getPlayerOrder())
    }

    @Test
    fun `when a starting player has been selected, getPlayerOrder contains the id for them at index 0, followed by the other player`() {
        // seed = 4 results in player 2 having the lowest card
        val board = Board(seed = 4)
        val turnController = TurnController(board)
        val startingPlayerId = board.getPlayerIds().last()
        turnController.findStartingPlayer()
        assertEquals(startingPlayerId, turnController.getPlayerOrder().first())
    }

    @Test
    fun `when a starting player has been selected, getPlayerOrder contains the id for them at index 0, followed by the other 2 players`() {
        // seed = 12825 results in player 1 and 2 having the lowest card
        val board = Board(numberOfPlayers = 3, seed = 12825)
        val turnController = TurnController(board)
        val boardPlayerIds = board.getPlayerIds()
        // seed = 2 results in player 2 being selected
        turnController.findStartingPlayer(seed = 2)
        assertEquals(boardPlayerIds[1], turnController.getPlayerOrder().first())
        assertEquals(boardPlayerIds[0], turnController.getPlayerOrder()[1])
        assertEquals(boardPlayerIds[2], turnController.getPlayerOrder().last())
    }

    @Test
    fun `when TurnController is initialised, getTurnNumber returns 0`() {
        val board = Board()
        val turnController = TurnController(board)
        val turnNumber = turnController.getTurnNumber()
        assertEquals(0, turnNumber)
    }

    @Test
    fun `when a starting player has been selected but no turns have been played, getTurnNumber returns 1`() {
        val board = Board()
        val turnController = TurnController(board)
        turnController.findStartingPlayer()
        assertEquals(1, turnController.getTurnNumber())
    }

    @Test
    fun `playTurn throws an exception if a starting player hasn't been chosen`() {
        val board = Board()
        val turnController = TurnController(board)
        val cardToPlay = Card(CardSuit.CLUBS, CardRank.ACE)
        assertFailsWith<IllegalStateException> { turnController.playTurn(cardToPlay) }
    }
}
