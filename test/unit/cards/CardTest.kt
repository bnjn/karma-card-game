@file:Suppress("ktlint:standard:no-wildcard-imports")

package unit.cards

import cards.Card
import cards.data.CardRank
import cards.data.CardSuit
import korlibs.io.util.UUID
import kotlin.test.*

class CardTest {
    @Test
    fun `a card is face up by default`() {
        val card = Card(CardSuit.SPADES, CardRank.ACE)
        assertEquals(card.isFaceUp, true)
    }

    @Test
    fun `a card has a suit and a rank`() {
        val card = Card(CardSuit.CLUBS, CardRank.ACE)
        assertEquals(card.suit, CardSuit.CLUBS)
        assertEquals(card.rank, CardRank.ACE)
    }

    @Test
    fun `a card has an id`() {
        val card = Card(CardSuit.CLUBS, CardRank.ACE)
        assertIs<UUID>(card.id)
    }
}
