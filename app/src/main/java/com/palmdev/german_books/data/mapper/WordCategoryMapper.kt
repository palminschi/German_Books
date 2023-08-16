package com.palmdev.german_books.data.mapper

import android.content.Context
import com.palmdev.german_books.data.model.WordCategoryDto
import com.palmdev.german_books.domain.model.WordCategory
import com.palmdev.german_books.extensions.splitToList

object WordCategoryMapper {

    fun toDomain(dto: WordCategoryDto, context: Context): WordCategory {
        return WordCategory(
            id = dto.id,
            categoryId = dto.category_id,
            categoryName = getCategoryNameFromId(context, id = dto.id),
            categoryIconUrl = dto.icon_url,
            progress = dto.progress,
            wordsId = dto.wordsId.splitToList()
        )
    }

    fun toData(category: WordCategory): WordCategoryDto {
        return WordCategoryDto(
            id = category.id,
            category_id = category.categoryId,
            icon_url = category.categoryIconUrl,
            progress = category.progress,
            wordsId = category.wordsId.joinToString(separator = ";"),
        )
    }

    private fun getCategoryNameFromId(context: Context, id: Int): String {
        return "" // TODO: IMPLEMENT
    }
}