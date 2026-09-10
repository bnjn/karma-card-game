package board

import cards.Card
import cards.Deck
import korlibs.io.util.UUID
import player.Player
import player.data.HandType

class Board(
    override val numberOfPlayers: Int = 2,
    override val seed: Int? = null,
) : IBoard {
    private val deck = Deck()
    private val discardPile: MutableList<Card> = mutableListOf()

    private val players: MutableList<Player> =
        MutableList(
            numberOfPlayers.coerceIn(2, 5),
        ) { Player() }

    init {
        deck.shuffle(seed)
        players.forEach { player ->
            player.addCards(deck.draw(3), HandType.BOARDFACEDOWN)
            player.addCards(deck.draw(3), HandType.BOARDFACEUP)
            player.addCards(deck.draw(3), HandType.HAND)
        }
    }

    override fun getPlayerIds(): List<UUID> = players.map { it.id }

    override fun getPlayerById(id: UUID): Player = players.single { it.id == id }

    override fun drawHandCardsForPlayerId(
        id: UUID,
        amount: Int,
    ) {
        getPlayerById(id).addCards(deck.draw(amount), HandType.HAND)
    }

    override fun discardHandCardsForPlayerId(
        id: UUID,
        cards: List<Card>,
    ) {
        addCardsToDiscardPile(getPlayerById(id).removeCards(cards, HandType.HAND))
    }

    override fun getDeckCardsRemaining(): Int = deck.getCardsRemaining()

    override fun swapFaceUpAndHandCardForPlayerId(
        id: UUID,
        faceUpCard: Card,
        handCard: Card,
    ) {
        val player = getPlayerById(id)
        val faceUpCardToSwap = player.removeCards(listOf(faceUpCard), HandType.BOARDFACEUP)
        val handCardToSwap = player.removeCards(listOf(handCard), HandType.HAND)
        player.addCards(handCardToSwap, HandType.BOARDFACEUP)
        player.addCards(faceUpCardToSwap, HandType.HAND)
    }

    override fun getDiscardPile(): List<Card> = discardPile.toList()

    override fun addCardsToDiscardPile(cards: List<Card>) {
        discardPile.addAll(cards)
    }

    override fun collectDiscardPile(): List<Card> {
        val collectedCards = mutableListOf<Card>()
        collectedCards.addAll(discardPile)
        discardPile.removeAll(collectedCards)
        return collectedCards
    }

    override fun burnDiscardPile() {
        discardPile.removeAll(discardPile)
    }

    override fun discardPileSize(): Int = discardPile.size
}
