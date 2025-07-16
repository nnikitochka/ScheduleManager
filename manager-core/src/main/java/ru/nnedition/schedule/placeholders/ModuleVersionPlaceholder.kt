package ru.nnedition.schedule.placeholders

import ru.nnedition.placeholders.Placeholder
import ru.nnedition.placeholders.findSingle
import ru.nnedition.schedule.module.ScheduleModule

class ModuleVersionPlaceholder : Placeholder(
    "module_version",
    { text, args ->
        args.findSingle<ScheduleModule>()?.version ?: text
    }
)