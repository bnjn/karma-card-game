package unit.board

import board.Board
import cards.Card
import cards.data.CardRank
import cards.data.CardSuit
import player.Player
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertIs
import kotlin.test.assertTrue

class BoardTest {
    @Test
    fun `is initialised with a default of 2 players`() {
        val board = Board()
        assertEquals(2, board.getPlayerIds().size)
    }

    @Test
    fun `is initialised with 2 players if numberOfPlayers is less than 2`() {
        val board = Board(1)
        assertEquals(2, board.getPlayerIds().size)
    }

    @Test
    fun `is initialised with 4 players if numberOfPlayers is 4`() {
        val board = Board(4)
        assertEquals(4, board.getPlayerIds().size)
    }

    @Test
    fun `is initialised with 5 players if numberOfPlayers is more than 5`() {
        val board = Board(6)
        assertEquals(5, board.getPlayerIds().size)
    }

    @Test
    fun `getPlayerById gets a Player by id`() {
        val board = Board()
        val playerId = board.getPlayerIds().first()
        val player = board.getPlayerById(playerId)
        assertIs<Player>(player)
        assertEquals(player.id, playerId)
    }

    @Test
    fun `each player starts with 3 cards in their hand, 3 face up cards and 3 face down cards`() {
        val board = Board()
        board.getPlayerIds().forEach { id ->
            val player = board.getPlayerById(id)
            assertEquals(3, player.getHandCards().size)
            assertEquals(3, player.getFaceUpCards().size)
            assertEquals(3, player.getFaceDownCards().size)
        }
    }

    @Test
    fun `players all have unique ids`() {
        val board = Board()
        val playerIds = board.getPlayerIds()
        assertEquals(board.getPlayerIds().size, playerIds.toSet().size)
    }

    @Test
    fun `can add cards to discard pile`() {
        val board = Board()
        board.addCardsToDiscardPile(
            listOf(
                Card(CardSuit.CLUBS, CardRank.EIGHT),
                Card(CardSuit.HEARTS, CardRank.EIGHT),
            ),
        )
        assertEquals(board.discardPileSize(), 2)
    }

    @Test
    fun `can get discard pile`() {
        val board = Board()
        val card = Card(CardSuit.CLUBS, CardRank.EIGHT)
        board.addCardsToDiscardPile(listOf(card))
        assertEquals(board.getDiscardPile().first(), card)
    }

    @Test
    fun `can collect discard pile`() {
        val board = Board()
        val cards =
            listOf(
                Card(CardSuit.CLUBS, CardRank.EIGHT),
                Card(CardSuit.HEARTS, CardRank.EIGHT),
            )
        board.addCardsToDiscardPile(cards)
        assertTrue { board.collectDiscardPile().containsAll(cards) }
        assertEquals(0, board.discardPileSize())
    }

    @Test
    fun `can burn discard pile`() {
        val board = Board()
        val cards =
            listOf(
                Card(CardSuit.CLUBS, CardRank.EIGHT),
                Card(CardSuit.HEARTS, CardRank.EIGHT),
            )
        board.addCardsToDiscardPile(cards)
        board.burnDiscardPile()
        assertFalse { board.collectDiscardPile().containsAll(cards) }
        assertEquals(0, board.discardPileSize())
    }

    @Test
    fun `can get deck cards remaining`() {
        val board = Board()
        assertEquals(34, board.getDeckCardsRemaining())
    }

    @Test
    fun `a player can draw a hand card from the deck`() {
        val board = Board()
        val playerId = board.getPlayerIds().first()
        val player = board.getPlayerById(playerId)
        board.drawHandCardsForPlayerId(playerId, 1)
        assertEquals(4, player.getHandCards().size)
        assertEquals(33, board.getDeckCardsRemaining())
    }

    @Test
    fun `a player can draw multiple hand cards from the deck`() {
        val board = Board()
        val playerId = board.getPlayerIds().first()
        val player = board.getPlayerById(playerId)
        board.drawHandCardsForPlayerId(playerId, 3)
        assertEquals(6, player.getHandCards().size)
        assertEquals(31, board.getDeckCardsRemaining())
    }

    @Test
    fun `a player can discard a hand card`() {
        val board = Board()
        val playerId = board.getPlayerIds().first()
        val player = board.getPlayerById(playerId)
        val cardsToDiscard = listOf(player.getHandCards().first())
        board.discardHandCardsForPlayerId(playerId, cardsToDiscard)
        assertEquals(2, player.getHandCards().size)
        assertEquals(1, board.getDiscardPile().size)
        assertFalse { player.getHandCards().containsAll(cardsToDiscard) }
        assertTrue { board.getDiscardPile().containsAll(cardsToDiscard) }
    }

    @Test
    fun `a player can discard multiple hand cards`() {
        val board = Board()
        val playerId = board.getPlayerIds().first()
        val player = board.getPlayerById(playerId)
        val cardsToDiscard = player.getHandCards().slice(0..1)
        board.discardHandCardsForPlayerId(playerId, cardsToDiscard)
        assertEquals(1, player.getHandCards().size)
        assertEquals(2, board.getDiscardPile().size)
        assertFalse { player.getHandCards().containsAll(cardsToDiscard) }
        assertTrue { board.getDiscardPile().containsAll(cardsToDiscard) }
    }

    @Test
    fun `a player can swap a face up card with a hand card`() {
        val board = Board()
        val playerId = board.getPlayerIds().first()
        val player = board.getPlayerById(playerId)
        val faceUpCard = player.getFaceUpCards().first()
        val handCard = player.getHandCards().first()
        board.swapFaceUpAndHandCardForPlayerId(playerId, faceUpCard, handCard)
        assertTrue { player.getFaceUpCards().containsAll(listOf(handCard)) }
        assertTrue(player.getHandCards().containsAll(listOf(faceUpCard)))
        assertEquals(3, player.getHandCards().size)
        assertEquals(3, player.getFaceUpCards().size)
    }
}
