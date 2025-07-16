package ru.nnedition.placeholders.manager

import ru.nnedition.placeholders.Argument
import ru.nnedition.schedule.utils.buildString

class PlaceholderParser {
    fun parse(text: String, args: Array<Argument<out Any>>) = buildString(text) {
        PlaceholderManager.getPlaceholders().forEach { (key, placeholder) ->
            var index = indexOf(key)
            while (index != -1) {
                replace(index, index + key.length, placeholder.process(key, args))
                index = indexOf(key, index + 1)
            }
        }
    }
}
