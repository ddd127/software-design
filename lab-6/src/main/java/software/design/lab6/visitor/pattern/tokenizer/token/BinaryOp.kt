package software.design.lab6.visitor.pattern.tokenizer.token

import software.design.lab6.visitor.pattern.visitor.Visitor

sealed interface BinaryOp : Token {
    fun evaluate(left: Int, right: Int): Int
    override fun accept(visitor: Visitor<*>) {
        visitor.visit(this)
    }
}

object Add : BinaryOp {
    override fun evaluate(left: Int, right: Int): Int = left + right
}

object Sub : BinaryOp {
    override fun evaluate(left: Int, right: Int): Int = left - right
}

object Mul : BinaryOp {
    override fun evaluate(left: Int, right: Int): Int = left * right
}

object Div : BinaryOp {
    override fun evaluate(left: Int, right: Int): Int = left / right
}
