package parser

enum class TokenType {
    ADD, SUB, MUL, DIV, L_PAREN, R_PAREN
}

sealed class Token
data class SimpleToken(val tokenType: TokenType) : Token()
data class IntegerToken(val value: Int) : Token()

fun tokenize(str: String): List<Token> {
    var i = 0
    val tokens = ArrayList<Token>()

    fun skipSpaces() {
        while (i < str.length && str[i].isWhitespace()) {
            ++i
        }
    }

    fun consume(tokenType: TokenType) {
        tokens.add(SimpleToken(tokenType))
        ++i
    }

    skipSpaces()
    while (i < str.length) {
        when (str[i]) {
            '+' -> consume(TokenType.ADD)
            '-' -> consume(TokenType.SUB)
            '*' -> consume(TokenType.MUL)
            '/' -> consume(TokenType.DIV)
            '(' -> consume(TokenType.L_PAREN)
            ')' -> consume(TokenType.R_PAREN)
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