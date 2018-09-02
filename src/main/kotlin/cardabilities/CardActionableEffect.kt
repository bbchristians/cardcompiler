package cardabilities

class CardActionableEffect(
        val nextEffect: CardEffect?,
        val thisEffect: (List<Any>) -> Unit
): CardEffect() {
    override fun performEffect() {
        thisEffect(listOf())
        nextEffect?.performEffect()
    }
}