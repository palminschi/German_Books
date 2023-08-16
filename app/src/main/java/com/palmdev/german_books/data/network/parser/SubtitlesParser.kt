package com.palmdev.german_books.data.network.parser

import com.palmdev.german_books.data.model.SubtitleDto

object SubtitlesParser {
    fun parseSrtByteArray(byteArray: ByteArray): List<SubtitleDto> {
        val subtitleList = mutableListOf<SubtitleDto>()
        val srtText = byteArray.toString(Charsets.UTF_8) // convert the byte array to a UTF-8 string
        val subtitleBlocks = srtText.trim().split("\n\n") // split the string into subtitle blocks

        for (subtitleBlock in subtitleBlocks) {
            val subtitleLines = subtitleBlock.trim().split("\n") // split the subtitle block into lines
            val number = subtitleLines[0].toInt() // the first line is the subtitle number
            val times = subtitleLines[1].split(" --> ") // the second line is the start and end times
            val startTime = parseTimecode(times[0])
            val endTime = parseTimecode(times[1])
            val text = subtitleLines.subList(2, subtitleLines.size).joinToString("\n") // the rest of the lines are the subtitle text

            val subtitleDto = SubtitleDto(number, startTime, endTime, text)
            subtitleList.add(subtitleDto)
        }

        return subtitleList
    }

    fun parseTimecode(timecode: String): Double {
        val parts = timecode.split(":")
        val hours = parts[0].toDouble()
        val minutes = parts[1].toDouble()
        val seconds = parts[2].replace(",", ".").toDouble() // replace comma with period to handle decimal fractions
        return hours * 3600 + minutes * 60 + seconds
    }

}