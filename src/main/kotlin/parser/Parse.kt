package parser

/*
Expr
    = Expr + Term
    | Expr - Term
    | Term

Term
    = Term * Prim
    | Term / Prim
    | Prim

Prim
    = Int
    | - Prim
    | (Expr)
 */

fun parse(tokens: List<Token>): Int {

    class Parser(var i: Int = 0) {

        fun parse(): Int {
            val result = parseExpression()
            if (i != tokens.size) {
                throw RuntimeException("incorrect expression")
            }
            return result
        }

        fun parseExpression(): Int {
            var result = parseTerm()
            while (i < tokens.size &&
                    (tokens[i] == SimpleToken(TokenType.ADD) ||
                            tokens[i] == SimpleToken(TokenType.SUB))) {
                if (tokens[i] == SimpleToken(TokenType.ADD)) {
                    ++i
                    result += parseTerm()
                } else {
                    ++i
                    result -= parseTerm()
                }
            }
            return result
        }

        fun parseTerm(): Int {
            var result = parsePrim()
            while (i < tokens.size &&
                    (tokens[i] == SimpleToken(TokenType.MUL) ||
                            tokens[i] == SimpleToken(TokenType.DIV))) {
                if (tokens[i] == SimpleToken(TokenType.MUL)) {
                    ++i
                    result *= parsePrim()
                } else {
                    ++i
                    result /= parsePrim()
                }
            }
            return result
        }

        fun parsePrim(): Int {
            if (i == tokens.size) {
                throw RuntimeException("incorrect expression")
            }
            val token = tokens[i]
            ++i
            return when (token) {
                is IntegerToken -> {
                    token.value
                }
                SimpleToken(TokenType.SUB) -> parsePrim()
                SimpleToken(TokenType.L_PAREN) -> {
                    val result = parseExpression()
                    if (i == tokens.size || tokens[i] != SimpleToken(TokenType.R_PAREN)) {
                        throw RuntimeException("incorrect expression")
                    }
                    ++i
                    result
                }
                else -> {
                    throw RuntimeException("incorrect expression")
                }
            }
        }
    }

    return Parser().parse()
}

fun parse(str: String): Int {
    return parse(tokenize(str))
}