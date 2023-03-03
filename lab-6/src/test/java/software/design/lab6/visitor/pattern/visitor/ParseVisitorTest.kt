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

class ParseVisitorTest {

    @Test
    fun number() {
        assertEquals(
            listOf(Num(1)),
            ParseVisitor().visit(
                listOf(Num(1)),
            ),
        )
    }

    @Test
    fun simple() {
        assertEquals(
            listOf(
                Num(7),
                Num(2),
                Add,
            ),
            ParseVisitor().visit(
                listOf(
                    Num(7),
                    Add,
                    Num(2),
                ),
            ),
        )
    }

    @Test
    fun brackets() {
        assertEquals(
            listOf(
                Num(1),
                Num(13),
                Add,
                Num(2),
                Mul,
            ),
            ParseVisitor().visit(
                listOf(
                    OpenBrace,
                    Num(1),
                    Add,
                    Num(13),
                    CloseBrace,
                    Mul,
                    Num(2),
                ),
            ),
        )
    }

    @Test
    fun precedence() {
        assertEquals(
            listOf(
                Num(1),
                Num(27),
                Num(8),
                Mul,
                Num(4),
                Div,
                Add,
            ),
            ParseVisitor().visit(
                listOf(
                    Num(1),
                    Add,
                    Num(27),
                    Mul,
                    Num(8),
                    Div,
                    Num(4),
                ),
            ),
        )
    }

    @Test
    fun multiple() {
        assertEquals(
            listOf(
                Num(1),
                Num(27),
                Add,
                Num(8),
                Num(-8),
                Sub,
                Mul,
            ),
            ParseVisitor().visit(
                listOf(
                    OpenBrace,
                    OpenBrace,
                    Num(1),
                    Add,
                    Num(27),
                    CloseBrace,
                    Mul,
                    OpenBrace,
                    Num(8),
                    Sub,
                    Num(-8),
                    CloseBrace,
                    CloseBrace,
                ),
            ),
        )
    }
}
