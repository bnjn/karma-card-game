package cards

import cards.data.CardRank
import cards.data.CardSuit
import korlibs.io.util.UUID
import kotlin.random.Random

class Deck : IDeck {
    override val id = UUID.randomUUID()
    private val cards: MutableList<Card> = mutableListOf()

    constructor() {
        enumValues<CardSuit>().forEach { suit ->
            enumValues<CardRank>().forEach { rank ->
                cards.add(Card(suit, rank))
            }
        }
    }

    override fun draw(amount: Int): List<Card> {
        val drawnCards: MutableList<Card> = mutableListOf()
        return try {
            repeat(amount) {
                drawnCards.add(cards.removeAt(0))
            }
            drawnCards
        } catch (e: IndexOutOfBoundsException) {
            drawnCards
        }
    }

    override fun shuffle(seed: Int?) {
        when (seed) {
            null -> cards.shuffle()
            else -> cards.shuffle(Random(seed))
        }
    }

    override fun getCards(): List<Card> = cards.map { it }

    override fun getCardIds(): List<UUID> = cards.map { it.id }

    override fun cardAtPosition(position: Int): Pair<CardSuit, CardRank>? =
        try {
            Pair(cards[position - 1].suit, cards[position - 1].rank)
        } catch (e: IndexOutOfBoundsException) {
            null
        }

    override fun getCardsRemaining(): Int = cards.size
}
