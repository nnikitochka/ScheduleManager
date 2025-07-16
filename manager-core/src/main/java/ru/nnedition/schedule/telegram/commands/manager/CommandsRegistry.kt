package ru.nnedition.schedule.telegram.commands.manager

import ru.nnedition.schedule.ScheduleManager
import ru.nnedition.schedule.module.ScheduleModule
import ru.nnedition.schedule.telegram.commands.StartCommand
import ru.nnedition.schedule.telegram.commands.Command
import ru.nnedition.schedule.telegram.commands.HelpCommand
import ru.nnedition.schedule.telegram.commands.InfoCommand
import ru.nnedition.schedule.telegram.commands.ScheduleCommand
import ru.nnedition.schedule.telegram.objects.chat.Chat
import ru.nnedition.schedule.telegram.objects.user.User

object CommandsRegistry {
    private val defaultCommands: MutableMap<String, Command> = HashMap()
    private val modulesCommands: MutableMap<ScheduleModule, Command> = HashMap()

    fun register(command: Command) {
        if (defaultCommands[command.label] != null) {
            ScheduleManager.logger.error("Команда ${command.label} уже зарегистрирована!")
            return
        }

        defaultCommands[command.label] = command
    }

    fun register(vararg commands: Command) = commands.forEach { register(it) }

    fun registerDefaults() {
        register(
            HelpCommand(),
            InfoCommand(),
            ScheduleCommand(),
            StartCommand(),
        )
    }

    fun execute(
        command: String, sender: User, chat: Chat,
        messageId: Int, module: ScheduleModule
    ) {
        val args = command.split(" ").toList()
        val label = args[0]

        val coincide = defaultCommands[label] ?: run {
            if (module.config.warnUnknownCommand)
                module.tgBot.sendMessage(chat, module.config.unknownCommandMessage)
            return
        }

        coincide.execute(args.drop(0), sender, chat, messageId, module)
    }
}