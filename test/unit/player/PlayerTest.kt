package unit.player

import cards.Card
import cards.Deck
import cards.data.CardRank
import cards.data.CardSuit
import korlibs.io.util.UUID
import player.Player
import player.data.HandType
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertIs
import kotlin.test.assertNull
import kotlin.test.assertTrue

class PlayerTest {
    @Test
    fun `cardsRemaining returns the total number of cards in the player's hand and board cards`() {
        val player = Player()
        assertEquals(player.cardsRemaining(), 0)
    }

    @Test
    fun `a player can draw a hand card`() {
        val player = Player()
        player.addCards(listOf(Card(CardSuit.CLUBS, CardRank.KING)), HandType.HAND)
        assertEquals(player.getHandCards().size, 1)
    }

    @Test
    fun `a player can draw multiple hand cards that are all face up`() {
        val player = Player()
        player.addCards(
            listOf(
                Card(CardSuit.CLUBS, CardRank.KING),
                Card(CardSuit.CLUBS, CardRank.QUEEN),
            ),
            HandType.HAND,
        )
        assertEquals(player.getHandCards().size, 2)
        assertTrue { player.getHandCards().all { it.isFaceUp } }
    }

    @Test
    fun `a player can draw a face down card`() {
        val player = Player()
        player.addCards(listOf(Card(CardSuit.CLUBS, CardRank.KING)), HandType.BOARDFACEDOWN)
        assertEquals(player.getFaceDownCards().size, 1)
    }

    @Test
    fun `a player can draw multiple face down cards that are all face down`() {
        val player = Player()
        player.addCards(
            listOf(
                Card(CardSuit.CLUBS, CardRank.KING),
                Card(CardSuit.CLUBS, CardRank.QUEEN),
            ),
            HandType.BOARDFACEDOWN,
        )
        assertEquals(player.getFaceDownCards().size, 2)
        assertFalse { player.getFaceDownCards().all { it.isFaceUp } }
    }

    @Test
    fun `a player can draw a face up card`() {
        val player = Player()
        player.addCards(listOf(Card(CardSuit.CLUBS, CardRank.KING)), HandType.BOARDFACEUP)
        assertEquals(player.getFaceUpCards().size, 1)
    }

    @Test
    fun `a player can draw multiple face up cards that are all face up`() {
        val player = Player()
        player.addCards(
            listOf(
                Card(CardSuit.CLUBS, CardRank.KING),
                Card(CardSuit.CLUBS, CardRank.QUEEN),
            ),
            HandType.BOARDFACEUP,
        )
        assertEquals(player.getFaceUpCards().size, 2)
        assertTrue { player.getFaceUpCards().all { it.isFaceUp } }
    }

    @Test
    fun `a player can discard a hand card`() {
        val player = Player()
        val card = listOf(Card(CardSuit.CLUBS, CardRank.KING))
        player.addCards(card, HandType.HAND)
        val discardedCard = player.removeCards(card, HandType.HAND)
        assertEquals(player.getHandCards().size, 0)
        assertFalse { player.getHandCards().containsAll(discardedCard) }
    }

    @Test
    fun `a player can discard a face down card and it's face up`() {
        val player = Player()
        val card = listOf(Card(CardSuit.CLUBS, CardRank.KING))
        player.addCards(card, HandType.BOARDFACEDOWN)
        val discardedCard = player.removeCards(card, HandType.BOARDFACEDOWN)
        assertEquals(player.getFaceDownCards().size, 0)
        assertFalse { player.getFaceDownCards().containsAll(discardedCard) }
        assertTrue { discardedCard.first().isFaceUp }
    }

    @Test
    fun `a player can discard a face up card`() {
        val player = Player()
        val card = listOf(Card(CardSuit.CLUBS, CardRank.KING))
        player.addCards(card, HandType.BOARDFACEUP)
        val discardedCard = player.removeCards(card, HandType.BOARDFACEUP)
        assertEquals(player.getFaceUpCards().size, 0)
        assertFalse { player.getFaceUpCards().containsAll(discardedCard) }
    }

    @Test
    fun `a player has an id`() {
        val player = Player()
        assertIs<UUID>(player.id)
    }
}
