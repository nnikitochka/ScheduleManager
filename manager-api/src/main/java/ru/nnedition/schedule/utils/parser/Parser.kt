package ru.nnedition.schedule.utils.parser

import com.vdurmont.emoji.EmojiParser
import ru.nnedition.placeholders.Argument
import ru.nnedition.placeholders.manager.PlaceholderParser
import ru.nnedition.schedule.module.ScheduleModule

object Parser {
    private val placeholdersParser = PlaceholderParser()

    /**
     * Насрано по взрослому...
     * @TODO написать адекватную систему парсинга маркдауна
     */
    fun markdown(text: String): String {
        return text.replace(".", "\\.")
            .replace("-", "\\-")
            .replace("!", "\\!")
            .replace(">", "\\>")
            .replace("{", "\\{")
            .replace("}", "\\}")
            .replace("(", "\\(")
            .replace(")", "\\)")
            .replace("_", "\\_")
    }

    fun placeholders(module: ScheduleModule, text: String) =
        placeholdersParser.parse(text, arrayOf(Argument(module)))


    fun String.parseAll(module: ScheduleModule) = this.parsePlaceholders(module).parseMarkdown().parseEmoji()

    fun String.parsePlaceholders(module: ScheduleModule) = placeholders(module, this)

    fun String.parseMarkdown() = markdown(this)

    fun String.parseEmoji() = EmojiParser.parseToUnicode(this)!!
}