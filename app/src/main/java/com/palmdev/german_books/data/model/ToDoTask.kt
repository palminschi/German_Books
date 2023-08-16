package com.palmdev.german_books.data.model

data class ToDoTask(
    val taskType: TaskType,
    val progress: Int,
    val max: Int,
    val isCompleted: Boolean
) {
    enum class TaskType {
        READ_BOOK,
        SAVE_WORDS,
        WATCH_VIDEO
    }
}