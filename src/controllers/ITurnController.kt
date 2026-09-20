package controllers

import board.Board
import cards.Card
import korlibs.io.util.UUID
import player.data.HandType

interface ITurnController {
    val board: Board

    fun getTurnNumber(): Int

    fun getPlayerOrder(): List<UUID>

    fun getActivePlayerId(): UUID

    fun findStartingPlayer(seed: Int? = null)

    fun playTurn(
        cards: List<Card>,
        handType: HandType,
    )
}
