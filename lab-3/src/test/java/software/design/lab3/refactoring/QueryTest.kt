package software.design.lab3.refactoring

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class QueryTest : AbstractTest() {

    @Test
    fun `test empty sum`() {
        testQuery(
            "sum",
            false,
            listOf(
                "Summary price: ",
                "0",
            ),
        )
    }

    @Test
    fun `test example sum`() {
        testQuery(
            "sum",
            true,
            listOf(
                "Summary price: ",
                "14",
            ),
        )
    }

    @Test
    fun `test empty count`() {
        testQuery(
            "count",
            false,
            listOf(
                "Number of products: ",
                "0",
            ),
        )
    }

    @Test
    fun `test example count`() {
        testQuery(
            "count",
            true,
            listOf(
                "Number of products: ",
                "3",
            ),
        )
    }

    @Test
    fun `test empty max`() {
        testQuery(
            "max",
            false,
            listOf(
                "<h1>Product with max price: </h1>",
            ),
        )
    }

    @Test
    fun `test example max`() {
        testQuery(
            "max",
            true,
            listOf(
                "<h1>Product with max price: </h1>",
                "product 3\t8</br>",
            ),
        )
    }

    @Test
    fun `test empty min`() {
        testQuery(
            "min",
            false,
            listOf(
                "<h1>Product with min price: </h1>",
            ),
        )
    }

    @Test
    fun `test example min`() {
        testQuery(
            "min",
            true,
            listOf(
                "<h1>Product with min price: </h1>",
                "product 1\t2</br>",
            ),
        )
    }

    private fun testQuery(
        command: String,
        withTestData: Boolean,
        expected: List<String>,
    ) {
        if (withTestData) {
            addExampleData()
        }

        request(
            "/query",
            mapOf(
                "command" to command,
            ),
        ).apply {
            assertEquals(200, code)
            assertEquals(expected, pageContent)
        }
    }

    private fun addExampleData() {
        request(
            "/add-product",
            mapOf(
                "name" to "product 1",
                "price" to "2",
            ),
        )
        request(
            "/add-product",
            mapOf(
                "name" to "product 2",
                "price" to "4",
            ),
        )
        request(
            "/add-product",
            mapOf(
                "name" to "product 3",
                "price" to "8",
            ),
        )
    }
}
