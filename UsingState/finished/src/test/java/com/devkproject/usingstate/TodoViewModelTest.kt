package com.devkproject.usingstate

import com.devkproject.usingstate.todo.TodoViewModel
import com.devkproject.usingstate.util.generateRandomTodoItem
import com.google.common.truth.Truth.assertThat
import org.junit.Test


class TodoViewModelTest {

    @Test
    fun whenRemovingItem_updatesList() {
        //before
        val viewModel = TodoViewModel()
        val item1 = generateRandomTodoItem()
        val item2 = generateRandomTodoItem()
        viewModel.addItem(item1)
        viewModel.addItem(item2)

        //during
        viewModel.removeItem(item1)

        //after
        assertThat(viewModel.todoItems).isEqualTo(listOf(item2))
    }
}