package me.hufman.completelywordlist.utils

/** Converts an Iterator into a read-once Collection */
class IteratorCollection<E>(val iterator: Iterator<E>): Collection<E> {
	override val size: Int
		get() = if (isEmpty()) 0 else 1

	override fun contains(element: E): Boolean {
		return false
	}

	override fun containsAll(elements: Collection<E>): Boolean {
		return false
	}

	override fun isEmpty(): Boolean {
		return !iterator.hasNext()
	}

	override fun iterator(): Iterator<E> {
		return iterator
	}

}