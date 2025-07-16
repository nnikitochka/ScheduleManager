package ru.nnedition.schedule.telegram.objects.chat

class PrivateChat(
    userId: Long,
) : Chat(
    userId,
    Type.PRIVATE
)