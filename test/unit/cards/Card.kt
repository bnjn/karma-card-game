@file:Suppress("ktlint:standard:no-wildcard-imports")

package unit.cards

import cards.Card
import cards.data.CardRank
import cards.data.CardSuit
import kotlin.test.*

class CardTest {
    @Test
    fun `a card is face down by default`() {
        val card = Card(CardSuit.SPADES, CardRank.ACE)
        assertEquals(card.isFaceUp, false)
    }

    @Test
    fun `a card has a suit and a rank`() {
        val card = Card(CardSuit.CLUBS, CardRank.ONE)
        assertEquals(card.suit, CardSuit.CLUBS)
        assertEquals(card.rank, CardRank.ONE)
    }
}
