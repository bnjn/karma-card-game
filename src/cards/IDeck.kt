package cards

import cards.data.CardRank
import cards.data.CardSuit
import korlibs.io.util.UUID

interface IDeck {
    val id: UUID

    fun draw(amount: Int): List<Card>

    fun shuffle(seed: Int? = null)

    fun cardAtPosition(position: Int): Pair<CardSuit, CardRank>?

    fun getCards(): List<Card>

    fun getCardIds(): List<UUID>

    fun getCardsRemaining(): Int
}
