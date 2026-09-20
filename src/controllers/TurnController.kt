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
import player.data.HandType
import kotlin.random.Random

class TurnController(
    override val board: Board,
) : ITurnController {
    private var turnNumber: Int = 0
    private var playerOrder: List<UUID> = board.getPlayerIds()
    private var activePlayerId: UUID = board.getPlayerIds().first()
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

    override fun getTurnNumber(): Int = turnNumber

    override fun getPlayerOrder(): List<UUID> = playerOrder

    override fun getActivePlayerId(): UUID = activePlayerId

    override fun findStartingPlayer(seed: Int?) {
        if (turnNumber > 0) throw IllegalStateException("Starting player has already been selected")

        val playersByLowestCardRank: List<Pair<UUID, Int>> =
            board
                .getPlayerIds()
                .map { id ->
                    val player = board.getPlayerById(id)
                    val faceUpCards = player.getFaceUpCards()
                    Pair(id, faceUpCards.maxOf { startingPlayerCardValues.getValue(it.rank) })
                }.sortedByDescending { it.second }

        val playersWithLowestCardRank = playersByLowestCardRank.filter { it.second == playersByLowestCardRank.first().second }

        val activePlayerId = playersWithLowestCardRank.random(seed?.let { Random(it) } ?: Random).first

        val playerOrder =
            arrayOf(
                activePlayerId,
                *playerOrder.filter { it !== activePlayerId }.toTypedArray(),
            ).toList()

        this.activePlayerId = activePlayerId
        this.playerOrder = playerOrder
        this.turnNumber = 1
    }

    override fun playTurn(
        cards: List<Card>,
        handType: HandType,
    ) {
        // TODO: Implement check for player hand cards remaining when attempting to play a face up or down card -> fails
        // TODO: Implement check for player face up cards remaining when attempting to play a face down card -> fails
        if (turnNumber == 0) {
            throw IllegalStateException("No starting player. TurnController.findStartingPlayer must be executed first")
        }
        try {
            board.getPlayerById(activePlayerId).removeCards(cards, handType)
            activePlayerId =
                if (activePlayerId == playerOrder.last()) {
                    playerOrder.first()
                } else {
                    playerOrder[playerOrder.indexOf(activePlayerId) + 1]
                }
            turnNumber++
        } catch (e: IllegalStateException) {
            throw IllegalStateException("Active player doesn't have those cards")
        }
    }
}
