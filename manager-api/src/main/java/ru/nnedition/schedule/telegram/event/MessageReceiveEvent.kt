package ru.nnedition.schedule.telegram.event

import ru.nnedition.schedule.telegram.event.impl.Event
import ru.nnedition.schedule.telegram.objects.chat.Chat
import ru.nnedition.schedule.telegram.objects.message.Message
import ru.nnedition.schedule.telegram.objects.user.User

class MessageReceiveEvent(
    updateId: Int,
    val user: User,
    val message: Message,
    val date: Int,
) : Event(updateId)