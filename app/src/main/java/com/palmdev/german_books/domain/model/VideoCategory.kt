package com.palmdev.german_books.domain.model

enum class VideoCategory(val id: Int) : java.io.Serializable {
    NO_CATEGORY(0),
    TOP_RATED(1),
    POPULAR(2),
    LEARNING(3),
    TRAVEL(4),
    FOOD(5),
    SPORT(6),
    CULTURAL(7),
    NATURE(9),
    FASHION(10)
}