package ru.nnedition.schedule.module

import ru.nnedition.schedule.module.config.DefaultSecretConfig
import ru.nnedition.schedule.telegram.TelegramBot
import nn.edition.yalogger.LoggerFactory
import ru.nnedition.schedule.Schedule
import ru.nnedition.schedule.module.config.DefaultConfig
import java.io.File

abstract class ScheduleModule {
    val logger = LoggerFactory.getLogger(this.javaClass.name)

    lateinit var config: DefaultConfig
        protected set

    lateinit var secret: DefaultSecretConfig
        protected set

    fun getToken() = secret.botToken

    lateinit var path: String
        private set

    lateinit var name: String
        private set

    lateinit var version: String
        private set

    lateinit var dataFolder: File
        private set

    fun init(
        path: String,
        name: String,
        version: String,
        dataFolder: File
    ) {
        this.path = path
        this.name = name
        this.version = version
        this.dataFolder = dataFolder

        this.secret = DefaultSecretConfig(this).load() as DefaultSecretConfig
        this.config = DefaultConfig(this).load() as DefaultConfig

        this.schedule = Schedule(this)
    }

    var isLoaded = false
        private set

    lateinit var tgBot: TelegramBot
        private set

    lateinit var schedule: Schedule
        private set

    fun enable(tgBot: TelegramBot) {
        if (this.isLoaded) {
            this.logger.warn("Пресечена попытка повторной загрузки модуля!")
            return
        }

        if (this.javaClass.classLoader !is ModuleClassLoader) {
            return
        }

        try {
            this.tgBot = tgBot
            this.tgBot.register(this)

            onEnable()

            this.isLoaded = true
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    abstract val scheduleUpdateTime: Long

    open fun onLoad() {
    }

    open fun onEnable() {
    }

    fun disable() {
        try {
            onDisable()
            tgBot.unregister()
            isLoaded = false
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    open fun onDisable() {
    }
}