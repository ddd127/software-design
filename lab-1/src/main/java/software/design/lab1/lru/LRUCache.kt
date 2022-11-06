package software.design.lab1.lru

class LRUCache<KEY : Any, VALUE : Any>(
    private val capacity: Int,
) {

    init {
        assert(capacity >= 0)
    }

    private var currentSize: Int = 0

    inner class Entry(
        var prev: Entry?,
        var next: Entry?,
        val key: KEY,
        val value: VALUE,
    )

    private val keyToEntry: MutableMap<KEY, Entry> = HashMap(capacity)
    private var headEntry: Entry? = null
    private var tailEntry: Entry? = null

    fun load(key: KEY, loader: (KEY) -> VALUE): VALUE = withStateDump { old ->
        get(key)?.let { value ->
            return value.also {
                assert(old.size == currentSize)
                assert(key in keyToEntry)
                assert(headEntry?.key == key && headEntry?.value == value)
            }
        }
        val value = loader(key)
        put(key, value)
        return value.also {
            assert(old.size + 1 == currentSize || currentSize == capacity)
            assert(key in keyToEntry)
            assert(headEntry?.key === key && headEntry?.value === value)
        }
    }

    fun get(key: KEY): VALUE? = withStateDump { old ->
        val entry = keyToEntry[key] ?: return null.also {
            assert(old.size == currentSize)
            assert(key !in keyToEntry)
            assert(headEntry === old.head)
        }
        moveToHead(entry)
        return entry.value.also {
            assert(old.size == currentSize)
            assert(key in keyToEntry)
            assert(headEntry === entry)
        }
    }

    fun put(key: KEY, value: VALUE): VALUE? = withStateDump { old ->
        val entry = Entry(null, null, key, value)
        return put(entry)?.value.also {
            assert(old.size + 1 == currentSize || currentSize == capacity)
            assert(key in keyToEntry)
            assert(headEntry === entry)
        }
    }

    fun remove(key: KEY): VALUE? = withStateDump { old ->
        val entry = keyToEntry[key] ?: return null.also {
            assert(currentSize == old.size)
            assert(key !in keyToEntry)
            assert(old.head === headEntry)
        }
        return remove(entry)?.value.also {
            assert(currentSize == old.size - 1)
            assert(key !in keyToEntry)
        }
    }

    val size: Int
        get() = currentSize

    private fun moveToHead(entry: Entry) {
        removeFromList(entry)
        putToList(entry)
    }

    private fun removeTailIfOver() {
        if (currentSize > capacity) {
            val last = tailEntry ?: return
            remove(last)
        }
    }

    private fun put(entry: Entry): Entry? {
        putToList(entry)
        val previousEntry = keyToEntry.put(entry.key, entry)
        if (previousEntry == null) {
            ++currentSize
        }
        removeTailIfOver()
        return previousEntry
    }

    private fun putToList(entry: Entry) {
        entry.prev = null
        entry.next = headEntry
        headEntry?.run { prev = entry }
        headEntry = entry
        if (tailEntry == null) {
            tailEntry = entry
        }
    }

    private fun remove(entry: Entry): Entry? {
        removeFromList(entry)
        val previousEntry = keyToEntry.remove(entry.key)
        if (previousEntry != null) {
            --currentSize
        }
        return previousEntry
    }

    private fun removeFromList(entry: Entry) {
        val prev = entry.prev
        val next = entry.next
        if (prev == null) { // entry is head
            headEntry = next
        } else {
            prev.next = next
        }
        if (next == null) { // entry is tail
            tailEntry = prev
        } else {
            next.prev = prev
        }
    }


    private inline fun <T> withStateDump(block: (StateDump) -> T): T {
        val dump = StateDump(currentSize, headEntry)
        return block(dump)
    }

    inner class StateDump(
        val size: Int,
        val head: Entry?,
    )
}
