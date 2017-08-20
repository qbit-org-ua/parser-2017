package parser

import org.junit.Assert.assertEquals
import org.junit.Test

class ParserTest {
    @Test
    fun tokenizeTest() {
        assertEquals(tokenize("1"), listOf(IntegerToken(1)))
        assertEquals(tokenize("+-*/()"), listOf(
                SimpleToken(SimpleTokenType.ADD),
                SimpleToken(SimpleTokenType.SUB),
                SimpleToken(SimpleTokenType.MUL),
                SimpleToken(SimpleTokenType.DIV),
                SimpleToken(SimpleTokenType.L_PAREN),
                SimpleToken(SimpleTokenType.R_PAREN)
        ))
        assertEquals(tokenize("1 + 2\t -  3 / (4 + 5 * 6)"), listOf(
                IntegerToken(1),
                SimpleToken(SimpleTokenType.ADD),
                IntegerToken(2),
                SimpleToken(SimpleTokenType.SUB),
                IntegerToken(3),
                SimpleToken(SimpleTokenType.DIV),
                SimpleToken(SimpleTokenType.L_PAREN),
                IntegerToken(4),
                SimpleToken(SimpleTokenType.ADD),
                IntegerToken(5),
                SimpleToken(SimpleTokenType.MUL),
                IntegerToken(6),
                SimpleToken(SimpleTokenType.R_PAREN)
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
        parse(listOf(IntegerToken(2), SimpleToken(SimpleTokenType.ADD), IntegerToken(2),
                IntegerToken(2)))
    }
}