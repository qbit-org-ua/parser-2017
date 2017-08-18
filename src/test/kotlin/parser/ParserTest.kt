package parser

import org.junit.Assert.assertEquals
import org.junit.Test

class ParserTest {
    @Test
    fun tokenizeTest() {
        assertEquals(tokenize("1"), listOf(IntegerToken(1)))
        assertEquals(tokenize("+-*/()"), listOf(
                SimpleToken(TokenType.ADD),
                SimpleToken(TokenType.SUB),
                SimpleToken(TokenType.MUL),
                SimpleToken(TokenType.DIV),
                SimpleToken(TokenType.L_PAREN),
                SimpleToken(TokenType.R_PAREN)
        ))
        assertEquals(tokenize("1 + 2\t -  3 / (4 + 5 * 6)"), listOf(
                IntegerToken(1),
                SimpleToken(TokenType.ADD),
                IntegerToken(2),
                SimpleToken(TokenType.SUB),
                IntegerToken(3),
                SimpleToken(TokenType.DIV),
                SimpleToken(TokenType.L_PAREN),
                IntegerToken(4),
                SimpleToken(TokenType.ADD),
                IntegerToken(5),
                SimpleToken(TokenType.MUL),
                IntegerToken(6),
                SimpleToken(TokenType.R_PAREN)
        ))
    }

    @Test
    fun parseTest() {
        assertEquals(parse("1"), 1)
        assertEquals(parse("1 + 2\t -  3 / (4 + 5 * 6)"), 3)
        assertEquals(parse("1 + 2\t -  3 * (4 + 5) / 6"), -1)
    }

    @Test(expected = RuntimeException::class)
    fun tokenizeThrowsTest() {
        tokenize("$")
    }

    @Test(expected = RuntimeException::class)
    fun parseThrowsTest() {
        parse(listOf(IntegerToken(2), SimpleToken(TokenType.ADD), IntegerToken(2),
                IntegerToken(2)))
    }
}