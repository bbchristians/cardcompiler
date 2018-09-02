package cardabilities

class CardConditional(
        val conditionalEffect: CardEffect,
        val conditional: List<(List<Any>) -> Boolean>
): CardEffect() {

    override fun performEffect() {
//        if( !conditional.contains { false } ) {
//            conditionalEffect.performEffect()
//        }
        conditionalEffect.performEffect()
    }
}