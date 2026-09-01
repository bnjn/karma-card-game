package cards

import cards.data.CardRank
import cards.data.CardSuit

interface ICard {
    val suit: CardSuit
    val rank: CardRank
    val isFaceUp: Boolean
}
