package cards

interface IDeck {
    fun draw(amount: Int)

    fun shuffle()

    fun reset()
}
