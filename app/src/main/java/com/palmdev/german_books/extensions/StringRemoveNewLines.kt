package com.palmdev.german_books.extensions

fun String.removeNewLines(): String {
    return this.replace("\n", "")
}