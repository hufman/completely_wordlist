package me.hufman.completelywordlist

import com.miguelfonseca.completely.AutocompleteEngine
import me.hufman.completelywordlist.utils.IteratorCollection
import me.hufman.completelywordlist.wordlist.WordListItem
import me.hufman.completelywordlist.wordlist.WordListLoader
import me.hufman.completelywordlist.wordlist.WordListReader

class CompletelyWordList {
	companion object {

		@JvmStatic
		fun main(args: Array<String>) {
			val index = WordListIndex()
			val engine = AutocompleteEngine.Builder<WordListItem>()
				.setIndex(index)
				.setComparator(compareByDescending { it.score - 1.0 / (it.`object`?.f ?: 1) })
				.build()

			var language = args.getOrNull(1) ?: "en_US"
			val reader = WordListLoader().load(language)
			engine.addAll(
				IteratorCollection(
					WordListReader(reader)
				)
			)
			println("Loaded ${index.size()} words")

			var input = args.getOrNull(2) ?: ""
			if (input.isNotBlank()) {
				println("Searching for $input")
				for (word in engine.search(input, 10)) {
					println(" - $word")
				}
			}

			val console = System.console()
			if (console != null) {
				input = "sample"
				while (input.isNotBlank()) {
					print("Search word: ")
					input = console.readLine().trim()
					for (word in engine.search(input, 10)) {
						println(" - $word")
					}
				}
			} else {
				println("No System Console found, pass in a word after the language")
			}
		}
	}
}