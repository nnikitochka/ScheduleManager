package ru.nnedition.schedule.placeholders

import ru.nnedition.placeholders.Argument
import ru.nnedition.placeholders.Placeholder
import ru.nnedition.placeholders.findSingle
import ru.nnedition.schedule.module.ScheduleModule

class BuildingsPlaceholder : Placeholder(
    "buildings",
    fun(text: String, args: Array<Argument<out Any>>) = buildString {
        val module = args.findSingle<ScheduleModule>() ?: return text

        module.schedule.buildings.forEach { building ->
            appendLine(building.format(module))
        }
    }.trimEnd()
)