package ru.nnedition.placeholders.manager

import nn.edition.yalogger.logger
import ru.nnedition.placeholders.Placeholder

object PlaceholderManager {
    private val logger = logger(this::class)

    private val placeholders = mutableMapOf<String, Placeholder>()

    fun getPlaceholders() = placeholders.toMap()

    fun register(placeholder: Placeholder) {
        val content = "%${placeholder.content}%"

        if (placeholders[content] != null) {
            logger.error("Ошибка при регистрации плейсхолдера $content. Плейсхолдер с таким идентификатором уже существует!")
            return
        }

        placeholders[content] = placeholder
    }

    fun register(vararg placeholders: Placeholder) = placeholders.forEach { register(it) }
}