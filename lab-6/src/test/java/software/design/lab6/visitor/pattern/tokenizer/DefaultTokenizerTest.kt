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
    fun `test empty input`() {
        assertEquals(
            emptyList(),
            DefaultTokenizer.tokenize(""),
        )
    }

    @Test
    fun `test all tokens`() {
        assertEquals(
            listOf(
                Add,
                Num(123),
                Num(45),
                Add,
                Sub,
                Mul,
                Div,
                OpenBrace,
                CloseBrace,
            ),
            DefaultTokenizer.tokenize(
                "+\t123 45+               -*/(                )          ",
            )
        )
    }

    @Test
    fun `test unsupported symbol`() {
        val exception = assertFailsWith<IllegalArgumentException> {
            DefaultTokenizer.tokenize(Source("1 + 3,".byteInputStream(Charsets.UTF_8)))
        }
        assertEquals("Expected '(' or digit, but got `,`", exception.message)
    }

    private fun Tokenizer.tokenize(string: String) =
        tokenize(Source(string.byteInputStream(Charsets.UTF_8)))
}
