package me.hufman.completelywordlist

import com.miguelfonseca.completely.IndexAdapter
import com.miguelfonseca.completely.data.ScoredObject
import com.miguelfonseca.completely.text.index.PatriciaTrie
import com.miguelfonseca.completely.text.match.EditDistanceAutomaton
import me.hufman.completelywordlist.wordlist.WordListItem
import kotlin.math.ln
import kotlin.math.max


class WordListIndex: IndexAdapter<WordListItem> {
	private val index = PatriciaTrie<WordListItem>()

	override fun put(token: String?, value: WordListItem?): Boolean {
		return index.put(token, value)
	}

	override fun remove(value: WordListItem?): Boolean {
		return index.remove(value)
	}

	override fun get(token: String?): Collection<ScoredObject<WordListItem>> {
		if (token == null) {
			return setOf()
		}
		val threshold = ln(max(token.length - 1, 1).toDouble())
		return index.getAny(EditDistanceAutomaton(token, threshold))
	}

	fun size(): Int {
		return index.size()
	}
}