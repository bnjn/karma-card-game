package cards

import cards.data.CardRank
import cards.data.CardSuit
import korlibs.io.util.UUID

class Card(
    override val suit: CardSuit,
    override val rank: CardRank,
    override var isFaceUp: Boolean = true,
) : ICard {
    override val id = UUID.randomUUID()
}
