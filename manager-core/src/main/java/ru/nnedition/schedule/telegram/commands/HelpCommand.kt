package ru.nnedition.schedule.telegram.commands

import ru.nnedition.schedule.module.ScheduleModule
import ru.nnedition.schedule.telegram.objects.chat.Chat
import ru.nnedition.schedule.telegram.objects.ParseMode
import ru.nnedition.schedule.telegram.objects.user.User

class HelpCommand : Command("help", "помощь", null) {
    override fun execute(
        args: List<String>,
        sender: User,
        chat: Chat,
        messageId: Int,
        module: ScheduleModule
    ) {
        module.tgBot.sendMessage(
            sender.chat,
            module.config.helpCommandText.joinToString("\n"),
            ParseMode.MARKDOWNV2
        )
    }
}