package card

import cardabilities.trigger.CardTriggeredAbility
import cardabilities.trigger.TriggerEvent
import library.Library
import util.ParenParser

class CardCompiler(
        val libs: Set<Library>
) {

    fun compileCard(cardContent: String): Card? {
        // Instantiate card sections from libs
        val cardSections = libs.map { library ->
            CardSection(library)
        }.toSet()

        // Instantiate card abilities, which span across libs
        val cardAbilities: Set<CardTriggeredAbility> = setOf()

        // Populate each section with the content from the card
        try {
            getAllLines(cardContent).forEach { contentLine ->
                // Comment characters
                if( !contentLine.startsWith("--") ) {
                    val pertinentLib = getPertinentLibrary(contentLine)
                    // Case: one namespace found and property can be associated
                    if( pertinentLib != null ) {
                        val pertinentCardSection = cardSections.filter{ it.library == pertinentLib }[0]
                        val handledContent = pertinentCardSection.library.handleCardContent(SingleParsedCardContent(contentLine))
                        when (handledContent?.first) {
                            CardSection.Companion.CardContentType.PROPERTY -> {
                                val propContent = handledContent.second as? Pair<String, Any> ?: throw Error()
                                pertinentCardSection.properties[propContent.first] = propContent.second
                            }
                            else -> {
                                return null
                            }
                        }
                    }
                    // No namespace found, but code block is found
                    else if(contentLine.startsWith('`')) {
                        // Trigger code block
                        if( contentLine.startsWith("`on(") ) {
                            // Get the Triggers for this trigger block
                            val triggerContents = ParenParser.getParenContents(contentLine, "on", 0)
                            val triggers = mutableListOf<TriggerEvent>()
                            triggerContents.split(',').forEach { triggerContent ->
                                val triggerEvent = getPertinentLibrary(triggerContent)?.
                                        handleTrigger(SingleParsedCardContent(triggerContent))
                                        ?: throw Error(TRIGGER_PARSE_ERROR)
                                triggers.add(triggerEvent)
                            }

                            // Get the effect for this trigger block
                            val eventContentString = ParenParser.getParenContents(contentLine, "", 0, '{', '}')

                        }
                    }
                }
            }
            return Card(cardSections, cardAbilities)
        } catch(e: Error) {
            when( e.message ) {
                NAMESPACE_ERROR -> {
                    // TODO handle namespace error
                    System.err.println("Error evaluating namespace in card file: Multiple imports with same namespace")
                }
                TRIGGER_PARSE_ERROR -> {
                    // TODO handle trigger parse error
                    System.err.println("Error parsing a trigger")
                }
                else -> {
                    throw e
                }
            }
        }
        return null
    }

    private fun getAllLines(cardContent: String): List<String> {
        var newContent = ""
        cardContent.split('`').forEachIndexed { index, string ->
            if( index % 2 == 1 ) {
                newContent += "`${string.replace("\r\n", "")}`"
            } else {
                newContent += string
            }
        }
        return newContent.lines()
    }

    private fun getPertinentLibrary(content: String): Library? {
        // Get the card section associated with this line's namespace
        val pertinentLibs: List<Library> = libs.filter { lib ->
            content.startsWith("${lib.libraryDef.namespaceVar}:")
        }

        // Case: multiple namespaces detected for this single property
        if (pertinentLibs.size > 1) {
            throw Error(NAMESPACE_ERROR)
        }
        // Case: one namespace found and property can be associated
        else if (pertinentLibs.size == 1) {
            return pertinentLibs[0]
        }
        return null
    }


    companion object {

        const val NAMESPACE_ERROR = "namespace_error"
        const val TRIGGER_PARSE_ERROR = "trigger_parse_error"
    }
}