package ru.nnedition.schedule.extensions

import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod
import org.telegram.telegrambots.meta.generics.TelegramClient
import java.io.Serializable

fun <T : Serializable, M : BotApiMethod<T>>TelegramClient.tryExecute(method: M) = try {
    this.execute(method)
} catch (e: Exception) {
    e.printStackTrace()
    null
}

fun <T : Serializable, M : BotApiMethod<T>>M.tryExecute(client: TelegramClient) = try {
    client.execute(this)
} catch (e: Exception) {
    e.printStackTrace()
    null
}
