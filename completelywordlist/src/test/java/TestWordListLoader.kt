import me.hufman.completelywordlist.wordlist.WordListLoader
import me.hufman.completelywordlist.wordlist.WordListReader
import org.junit.Assert.assertTrue
import org.junit.Test

class TestWordListLoader {
	@Test
	fun testDiscovery() {
		val loader = WordListLoader()
		val discovered = loader.discover()
		println("Discovered these languages for download: $discovered")
		assertTrue(discovered.containsAll(setOf("de", "en", "en_GB", "es")))
	}

	@Test
	fun testDownload() {
		val loader = WordListLoader()
		val downloader = loader.load("en")
		val words = WordListReader(
			downloader
		).asSequence().toList()
		assertTrue(words.any {it.word == "found"})
	}
}