package ru.nnedition.schedule.telegram

import ru.nnedition.schedule.extensions.parse
import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer
import org.telegram.telegrambots.meta.api.objects.Update
import ru.nnedition.schedule.extensions.convert
import ru.nnedition.schedule.telegram.event.CommandReceiveEvent
import ru.nnedition.schedule.telegram.event.MessageReceiveEvent
import ru.nnedition.schedule.telegram.objects.message.Message
import java.util.concurrent.CompletableFuture

interface UpdateConsumer : LongPollingUpdateConsumer {
    override fun consume(updates: List<Update>) {
        updates.forEach { update ->
            CompletableFuture.runAsync {
                if (update.hasMessage() && update.message.hasText()) {
                    val message = update.message
                    val chat = message.chat
                    
                    if (message.isCommand) {
                        val received = CommandReceiveEvent(
                            update.updateId,
                            message.from.convert(),
                            chat.convert(),
                            message.messageId,
                            message.date,
                            message.text,
                        )

                        onReceiveCommand(received)
                    }
                    else {
                        val received = MessageReceiveEvent(
                            update.updateId,
                            message.from.convert(),
                            Message(
                                message.messageId,
                                chat.convert(),
                                message.text
                            ),
                            message.date
                        )
                        onReceiveMessage(received)
                    }

                    update.parse()
                    return@runAsync
                }
                else if (update.hasCallbackQuery()) {
                    update.parse()
                    onReceiveCallbackQuery(update)
                    return@runAsync
                }

                val myChatMember = update.myChatMember
                val newMember = myChatMember.newChatMember
                val oldMember = myChatMember.oldChatMember
                if (newMember.status == "kicked" && oldMember.status == "member") {
                    update.parse()
                    onUserBlockedBot(update)
                    return@runAsync
                }
                else if (newMember.status == "member" && oldMember.status == "kicked") {
                    update.parse()
                    onUserUnblockedBot(update)
                    return@runAsync
                }

                update.parse()
            }
        }
    }

    fun onReceiveCommand(event: CommandReceiveEvent)

    fun onReceiveMessage(event: MessageReceiveEvent)

    fun onReceiveCallbackQuery(update: Update)

    fun onUserBlockedBot(update: Update)

    fun onUserUnblockedBot(update: Update)
}
