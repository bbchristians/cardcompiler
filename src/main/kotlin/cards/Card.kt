package cards

class Card(
        val cardSections: Set<CardSection>
) {

    override fun toString(): String {
        var toString = ""
        cardSections.forEach { section ->
            toString += "$section\n"
        }
        return toString
    }
}
