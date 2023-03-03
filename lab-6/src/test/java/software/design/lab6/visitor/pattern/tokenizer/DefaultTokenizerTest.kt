package software.design.lab6.visitor.pattern.tokenizer

import org.junit.jupiter.api.Test
import software.design.lab6.visitor.pattern.tokenizer.token.Add
import software.design.lab6.visitor.pattern.tokenizer.token.CloseBrace
import software.design.lab6.visitor.pattern.tokenizer.token.Div
import software.design.lab6.visitor.pattern.tokenizer.token.Mul
import software.design.lab6.visitor.pattern.tokenizer.token.Num
import software.design.lab6.visitor.pattern.tokenizer.token.OpenBrace
import software.design.lab6.visitor.pattern.tokenizer.token.Sub
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class DefaultTokenizerTest {

    @Test
    fun empty() {
        assertEquals(
            emptyList(),
            DefaultTokenizer.tokenize(""),
        )
    }

    @Test
    fun all() {
        assertEquals(
            listOf(
                Add,
                OpenBrace,
                Num(12),
                Num(3),
                Num(45),
                Add,
                Sub,
                Num(0),
                Mul,
                Div,
                CloseBrace,
            ),
            DefaultTokenizer.tokenize(
                "+\t(      12    3 \n\r45+   \r            -0*/\n           )          ",
            )
        )
    }

    @Test
    fun unexpected() {
        val exception = assertFailsWith<IllegalArgumentException> {
            DefaultTokenizer.tokenize("1 + 3,")
        }
        assertEquals("Expected '(' or digit, but got `,`", exception.message)
    }

    private fun Tokenizer.tokenize(string: String) =
        tokenize(Source(string.byteInputStream(Charsets.UTF_8)))
}
