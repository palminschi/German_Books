package com.palmdev.german_books.data.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.palmdev.german_books.data.model.SubtitleDto

class SubtitleDtoConverter {

    @TypeConverter
    fun fromJson(json: String): List<SubtitleDto> {
        val type = object : TypeToken<List<SubtitleDto>>() {}.type
        return Gson().fromJson(json, type)
    }

    @TypeConverter
    fun toJson(subtitleDtoList: List<SubtitleDto>): String {
        return Gson().toJson(subtitleDtoList)
    }

}