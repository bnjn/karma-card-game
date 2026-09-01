package cards

import cards.data.CardRank
import cards.data.CardSuit

class Card(
    override val suit: CardSuit,
    override val rank: CardRank,
    override val isFaceUp: Boolean = false,
) : ICard
