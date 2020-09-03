import com.miguelfonseca.completely.AutocompleteEngine
import me.hufman.completelywordlist.WordListIndex
import me.hufman.completelywordlist.wordlist.WordListItem
import org.junit.Assert.assertEquals
import org.junit.Test

class TestWordListIndex {
	@Test
	fun testSearch() {
		val index = WordListIndex()
		val engine = AutocompleteEngine.Builder<WordListItem>()
			.setIndex(index)
			.setComparator(compareByDescending { it.score - 1.0 / (it.`object`?.f ?: 1) })
			.build()

		engine.add(
			WordListItem(
				"treat",
				120
			)
		)
		engine.add(
			WordListItem(
				"tread",
				85
			)
		)
		engine.add(
			WordListItem(
				"missing",
				129
			)
		)

		assertEquals(3, index.size())

		val exactResults = engine.search("tread")
		assertEquals(2, exactResults.size)
		assertEquals("tread", exactResults[0].word)
		assertEquals("treat", exactResults[1].word)

		val closeResults = engine.search("treah")
		assertEquals(2, closeResults.size)
		assertEquals("treat", closeResults[0].word)
		assertEquals("tread", closeResults[1].word)

		engine.remove(
			WordListItem(
				"treat",
				120
			)
		)
		assertEquals(2, index.size())
		val removedResults = engine.search("treat")
		assertEquals(1, removedResults.size)
		assertEquals("tread", removedResults[0].word)
	}
}