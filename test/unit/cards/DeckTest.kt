@file:Suppress("ktlint:standard:no-wildcard-imports")

package unit.cards

import cards.Card
import cards.Deck
import cards.data.CardRank
import cards.data.CardSuit
import korlibs.io.util.UUID
import kotlin.test.*

class DeckTest {
    @Test
    fun `a deck has an id`() {
        val deck = Deck()
        assertIs<UUID>(deck.id)
    }

    @Test
    fun `can draw a single card from the deck`() {
        val deck = Deck()
        val card = deck.draw(1).first()
        assertIs<Card>(card)
    }

    @Test
    fun `can draw a multiple cards from the deck`() {
        val deck = Deck()
        val cards = deck.draw(10)
        cards.forEach { card ->
            assertIs<Card>(card)
        }
    }

    @Test
    fun `drawing more cards than available returns only the available cards`() {
        val deck = Deck()
        val cards = deck.draw(100)
        assertEquals(cards.size, 52)
    }

    @Test
    fun `drawing cards from an empty deck returns an empty list`() {
        val deck = Deck()
        deck.draw(52)
        assertEquals(deck.draw(10).size, 0)
    }

    @Test
    fun `drawn cards are no longer present in the deck`() {
        val deck = Deck()
        val cards = deck.draw(3)
        val cardIdsAfterDraw = deck.getCardIds()
        assertEquals(deck.getCardsRemaining(), 49)
        cards.forEach { card ->
            assertFalse { cardIdsAfterDraw.contains(card.id) }
        }
    }

    @Test
    fun `can peek at the first card in the deck`() {
        val deck = Deck()
        val expectedCard = deck.cardAtPosition(1)
        assertEquals(expectedCard?.first, CardSuit.HEARTS)
        assertEquals(expectedCard?.second, CardRank.TWO)
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
        deck.draw(1)
        assertEquals(51, deck.getCardsRemaining())
    }

    @Test
    fun `can shuffle the deck`() {
        val deck = Deck()
        deck.shuffle(9992)
        assertEquals(CardSuit.DIAMONDS, deck.cardAtPosition(1)?.first)
        assertEquals(CardRank.THREE, deck.cardAtPosition(1)?.second)
    }

    @Test
    fun `getCards returns a list of 52 cards with initial deck state`() {
        val deck = Deck()
        val cardList = deck.getCards()
        assertIs<List<Card>>(cardList)
        assertEquals(cardList.size, 52)
    }

    @Test
    fun `cards all have unique ids`() {
        val deck = Deck()
        val cardIds = deck.getCardIds()
        assertEquals(deck.getCardsRemaining(), cardIds.toSet().size)
    }
}
