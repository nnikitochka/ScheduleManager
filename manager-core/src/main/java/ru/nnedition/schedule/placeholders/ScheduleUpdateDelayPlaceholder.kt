package ru.nnedition.schedule.placeholders

import nn.edition.utils.format.time.TimeFormatter
import ru.nnedition.placeholders.Argument
import ru.nnedition.placeholders.Argument.Companion.findSingle
import ru.nnedition.placeholders.Placeholder
import ru.nnedition.placeholders.findSingle
import ru.nnedition.schedule.module.ScheduleModule

class ScheduleUpdateDelayPlaceholder : Placeholder(
    "schedule_update_delay",
    fun(text: String, args: Array<Argument<out Any>>): String {
        val module = args.findSingle<ScheduleModule>() ?: return text

        return TimeFormatter.formatMinutes(module.scheduleUpdateTime)
    }
)