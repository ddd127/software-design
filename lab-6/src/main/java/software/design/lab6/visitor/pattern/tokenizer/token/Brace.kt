package software.design.lab6.visitor.pattern.tokenizer.token

sealed interface Brace : Token

object OpenBrace : Brace

object CloseBrace : Brace

