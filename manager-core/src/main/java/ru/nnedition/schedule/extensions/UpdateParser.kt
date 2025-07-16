package ru.nnedition.schedule.extensions

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import org.telegram.telegrambots.meta.api.objects.Update

object UpdateParser {
    val gson = Gson()

    fun parse(update: Update): String {
        val text = gson.toJson(update)
        println(text)
        return formatJson(text)
    }

    fun formatJson(input: String) = GsonBuilder()
        .setPrettyPrinting()
        .disableHtmlEscaping()
        .serializeSpecialFloatingPointValues()
        .create()
        .toJson(JsonParser.parseString(input))
}

fun Update.parse() {
    println(UpdateParser.parse(this))
}