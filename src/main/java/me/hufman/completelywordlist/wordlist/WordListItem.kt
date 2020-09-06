package me.hufman.completelywordlist.wordlist

import com.miguelfonseca.completely.data.Indexable

class WordListItem(val word: String, val f: Int, val flags: Set<String> = setOf(),
				   val originalFreq: Int? = null,
				   val shortcut: String? = null, val shortcuts: Map<String, Int>? = null,
				   val bigrams: Map<String, Int>? = null): Indexable {

	companion object {
		/** Helper function for basic parsing */
		fun parseWordListLine(line: String): WordListItem? {
			val parser =
				WordListItemParser()
			line.split(Regex("[\n\r]")).forEach {
				parser.addLine(it)
			}
			return parser.build()
		}
	}

	override fun getFields(): List<String> {
		return listOf(word.toLowerCase())
	}



	override fun equals(other: Any?): Boolean {
		if (this === other) return true
		if (javaClass != other?.javaClass) return false

		other as WordListItem

		if (word != other.word) return false
		if (f != other.f) return false
		if (flags != other.flags) return false
		if (originalFreq != other.originalFreq) return false
		if (shortcut != other.shortcut) return false
		if (shortcuts != other.shortcuts) return false
		if (bigrams != other.bigrams) return false

		return true
	}

	override fun hashCode(): Int {
		var result = word.hashCode()
		result = 31 * result + f
		result = 31 * result + flags.hashCode()
		result = 31 * result + (originalFreq ?: 0)
		result = 31 * result + (shortcut?.hashCode() ?: 0)
		result = 31 * result + (shortcuts?.hashCode() ?: 0)
		result = 31 * result + (bigrams?.hashCode() ?: 0)
		return result
	}

	override fun toString(): String {
		return "WordListItem(word='$word', f=$f, flags=$flags, originalFreq=$originalFreq, shortcut=$shortcut)"
	}


}