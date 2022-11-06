package software.design.lab1.lru

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class LRUCacheTest {

    @Test
    fun testPutAndGet() {
        val cache = LRUCache<Int, String>(CAPACITY)
        for (i in 1..CAPACITY) {
            assertNull(cache.put(i, i.toString()))
        }
        for (i in 1..CAPACITY) {
            assertEquals(cache.get(i), i.toString())
        }
    }

    @Test
    fun testRemove() {
        val cache = LRUCache<Int, String>(CAPACITY)
        for (i in 1..CAPACITY) {
            cache.put(i, i.toString())
        }
        for (i in 1..CAPACITY) {
            assertEquals(cache.remove(i), i.toString())
        }
        assertNull(cache.remove(6))
    }

    @Test
    fun testLoad() {
        val cache = LRUCache<Int, String>(CAPACITY)
        for (i in 1..3) {
            cache.put(i, i.toString())
        }
        for (i in 1..CAPACITY) {
            assertEquals(cache.load(i) { i.toString() }, i.toString())
        }
    }

    @Test
    fun testOverflow() {
        val cache = LRUCache<Int, String>(CAPACITY)
        for (i in 1..CAPACITY) {
            cache.put(i, i.toString())
        }
        for (i in (CAPACITY + 1)..3 * CAPACITY) {
            cache.put(i, i.toString())
            assertEquals(cache.size, CAPACITY)
            for (j in (i - CAPACITY + 1)..i) {
                assertEquals(cache.get(j), j.toString())
            }
        }
    }

    companion object {
        private const val CAPACITY = 5
    }
}
