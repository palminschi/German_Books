package com.palmdev.german_books.extensions

fun String.toLettersOnly(): String {
    return this.lowercase().removeNewLines()
        .replace("\\s".toRegex(), "")
        .replace("?", "")
        .replace("!", "")
        .replace(".", "")
        .replace(",", "")
        .replace("\'", "")
        .replace("\"", "")
        .replace("”", "")
        .replace("“", "")
        .replace(" ", "")
}