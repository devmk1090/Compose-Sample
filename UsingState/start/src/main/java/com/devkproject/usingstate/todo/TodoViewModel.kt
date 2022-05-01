package com.devkproject.usingstate.todo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TodoViewModel : ViewModel() {

    //state: todoItems
    private var _todoItems = MutableLiveData(listOf<TodoItem>())
    val todoItems: LiveData<List<TodoItem>> = _todoItems

    //event: addItem
    fun addItem(item: TodoItem) {
        _todoItems.value = _todoItems.value!! + listOf(item)
    }

    //event: removeItem
    fun removeItem(item: TodoItem) {
        _todoItems.value = _todoItems.value!!.toMutableList().also {
            it.remove(item)
        }
    }
}