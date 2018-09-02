package cardsets

import card.CardCompiler
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonParser
import library.Library
import library.LibraryParse
import java.io.File
import kotlin.reflect.full.primaryConstructor

class CardSetCompiler(
        val setName: String
) {

    private val libs by lazy (::generateLibs)

    fun compileSet(): CardSet? {
        try {
            val cardCompiler = CardCompiler(libs)
            return CardSet(File("$RES_FILE/$setName").walk().filter { file ->
                file.name.endsWith(".card")
            }.map { file ->
                val fileContents = file.readText()
                cardCompiler.compileCard(fileContents) ?: throw Error(CARD_PARSE_ERROR)
            }.toSet()
            )
        } catch(e: Error) {
            when( e.message ) {
                CARD_PARSE_ERROR -> {
                    // TODO Handle
                    System.err.println("Error parsing card")
                }
                LOAD_LIBS_ERROR -> {
                    // TODO Handle
                    System.err.println("Error parsing libs")
                }
                LOAD_LIBS_ERROR_BAD_TYPE -> {
                    // TODO Handle
                    System.err.println("Error parsing libs: Imported library is not of type `Library`")
                }
                else -> throw e
            }
            return null
        }
    }

    private fun generateLibs(): Set<Library> {
        return Gson().fromJson(getLibJSON(), LibraryParse::class.java)?.libs?.map { libraryDef ->
            // Parse the content line using the namespace
            val libClass = Class.forName("$LIBRARIES_PACKAGE.${libraryDef.lib}").kotlin
            libClass.primaryConstructor?.call(libraryDef) as? Library ?: throw Error(LOAD_LIBS_ERROR_BAD_TYPE)
        }?.toSet() ?: throw Error(LOAD_LIBS_ERROR)
    }

    private fun getLibJSON(): JsonElement {
        return JsonParser().parse( File("$RES_FILE/$setName/libs.json").readText())
    }

    companion object {

        // Resource file location
        const val RES_FILE = "src/main/resources/cardTests"
        const val LIBRARIES_PACKAGE = "libraries"

        // Error constants
        const val CARD_PARSE_ERROR = "card_parse_error"
        const val LOAD_LIBS_ERROR = "load_libs_error"
        const val LOAD_LIBS_ERROR_BAD_TYPE = "load_libs_error_bad_type"
    }
}