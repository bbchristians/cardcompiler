package libraries

import card.CardSection
import card.SingleParsedCardContent
import cardabilities.trigger.TriggerEvent
import library.Library
import library.LibraryDef

class ExtraLib(libraryDef: LibraryDef): Library(libraryDef) {

    override fun handleCardContent(cardContent: SingleParsedCardContent): Pair<CardSection.Companion.CardContentType, Any?>? {
        return Pair(CardSection.Companion.CardContentType.PROPERTY, Pair("Weight", 1050))
    }

    override fun handleTrigger(cardContent: SingleParsedCardContent): TriggerEvent? {
        return null
    }
}