package com.palmdev.german_books.extensions

fun String.separateString(chunkSize: Int): List<String> {
    val result = mutableListOf<String>()
    var index = 0

    while (index < this.length) {
        val endIndex = minOf(index + chunkSize, this.length)
        result.add(this.substring(index, endIndex))
        index += chunkSize
    }

    return result
}