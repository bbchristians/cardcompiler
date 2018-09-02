package card

import cardabilities.CardAbility
import cardabilities.trigger.CardTriggeredAbility

class Card(
        val cardSections: Set<CardSection>,
        val cardAbilities: Set<CardAbility>
) {

    override fun toString(): String {
        var toString = ""
        cardSections.forEach { section ->
            toString += "$section\n"
        }
        return toString
    }
}
