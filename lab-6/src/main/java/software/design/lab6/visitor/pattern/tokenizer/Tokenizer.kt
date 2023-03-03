package software.design.lab6.visitor.pattern.tokenizer

import software.design.lab6.visitor.pattern.tokenizer.token.Add
import software.design.lab6.visitor.pattern.tokenizer.token.CloseBrace
import software.design.lab6.visitor.pattern.tokenizer.token.Div
import software.design.lab6.visitor.pattern.tokenizer.token.Mul
import software.design.lab6.visitor.pattern.tokenizer.token.Num
import software.design.lab6.visitor.pattern.tokenizer.token.OpenBrace
import software.design.lab6.visitor.pattern.tokenizer.token.Sub
import software.design.lab6.visitor.pattern.tokenizer.token.Token

interface Tokenizer {
    fun tokenize(source: Source): List<Token>
}

object DefaultTokenizer : Tokenizer {

    override fun tokenize(source: Source): List<Token> {
        var state: ParserState = ParserState.Start
        val result = ParseResult()
        while (true) {
            when (state) {
                is ParserState.Transient -> {
                    state = state.process(source, result)
                }
                is ParserState.End -> {
                    return result.tokens
                }
                is ParserState.Error -> {
                    throw IllegalArgumentException(state.message)
                }
            }
        }
    }
}

private class ParseResult {
    val tokens: MutableList<Token> = mutableListOf()
}

private sealed interface ParserState {

    sealed interface Terminal : ParserState
    sealed interface Transient : ParserState {
        fun process(source: Source, result: ParseResult): ParserState
    }

    object Start : Transient {
        override fun process(source: Source, result: ParseResult): ParserState {
            source.skipBlank()
            return when (val current = source.current()) {
                null -> End
                in braces -> Braces
                in ops -> Operation
                in digits -> Number()
                else -> {
                    Error("Expected '(' or digit, but got `$current`")
                }
            }
        }
    }

    object End : Terminal

    class Error(val message: String) : Terminal

    class Number : Transient {
        val number = StringBuilder()

        override fun process(source: Source, result: ParseResult): ParserState {
            if (number.isEmpty() && source.current() == '-') {
                number.append(source.consume())
                return this
            }
            val current = source.current()
            if (current == null || current !in digits) {
                if (number.isEmpty() || number.contentEquals("-")) {
                    return Error("Expected digit but found ${if (current == null) "end of line" else "current"}")
                }
                result.tokens.add(Num(number.toString().toInt()))
                return Start
            }
            number.append(source.consume())
            return this
        }
    }

    object Operation : Transient {

        override fun process(source: Source, result: ParseResult): ParserState {
            val current = source.consume()
            return if (current == null || current !in ops) {
                Error("Expected operation but found ${if (current == null) "end of line" else "current"}")
            } else {
                result.tokens += ops.getValue(current)
                Start
            }
        }
    }

    object Braces : Transient {

        override fun process(source: Source, result: ParseResult): ParserState {
            val current = source.consume()
            return if (current == null || current !in braces) {
                Error("Expected brace but found ${if (current == null) "end of line" else "current"}")
            } else {
                result.tokens += braces.getValue(current)
                Start
            }
        }
    }

    companion object {

        private val digits = '0'..'9'
        private val ops = mapOf(
            '+' to Add,
            '-' to Sub,
            '*' to Mul,
            '/' to Div,
        )
        private val braces = mapOf(
            '(' to OpenBrace,
            ')' to CloseBrace,
        )
    }
}
