package cards

import library.Library

class CardSection(
        val library: Library
) {

    val properties: HashMap<String, Any> = hashMapOf()

    val effects: HashMap<Trigger, Effect> = hashMapOf()

    override fun toString(): String {
        var toString = "${library.libraryDef.lib}:\n\tProperties:\n"
        properties.forEach { key, value ->
            toString += "\t\t$key: $value\n"
        }
        toString += "\tEffects:\n"
        effects.forEach { key, value ->
            toString += "\t\t$key: $value\n"
        }
        return toString
    }

    companion object {

        enum class CardContentType {
            PROPERTY,
            EFFECT
        }
    }
}