package library

import cards.CardSection
import cards.SingleParsedCardContent

abstract class Library(
    val libraryDef: LibraryDef
) {

    override fun toString() = "${libraryDef.lib} v${libraryDef.version}"

    abstract fun handleCardContent(cardContent: SingleParsedCardContent): Pair<CardSection.Companion.CardContentType, Any?>?
}