package ru.nnedition.schedule.placeholders

import ru.nnedition.placeholders.Argument
import ru.nnedition.placeholders.Placeholder
import ru.nnedition.placeholders.findSingle
import ru.nnedition.schedule.module.ScheduleModule

class UsersCountPlaceholder : Placeholder(
    "users_count",
    fun(text: String, args: Array<Argument<out Any>>): String {
        val module = args.findSingle<ScheduleModule>() ?: return text
        return module.tgBot.users.size.toString()
    }
)