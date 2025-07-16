package ru.nnedition.schedule.extensions

import ru.nnedition.schedule.telegram.objects.chat.Chat
import ru.nnedition.schedule.telegram.objects.chat.PrivateChat
import org.telegram.telegrambots.meta.api.objects.chat.Chat as TChat

fun TChat.convert(): Chat = when {
    this.isGroupChat -> Chat(this.id, Chat.Type.GROUP)
    this.isChannelChat -> Chat(this.id, Chat.Type.CHANNEL)
    this.isSuperGroupChat -> Chat(this.id, Chat.Type.SUPER_GROUP)
    else -> PrivateChat(this.id)
}