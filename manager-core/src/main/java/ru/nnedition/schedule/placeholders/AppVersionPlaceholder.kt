package ru.nnedition.schedule.placeholders

import ru.nnedition.placeholders.Placeholder
import ru.nnedition.schedule.ProjectInfo

class AppVersionPlaceholder : Placeholder(
    "app_version",
    { text, args ->
        ProjectInfo.VERSION
    }
)