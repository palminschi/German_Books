package com.palmdev.german_books.data.storage

import android.content.Context
import com.palmdev.german_books.data.model.ToDoTask
import org.threeten.bp.LocalDate
import javax.inject.Inject

private const val DATE_KEY = "DATE_KEY"

class ToDoTasksStorage @Inject constructor(context: Context) {

    private val prefs = context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)

    init {
        checkAndResetTasks()
    }

    // Method to check if today is a new day and reset the tasks
    private fun checkAndResetTasks() {
        val now = LocalDate.now()
        val lastTaskDate = LocalDate.parse(prefs.getString(DATE_KEY, now.toString()))
        val isBefore = lastTaskDate.isBefore(now)
        if (isBefore) {
            // Today is a new day, so reset the tasks
            resetTasks()
        }
        prefs.edit().putString(DATE_KEY, now.toString()).apply()
    }

    private fun resetTasks() {
        ToDoTask.TaskType.values().forEach {
            prefs.edit().putInt(it.name, 0).apply()
        }

        // Update the last task date to today
        prefs.edit()
            .putString(DATE_KEY, LocalDate.now().toString())
            .apply()
    }

    fun getAllTasks(): List<ToDoTask> {
        val task1Progress = prefs.getInt(ToDoTask.TaskType.READ_BOOK.name, 0)
        val task2Progress = prefs.getInt(ToDoTask.TaskType.SAVE_WORDS.name, 0)
        val task3Progress = prefs.getInt(ToDoTask.TaskType.WATCH_VIDEO.name, 0)
        return listOf(
            ToDoTask(ToDoTask.TaskType.READ_BOOK, task1Progress, 1, task1Progress >= 1),
            ToDoTask(ToDoTask.TaskType.SAVE_WORDS, task2Progress, 5, task2Progress >= 5),
            ToDoTask(ToDoTask.TaskType.WATCH_VIDEO, task3Progress, 1, task3Progress >= 1)
        )
    }

    fun getTaskByType(taskType: ToDoTask.TaskType): ToDoTask {
        val taskProgress = prefs.getInt(taskType.name, 0)
        return ToDoTask(taskType, taskProgress, 1, taskProgress >= 1)
    }

    fun saveTask(task: ToDoTask) {
        prefs.edit().putInt(task.taskType.name, task.progress).apply()
    }
}