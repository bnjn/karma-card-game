package cards

import cards.data.CardRank
import cards.data.CardSuit
import kotlin.random.Random

class Deck : IDeck {
    private val cards: MutableList<Card> = mutableListOf()

    constructor() {
        enumValues<CardSuit>().forEach { suit ->
            enumValues<CardRank>().forEach { rank ->
                cards.add(Card(suit, rank))
            }
        }
    }

    override fun draw(): Card? =
        try {
            cards.removeAt(0)
        } catch (e: IndexOutOfBoundsException) {
            null
        }

    override fun shuffle(seed: Int?) {
        when (seed) {
            null -> cards.shuffle()
            else -> cards.shuffle(Random(seed))
        }
    }

    override fun reset() {
        TODO("Not yet implemented")
    }

    override fun cardAtPosition(position: Int): Pair<CardSuit, CardRank>? =
        try {
            Pair(cards[position - 1].suit, cards[position - 1].rank)
        } catch (e: IndexOutOfBoundsException) {
            null
        }

    override fun getCardsRemaining(): Int = cards.size
}
