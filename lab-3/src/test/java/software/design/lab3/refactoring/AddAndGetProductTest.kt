package software.design.lab3.refactoring

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class AddAndGetProductTest : AbstractTest() {

    @Test
    fun `test empty products`() {
        request("/get-products")
            .apply {
                assertEquals(200, code)
                assertTrue(pageContent.isEmpty())
            }
    }

    @Test
    fun `test one product`() {
        request(
            "/add-product",
            mapOf(
                "name" to "product",
                "price" to "2",
            ),
        ).apply {
            assertEquals(200, code)
            assertEquals(listOf("OK"), pageContent)
        }

        request("/get-products")
            .apply {
                assertEquals(200, code)
                assertEquals(listOf("product\t2</br>"), pageContent)
            }
    }

    @Test
    fun `test many products`() {
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

        request("/get-products")
            .apply {
                assertEquals(200, code)
                assertEquals(
                    listOf(
                        "product 1\t2</br>",
                        "product 2\t4</br>",
                        "product 3\t8</br>",
                    ),
                    pageContent,
                )
            }
    }
}
