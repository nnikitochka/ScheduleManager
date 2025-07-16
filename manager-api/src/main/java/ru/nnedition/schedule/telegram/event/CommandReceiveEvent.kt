package ru.nnedition.schedule.telegram.event

import ru.nnedition.schedule.telegram.event.impl.Event
import ru.nnedition.schedule.telegram.objects.chat.Chat
import ru.nnedition.schedule.telegram.objects.user.User

class CommandReceiveEvent(
    updateId: Int,
    val user: User,
    val chat: Chat,
    val messageId: Int,
    val date: Int,
    val command: String,
) : Event(updateId)