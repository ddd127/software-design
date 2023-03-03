package software.design.lab6.visitor.pattern.tokenizer.token

import software.design.lab6.visitor.pattern.visitor.Visitor

sealed interface Terminal : Token {
    fun evaluate(): Int
}

data class Num(
    private val value: Int,
) : Terminal {
    override fun evaluate(): Int = value

    override fun accept(visitor: Visitor<*>) {
        visitor.visit(this)
    }
}
