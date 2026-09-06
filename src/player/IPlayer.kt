package player

import cards.Card
import korlibs.io.util.UUID
import player.data.HandType

interface IPlayer {
    val id: UUID

    fun addCards(
        cards: List<Card>,
        type: HandType,
    )

    fun removeCards(
        cards: List<Card>,
        type: HandType,
    ): List<Card>

    fun getHandCards(): List<Card>

    fun getFaceDownCards(): List<Card>

    fun getFaceUpCards(): List<Card>

    fun cardsRemaining(): Int
}
