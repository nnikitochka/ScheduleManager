package ru.nnedition.schedule

import nn.edition.yalogger.LoggerFactory
import ru.nnedition.schedule.module.ScheduleModule
import ru.nnedition.schedule.module.ModulesLoader
import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication
import ru.nnedition.placeholders.manager.PlaceholderManager
import ru.nnedition.schedule.placeholders.AppVersionPlaceholder
import ru.nnedition.schedule.placeholders.BotInfoPlaceholder
import ru.nnedition.schedule.placeholders.BuildingsPlaceholder
import ru.nnedition.schedule.placeholders.ModuleVersionPlaceholder
import ru.nnedition.schedule.placeholders.ScheduleUpdateDelayPlaceholder
import ru.nnedition.schedule.placeholders.UsersCountPlaceholder
import ru.nnedition.schedule.telegram.commands.manager.CommandsRegistry
import java.util.Properties
import kotlin.jvm.javaClass

object ScheduleManager {
    val logger = LoggerFactory.getLogger(this.javaClass.name)

    private var startTime = System.currentTimeMillis()
    fun getUptime() = System.currentTimeMillis() - startTime

    private val modulesLoader = ModulesLoader()
    val modules = mutableListOf<ScheduleModule>()

    val botsApp = TelegramBotsLongPollingApplication()

    val config = ManagerConfig()

    @JvmStatic
    fun main(args: Array<String>) {
        config.load()

        registerPlaceholders()

        CommandsRegistry.registerDefaults()

        modulesLoader.loadModules()

        Runtime.getRuntime().addShutdownHook(Thread { onShutdown() })
    }

    fun onShutdown() {
        modulesLoader.unloadModules()
    }

    fun registerPlaceholders() {
        PlaceholderManager.register(
            AppVersionPlaceholder(),
            BotInfoPlaceholder(),
            BuildingsPlaceholder(),
            ModuleVersionPlaceholder(),
            ScheduleUpdateDelayPlaceholder(),
            UsersCountPlaceholder(),
        )
    }
}