package cards

import library.Library

class CardCompiler(
        val libs: Set<Library>
) {

    fun compileCard(cardContent: String): Card? {
        // Instantiate card sections from libs
        val cardSections = libs.map { library ->
            CardSection(library)
        }.toSet()

        // Populate each section with the content from the card
        try {
            cardContent.lines().forEach { contentLine ->
                // Get the card section associated with this line's namespace
                val pertinentCardSections: List<CardSection> = cardSections.filter { section ->
                    contentLine.startsWith("${section.library.libraryDef.namespaceVar}:")
                }
                if (pertinentCardSections.size > 1) {
                    throw Error(NAMESPACE_ERROR)
                }
                if( pertinentCardSections.size == 1 ) {
                    val pertinentCardSection = pertinentCardSections[0]
                    val handledContent = pertinentCardSection.library.handleCardContent(SingleParsedCardContent(contentLine))
                    when( handledContent?.first ) {
                        CardSection.Companion.CardContentType.PROPERTY -> {
                            val propContent = handledContent.second as? Pair<String, Any> ?: throw Error()
                            pertinentCardSection.properties[propContent.first] = propContent.second
                        }
                        CardSection.Companion.CardContentType.EFFECT -> {
                            val effectContent = handledContent.second as? Pair<Trigger, Effect> ?: throw Error()
                            pertinentCardSection.effects[effectContent.first] = effectContent.second
                        }
                        null -> {
                            return null
                        }
                    }
                }
            }
            return Card(cardSections)
        } catch(e: Error) {
            when( e.message ) {
                NAMESPACE_ERROR -> {
                    // TODO handle namespace error
                    System.err.println("Error evaluating namespace in card file: Multiple imports with same namespace")
                }
                else -> {
                    throw e
                }
            }
        }
        return null
    }


    companion object {

        const val NAMESPACE_ERROR = "namespace_error"
    }
}