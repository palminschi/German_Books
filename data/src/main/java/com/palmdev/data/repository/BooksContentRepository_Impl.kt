package com.palmdev.data.repository

import com.palmdev.data.storage.BooksContentStorage
import com.palmdev.data.storage.model.BookContentEntity
import com.palmdev.domain.model.BookContent
import com.palmdev.domain.repository.BooksContentRepository

class BooksContentRepository_Impl(private val booksContentStorage: BooksContentStorage) :
    BooksContentRepository {

    override fun getBookContentById(id: Int): BookContent {
        val bookContentEntity = booksContentStorage.getBookContentById(bookId = id)
        return mapBookToDomain(bookContentEntity)
    }

    override fun saveBookReadingProgress(bookId: Int, readingProgress: Int) {
        booksContentStorage.saveReadingProgress(bookId = bookId, readingProgress = readingProgress)
    }

    private fun mapBookToDomain(bookContentEntity: BookContentEntity) : BookContent {
        return BookContent(
            id = bookContentEntity.id,
            content = bookContentEntity.content,
            readingProgress = bookContentEntity.readingProgress
        )
    }

}