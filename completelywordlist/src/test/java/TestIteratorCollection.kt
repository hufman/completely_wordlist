import me.hufman.completelywordlist.utils.IteratorCollection
import org.junit.Assert.*
import org.junit.Test

class TestIteratorCollection {
	@Test
	fun testIteratorCollection() {
		val data = listOf("a", "b", "c")
		val iterator = data.iterator()
		val collection = IteratorCollection(
			iterator
		) as Collection<String>

		assertFalse("not expected to support contains",
			collection.contains("a"))
		assertFalse("not expected to support contains",
			collection.containsAll(listOf("a", "b")))
		assertEquals("Unknown size, but not empty",
			1, collection.size)
		assertFalse(collection.isEmpty())

		val consumed = ArrayList(collection)
		assertEquals(listOf("a", "b", "c"), consumed)

		assertEquals("Empty size",
			0, collection.size)
		assertTrue(collection.isEmpty())
	}
}