package com.devkproject.usingstate.todo

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun TodoScreen(
    items: List<TodoItem>,
    onAddItem: (TodoItem) -> Unit,
    onRemoveItem: (TodoItem) -> Unit
) {
    Column {
        // add TodoItemInputBackground and TodoItem at the top of TodoScreen
        TodoItemInputBackground(elevate = true, modifier = Modifier.fillMaxWidth()) {
            TodoItemEntryInput(onItemComplete = onAddItem)
        }
    }
}

//스테이트풀(Stateful) 컴포저블에서 스테이트리스(Stateless) 컴포저블을 추출하면 다양한 위치에서 UI를 더 쉽게 재사용할 수 있습니다.
//상태가 있는 부분
@Composable
fun TodoItemEntryInput(onItemComplete: (TodoItem) -> Unit) {
    val (text, setText) = remember { mutableStateOf("") }
    val (icon, setIcon) = remember { mutableStateOf(TodoIcon.Default) }
    val iconsVisible = text.isNotBlank()
    val submit = {
        onItemComplete(TodoItem(text, icon))
        setIcon(TodoIcon.Default)
        setText("")
    }
    TodoItemInput(
        text = text,
        onTextChange  = setText,
        icon = icon,
        onIconChange  = setIcon,
        submit = submit,
        iconsVisible = iconsVisible
    )
}

//스테이트리스(Stateless) 부분
@Composable
private fun TodoItemInput(
    text: String,
    onTextChange: (String) -> Unit,
    icon: TodoIcon,
    onIconChange : (TodoIcon) -> Unit,
    submit: () -> Unit,
    iconsVisible: Boolean
) {
    Column {
        Row(
            Modifier
                .padding(horizontal = 16.dp)
                .padding(top = 16.dp)
        ) {
            TodoInputText(
                text,
                onTextChange,
                Modifier
                    .weight(1f)
                    .padding(end = 8.dp),
                submit
            )
            TodoEditButton(
                onClick = submit,
                text = "Add",
                modifier = Modifier.align(Alignment.CenterVertically),
                enabled = text.isNotBlank()
            )
        }
        if (iconsVisible) {
            AnimatedIconRow(icon, onIconChange, Modifier.padding(top = 8.dp))
        } else {
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun TodoInputTextField(modifier: Modifier) {
    val (text, setText) = remember { mutableStateOf("") }
    TodoInputText(text, setText, modifier)
}

@Preview
@Composable
fun PreviewTodoItemInput() = TodoItemEntryInput(onItemComplete = { })