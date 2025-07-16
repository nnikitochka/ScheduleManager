package ru.nnedition.schedule.extensions

import ru.nnedition.schedule.module.ScheduleModule
import ru.nnedition.schedule.telegram.TelegramBot
import ru.nnedition.schedule.telegram.objects.chat.PrivateChat
import ru.nnedition.schedule.telegram.objects.user.User
import org.telegram.telegrambots.meta.api.objects.User as TUser


fun User.getOrCreate(module: ScheduleModule, tUser: TUser): User = getOrCreate(module.tgBot, tUser)

fun User.getOrCreate(bot: TelegramBot, tUser: TUser): User {
    return bot.users.getOrPut(tUser.id) {
        User(
            tUser.id, tUser.firstName, tUser.userName,
            tUser.languageCode, tUser.isBot, PrivateChat(id)
        )
    }
}

fun TUser.convert() = User(
    this.id, this.firstName, this.userName,
    this.languageCode, this.isBot, PrivateChat(id)
)