package com.palmdev.german_books.domain.usecases.words

import com.palmdev.german_books.data.model.ToDoTask
import com.palmdev.german_books.data.storage.ToDoTasksStorage
import com.palmdev.german_books.domain.model.Word
import com.palmdev.german_books.domain.repository.SavedWordsRepository
import java.util.Calendar
import javax.inject.Inject

class SaveNewWordsUseCase @Inject constructor(
    private val savedWordsRepository: SavedWordsRepository,
    private val toDoTasksStorage: ToDoTasksStorage
) {

    suspend operator fun invoke(words: List<Word>) {
        val currentTime = Calendar.getInstance().timeInMillis
        words.forEach { word ->
            savedWordsRepository.saveWord(
                word.toSavedWord(nextRepetitionTime = currentTime)
            )
        }
        val task = toDoTasksStorage.getTaskByType(taskType = ToDoTask.TaskType.SAVE_WORDS)
        toDoTasksStorage.saveTask(
            task.copy(
                progress = task.progress + words.size
            )
        )
    }
}
