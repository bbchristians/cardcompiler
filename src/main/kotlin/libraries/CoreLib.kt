package libraries

import cards.CardSection
import cards.SingleParsedCardContent
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

    override fun handleCardContent(cardContent: SingleParsedCardContent): Pair<CardSection.Companion.CardContentType, Any?>? {

        if( validProperties.contains(cardContent.propCall) ) {
            return Pair(CardSection.Companion.CardContentType.PROPERTY, Pair(cardContent.propCall, cardContent.propValue))
        }
        return null
    }
}