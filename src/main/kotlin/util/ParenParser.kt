package util

class ParenParser {

    companion object {
        fun getParenContents(content: String, tag: String, startIndex: Int, openDelim: Char = '(', closeDelim: Char = ')'): String {
            var parenContents = ""
            content.substring(startIndex).substringAfter("$tag$openDelim").fold(0) { parenNestLevel, thisChar ->
                if( parenNestLevel >= 0 ) {
                    if (thisChar == openDelim) {
                        parenContents += thisChar
                        parenNestLevel + 1
                    }
                    else if (thisChar == closeDelim) {
                        if( parenNestLevel > 0 ) {
                            parenContents += thisChar
                        }
                        parenNestLevel - 1
                    }
                    else {
                        parenContents += thisChar
                        parenNestLevel
                    }
                } else {
                    parenNestLevel
                }
            }
            return parenContents
        }

    }
}