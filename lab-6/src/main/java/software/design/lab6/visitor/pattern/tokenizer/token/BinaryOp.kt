package software.design.lab6.visitor.pattern.tokenizer.token

sealed interface BinaryOp : Token {
    fun evaluate(left: Int, right: Int): Int
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
