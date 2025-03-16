package com.example.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class TasksViewModel(val dao: TaskDao) : ViewModel() {
    var newTaskName = ""
    var taskExecutor = ""

    private val tasks = dao.getAll()
//    начиная с Lifecycle_version >= 2.6.0,
//    вместо val tasksString = Transformations.map(tasks) {
    val tasksString = tasks.map {
        tasks -> formatTasks(tasks)
    }

    fun addTask() {
        viewModelScope.launch {
            val task = Task()
            task.taskName = newTaskName
            task.taskExecutor = taskExecutor
            dao.insert(task)
        }
    }

    fun formatTasks(tasks: List<Task>): String {
        return tasks.fold("") {
            str, item -> str + '\n' + formatTask(item)
        }
    }

    fun formatTask(task: Task): String {
        var str = "ID: ${task.taskId}"
        str += '\n' + "Executor Task: ${task.taskExecutor}" + '\n'
        str += '\n' + "Name: ${task.taskName}"
        str += '\n' + "Complete: ${task.taskDone}" + '\n'

        return str
    }
}