package com.palmdev.german_books.utils

import com.palmdev.german_books.domain.model.Language

object Languages {
    val AR = Language(code = "ar", name = "عربي", imageUrl = "${BASE_URL}wp-content/uploads/2023/03/arabic.png")
    val EN = Language(code = "en", name = "English", imageUrl = "${BASE_URL}wp-content/uploads/2023/03/usa.png")
    val ES = Language(code = "es", name = "Español", imageUrl = "${BASE_URL}wp-content/uploads/2023/03/spain-1.png")
    val FR = Language(code = "fr", name = "Français", imageUrl = "${BASE_URL}wp-content/uploads/2023/03/france.png")
    val DE = Language(code = "de", name = "Deutsch", imageUrl = "${BASE_URL}wp-content/uploads/2023/03/germany.png")
    val ZH = Language(code = "zh", name = "中文", imageUrl = "${BASE_URL}wp-content/uploads/2023/03/china.png")
    val HR = Language(code = "hr", name = "Hrvatski", imageUrl = "${BASE_URL}wp-content/uploads/2023/03/croatia.png")
    val CS = Language(code = "cs", name = "Česky", imageUrl = "${BASE_URL}wp-content/uploads/2023/03/czech-republic.png")
    val DA = Language(code = "da", name = "Dansk", imageUrl = "${BASE_URL}wp-content/uploads/2023/03/denmark.png")
    val FI = Language(code = "fi", name = "Suomalainen", imageUrl = "${BASE_URL}wp-content/uploads/2023/03/finland.png")
    val EL = Language(code = "el", name = "Ελληνικά", imageUrl = "${BASE_URL}wp-content/uploads/2023/03/greece.png")
    val HU = Language(code = "hu", name = "Magyar", imageUrl = "${BASE_URL}wp-content/uploads/2023/03/hungary.png")
    val HI = Language(code = "hi", name = "हिंदी", imageUrl = "${BASE_URL}wp-content/uploads/2023/03/india.png")
    val ID = Language(code = "id", name = "Bahasa Indonesia", imageUrl = "${BASE_URL}wp-content/uploads/2023/03/indonesia.png")
    val HE = Language(code = "he", name = "עִברִית", imageUrl = "${BASE_URL}wp-content/uploads/2023/03/israel.png")
    val IT = Language(code = "it", name = "Italiano", imageUrl = "${BASE_URL}wp-content/uploads/2023/03/italy.png")
    val JA = Language(code = "ja", name = "日本語", imageUrl = "${BASE_URL}wp-content/uploads/2023/03/japan.png")
    val NL = Language(code = "nl", name = "Nederlands", imageUrl = "${BASE_URL}wp-content/uploads/2023/03/netherlands.png")
    val NO = Language(code = "no", name = "Norsk", imageUrl = "${BASE_URL}wp-content/uploads/2023/03/norway.png")
    val PL = Language(code = "pl", name = "Polska", imageUrl = "${BASE_URL}wp-content/uploads/2023/03/poland.png")
    val PT = Language(code = "pt", name = "Português", imageUrl = "${BASE_URL}wp-content/uploads/2023/03/portugal.png")
    val RO = Language(code = "ro", name = "Română", imageUrl = "${BASE_URL}wp-content/uploads/2023/03/romania.png")
    val RU = Language(code = "ru", name = "Русский", imageUrl = "${BASE_URL}wp-content/uploads/2023/03/russia.png")
    val SR = Language(code = "sr", name = "Српски", imageUrl = "${BASE_URL}wp-content/uploads/2023/03/serbia.png")
    val SK = Language(code = "sk", name = "Slovenská", imageUrl = "${BASE_URL}wp-content/uploads/2023/03/slovakia.png")
    val KO = Language(code = "ko", name = "한국어", imageUrl = "${BASE_URL}wp-content/uploads/2023/03/south-korea.png")
    val SV = Language(code = "sv", name = "Svenska", imageUrl = "${BASE_URL}wp-content/uploads/2023/03/sweden.png")
    val TL = Language(code = "tl", name = "Tagalog", imageUrl = "${BASE_URL}wp-content/uploads/2023/03/philippines.png")
    val TH = Language(code = "th", name = "ภาษาไทย", imageUrl = "${BASE_URL}wp-content/uploads/2023/03/thailand.png")
    val TR = Language(code = "tr", name = "Türkçe", imageUrl = "${BASE_URL}wp-content/uploads/2023/03/turkey.png")
    val VI = Language(code = "vi", name = "Tiếng Việt", imageUrl = "${BASE_URL}wp-content/uploads/2023/03/vietnam.png")
    val UK = Language(code = "uk", name = "Українська мова", imageUrl = "${BASE_URL}wp-content/uploads/2023/03/ukraine.png")



    val all = listOf(AR,EN,ES,FR,DE,ID,IT,PL,PT,RO,RU,TH,TR,VI,UK)
    val availableUserLanguages = all
    val learningLanguage = DE

}