package ru.nnedition.schedule.telegram.objects.user

import ru.nnedition.schedule.module.ScheduleModule
import ru.nnedition.schedule.telegram.TelegramBot
import ru.nnedition.schedule.telegram.objects.chat.Chat
import ru.nnedition.schedule.telegram.objects.ParseMode

open class User(
    val id: Long,
    val firstName: String,
    val userName: String,
    val languageCode: String,
    val isBot: Boolean,
    val chat: Chat,
) {
    fun sendMessage(module: ScheduleModule, message: String, parseMode: ParseMode = ParseMode.MARKDOWNV2) =
        sendMessage(module.tgBot, message, parseMode)

    fun sendMessage(tgBot: TelegramBot, message: String, parseMode: ParseMode = ParseMode.MARKDOWNV2) {
        tgBot.sendMessage(chat, message, parseMode)
    }
}