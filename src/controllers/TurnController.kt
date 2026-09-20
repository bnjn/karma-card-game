package controllers

import board.Board
import cards.Card
import cards.data.CardRank
import cards.data.CardRank.ACE
import cards.data.CardRank.EIGHT
import cards.data.CardRank.FIVE
import cards.data.CardRank.FOUR
import cards.data.CardRank.JACK
import cards.data.CardRank.KING
import cards.data.CardRank.NINE
import cards.data.CardRank.QUEEN
import cards.data.CardRank.SEVEN
import cards.data.CardRank.SIX
import cards.data.CardRank.TEN
import cards.data.CardRank.THREE
import cards.data.CardRank.TWO
import korlibs.io.util.UUID
import player.Player

class TurnController(
    override val board: Board,
) : ITurnController {
    private val turnNumber: Int = 0
    private var activePlayer: Player? = null
    private val startingPlayerCardValues: Map<CardRank, Int> =
        mapOf(
            THREE to 12,
            FOUR to 11,
            FIVE to 10,
            SIX to 9,
            SEVEN to 8,
            EIGHT to 7,
            NINE to 6,
            TEN to 5,
            JACK to 4,
            QUEEN to 3,
            KING to 2,
            ACE to 1,
            TWO to 0,
        )

    override fun getActivePlayer(): Player? = activePlayer

    override fun getTurnNumber(): Int = turnNumber

    override fun findStartingPlayer(seed: Int?) {
        val lowestCardPlayers: List<Pair<UUID, Int>> =
            board
                .getPlayerIds()
                .map { id ->
                    val player = board.getPlayerById(id)
                    val faceUpCards = player.getFaceUpCards()
                    Pair(id, faceUpCards.maxOf { startingPlayerCardValues.getValue(it.rank) })
                }.sortedByDescending { it.second }

        if (lowestCardPlayers.distinctBy { it.second }.size == 1) {
            this.activePlayer = board.getPlayerById(lowestCardPlayers.random().first)
        } else {
            this.activePlayer = board.getPlayerById(lowestCardPlayers.first().first)
        }
    }

    override fun playTurn(
        playerId: UUID,
        card: Card,
    ) {
        if (activePlayer ==
            null
        ) {
            throw IllegalStateException("No starting player. TurnController.findStartingPlayer must be executed first")
        }
    }
}
