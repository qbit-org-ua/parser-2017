package parser

enum class SimpleTokenType {
    ADD, SUB, MUL, DIV, L_PAREN, R_PAREN
}

sealed class Token
data class SimpleToken(val simpleTokenType: SimpleTokenType) : Token()
data class IntegerToken(val value: Int) : Token()

fun tokenize(str: String): List<Token> {
    var i = 0
    val tokens = ArrayList<Token>()

    fun skipSpaces() {
        while (i < str.length && str[i].isWhitespace()) {
            ++i
        }
    }

    skipSpaces()
    while (i < str.length) {
        fun consume(simpleTokenType: SimpleTokenType) {
            tokens.add(SimpleToken(simpleTokenType))
            ++i
        }

        when (str[i]) {
            '+' -> consume(SimpleTokenType.ADD)
            '-' -> consume(SimpleTokenType.SUB)
            '*' -> consume(SimpleTokenType.MUL)
            '/' -> consume(SimpleTokenType.DIV)
            '(' -> consume(SimpleTokenType.L_PAREN)
            ')' -> consume(SimpleTokenType.R_PAREN)
            in '0'..'9' -> {
                var value = 0
                while (i < str.length && str[i] in '0'..'9') {
                    value = value * 10 + (str[i] - '0')
                    ++i
                }
                tokens.add(IntegerToken(value))
            }
            else -> throw RuntimeException("unknown token '${str[i]}'")
        }
        skipSpaces()
    }

    return tokens
}