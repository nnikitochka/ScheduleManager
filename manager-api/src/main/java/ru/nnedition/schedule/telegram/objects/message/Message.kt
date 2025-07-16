package ru.nnedition.schedule.telegram.objects.message

import ru.nnedition.schedule.telegram.objects.chat.Chat

open class Message(
    open val id: Int,
    open val chat: Chat,
    open val content: String,
)