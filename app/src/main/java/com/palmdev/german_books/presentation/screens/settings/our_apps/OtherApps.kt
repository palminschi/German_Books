package com.palmdev.german_books.presentation.screens.settings.our_apps

data class AppInfo(
    val name: String,
    val imageUrl: String,
    val packageName: String
)

class OtherApps {
    val ENGLISH_APP = AppInfo(
        name = "iSpeak English",
        imageUrl = "https://ispeak.site/wp-content/uploads/2023/04/app_icon_us.jpg",
        packageName = "ispeak.english.conversations.words.phrases.learn.american.english"
    )
    val GERMAN_APP = AppInfo(
        name = "iSpeak Deutsch",
        imageUrl = "https://ispeak.site/wp-content/uploads/2023/04/app_icon_512.png",
        packageName = "ispeak.german.learning.app.words.german.conversations.dialogues\n"
    )
    val FLOW_ENGLISH_APP = AppInfo(
        name = "English FLOW",
        imageUrl = "https://ispeak.site/wp-content/uploads/2023/08/app_icon_express_new.jpg",
        packageName = "com.palmdev.expressenglish"
    )
    val SPANISH_APP = AppInfo(
        name = "iSpeak Español",
        imageUrl = "https://ispeak.site/wp-content/uploads/2023/04/app_icon-5.jpg",
        packageName = "ispeak.spanish.words.conversations.phrases.learning"
    )
    val FRENCH_APP = AppInfo(
        name = "iSpeak Français",
        imageUrl = "https://ispeak.site/wp-content/uploads/2023/04/app_icon.jpg",
        packageName = "ispeak.french.words.conversations.learn.french"
    )
    val ITALIAN_APP = AppInfo(
        name = "iSpeak Italiano",
        imageUrl = "https://ispeak.site/wp-content/uploads/2023/04/app_icon-2.jpg",
        packageName = "ispeak.learn.italian.free.app"
    )
    val KOREAN_APP = AppInfo(
        name = "iSpeak Korean",
        imageUrl = "https://ispeak.site/wp-content/uploads/2023/04/app_icon-4.jpg",
        packageName = "ispeak.how.to.learn.korean.alphabet.hangul.words"
    )
    val JAPANESE_APP = AppInfo(
        name = "iSpeak Japanese",
        imageUrl = "https://ispeak.site/wp-content/uploads/2023/07/app_icon_v1.jpg",
        packageName = "ispeak.japanese.letters.writing.learn.hirigana.japan.words.characters"
    )
    val CHINESE_APP = AppInfo(
        name = "iSpeak Chinese",
        imageUrl = "https://ispeak.site/wp-content/uploads/2023/04/app_icon-1.jpg",
        packageName = "belajar.mandarin.pemula"
    )

    val all = listOf(
        ENGLISH_APP,
        FLOW_ENGLISH_APP,
        GERMAN_APP,
        SPANISH_APP,
        FRENCH_APP,
        ITALIAN_APP,
        JAPANESE_APP,
        KOREAN_APP,
        CHINESE_APP
    )
}