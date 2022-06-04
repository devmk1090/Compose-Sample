package com.devkproject.jetchat.conversation

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.mutableStateListOf
import com.devkproject.jetchat.R

class ConversationUiState (
    val channelName: String,
    val channelMembers: Int,
    initialMessage: List<Message>
) {
    private val _messages: MutableList<Message> =
        mutableStateListOf(*initialMessage.toTypedArray())
    val messages: List<Message> = _messages

    fun addMessage(msg: Message) {
        _messages.add(0, msg)
    }
}

@Immutable
data class Message(
    val author: String,
    val content: String,
    val timestamp: String,
    val image: Int? = null,
    val authorImage: Int = if (author == "me") R.drawable.sticker else R.drawable.someone_else
)