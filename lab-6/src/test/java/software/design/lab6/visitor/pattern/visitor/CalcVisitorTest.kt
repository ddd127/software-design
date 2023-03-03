package software.design.lab6.visitor.pattern.visitor

import org.junit.jupiter.api.Test
import software.design.lab6.visitor.pattern.tokenizer.token.Add
import software.design.lab6.visitor.pattern.tokenizer.token.Div
import software.design.lab6.visitor.pattern.tokenizer.token.Mul
import software.design.lab6.visitor.pattern.tokenizer.token.Num
import software.design.lab6.visitor.pattern.tokenizer.token.Sub
import kotlin.test.assertEquals

class CalcVisitorTest {

    @Test
    fun `single number`() {
        assertEquals(
            127,
            CalcVisitor().visit(listOf(Num(127))),
        )
    }

    @Test
    fun `all operations`() {
        val list = listOf(Num(84), Num(19))

        assertEquals(
            103,
            CalcVisitor().visit(
                list + Add,
            ),
        )
        assertEquals(
            65,
            CalcVisitor().visit(
                list + Sub,
            ),
        )
        assertEquals(
            1596,
            CalcVisitor().visit(
                list + Mul,
            ),
        )
        assertEquals(
            4,
            CalcVisitor().visit(
                list + Div,
            ),
        )
    }

    @Test
    fun `multiple operations`() {
        assertEquals(
            7,
            CalcVisitor().visit(
                listOf(
                    Num(12),
                    Num(7),
                    Mul,
                    Num(17),
                    Num(5),
                    Sub,
                    Div,
                ),
            ),
        )
    }
}
