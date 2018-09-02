package card

import library.Library

class CardSection(
        val library: Library
) {

    val properties: HashMap<String, Any> = hashMapOf()

    override fun toString(): String {
        var toString = "${library.libraryDef.lib}:\n\tProperties:\n"
        properties.forEach { key, value ->
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