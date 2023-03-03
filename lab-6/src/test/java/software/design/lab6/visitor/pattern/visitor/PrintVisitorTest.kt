package software.design.lab6.visitor.pattern.visitor

import org.junit.jupiter.api.Test
import software.design.lab6.visitor.pattern.tokenizer.token.Add
import software.design.lab6.visitor.pattern.tokenizer.token.CloseBrace
import software.design.lab6.visitor.pattern.tokenizer.token.Div
import software.design.lab6.visitor.pattern.tokenizer.token.Mul
import software.design.lab6.visitor.pattern.tokenizer.token.Num
import software.design.lab6.visitor.pattern.tokenizer.token.OpenBrace
import software.design.lab6.visitor.pattern.tokenizer.token.Sub
import kotlin.test.assertEquals

class PrintVisitorTest {

    @Test
    fun empty() {
        assertEquals(
            "",
            PrintVisitor().visit(listOf()),
        )
    }

    @Test
    fun all() {
        assertEquals(
            "(12 + 11) * 3 / 1289 - 129",
            PrintVisitor().visit(
                listOf(
                    OpenBrace,
                    Num(12),
                    Add,
                    Num(11),
                    CloseBrace,
                    Mul,
                    Num(3),
                    Div,
                    Num(1289),
                    Sub,
                    Num(129),
                ),
            ),
        )
    }
}
