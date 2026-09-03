package cards

import cards.data.CardRank
import cards.data.CardSuit

interface IDeck {
    fun draw(): Card?

    fun shuffle(seed: Int? = null)

    fun reset()

    fun cardAtPosition(position: Int): Pair<CardSuit, CardRank>?

    fun getCardsRemaining(): Int
}
