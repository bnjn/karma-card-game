package cards

import cards.data.CardRank
import cards.data.CardSuit
import korlibs.io.util.UUID

interface ICard {
    val suit: CardSuit
    val rank: CardRank
    var isFaceUp: Boolean
    val id: UUID
}
