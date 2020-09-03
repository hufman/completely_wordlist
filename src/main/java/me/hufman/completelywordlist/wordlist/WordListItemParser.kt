package me.hufman.completelywordlist.wordlist

/**
 * Given one or more lines from a word list, builds up a WordListItem
 * The first line should start with "word=", any later lines should be "shortcut=" or "bigram="
 *
 * Format is specified in https://android.googlesource.com/platform/packages/inputmethods/LatinIME/+/master/dictionaries/sample.combined
 */
class WordListItemParser {
	var word: String? = null
	var f: Int? = null
	var flags = mutableSetOf<String>()
	var originalFreq: Int? = null
	val shortcutsConstructor = lazy { HashMap<String, Int>() }
	val shortcuts by shortcutsConstructor
	val bigramsConstructor = lazy { HashMap<String, Int>() }
	val bigrams by bigramsConstructor

	fun addLine(line: String) {
		val fields = line.trim().split(',')
		val mode = fields.getOrNull(0)?.split('=')?.getOrNull(0) ?: return

		var thisShortcut: String? = null
		var thisBigram: String? = null
		var thisF: Int? = null

		for (field in fields) {
			val pair = field.split('=', limit = 2)
			if (pair.size == 2) {
				when (pair[0]) {
					"word" -> word = pair[1]
					"f" -> when (mode) {
						"word" -> f = pair[1].toIntOrNull(10) ?: f
						"shortcut" -> if (pair[1] == "whitelist") {
							thisF = 15
						} else {
							thisF = pair[1].toIntOrNull(10) ?: thisF
						}
						"bigram" -> thisF = pair[1].toIntOrNull(10) ?: thisF
					}
					"flags" -> if (pair[1].isNotBlank()) flags.addAll(pair[1].split(':'))
					"not_a_word" -> flags.add("not_a_word")
					"originalFreq" -> originalFreq = pair[1].toIntOrNull(10) ?: originalFreq
					"shortcut" -> thisShortcut = pair[1]
					"bigram" -> thisBigram = pair[1]
				}
			}
		}
		if (thisShortcut != null && thisF != null) {
			shortcuts[thisShortcut] = thisF
		}
		if (thisBigram != null && thisF != null) {
			bigrams[thisBigram] = thisF
		}
	}

	fun build(): WordListItem? {
		val shortcuts = if (shortcutsConstructor.isInitialized()) { this.shortcuts } else { null }
		val shortcut = shortcuts?.entries?.maxBy { it.value	}?.key
		val bigrams = if (bigramsConstructor.isInitialized()) { this.bigrams } else { null }

		val word = word ?: return null
		val f = f ?: return null

		return WordListItem(
			word,
			f,
			flags,
			originalFreq,
			shortcut,
			shortcuts,
			bigrams
		)
	}
}