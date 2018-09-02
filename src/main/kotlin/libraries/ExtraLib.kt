package libraries

import cards.CardSection
import cards.SingleParsedCardContent
import library.Library
import library.LibraryDef

class ExtraLib(libraryDef: LibraryDef): Library(libraryDef) {

    override fun handleCardContent(cardContent: SingleParsedCardContent): Pair<CardSection.Companion.CardContentType, Any?>? {
        return Pair(CardSection.Companion.CardContentType.PROPERTY, Pair("Weight", 1050))
    }
}