package card

class SingleParsedCardContent() {

    var namespace: String = ""
    var propCall: String = ""
    var propValue: String = ""
    var parenContent: String = ""
    var propArgs: List<String> = listOf()

    constructor(unparsedContent: String): this() {
        namespace = unparsedContent.substringBefore(":").trim()
        propCall = unparsedContent.substringBefore("=").replace("$namespace:", "").trim()
        propValue = unparsedContent.substringAfter("=").trim()
        parenContent = util.ParenParser.getParenContents(unparsedContent, propValue, 0)
        propArgs = parenContent.split(',')
    }
}