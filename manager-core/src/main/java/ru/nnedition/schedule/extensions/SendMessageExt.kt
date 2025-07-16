package ru.nnedition.schedule.extensions

import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import ru.nnedition.schedule.telegram.objects.ParseMode

fun SendMessage.enableParseMode(mode: ParseMode): SendMessage = when (mode) {
    ParseMode.NONE -> this
    else -> {
        this.parseMode = mode.toString()
        this
    }
}