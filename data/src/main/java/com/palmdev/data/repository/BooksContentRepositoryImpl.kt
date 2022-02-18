package com.palmdev.data.repository

import com.palmdev.data.storage.books.BooksContentStorage
import com.palmdev.data.storage.books.model.BookContentEntity
import com.palmdev.data.storage.books.model.BookReadingProgressEntity
import com.palmdev.domain.model.BookContent
import com.palmdev.domain.model.BookReadingProgress
import com.palmdev.domain.repository.BooksContentRepository

class BooksContentRepositoryImpl(private val booksContentStorage: BooksContentStorage) :
    BooksContentRepository {

    override fun getBookContentById(id: Int): BookContent {
        val bookContentEntity = booksContentStorage.getBookContentById(bookId = id)
        return mapBookToDomain(bookContentEntity)
    }

    override fun saveBookReadingProgress(readingProgress: BookReadingProgress) {
        booksContentStorage.saveReadingProgress(
            readingProgress = mapReadingProgressToData(readingProgress)
        )
    }

    override fun getBookReadingProgress(bookId: Int): BookReadingProgress {
        return mapReadingProgressToDomain(
            booksContentStorage.getReadingProgress(bookId = bookId)
        )
    }


    // Mappers
    private fun mapBookToDomain(bookContentEntity: BookContentEntity): BookContent {
        return BookContent(
            id = bookContentEntity.id,
            content = bookContentEntity.content
        )
    }

    private fun mapReadingProgressToData(bookReadingProgress: BookReadingProgress): BookReadingProgressEntity {
        return BookReadingProgressEntity(
            bookId = bookReadingProgress.bookId,
            currentPage = bookReadingProgress.currentPage,
            totalPages = bookReadingProgress.totalPages
        )
    }

    private fun mapReadingProgressToDomain(bookReadingProgressEntity: BookReadingProgressEntity): BookReadingProgress {
        return BookReadingProgress(
            bookId = bookReadingProgressEntity.bookId,
            currentPage = bookReadingProgressEntity.currentPage,
            totalPages = bookReadingProgressEntity.totalPages
        )
    }

}