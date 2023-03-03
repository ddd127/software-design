package software.design.lab6.visitor.pattern.tokenizer.token

import software.design.lab6.visitor.pattern.visitor.Visitor

sealed interface Brace : Token {
    override fun accept(visitor: Visitor<*>) {
        visitor.visit(this)
    }
}

object OpenBrace : Brace

object CloseBrace : Brace

