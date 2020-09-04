package me.hufman.completelywordlist.wordlist

import java.io.*
import java.net.URL
import java.util.zip.GZIPInputStream

class WordListLoader {
	val ROOT_URL = "https://git.replicant.us/LineageOS-mirror/android_packages_inputmethods_LatinIME/plain/dictionaries/"

	private fun openUrl(url: String): InputStream {
		val urlConnection = URL(url)
		return urlConnection.openConnection().inputStream
	}

	fun discover(): List<String> {
		val matcher = Regex(">([a-zA-Z_]+)_wordlist\\.combined\\.gz</")
		val languages = openUrl(ROOT_URL).bufferedReader().readLines().mapNotNull { line ->
			val result = matcher.find(line)
			result?.groups?.get(1)?.value
		}
		return languages
	}

	fun download(language: String): InputStream {
		val url = "${ROOT_URL}${language}_wordlist.combined.gz"
		return openUrl(url)
	}

	fun load(language: String): Reader {
		val inputStream = try {
			FileInputStream(File("build/wordlists/${language}_wordlist.combined.gz"))
		} catch (e: IOException) {
			download(language)
		}
		return BufferedReader(InputStreamReader(GZIPInputStream(inputStream)))
	}
}