package me.hufman.completelywordlist.wordlist

import java.io.Reader

/** Given a Reader that represents an Android-formatted Word List
 * this functions as an Iterator yielding up parsed WordListItem with each next() call
 */
class WordListReader(reader: Reader): Iterator<WordListItem> {
	val reader = reader.buffered()
	private var nextItem: WordListItem? = null

	init {
		bufferNextWord()
	}

	override fun hasNext(): Boolean {
		return nextItem != null
	}

	override fun next(): WordListItem {
		try {
			return nextItem ?: throw NoSuchElementException()
		} finally {
			bufferNextWord()
		}
	}

	private fun bufferNextWord() {
		nextItem = getNextWord()
	}

	private fun getNextWord(): WordListItem? {
		var parser: WordListItemParser? = null
		while (true) {
			// check that we haven't found a new word
			reader.mark(300)
			val peekLine = reader.readLine()?.trim() ?: break	// break if we're at EOF
			reader.reset()
			if (peekLine.startsWith("word=") && parser != null) {
				break
			}

			// actually try to parse the line
			val line = reader.readLine()?.trim() ?: break
			if (line.startsWith("word=")) {
				parser = WordListItemParser()
				parser.addLine(line)
			} else if (line.startsWith("shortcut=") || line.startsWith("bigram=")) {
				parser?.addLine(line)
			}
		}
		return parser?.build()
	}
}