package software.design.lab6.visitor.pattern.tokenizer.token

import software.design.lab6.visitor.pattern.visitor.Visitor

sealed interface BinaryOp : Token {

    val precedence: Precedence

    fun evaluate(left: Int, right: Int): Int

    override fun accept(visitor: Visitor<*>) {
        visitor.visit(this)
    }

    enum class Precedence {
        ADD_SUB,
        MUL_DIV,
        ;
    }
}

object Add : BinaryOp {
    override val precedence: BinaryOp.Precedence = BinaryOp.Precedence.ADD_SUB

    override fun evaluate(left: Int, right: Int): Int = left + right
}

object Sub : BinaryOp {
    override val precedence: BinaryOp.Precedence = BinaryOp.Precedence.ADD_SUB

    override fun evaluate(left: Int, right: Int): Int = left - right
}

object Mul : BinaryOp {
    override val precedence: BinaryOp.Precedence = BinaryOp.Precedence.MUL_DIV

    override fun evaluate(left: Int, right: Int): Int = left * right
}

object Div : BinaryOp {
    override val precedence: BinaryOp.Precedence = BinaryOp.Precedence.MUL_DIV

    override fun evaluate(left: Int, right: Int): Int = left / right
}
