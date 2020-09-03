import me.hufman.completelywordlist.wordlist.WordListItem
import org.junit.Assert.assertEquals
import org.junit.Test

class TestWordListItem {
	@Test
	fun testEmptyParse() {
		assertEquals(null, WordListItem.parseWordListLine(""))
		assertEquals(null, WordListItem.parseWordListLine("word=space,freq=1"))
	}

	@Test
	fun testParse() {
		val parsed = WordListItem.parseWordListLine("word=space,f=1,flags=")
		assertEquals(0, parsed?.flags?.size)
		assertEquals(
			WordListItem(
				"space",
				1,
				setOf()
			), WordListItem.parseWordListLine("word=space,f=1"))
		assertEquals(
			WordListItem(
				"space",
				1,
				setOf()
			), WordListItem.parseWordListLine("word=space,f=1,flags="))
		assertEquals(
			WordListItem(
				"space",
				1,
				setOf("offensive", "test")
			),
			WordListItem.parseWordListLine("word=space,f=1,flags=offensive:test"))
		assertEquals(
			WordListItem(
				"cuss",
				0,
				setOf("offensive"),
				originalFreq = 1
			),
			WordListItem.parseWordListLine("word=cuss,f=0,flags=offensive,originalFreq=1"))
		assertEquals(
			WordListItem(
				"whove", 0, setOf("not_a_word"),
				shortcuts = mapOf("who've" to 15), shortcut = "who've"
			),
			WordListItem.parseWordListLine("word=whove,f=0,not_a_word=true\nshortcut=who've,f=whitelist"))
		assertEquals(
			WordListItem(
				"sample",
				200,
				bigrams = mapOf("wordlist" to 243)
			),
			WordListItem.parseWordListLine(" word=sample,f=200\nbigram=wordlist,f=243"))
	}

	@Test
	fun testKeyField() {
		assertEquals(listOf("whove"), WordListItem(
			"whove",
			0,
			setOf("not_a_word"),
			shortcut = "who've"
		).fields)
	}
}