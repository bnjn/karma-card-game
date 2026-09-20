package player

import cards.Card
import korlibs.io.util.UUID
import player.data.HandType
import player.data.HandType.BOARDFACEDOWN
import player.data.HandType.BOARDFACEUP
import player.data.HandType.HAND

class Player : IPlayer {
    override val id = UUID.randomUUID()
    private val hand: MutableList<Card> = mutableListOf()
    private val faceUpCards: MutableList<Card> = mutableListOf()
    private val faceDownCards: MutableList<Card> = mutableListOf()

    override fun addCards(
        cards: List<Card>,
        type: HandType,
    ) {
        when (type) {
            HAND -> {
                hand.addAll(cards)
            }

            BOARDFACEUP -> {
                faceUpCards.addAll(cards)
            }

            BOARDFACEDOWN -> {
                cards.forEach { it.isFaceUp = false }
                faceDownCards.addAll(cards)
            }
        }
    }

    override fun removeCards(
        cards: List<Card>,
        type: HandType,
    ): List<Card> {
        if (!hasCards(cards, type)) throw IllegalStateException("Player doesn't have those cards")
        return when (type) {
            HAND -> {
                hand.removeAll(cards)
                cards
            }

            BOARDFACEUP -> {
                faceUpCards.removeAll(cards)
                cards
            }

            BOARDFACEDOWN -> {
                faceDownCards.removeAll(cards)
                cards.forEach { it.isFaceUp = true }
                cards
            }
        }
    }

    override fun getHandCards(): List<Card> = hand.toList()

    override fun getFaceDownCards(): List<Card> = faceDownCards.toList()

    override fun getFaceUpCards(): List<Card> = faceUpCards.toList()

    override fun cardsRemaining(): Int = hand.size + faceUpCards.size + faceDownCards.size

    private fun hasCards(
        cards: List<Card>,
        type: HandType,
    ): Boolean =
        when (type) {
            HAND -> {
                hand.containsAll(cards)
            }

            BOARDFACEUP -> {
                faceUpCards.containsAll(cards)
            }

            BOARDFACEDOWN -> {
                faceDownCards.containsAll(cards)
            }
        }
}
