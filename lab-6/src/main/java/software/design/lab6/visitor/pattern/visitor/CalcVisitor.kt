package software.design.lab6.visitor.pattern.visitor

import software.design.lab6.visitor.pattern.tokenizer.token.BinaryOp
import software.design.lab6.visitor.pattern.tokenizer.token.Brace
import software.design.lab6.visitor.pattern.tokenizer.token.Num

class CalcVisitor : Visitor<Int> {

    private val result = ArrayDeque<Int>()

    override fun visit(number: Num) {
        result.addLast(number.evaluate())
    }

    override fun visit(brace: Brace) {
        throw IllegalArgumentException("Braces are not allowed in CalcVisitor")
    }

    override fun visit(operation: BinaryOp) {
        val right = result.removeLast()
        val left = result.removeLast()
        val newResult = operation.evaluate(left, right)
        result.addLast(newResult)
    }

    override fun result(): Int {
        return result.singleOrNull()
            ?: throw IllegalStateException(
                "Stack must have exactly one number, but ${result.size} found",
            )
    }
}
