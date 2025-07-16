package ru.nnedition.schedule.telegram.objects.chat

open class Chat(
    val id: Long,
    val type: Type,
) {
    enum class Type {
        GROUP,
        CHANNEL,
        PRIVATE,
        SUPER_GROUP,
    }
}