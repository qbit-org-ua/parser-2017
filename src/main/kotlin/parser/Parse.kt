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

private class Parser(val tokens: List<Token>, var i: Int = 0) {

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
                (tokens[i] == SimpleToken(SimpleTokenType.ADD) ||
                        tokens[i] == SimpleToken(SimpleTokenType.SUB))) {
            if (tokens[i] == SimpleToken(SimpleTokenType.ADD)) {
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
                (tokens[i] == SimpleToken(SimpleTokenType.MUL) ||
                        tokens[i] == SimpleToken(SimpleTokenType.DIV))) {
            if (tokens[i] == SimpleToken(SimpleTokenType.MUL)) {
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
            SimpleToken(SimpleTokenType.SUB) -> parsePrim()
            SimpleToken(SimpleTokenType.L_PAREN) -> {
                val result = parseExpression()
                if (i == tokens.size || tokens[i] != SimpleToken(SimpleTokenType.R_PAREN)) {
                    throw RuntimeException("incorrect expression")
                }
                ++i
                result
            }
            else -> throw RuntimeException("incorrect expression")
        }
    }
}

fun parse(tokens: List<Token>): Int {
    return Parser(tokens).parse()
}

fun parse(str: String): Int {
    return parse(tokenize(str))
}