package com.palmdev.german_books.extensions

fun String.splitToList(): List<String> {
    val list = this.removeNewLines().split(";").toMutableList()
    for (i in list.indices) {
        if (list[i].isNotEmpty()) {
            list[i] = list[i].trim()
        } else {
            list.removeAt(i)
        }
    }
    return list
}