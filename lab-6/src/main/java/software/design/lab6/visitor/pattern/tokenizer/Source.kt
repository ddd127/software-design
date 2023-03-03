package software.design.lab6.visitor.pattern.tokenizer

import java.io.InputStream

class Source(input: InputStream, bufferSize: Int = DEFAULT_BUFFER_SIZE) {

    private val input = input.buffered(bufferSize)
    private var buffer: Char? = this.input.nextOrNull()

    fun current(): Char? = buffer
    fun consume(): Char? = buffer.also { buffer = input.nextOrNull() }
    fun skipBlank() {
        while (buffer?.isWhitespace() == true) {
            buffer = input.nextOrNull()
        }
    }

    companion object {
        private fun InputStream.nextOrNull(): Char? {
            val int = read()
            return if (int == -1) null else Char(int)
        }
    }
}
