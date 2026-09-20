package controllers

import board.Board
import cards.Card
import korlibs.io.util.UUID
import player.Player

interface ITurnController {
    val board: Board

    fun getActivePlayer(): Player?

    fun getTurnNumber(): Int

    fun findStartingPlayer(seed: Int? = null)

    fun playTurn(
        playerId: UUID,
        card: Card,
    )
}
