@file:Suppress("ktlint:standard:no-wildcard-imports")

package unit.cards

import cards.Card
import cards.Deck
import cards.data.CardRank
import cards.data.CardSuit
import kotlin.random.Random
import kotlin.test.*

class DeckTest {
    @Test
    fun `can draw a single card from the deck`() {
        val deck = Deck()
        val card = deck.draw()
        assertIs<Card>(card)
        assertEquals(card.suit, CardSuit.HEARTS)
        assertEquals(card.rank, CardRank.ACE)
    }

    @Test
    fun `attempting to draw a card from an empty deck returns null`() {
        val deck = Deck()
        repeat(52) {
            deck.draw()
        }
        assertNull(deck.draw())
    }

    @Test
    fun `can peek at the first card in the deck`() {
        val deck = Deck()
        val expectedCard = deck.cardAtPosition(1)
        assertEquals(expectedCard?.first, CardSuit.HEARTS)
        assertEquals(expectedCard?.second, CardRank.ACE)
    }

    @Test
    fun `peeking at an invalid card position returns null`() {
        val deck = Deck()
        val expectedCard = deck.cardAtPosition(72)
        assertNull(expectedCard)
    }

    @Test
    fun `peeking at a negative card position returns null`() {
        val deck = Deck()
        val expectedCard = deck.cardAtPosition(-2)
        assertNull(expectedCard)
    }

    @Test
    fun `can get the total cards remaining in the deck`() {
        val deck = Deck()
        assertEquals(52, deck.getCardsRemaining())
        deck.draw()
        assertEquals(51, deck.getCardsRemaining())
    }

    @Test
    fun `can shuffle the deck`() {
        val deck = Deck()
        deck.shuffle(9992)
        assertEquals(CardSuit.DIAMONDS, deck.cardAtPosition(1)?.first)
        assertEquals(CardRank.TWO, deck.cardAtPosition(1)?.second)
    }
}
