package ru.nnedition.schedule.telegram.objects

enum class ParseMode(
    private val value: String
) {
    NONE("none"),
    MARKDOWN("Markdown"),
    MARKDOWNV2("MarkdownV2"),
    HTML("html");

    override fun toString() = value
}