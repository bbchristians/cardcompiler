package cards

class SingleParsedCardContent() {

    var namespace: String = ""
    var propCall: String = ""
    var propValue: String = ""

    constructor(unparsedContent: String): this() {
        namespace = unparsedContent.substringBefore(":").trim()
        propCall = unparsedContent.substringBefore("=").replace("$namespace:", "").trim()
        propValue = unparsedContent.substringAfter("=").trim()
    }
}