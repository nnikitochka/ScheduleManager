package ru.nnedition.schedule.placeholders

import ru.nnedition.placeholders.Argument
import ru.nnedition.placeholders.findSingle
import ru.nnedition.placeholders.Placeholder
import ru.nnedition.schedule.module.ScheduleModule
import ru.nnedition.schedule.utils.parser.parsePlaceholders

class BotInfoPlaceholder : Placeholder(
    "bot_info",
    fun(text: String, args: Array<Argument<out Any>>): String {
        val module = args.findSingle<ScheduleModule>() ?: return text

        return module.config.botInfo.joinToString("\n").parsePlaceholders(module)
    }
)