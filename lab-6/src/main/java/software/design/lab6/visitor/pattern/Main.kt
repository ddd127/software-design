package software.design.lab6.visitor.pattern

import software.design.lab6.visitor.pattern.tokenizer.DefaultTokenizer
import software.design.lab6.visitor.pattern.tokenizer.Source
import software.design.lab6.visitor.pattern.visitor.CalcVisitor
import software.design.lab6.visitor.pattern.visitor.ParseVisitor
import software.design.lab6.visitor.pattern.visitor.PrintVisitor

fun main() {
    println(
        """
            Hello, this is simple calculator!
            Write your expression here and we will evaluate it for you
        """.trimIndent(),
    )
    while (iteration()) {
        // nothing
    }
}

private fun iteration(): Boolean {
    val input = readInput() ?: return false
    if (input == "exit" || input == "q" || input == ":q") {
        println("Got exit command, good bye!")
        return false
    }
    doCalculations(input)
    return true
}

private fun readInput(): String? {
    var count = 0
    while (true) {
        print("> ")
        try {
            return readln()
        } catch (e: Exception) {
            if (++count == 5) {
                println("Failed to read your input for 5 times, exiting")
                return null
            } else {
                println("Failed to read your input, please, try again:")
            }
        }
    }
}

private fun doCalculations(string: String) {
    try {
        val rawTokens = DefaultTokenizer.tokenize(Source(string.byteInputStream()))

        val stringResult = PrintVisitor().visit(rawTokens)
        println("original  expression:    $stringResult")
        val postfixResult = ParseVisitor().visit(rawTokens)
        println("postfix   expression:    ${PrintVisitor().visit(postfixResult)}")
        val evaluationResult = CalcVisitor().visit(postfixResult)
        println("evaluated expression:    $evaluationResult")

    } catch (e: Exception) {
        println("Failed to execute your request due to ${e.message}")
    }
}
