package com.palmdev.domain.model

import java.io.InputStream

data class Book(
    val id: Int,
    val title: String,
    val author: String,
    val level: String,
    val image: InputStream,
    val premium: Boolean
)
