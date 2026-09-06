package board

import cards.Card
import korlibs.io.util.UUID
import player.Player

interface IBoard {
    val numberOfPlayers: Int
    val seed: Int?

    fun getPlayerIds(): List<UUID>

    fun getPlayerById(id: UUID): Player

    fun drawHandCardsForPlayerId(
        id: UUID,
        amount: Int,
    )

    fun discardHandCardsForPlayerId(
        id: UUID,
        cards: List<Card>,
    )

    fun getDeckCardsRemaining(): Int

    fun swapFaceUpAndHandCardForPlayerId(
        id: UUID,
        faceUpCard: Card,
        handCard: Card,
    )

    fun getDiscardPile(): List<Card>

    fun addCardsToDiscardPile(cards: List<Card>)

    fun collectDiscardPile(): List<Card>

    fun burnDiscardPile()

    fun discardPileSize(): Int
}
