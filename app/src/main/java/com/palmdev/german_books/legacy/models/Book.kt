package com.palmdev.german_books.legacy.models

data class Book(
    val bookID: String,
    val bookImg: Int,
    val bookTitle: String,
    val bookAuthor: String,
    val bookLevel: String,
    val bookAccess: Boolean,
    val bookCategory01: String,
    val bookCategory02: String,
    val bookCategory03: String
)
