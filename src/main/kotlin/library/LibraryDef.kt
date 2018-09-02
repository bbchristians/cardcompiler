package library

class LibraryDef(
        val lib: String,
        val version: Int,
        val namespaceVar: String
)

class LibraryParse(
        var libs: ArrayList<LibraryDef>
)