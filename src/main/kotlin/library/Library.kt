package library

import card.CardSection
import card.SingleParsedCardContent
import cardabilities.trigger.TriggerEvent

abstract class Library(
    val libraryDef: LibraryDef
) {

    override fun toString() = "${libraryDef.lib} v${libraryDef.version}"

    abstract fun handleCardContent(cardContent: SingleParsedCardContent): Pair<CardSection.Companion.CardContentType, Any?>?

    abstract fun handleTrigger(cardContent: SingleParsedCardContent): TriggerEvent?
}