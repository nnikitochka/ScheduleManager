package ru.nnedition.schedule.telegram.commands

import ru.nnedition.schedule.module.ScheduleModule
import ru.nnedition.schedule.telegram.objects.chat.Chat
import ru.nnedition.schedule.telegram.objects.user.User

class ScheduleCommand : Command("schedule", "расписание", null) {
    override fun execute(
        args: List<String>,
        sender: User,
        chat: Chat,
        messageId: Int,
        module: ScheduleModule
    ) {

    }
}