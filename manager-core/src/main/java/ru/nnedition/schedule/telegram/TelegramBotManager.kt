package ru.nnedition.schedule.telegram

import ru.nnedition.schedule.extensions.tryExecute
import ru.nnedition.schedule.telegram.commands.Command
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient
import org.telegram.telegrambots.meta.api.methods.commands.DeleteMyCommands
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand
import ru.nnedition.schedule.ScheduleManager
import ru.nnedition.schedule.extensions.enableParseMode
import ru.nnedition.schedule.module.ScheduleModule
import ru.nnedition.schedule.telegram.commands.manager.CommandsRegistry
import ru.nnedition.schedule.telegram.event.CommandReceiveEvent
import ru.nnedition.schedule.telegram.event.MessageReceiveEvent
import ru.nnedition.schedule.telegram.objects.chat.Chat
import ru.nnedition.schedule.telegram.objects.ParseMode
import ru.nnedition.schedule.utils.parser.parseAll

class TelegramBotManager(token: String) : UpdateConsumer, TelegramBot(token) {
    var client = OkHttpTelegramClient(this.token)
        private set

    override fun sendMessage(chat: Chat, content: String, parseMode: ParseMode) {
        SendMessage.builder()
            .text(content.parseAll(module))
            .chatId(chat.id)
            .build()
            .enableParseMode(parseMode)
            .tryExecute(client)
    }


    override fun register(module: ScheduleModule) {
        this.module = module
        ScheduleManager.botsApp.registerBot(this.token, this)
    }

    override fun unregister() {
        ScheduleManager.botsApp.unregisterBot(this.token)
    }


    override fun onReceiveCommand(event: CommandReceiveEvent) {
        CommandsRegistry.execute(
            event.command.substring(1),
            event.user,
            event.chat,
            event.messageId,
            this.module,
        )
    }

    override fun onReceiveMessage(event: MessageReceiveEvent) {
        sendMessage(event.message.chat, event.message.content)
    }

    override fun onReceiveCallbackQuery(update: Update) {
    }

    override fun onUserBlockedBot(update: Update) {
    }

    override fun onUserUnblockedBot(update: Update) {
    }


    fun clearCommands() = DeleteMyCommands.builder().build().tryExecute(client) == true
    fun setCommands(commands: List<Command>) {
        commands.filter { it.scope != null }.groupBy { it.scope }.forEach { (scope, commands) ->
            SetMyCommands.builder()
                .commands(commands.map { BotCommand(it.label, it.description) })
                .scope { scope!!.toString() }
                .build().tryExecute(client)
        }
    }

    fun refreshCommands(commands: List<Command>) {
        clearCommands()
        setCommands(commands)
    }
}