package libraries

import card.CardSection
import card.SingleParsedCardContent
import cardabilities.trigger.TriggerEvent
import library.Library
import library.LibraryDef

class CoreLib(libraryDef: LibraryDef): Library(libraryDef) {

    val validProperties = setOf(
            "cost",
            "power",
            "toughness",
            "flying",
            "name",
            "card_type"
    )

    val validTriggers = setOf(
            "card_cast",
            "card_drawn"
    )

    override fun handleCardContent(cardContent: SingleParsedCardContent): Pair<CardSection.Companion.CardContentType, Any?>? {

        if( validProperties.contains(cardContent.propCall) ) {
            return Pair(CardSection.Companion.CardContentType.PROPERTY, Pair(cardContent.propCall, cardContent.propValue))
        }
        return null
    }

    override fun handleTrigger(cardContent: SingleParsedCardContent): TriggerEvent? {
        if( validTriggers.contains(cardContent.propCall) ) {
            return TriggerEvent(cardContent.propArgs)
        }
        return null
    }
}