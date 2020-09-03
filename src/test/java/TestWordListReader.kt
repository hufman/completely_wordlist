import me.hufman.completelywordlist.wordlist.WordListItem
import me.hufman.completelywordlist.wordlist.WordListReader
import org.junit.Assert.assertEquals
import org.junit.Test
import java.io.StringReader

class TestWordListReader {
	@Test
	fun testReader() {
		val data = "dictionary=main:en,locale=en,description=English,date=1402373178,version=47\n" +
				" word=tread,f=85,flags=,originalFreq=85\n" +
				"junk lol\n" +
				" word=yall,f=0,not_a_word=true\n" +
				"  shortcut=y'all,f=whitelist\n" +
				"  shortcut=yins,f=2\n" +
				" word=sample,f=200\n" +
				"  bigram=wordlist,f=243\n" +
				"  shortcut=sample,f=3\n"
		val results = WordListReader(
			StringReader(data)
		).asSequence().toList()
		assertEquals(3, results.size)
		assertEquals(
			WordListItem(
				"tread",
				85,
				originalFreq = 85
			), results[0])
		assertEquals(
			WordListItem(
				"yall", 0, flags = setOf("not_a_word"),
				shortcut = "y'all", shortcuts = mapOf("y'all" to 15, "yins" to 2)
			), results[1])
		assertEquals(
			WordListItem(
				"sample", 200,
				shortcut = "sample", shortcuts = mapOf("sample" to 3),
				bigrams = mapOf("wordlist" to 243)
			), results[2])
	}
}